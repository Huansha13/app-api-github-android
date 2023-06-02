package xyz.android.api_github;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.android.api_github.model.Perfil;
import xyz.android.api_github.service.GitHubApiService;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText usernameEditText;
    private MaterialButton buscarPerfilButton, repositorioViewButton;
    private ImageView avatarImageView;
    private TextView nameTextView, biografiaTextView;

    private ConstraintLayout contentPerfil;
    private Perfil perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = findViewById(R.id.editTextUsername);
        buscarPerfilButton = findViewById(R.id.buttonGetProfile);
        avatarImageView = findViewById(R.id.avatar_image);
        nameTextView = findViewById(R.id.username_text);
        biografiaTextView = findViewById(R.id.bio_text);
        contentPerfil = findViewById(R.id.profile_content);
        repositorioViewButton = findViewById(R.id.buttonRepositorio);

        buscarPerfilButton.setOnClickListener(v -> {
            ocultarTeclado();
            String username = Objects.requireNonNull(usernameEditText.getText()).toString().trim();
            if (!username.isEmpty()) {
                obtenerPerfil(username);
            } else {
                Toast.makeText(MainActivity.this, "Ingresa un nombre de usuario", Toast.LENGTH_SHORT).show();
            }

        });

        repositorioViewButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, RepositorioActivity.class);
            intent.putExtra("username", perfil.getUsername());
            startActivity(intent);
        });
    }

    private void obtenerPerfil(String username) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubApiService apiService = retrofit.create(GitHubApiService.class);
        Call<Perfil> callPerfil = apiService.getProfile(username);
        callPerfil.enqueue(new Callback<Perfil>() {
            @Override
            public void onResponse(Call<Perfil> call, Response<Perfil> response) {
                if (!response.isSuccessful())  {
                    Toast.makeText(MainActivity.this, "Error en la respuesta", Toast.LENGTH_SHORT).show();
                    return;
                }

                perfil = response.body();
                if (perfil == null) {
                    Toast.makeText(MainActivity.this, "No se encontró ningún perfil", Toast.LENGTH_SHORT).show();
                    return;
                }

                nameTextView.setText(perfil.getNameFull());
                biografiaTextView.setText(perfil.getBio());

                Picasso.get()
                        .load(perfil.getAvatarUrl())
                        .placeholder(R.drawable.fondo_img)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(avatarImageView);

                contentPerfil.setVisibility(View.VISIBLE);
                usernameEditText.clearFocus();
            }

            @Override
            public void onFailure(Call<Perfil> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error en la llamada de API", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void ocultarTeclado(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
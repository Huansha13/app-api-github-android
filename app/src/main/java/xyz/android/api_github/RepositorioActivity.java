package xyz.android.api_github;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.android.api_github.adapter.ReposAdapter;
import xyz.android.api_github.model.Repository;
import xyz.android.api_github.service.GitHubApiService;

public class RepositorioActivity extends AppCompatActivity {

    private RecyclerView recyclerViewRepos;
    private ReposAdapter adapter;
    private List<Repository> repos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositorio);

        recyclerViewRepos = findViewById(R.id.recyclerViewRepos);
        loadRepositorios();

        String username = getIntent().getStringExtra("username");
        getPublicRepos(username);

    }

    private void getPublicRepos(String username) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubApiService apiService = retrofit.create(GitHubApiService.class);

        Call<List<Repository>> call = apiService.getPublicRepos(username);
        call.enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(RepositorioActivity.this, "Error en la respuesta", Toast.LENGTH_SHORT).show();
                    return;
                }

                repos = response.body();

                if (repos == null || repos.isEmpty()) {
                    Toast.makeText(RepositorioActivity.this, "No se encontraron repositorios", Toast.LENGTH_SHORT).show();
                    return;
                }

                loadRepositorios();
            }

            @Override
            public void onFailure(Call<List<Repository>> call, Throwable t) {
                Toast.makeText(RepositorioActivity.this, "Error en la llamada de API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadRepositorios() {
        recyclerViewRepos.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRepos.setAdapter(new ReposAdapter(this, repos));
    }
}
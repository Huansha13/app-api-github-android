package xyz.android.api_github.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import xyz.android.api_github.R;
import xyz.android.api_github.RepositorioActivity;
import xyz.android.api_github.model.Repository;

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.RepoViewHolder> {
    private List<Repository> repoList;
    private Context context;

    public ReposAdapter(Context context, List<Repository> repos) {
        this.repoList = repos;
        this.context = context;
    }

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repo, parent, false);
        return new RepoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        if (repoList.size() == 0) {
            return;
        }

        Repository repository = repoList.get(position);
        System.out.println(repository);

        holder.repoNameTextView.setText(repository.getName());
        holder.repoDescriptionTextView.setText(repository.getDescription());

        holder.viewRepoButton.setOnClickListener(v -> {
            String url = repository.getHtmlUrl();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return repoList.size();
    }


    public class RepoViewHolder extends RecyclerView.ViewHolder {
        public TextView repoNameTextView;
        public TextView repoDescriptionTextView;
        public Button viewRepoButton;
        public RepoViewHolder(@NonNull View itemView) {
            super(itemView);
            repoNameTextView = itemView.findViewById(R.id.repoNameText);
            repoDescriptionTextView = itemView.findViewById(R.id.repoDescriptionText);
            viewRepoButton = itemView.findViewById(R.id.viewRepoButton);
        }
    }

}

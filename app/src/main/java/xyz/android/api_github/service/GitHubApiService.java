package xyz.android.api_github.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import xyz.android.api_github.model.Perfil;
import xyz.android.api_github.model.Repository;

public interface GitHubApiService {

    @GET("users/{username}")
    Call<Perfil> getProfile(@Path("username") String username);

    @GET("users/{username}/repos")
    Call<List<Repository>> getPublicRepos(@Path("username") String username);

}

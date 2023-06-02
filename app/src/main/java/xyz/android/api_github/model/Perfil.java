package xyz.android.api_github.model;

import com.google.gson.annotations.SerializedName;

public class Perfil {
    @SerializedName("login")
    private String username;

    @SerializedName("name")
    private String nameFull;

    @SerializedName("avatar_url")
    private String avatarUrl;

    @SerializedName("bio")
    private String bio;

    public String getUsername() {
        return username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getBio() {
        return bio;
    }

    public String getNameFull() {
        return nameFull;
    }

    @Override
    public String toString() {
        return "Perfil{" +
                "username='" + username + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", bio='" + bio + '\'' +
                '}';
    }
}

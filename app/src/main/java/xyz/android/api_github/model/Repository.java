package xyz.android.api_github.model;

import com.google.gson.annotations.SerializedName;

public class Repository {

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("html_url")
    private String htmlUrl;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    @Override
    public String toString() {
        return "Repository{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", htmlUrl='" + htmlUrl + '\'' +
                '}';
    }
}

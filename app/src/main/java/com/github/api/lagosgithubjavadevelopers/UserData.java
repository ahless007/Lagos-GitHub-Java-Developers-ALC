package com.github.api.lagosgithubjavadevelopers;

/**
 * Created by Garuba Alex Samuel on 29-08-2017.
 */

public class UserData {

    private String gitUserName;
    private String profileimageUrl;
    private String profileUrl;

    public UserData(String gitUserName,String profileimageUrl,String profileUrl)
    {
        this.gitUserName = gitUserName;
        this.profileimageUrl = profileimageUrl;
        this.profileUrl = profileUrl;

    }

    public String getGitUserName() {
        return gitUserName;
    }

    public String getProfileimageUrl() {
        return profileimageUrl;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

}

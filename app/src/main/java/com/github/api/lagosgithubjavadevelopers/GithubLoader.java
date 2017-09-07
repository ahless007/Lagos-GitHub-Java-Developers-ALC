package com.github.api.lagosgithubjavadevelopers;

import android.content.Context;
import android.content.AsyncTaskLoader;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Garuba Alex Samuel on 29-08-2017.
 */

public class GithubLoader extends AsyncTaskLoader<String> {

    private static final String GITHUB_URL = "https://api.github.com/search/users?q=location:Lagos+language:Java";
    public GithubLoader(Context context){

        super(context);
    }

    // Using OkHTTP library for grabbing content from github api
    @Override
    public String loadInBackground(){

        try{

            OkHttpClient loadJson = new OkHttpClient();
            Request requestData = new Request.Builder().url(GITHUB_URL).build();
            Response responseJson = loadJson.newCall(requestData).execute();
            String resultJson = responseJson.body().string();
            return resultJson;

        }

        catch (Exception e){
            return null;

        }
    }

}

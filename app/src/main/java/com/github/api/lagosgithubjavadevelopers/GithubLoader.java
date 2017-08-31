package com.github.api.lagosgithubjavadevelopers;

import android.content.Context;
import android.content.AsyncTaskLoader;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * Created by Garuba Alex Samuel on 29-08-2017.
 */

public class GithubLoader extends AsyncTaskLoader<String> {

    private static final String GITHUB_URL = "https://api.github.com/search/users?q=location:Lagos+language:Java";
    public GithubLoader(Context context){

        super(context);
    }

    @Override
    public String loadInBackground(){

        try{

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(GITHUB_URL).build();
            Response response = client.newCall(request).execute();
            String result = response.body().string();
            return result;

        }

        catch (Exception e){
            return null;

        }
    }

}

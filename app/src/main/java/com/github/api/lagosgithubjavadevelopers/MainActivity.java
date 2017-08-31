package com.github.api.lagosgithubjavadevelopers;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks  {

    ProgressBar progress;
    ArrayList<UserData> arrayList;
    ListView listForMainActivity;

    private static final String GITHUB_URL = "https://api.github.com/search/users?q=location:Lagos+language:Java";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrayList = new ArrayList<>();

        listForMainActivity = (ListView) findViewById(R.id.listView);

        //Listener for the list to listen to click and launch another activity  using explicit intent
        listForMainActivity.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> listView, View v, int position, long id) {
                Object o = listForMainActivity.getItemAtPosition(position);

                UserData user = (UserData) o;

                Intent intent = new Intent(MainActivity.this, UsersInfo.class);
                intent.putExtra("userId", user.getGitUserName());
                intent.putExtra("avatarId", user.getProfileimageUrl());
                intent.putExtra("htmlId", user.getProfileUrl());

                startActivity(intent);
            }


        });

        //Start the asynchronous task of fetching data from  github if there is connection
        isThereConnection();
    }

    public Loader<String> onCreateLoader(int id ,Bundle args){

        return new GithubLoader(MainActivity.this);
    }

    @Override
    public void onLoadFinished(Loader loader, Object o) {
        progress=(ProgressBar) findViewById(R.id.spinner);
        progress.setIndeterminate(false);
        progress.setVisibility(View.GONE);
        String git = (String) o;
        getUserInfoFromJson(git);
        setListAdapter();

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }


    //parse the Json String and populate the arrayList accordingly
    private void getUserInfoFromJson(String gitHubJson){
        try{
            JSONObject root = new JSONObject(gitHubJson);
            JSONArray items=  root.getJSONArray("items");
            for (int i=0;i<items.length();i++){
                JSONObject eachUser=items.getJSONObject(i);
                arrayList.add(new UserData(eachUser.getString("login"),eachUser.getString("avatar_url"),
                        eachUser.getString("html_url") ));

            }

        }
        catch(JSONException e){
            e.printStackTrace();
        }


    }

    //Bind the arrayList  to the listForMainActivity using the custom GitUserAdapter class
    private void setListAdapter(){

        GitUserAdapter adapter = new GitUserAdapter (getApplicationContext(),R.layout.activity_users_list,arrayList);
        listForMainActivity.setAdapter(adapter);


    }

    public void isThereConnection(){

        ConnectivityManager connection = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connection.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected()){

            getLoaderManager().initLoader(1,null,this).forceLoad();
            progress=(ProgressBar) findViewById(R.id.spinner);
            progress.setIndeterminate(true);
            progress.setVisibility(View.VISIBLE);
        }
        else{

            TextView noConnectionState=(TextView) findViewById(R.id.noInternetConnection);
            noConnectionState.setText("No Internet Connection");
            progress=(ProgressBar) findViewById(R.id.spinner);
            progress.setIndeterminate(true);
            progress.setVisibility(View.GONE);

        }


    }

}

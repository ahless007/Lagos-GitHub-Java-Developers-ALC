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
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks  {

    ProgressBar progress;
    Button buttonReload;
    ArrayList<UserData> arrayList;
    ListView listForMainActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrayList = new ArrayList<>();

        listForMainActivity = (ListView) findViewById(R.id.listView);

        //Onclicklistener for the listview to show more details about thee developer when touched
        listForMainActivity.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> listView, View v, int position, long id) {
                Object gith = listForMainActivity.getItemAtPosition(position);

                UserData user = (UserData) gith;

                Intent intent = new Intent(MainActivity.this, UsersInfo.class);
                intent.putExtra("userId", user.getGitUserName());
                intent.putExtra("avatarId", user.getProfileimageUrl());
                intent.putExtra("htmlId", user.getProfileUrl());

                startActivity(intent);
            }


        });

        //Start the connection to github api if there is internet
        isThereConnection();
    }

    public Loader<String> onCreateLoader(int id ,Bundle args){

        return new GithubLoader(MainActivity.this);
    }

    @Override
    public void onLoadFinished(Loader loader, Object gith) {
        progress=(ProgressBar) findViewById(R.id.spinner);
        progress.setIndeterminate(false);
        progress.setVisibility(View.GONE);
        String git = (String) gith;
        getUserInfoFromJson(git);
        setListAdapter();

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }


    //Using JSON String to populate the Arraylist with developers from github
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

    //Bind the arrayList to the listForMainActivity using the custom GitUserAdapter class
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

            buttonReload = (Button) findViewById(R.id.reloadbutton);
            buttonReload.setVisibility(View.GONE);
        }
        else{

            TextView noConnectionState=(TextView) findViewById(R.id.noInternetConnection);
            noConnectionState.setText("No Internet Connection");
            progress =(ProgressBar) findViewById(R.id.spinner);
            progress.setIndeterminate(true);
            progress.setVisibility(View.GONE);

            buttonReload = (Button) findViewById(R.id.reloadbutton);
            buttonReload.setVisibility(View.VISIBLE);

        }


    }

    public void Reload(View view){

        ConnectivityManager connection = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connection.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected()){

            getLoaderManager().initLoader(1,null,this).forceLoad();
            progress=(ProgressBar) findViewById(R.id.spinner);
            progress.setIndeterminate(true);
            progress.setVisibility(View.VISIBLE);

            buttonReload = (Button) findViewById(R.id.reloadbutton);
            buttonReload.setVisibility(View.GONE);

            TextView noConnectionState=(TextView) findViewById(R.id.noInternetConnection);
            noConnectionState.setVisibility(View.GONE);
        }
        else{

            TextView noConnectionState=(TextView) findViewById(R.id.noInternetConnection);
            noConnectionState.setText("No Internet Connection");
            progress =(ProgressBar) findViewById(R.id.spinner);
            progress.setIndeterminate(true);
            progress.setVisibility(View.GONE);

            buttonReload = (Button) findViewById(R.id.reloadbutton);
            buttonReload.setVisibility(View.VISIBLE);

        }

    }

}

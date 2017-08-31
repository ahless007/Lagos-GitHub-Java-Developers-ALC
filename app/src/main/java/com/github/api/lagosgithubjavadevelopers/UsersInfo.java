package com.github.api.lagosgithubjavadevelopers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Garuba Alex Samuel on 29-08-2017.
 */

public class UsersInfo extends AppCompatActivity {
    public static String gitUserName;
    public static String profileimageUrl;
    public static String profileUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        updateList();
    }

    public void shareUrl(View view){
        TextView html=(TextView) findViewById(R.id.user_html);

        String message = "Check out this awesome developer @ "+gitUserName+" "+ html.getText();
        Intent intents = new Intent(Intent.ACTION_SEND);
        intents.setType("text/plain");
        intents.putExtra(Intent.EXTRA_TEXT,message);
        Intent chooseIntent=Intent.createChooser(intents,"Share");
        startActivity(chooseIntent);


    }

    public void updateList(){
        gitUserName = getIntent().getStringExtra("userId");
        profileimageUrl = getIntent().getStringExtra("avatarId");
        profileUrl = getIntent().getStringExtra("htmlId");

        CircleImageView imageView = (CircleImageView) findViewById(R.id.profileImageId);
        Picasso.with(getBaseContext()).load(profileimageUrl).placeholder(R.drawable.githubimage).resize(80,80).into(imageView);
        TextView nameView =(TextView) findViewById(R.id.user_name);

        nameView.setText(gitUserName);

        TextView htmlView = (TextView) findViewById(R.id.user_html);

        htmlView.setText(profileUrl);


    }

}

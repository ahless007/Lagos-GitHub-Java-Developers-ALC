package com.github.api.lagosgithubjavadevelopers;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Garuba Alex Samuel on 29-08-2017.
 */

public class GitUserAdapter extends ArrayAdapter<UserData> {

    ArrayList<UserData> userData;
    Context context;
    int resource;

    public GitUserAdapter(Context context, int resource, ArrayList<UserData> userData) {
        super(context, resource, userData);
        this.userData = userData;
        this.context = context;
        this.resource = resource;
    }

    // Using layoutinflater to popukate listview
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if (convertView == null){

            LayoutInflater layoutInflater=(LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.activity_users_list,null,true);
        }

        UserData users = getItem(position);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.profileImageId);
        Picasso.with(context).load(users.getProfileimageUrl()).placeholder(R.drawable.githubimage).resize(80,80).into(imageView);

        TextView textView=(TextView) convertView.findViewById(R.id.username);
        textView.setText(users.getGitUserName());
        return convertView;
    }

}

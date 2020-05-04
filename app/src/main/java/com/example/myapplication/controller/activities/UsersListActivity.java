package com.example.myapplication.controller.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.controller.adapters.SearchUsersAdapter;
import com.example.myapplication.controller.fragments.ProfileFragment;
import com.example.myapplication.model.User;
import com.example.myapplication.restapi.callback.UserResourcesCallback;

import java.util.ArrayList;
import java.util.List;

public class UsersListActivity extends AppCompatActivity implements UserResourcesCallback {

    private String typeOfList;
    private TextView title;
    private RecyclerView userslist;
    private ArrayList<User> users;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        Bundle bundle = getIntent().getExtras();
        users = bundle.getParcelableArrayList("users");

        typeOfList = getIntent().getStringExtra("type");

        initViews();
    }

    private void initViews(){

        title = (TextView) findViewById(R.id.profile_users_list_header);
        if(typeOfList.equalsIgnoreCase("following")){
            title.setText("Following List");
        }else{
            title.setText("Followers List");
        }
        userslist = (RecyclerView) findViewById(R.id.profile_users_list);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        SearchUsersAdapter adapter = new SearchUsersAdapter(this, users, UsersListActivity.this);
        userslist.setLayoutManager(manager);
        userslist.setAdapter(adapter);
    }

    public RecyclerView getUserslist() {
        return userslist;
    }

    public void setUserslist(RecyclerView userslist) {
        this.userslist = userslist;
    }

    @Override
    public void onUsersReceived(List<User> tracks) {

    }

    @Override
    public void onNoUsers(Throwable throwable) {

    }

    @Override
    public void onFailure(Throwable throwable) {

    }

    @Override
    public void onArtistsReceived(List<User> users) {

    }

    @Override
    public void onUserReceived(User user) {

    }

    @Override
    public void onNoUserReceived(Throwable throwable) {

    }

    @Override
    public void onFollowingArtistsReceived(List<User> followingArtists) {

    }

    @Override
    public void onNoFollowingArtists(Throwable noFollowingArtists) {

    }

    @Override
    public void onUserFollowingReceived(List<User> followingArtists) {

    }

    @Override
    public void onNoUserFollowing(Throwable noFollowingArtists) {

    }

    @Override
    public void onUserFollowersReceived(List<User> followers) {

    }

    @Override
    public void onNoUserFollowers(Throwable noFollowers) {

    }

    @Override
    public void onUserFollowedUnfollowed(User user) {

    }

    @Override
    public void onNoUserFollowedUnfollowed(Throwable throwable) {

    }

    @Override
    public void onArtistClicked(User clickedArtist) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("artist", clickedArtist);
        ProfileFragment profileFragment = new ProfileFragment();
        profileFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(android.R.id.content, profileFragment).commit();
    }
}

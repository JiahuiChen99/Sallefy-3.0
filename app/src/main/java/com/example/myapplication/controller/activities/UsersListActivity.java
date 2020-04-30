package com.example.myapplication.controller.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.controller.adapters.SearchUsersAdapter;
import com.example.myapplication.model.User;

import java.util.ArrayList;

public class UsersListActivity extends AppCompatActivity {

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
        SearchUsersAdapter adapter = new SearchUsersAdapter(this, users);
        userslist.setLayoutManager(manager);
        userslist.setAdapter(adapter);
    }

    public RecyclerView getUserslist() {
        return userslist;
    }

    public void setUserslist(RecyclerView userslist) {
        this.userslist = userslist;
    }
}

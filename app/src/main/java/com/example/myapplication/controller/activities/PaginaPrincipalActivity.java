package com.example.myapplication.controller.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.example.myapplication.controller.fragments.HomeFragment;
import com.example.myapplication.controller.fragments.LibraryFragment;
import com.example.myapplication.controller.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PaginaPrincipalActivity extends AppCompatActivity {

    private FragmentManager fmFragment;
    private FragmentTransaction ftFragment;
    private BottomNavigationView bnvMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_bar);
        setInicialFragment();
        initViews();
    }

    private void initViews() {
        fmFragment = getSupportFragmentManager();
        ftFragment = fmFragment.beginTransaction();

        bnvMenu = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bnvMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch (item.getItemId()) {
                    case R.id.action_home:
                        fragment = new HomeFragment();
                        break;

                    case R.id.action_search:
                        fragment = new SearchFragment();
                        break;

                    case R.id.action_content:
                        fragment = new LibraryFragment();
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                //replaceFragment(fragment);
                return true;
            }
        });
    }

    private void setInicialFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
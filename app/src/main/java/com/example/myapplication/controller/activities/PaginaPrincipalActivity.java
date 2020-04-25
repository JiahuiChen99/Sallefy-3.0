package com.example.myapplication.controller.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.controller.fragments.ExploreFragment;
import com.example.myapplication.controller.fragments.ProfileFragment;
import com.example.myapplication.controller.fragments.LibraryFragment;
import com.example.myapplication.controller.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PaginaPrincipalActivity extends AppCompatActivity {

    private FragmentManager fmFragment;
    private FragmentTransaction ftFragment;
    private BottomNavigationView bnvMenu;

    private ImageView ivThumbnail;
    private TextView tvSongName;
    private TextView tvArtist;
    private ImageButton btnPlay;

    private String url;
    private String songName;
    private String artistName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.url = getIntent().getStringExtra("songID");
        this.songName = getIntent().getStringExtra("songName");
        this.artistName = getIntent().getStringExtra("songArtist");

        if(url == null){
            this.url = "https://www.crank-in.net/img/db/1263018_650.jpg";
        }

        setContentView(R.layout.activity_menu_bar);
        setInicialFragment();
        initViews();
        loadData();
    }

    private void initViews() {

        fmFragment = getSupportFragmentManager();
        ftFragment = fmFragment.beginTransaction();

        ivThumbnail = (ImageView) findViewById(R.id.song_thumbnail);
        //supportFinishAfterTransition();
        ivThumbnail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                supportFinishAfterTransition();
            }
        });
        tvSongName = (TextView) findViewById(R.id.song_title);
        tvArtist = (TextView) findViewById(R.id.song_author);
        btnPlay = (ImageButton) findViewById(R.id.play_stop);

        //supportPostponeEnterTransition();

        bnvMenu = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bnvMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch (item.getItemId()) {
                    case R.id.action_explore:
                        fragment = new ExploreFragment();
                        break;

                    case R.id.action_search:
                        fragment = new SearchFragment();
                        break;

                    case R.id.action_content:
                        fragment = new LibraryFragment();
                        break;
                    case R.id.action_profile:
                        fragment = new ProfileFragment();
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                //replaceFragment(fragment);
                return true;
            }
        });

    }

    private void loadData(){
        tvSongName.setText(this.songName);
        tvSongName.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tvSongName.setSelected(true);
        tvArtist.setText(this.artistName);
        Glide.with(this)
                .asBitmap()
                .load(this.url)
                .into(ivThumbnail);
    }

    private void setInicialFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ExploreFragment()).commit();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}

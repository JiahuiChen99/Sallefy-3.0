package com.example.myapplication.controller.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.R;
import com.example.myapplication.controller.adapters.SearchAdapter;
import com.gigamole.navigationtabstrip.NavigationTabStrip;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarOutputStream;

public class SearchFragment extends Fragment {

    private EditText songId;
    private Button search;
    private String input = "";
    private List<Fragment> fragments = new ArrayList<>();

    private NavigationTabStrip navigationTabStrip;
    private ViewPager mPager;
    private SearchAdapter adapter;
    SearchSongsFragment adapter2;

    private SearchView searchView;

    @Override
    public void onDestroyView() {
        mPager.removeAllViews();
        super.onDestroyView();
    }

    @Override
    public void onStop() {
        mPager.removeAllViews();
        super.onStop();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_search, container, false);

        navigationTabStrip = (NavigationTabStrip) view.findViewById(R.id.search_tabs);
        mPager = (ViewPager) view.findViewById(R.id.view_pager);

        searchView = view.findViewById(R.id.search);

        //songId = view.findViewById(R.id.searching_bar);
        //search = view.findViewById(R.id.search_button_window);

        init(input);
        //uploadFragments();

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }


            @Override
            public void onPageScrollStateChanged(int state) {
                adapter.setInput(input, mPager.getCurrentItem());
            }
        });




        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                input = query;
                //adapter.getFilter().filter(query);
                adapter.setInput(input, mPager.getCurrentItem());

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals("")) {
                    input = "";
                    adapter.setInput(input, mPager.getCurrentItem());
                }
                return false;
            }
        });

        /*search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input = songId.getText().toString();
                uploadFragments();

            }
        });*/

        return view;
    }

    private void init(String input) {
        adapter = new SearchAdapter(this.getFragmentManager(), input);
        mPager.setAdapter(adapter);
        mPager.setOffscreenPageLimit(4);
        navigationTabStrip.setViewPager(mPager);
        navigationTabStrip.setTitles("Songs", "Genre", "Artists", "Playlists");
        navigationTabStrip.setTitleSize(30);
        navigationTabStrip.setStripColor(Color.GREEN);
        navigationTabStrip.setStripWeight(5);
        navigationTabStrip.setStripFactor(2);
        navigationTabStrip.setStripType(NavigationTabStrip.StripType.POINT);
        navigationTabStrip.setStripGravity(NavigationTabStrip.StripGravity.BOTTOM);
        navigationTabStrip.setCornersRadius(2);
        navigationTabStrip.setAnimationDuration(300);
        navigationTabStrip.setInactiveColor(Color.GRAY);
        navigationTabStrip.setActiveColor(Color.WHITE);
    }
}
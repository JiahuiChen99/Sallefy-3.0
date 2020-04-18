package com.example.myapplication.controller.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.R;
import com.example.myapplication.controller.adapters.LibraryAdapter;
import com.gigamole.navigationtabstrip.NavigationTabStrip;

public class SearchFragment extends Fragment {

    private NavigationTabStrip navigationTabStrip;
    private ViewPager mPager;
    private LibraryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_search, container, false);
        navigationTabStrip = (NavigationTabStrip) view.findViewById(R.id.search_tabs);
        mPager = (ViewPager) view.findViewById(R.id.view_pager_search);
        init();

        return view;
    }

    private void init(){
        adapter = new LibraryAdapter(this.getFragmentManager());
        mPager.setAdapter(adapter);
        navigationTabStrip.setViewPager(mPager);
        navigationTabStrip.setTitles("Songs", "Genre", "Artists", "Playlists");
        navigationTabStrip.setTitleSize(30);
        navigationTabStrip.setStripColor(Color.RED);
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

package com.example.myapplication.controller.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.R;
import com.example.myapplication.controller.adapters.LibraryAdapter;
import com.gigamole.navigationtabstrip.NavigationTabStrip;

import java.util.List;


public class LibraryFragment extends Fragment {

    private NavigationTabStrip navigationTabStrip;
    private ViewPager mPager;
    private LibraryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_library, container, false);

        navigationTabStrip = (NavigationTabStrip) view.findViewById(R.id.library_tabs);
        mPager = (ViewPager) view.findViewById(R.id.view_pager);

        init();

        return view;
    }

    private void init(){
        adapter = new LibraryAdapter(this.getFragmentManager());
        mPager.setAdapter(adapter);
        navigationTabStrip.setViewPager(mPager);
        mPager.setOffscreenPageLimit(4);
        navigationTabStrip.setTitles("My Playlists", "Liked Playlists", "Favourite Songs", "Artists");
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

package com.example.myapplication.controller.adapters;

import android.content.Context;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.myapplication.R;
import com.example.myapplication.controller.fragments.SearchArtistFragment;
import com.example.myapplication.controller.fragments.SearchFragment;
import com.example.myapplication.controller.fragments.SearchGenreFragment;
import com.example.myapplication.controller.fragments.SearchPlaylistsFragment;
import com.example.myapplication.controller.fragments.SearchSongsFragment;

import java.lang.invoke.CallSite;
import java.util.ArrayList;
import java.util.List;



public class SearchAdapter extends FragmentStatePagerAdapter {

    private String input;
    private Integer index;
    private List<Fragment> fragmentList = new ArrayList<>();

    public SearchAdapter (FragmentManager manager, String input) {
        super(manager);
        this.input = input;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        index = position;
        return fragmentList.get(position);
        /*switch (position) {
            case 0:
                return SearchSongsFragment.getInstance(input);
            case 1:
                return SearchGenreFragment.getInstance(input);
            case 2:
                return SearchArtistFragment.getInstance(input);
            case 3:
                return SearchPlaylistsFragment.getInstance(input);
        }
        return null;*/
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void setInput (String input) {
        this.input = input;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }

    public void setFragmentList1 (List<Fragment> fragments) {
        fragmentList = fragments;
        notifyDataSetChanged();
    }
}





/*
public class SearchAdapter extends FragmentPagerAdapter {

    private String input;
    private int index;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();

    public SearchAdapter (FragmentManager fm, String input) {
        super(fm);
        this.input = input;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return SearchSongsFragment.getInstance(input);

            case 1:
                return SearchGenreFragment.getInstance(input);

            case 2:
                return SearchArtistFragment.getInstance(input);

            case 3:
                return SearchPlaylistsFragment.getInstance(input);

        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

    public void addFragment ( String title) {
        titleList.add(title);
    }

    public void getFragment () {
        for (int i = 0; i < fragmentList.size(); i++) {
            fragmentList.set(i, getItem(i));
        }

    }

    public void resetFragments() {
        this.fragmentList.removeAll(fragmentList);
    }

    public void refreshFragments(List<Fragment> fragments) {
        fragmentList = fragments;
    }

    public int getIndex () {
        return index;
    }

    public void refreshFragment(int index, Fragment fragment) {
        fragmentList.set(index, fragment);
    }
}*/
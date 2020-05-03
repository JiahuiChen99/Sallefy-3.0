package com.example.myapplication.controller.adapters;

import android.content.Context;
import android.widget.Filter;
import android.widget.Filterable;

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
import com.example.myapplication.model.ListItem;
import com.example.myapplication.model.SearchGenre;

import java.lang.invoke.CallSite;
import java.util.ArrayList;
import java.util.List;



public class SearchAdapter extends FragmentStatePagerAdapter implements Filterable {

    private String input;
    private Integer index;
    private ArrayList<SearchGenre> list = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();
    private Fragment currentFragment;

    public SearchAdapter (FragmentManager manager, String input) {
        super(manager);
        this.input = input;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        index = position;
        switch (position) {
            case 0:
                fragmentList.add(0, SearchSongsFragment.getInstance(input));
                return fragmentList.get(0);
            case 1:
                fragmentList.add(1, SearchGenreFragment.getInstance(input, list));
                list = SearchGenreFragment.getList();
                return fragmentList.get(1);
            case 2:
                fragmentList.add(2,SearchArtistFragment.getInstance(input));
                return fragmentList.get(2);
            case 3:
                fragmentList.add(3, SearchPlaylistsFragment.getInstance(input));
                return fragmentList.get(3);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    public void setInput (String input, int currentItem) {
        this.input = input;

        currentFragment = fragmentList.get(currentItem);
        switch (currentItem){
            case 0:
                if(input.equalsIgnoreCase("")){
                    ((SearchSongsFragment) currentFragment).getData();
                }else{
                    ((SearchSongsFragment) currentFragment).updateSongs(input);
                }
                break;
            case 1:
                if(input.equalsIgnoreCase("")){
                    ((SearchGenreFragment) currentFragment).getData();
                }else{
                    ((SearchGenreFragment) currentFragment).updateSongs(input);
                }
                break;
            case 2:
                if(input.equalsIgnoreCase("")){
                    ((SearchArtistFragment) currentFragment).getData(input);
                }else{
                    ((SearchArtistFragment) currentFragment).getData(input);
                }
                break;
            case 3:
                if(input.equalsIgnoreCase("")){
                    ((SearchPlaylistsFragment) currentFragment).getData();
                }else{
                    ((SearchPlaylistsFragment) currentFragment).updateSongs(input);
                }
                break;
        }


        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public Filter getFilter() {
        return null;
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
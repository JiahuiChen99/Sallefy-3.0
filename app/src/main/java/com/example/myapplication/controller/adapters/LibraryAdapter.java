package com.example.myapplication.controller.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


import com.example.myapplication.controller.fragments.LibraryFavouriteSongsFragment;
import com.example.myapplication.controller.fragments.LibraryLikedPlaylistsFragment;
import com.example.myapplication.controller.fragments.LibraryUserPlaylistsFragment;

import java.util.List;

public class LibraryAdapter extends FragmentStatePagerAdapter {

    public static  final int count = 3;

    public LibraryAdapter(FragmentManager manager){
        super(manager);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return LibraryUserPlaylistsFragment.getInstance();
            case 1:
                return LibraryLikedPlaylistsFragment.getInstance();
            case 2:
                return LibraryFavouriteSongsFragment.getInstance();
            /*case 3:
                return new;*/
        }
        return null;
    }

    @Override
    public int getCount() {
        return count;
    }
}

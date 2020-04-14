package com.example.myapplication.controller.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


import com.example.myapplication.controller.fragments.LibraryUserPlaylistsFragment;

import java.util.List;

public class LibraryAdapter extends FragmentStatePagerAdapter {

    public static  final int Count = 2;

    public LibraryAdapter(FragmentManager manager){
        super(manager);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new LibraryUserPlaylistsFragment();
            case 1:
                return new LibraryUserPlaylistsFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return Count;
    }
}

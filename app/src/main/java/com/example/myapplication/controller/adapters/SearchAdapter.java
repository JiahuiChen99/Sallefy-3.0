package com.example.myapplication.controller.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.myapplication.controller.fragments.SearchArtistFragment;
import com.example.myapplication.controller.fragments.SearchGenreFragment;
import com.example.myapplication.controller.fragments.SearchPlaylistsFragment;
import com.example.myapplication.controller.fragments.SearchSongsFragment;
import com.example.myapplication.model.SearchGenre;
import java.util.ArrayList;
import java.util.List;



public class SearchAdapter extends FragmentStatePagerAdapter {

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

        for (int i = 0; i < fragmentList.size(); i++) {
            currentFragment = fragmentList.get(i);
            if(input.equalsIgnoreCase("")){
                if(currentFragment instanceof SearchSongsFragment){
                    ((SearchSongsFragment) currentFragment).getData();
                }
                if(currentFragment instanceof SearchGenreFragment){
                    ((SearchGenreFragment) currentFragment).getData();
                }
                if(currentFragment instanceof SearchArtistFragment){
                    ((SearchArtistFragment) currentFragment).getData(input);
                }
                if(currentFragment instanceof SearchPlaylistsFragment){
                    ((SearchPlaylistsFragment) currentFragment).getData();
                }
            }else{
                if(currentFragment instanceof SearchSongsFragment){
                    ((SearchSongsFragment) currentFragment).updateSongs(input);
                }
                if(currentFragment instanceof SearchGenreFragment){
                    ((SearchGenreFragment) currentFragment).updateSongs(input);
                }
                if(currentFragment instanceof SearchArtistFragment){
                    ((SearchArtistFragment) currentFragment).getData(input);
                }
                if(currentFragment instanceof SearchPlaylistsFragment){
                    ((SearchPlaylistsFragment) currentFragment).updateSongs(input);
                }
            }
        }
        /*switch (currentItem){
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
        }*/


        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }

}

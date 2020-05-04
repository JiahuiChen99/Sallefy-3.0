package com.example.myapplication.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.controller.adapters.SearchGenreAdapter;
import com.example.myapplication.model.EmptyItem;
import com.example.myapplication.model.EssencialGenre;
import com.example.myapplication.model.EventItem;
import com.example.myapplication.model.HeaderItem;
import com.example.myapplication.model.ListItem;
import com.example.myapplication.model.SearchGenre;
import com.example.myapplication.model.SearchResult;
import com.example.myapplication.model.Track;
import com.example.myapplication.restapi.callback.GenreCallback;
import com.example.myapplication.restapi.callback.SearchCallback;
import com.example.myapplication.restapi.callback.TrackCallback;
import com.example.myapplication.restapi.manager.GenreManager;
import com.example.myapplication.restapi.manager.TrackManager;

import java.util.ArrayList;
import java.util.List;

public class SearchGenreFragment extends Fragment implements SearchCallback, TrackCallback, GenreCallback {

    private ArrayList<Track> tracks;
    private static ArrayList<SearchGenre> list;
    private RecyclerView msongListRecyclerView;
    private SearchGenreFragment instance;
    private String input;
    private Boolean mode;

    public Boolean getMode() {
        return mode;
    }

    public String getInput() {
        return input;
    }

    public SearchGenreFragment(String input, ArrayList<SearchGenre> list) {
        this.input = input;
        this.list = list;
    }

    public static SearchGenreFragment getInstance(String input, ArrayList<SearchGenre> list){
        return new SearchGenreFragment(input, list);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_songs, container, false);

        //**************************************************************
        msongListRecyclerView = (RecyclerView) v.findViewById(R.id.search_songs);
        LinearLayoutManager songsListManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        msongListRecyclerView.setLayoutManager(songsListManager);
        //***************************************************************

        if (input == null) {
            getData();
        }else if (input.equals("")) {
            mode = false;
            getData();
        }else {
            mode = true;
            updateSongs(input);
        }

        return v;
    }

    public void updateSongs(String input) {
        List<ListItem> items = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getGenre().toLowerCase().equals(input.toLowerCase())) {
                HeaderItem header = new HeaderItem();
                header.setGenre(list.get(i).getGenre());
                items.add(header);
                if (list.get(i).getTracks().size() > 0) {
                    for (int j = 0; j < list.get(i).getTracks().size(); j++) {
                        EventItem item = new EventItem();
                        item.setTrack(list.get(i).getTracks().get(j));
                        items.add(item);
                    }
                } else {
                    EmptyItem empty = new EmptyItem();
                    empty.setGenre("This playlist is empty");
                    items.add(empty);
                }
            }
        }
        SearchGenreAdapter adapter = new SearchGenreAdapter(getContext(), items, getActivity(), this, this);
        msongListRecyclerView.setAdapter(adapter);
    }

    public void getData(){
        GenreManager.getInstance(this.getActivity()).getGenre(this);

    }

    @Override
    public void onLikedTracksReceived(List<Track> tracks) {

    }

    private void sendInfo() {

        List<ListItem> items = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTracks().size() > 0) {
                HeaderItem header = new HeaderItem();
                header.setGenre(list.get(i).getGenre());
                header.setEmpty(false);
                items.add(header);
                for (int j = 0; j < list.get(i).getTracks().size(); j++) {
                    EventItem item = new EventItem();
                    item.setTrack(list.get(i).getTracks().get(j));
                    items.add(item);
                }
            }
        }
        SearchGenreAdapter adapter = new SearchGenreAdapter(getContext(), items, getActivity(), this, this);
        msongListRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onTracksReceived(List<Track> tracks) {
        this.tracks = (ArrayList)tracks;

        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < this.tracks.size(); j++) {
                for (int k = 0; k < this.tracks.get(j).getGenres().size(); k++) {
                    if (this.tracks.get(j).getGenres().get(k).getId().compareTo(list.get(i).getId()) == 0) {
                        list.get(i).setTracks(tracks.get(j));
                    }
                }
            }
        }
        sendInfo();
    }

    @Override
    public void onGenreReceived(List<EssencialGenre> essencial) {
        list.clear();
        for (int i = 0; i < essencial.size(); i++) {
            list.add(new SearchGenre());
            list.get(i).setGenre(essencial.get(i).getName());
            list.get(i).setId(essencial.get(i).getId());
        }
        TrackManager.getInstance(this.getActivity()).getAllTracks(this);
    }

    public static ArrayList<SearchGenre> getList() {
        return list;
    }


    @Override
    public void onNoTracks(Throwable throwable) {

    }

    @Override
    public void onRecentTracksReceived(List<Track> tracks) {

    }

    @Override
    public void onNoRecentTracksReceived(Throwable throwable) {

    }

    @Override
    public void onRecommendedTracksReceived(List<Track> tracks) {

    }

    @Override
    public void onNoRecommendedTracks(Throwable throwable) {

    }

    @Override
    public void onSpecificTrackReceived(Track track) {

    }

    @Override
    public void onNoSpecificTrack(Track track) {

    }

    @Override
    public void onTrackSelected(Integer id, String sectionID) {

    }

    @Override
    public void onTrackLiked(Track like) {

    }

    @Override
    public void onNoLikedTracks(Throwable noLikedTracks) {

    }

    @Override
    public void onArtistTracksReceived(List<Track> artistTracks) {

    }

    @Override
    public void onNoArtistTracks(Throwable noArtistTracks) {

    }

    @Override
    public void onFailure(Throwable throwable) {

    }

    @Override
    public void onInfoReceived(SearchResult output) {
        /*this.tracks = (ArrayList)output.getTracks();
        TrackListAdapter adapter = new TrackListAdapter(this, getContext(), this.tracks);
        msongListRecyclerView.setAdapter(adapter);*/
    }

    @Override
    public void onNoInfo(Throwable throwable) {
    }

    @Override
    public void onNoGenre(Throwable throwable) {

    }

    @Override
    public void onGenreSelected(Integer id, String sectionID) {

    }
}

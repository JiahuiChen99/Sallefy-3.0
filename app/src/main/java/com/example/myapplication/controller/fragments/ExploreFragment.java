package com.example.myapplication.controller.fragments;

import android.content.Intent;
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
import com.example.myapplication.controller.activities.TrackDetailsActivity;
import com.example.myapplication.controller.adapters.ArtistsAdapter;
import com.example.myapplication.controller.adapters.RecentTracksAdapter;
import com.example.myapplication.controller.adapters.RecommendAdapter;
import com.example.myapplication.model.Track;
import com.example.myapplication.model.User;
import com.example.myapplication.restapi.callback.TrackCallback;
import com.example.myapplication.restapi.callback.UserResourcesCallback;
import com.example.myapplication.restapi.manager.TrackManager;
import com.example.myapplication.restapi.manager.UserResourcesManager;

import java.util.ArrayList;
import java.util.List;

public class ExploreFragment extends Fragment implements TrackCallback, UserResourcesCallback {

    private RecyclerView mRecentRecyclerView;
    private RecyclerView mArtistsRecyclerView;
    private RecyclerView mRecommendedRecyclerView;
    private ArrayList mRecentTracks;
    private ArrayList mArtists;
    private ArrayList mRecommendedTracks;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        mRecentRecyclerView = (RecyclerView) view.findViewById(R.id.recentList);
        LinearLayoutManager recentTracksManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RecentTracksAdapter recentTracksAdapter = new RecentTracksAdapter(this, this, null);
        mRecentRecyclerView.setLayoutManager(recentTracksManager);
        mRecentRecyclerView.setAdapter(recentTracksAdapter);

        mArtistsRecyclerView = (RecyclerView) view.findViewById(R.id.recommendArtistsList);
        LinearLayoutManager artistManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        ArtistsAdapter artistAdapter = new ArtistsAdapter(this, null);
        mArtistsRecyclerView.setLayoutManager(artistManager);
        mArtistsRecyclerView.setAdapter(artistAdapter);

        mRecommendedRecyclerView = (RecyclerView) view.findViewById(R.id.recommendTracksList);
        LinearLayoutManager recommendManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RecommendAdapter recommendAdapter = new RecommendAdapter(this, this, null);
        mRecommendedRecyclerView.setLayoutManager(recommendManager);
        mRecommendedRecyclerView.setAdapter(recommendAdapter);

        getData();

        return view;
    }

    private void getData() {
        TrackManager.getInstance(this.getActivity()).getRecentTracks(this);
        mRecentTracks = new ArrayList<>();

        UserResourcesManager.getInstance(this.getActivity()).getTopArtists(this);
        mArtists = new ArrayList<>();

        TrackManager.getInstance(this.getActivity()).getRecommendedTracks(this);
        mRecommendedTracks = new ArrayList<>();
    }

    @Override
    public void onTracksReceived(List<Track> tracks) {
    }

    @Override
    public void onNoTracks(Throwable throwable) {

    }

    @Override
    public void onRecentTracksReceived(List<Track> tracks) {
        mRecentTracks = (ArrayList) tracks;
        RecentTracksAdapter adapter = new RecentTracksAdapter(this, this, mRecentTracks);
        mRecentRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onNoRecentTracksReceived(Throwable throwable) {

    }

    @Override
    public void onRecommendedTracksReceived(List<Track> recommendedTracks) {
        mRecommendedTracks = (ArrayList) recommendedTracks;
        RecommendAdapter adapter = new RecommendAdapter(this,this, mRecommendedTracks);
        mRecommendedRecyclerView.setAdapter(adapter);
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
        Intent intent = new Intent(getActivity(), TrackDetailsActivity.class);
        intent.putExtra("songId", id);
        intent.putExtra("sectionId", sectionID);
        startActivity(intent);
    }

    @Override
    public void onTrackLiked(Track like) {

    }

    @Override
    public void onLikedTracksReceived(List<Track> likedTracks) {

    }

    @Override
    public void onNoLikedTracks(Throwable noLikedTracks) {

    }


    @Override
    public void onUsersReceived(List<User> artists) {
        mArtists = (ArrayList) artists;
        ArtistsAdapter adapter = new ArtistsAdapter(this, mArtists);
        mArtistsRecyclerView.setAdapter(adapter);

    }

    @Override
    public void onNoUsers(Throwable throwable) {

    }

    @Override
    public void onFailure(Throwable throwable) {

    }
}

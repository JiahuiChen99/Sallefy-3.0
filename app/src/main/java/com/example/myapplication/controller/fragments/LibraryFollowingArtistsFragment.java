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
import com.example.myapplication.controller.adapters.LikedPlaylistAdapter;
import com.example.myapplication.controller.adapters.TrackListAdapter;
import com.example.myapplication.controller.callbacks.TrackListCallback;
import com.example.myapplication.model.Track;
import com.example.myapplication.model.User;
import com.example.myapplication.restapi.callback.TrackCallback;
import com.example.myapplication.restapi.callback.UserResourcesCallback;
import com.example.myapplication.restapi.manager.UserResourcesManager;

import java.util.ArrayList;
import java.util.List;

import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

public class LibraryFollowingArtistsFragment extends Fragment implements UserResourcesCallback, TrackCallback, TrackListCallback {

    private RecyclerCoverFlow mArtistsRecyclerView;
    private ArrayList<User> mFollowingArtists;
    private ArrayList<Track> mArtistSongsList;
    private RecyclerView msongList;
    private Integer artistID = 0;

    public static LibraryFollowingArtistsFragment getInstance(){
        return new LibraryFollowingArtistsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_library_following_artists, container, false);

        mArtistsRecyclerView = (RecyclerCoverFlow) view.findViewById(R.id.library_artists);
        CoverFlowLayoutManger userPlaylistManager = new CoverFlowLayoutManger(false, false, true, (float) 1);
        ArtistsAdapter followingArtistslistAdapter = new ArtistsAdapter( getContext(), null);
        mArtistsRecyclerView.setLayoutManager(userPlaylistManager);
        mArtistsRecyclerView.setAdapter(followingArtistslistAdapter);
        mArtistsRecyclerView.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected(){
            @Override
            public void onItemSelected(int position) {
                System.out.println("POSITION: " + position + " - " + mFollowingArtists.get(position).getLogin());
                artistID = position;
                UserResourcesManager.getInstance(getContext()).getFollowingArtistsTopSongs( mFollowingArtists.get(position).getLogin(), LibraryFollowingArtistsFragment.this);
            }});

        msongList = (RecyclerView) view.findViewById(R.id.library_artists_songs);
        LinearLayoutManager songsListManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        msongList.setLayoutManager(songsListManager);;

        getData();

        return view;
    }

    private void getData(){
        UserResourcesManager.getInstance(this.getActivity()).getFollowingArtists(this);
        mFollowingArtists = new ArrayList<>();
    }

    @Override
    public void onUsersReceived(List<User> tracks) {

    }

    @Override
    public void onNoUsers(Throwable throwable) {

    }

    @Override
    public void onFailure(Throwable throwable) {

    }

    @Override
    public void onFollowingArtistsReceived(List<User> followingArtists) {
        mFollowingArtists = (ArrayList<User>) followingArtists;
        ArtistsAdapter adapter = new ArtistsAdapter(getContext(), mFollowingArtists);
        mArtistsRecyclerView.setAdapter(adapter);
        UserResourcesManager.getInstance(getContext()).getFollowingArtistsTopSongs( mFollowingArtists.get(0).getLogin(), LibraryFollowingArtistsFragment.this);
    }

    @Override
    public void onNoFollowingArtists(Throwable noFollowingArtists) {

    }

    @Override
    public void onUserFollowingReceived(List<User> followingArtists) {

    }

    @Override
    public void onNoUserFollowing(Throwable noFollowingArtists) {

    }

    @Override
    public void onUserFollowersReceived(List<User> followers) {

    }

    @Override
    public void onNoUserFollowers(Throwable noFollowers) {

    }

    @Override
    public void onTracksReceived(List<Track> tracks) {

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
        Intent intent = new Intent(getActivity(), TrackDetailsActivity.class);
        intent.putExtra("songId", id);
        intent.putExtra("sectionId", "Artists");
        intent.putExtra("artistID", mFollowingArtists.get(artistID).getLogin());
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
    public void onArtistTracksReceived(List<Track> artistTracks) {
        mArtistSongsList = (ArrayList) artistTracks;
        TrackListAdapter adapter = new TrackListAdapter(this, getContext(), mArtistSongsList);
        msongList.setAdapter(adapter);
    }

    @Override
    public void onNoArtistTracks(Throwable noArtistTracks) {

    }

    @Override
    public void onTrackSelected(Track track) {

    }

    @Override
    public void onTrackSelected(int index) {

    }
}

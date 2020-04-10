package com.example.myapplication.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;
import com.example.myapplication.controller.adapters.RecentTracksAdapter;
import com.example.myapplication.controller.adapters.UserPlaylistAdapter;
import com.example.myapplication.model.Playlist;
import com.example.myapplication.restapi.callback.PlaylistCallback;
import com.example.myapplication.restapi.manager.PlaylistManager;

import java.util.ArrayList;
import java.util.List;

import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

public class LibraryFragment extends Fragment implements PlaylistCallback {

    private RecyclerCoverFlow mPlaylistRecyclerView;
    private ArrayList<Playlist> mPlaylists;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_library, container, false);

        mPlaylistRecyclerView = (RecyclerCoverFlow) view.findViewById(R.id.library_playlist);
        CoverFlowLayoutManger userPlaylistManager = new CoverFlowLayoutManger(false, false, true, (float) 1);
        UserPlaylistAdapter userPlaylistAdapter = new UserPlaylistAdapter( this, null);
        mPlaylistRecyclerView.setLayoutManager(userPlaylistManager);
        mPlaylistRecyclerView.setAdapter(userPlaylistAdapter);
        mPlaylistRecyclerView.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected(){
            @Override
            public void onItemSelected(int position) {
                System.out.println("POSITION: " + position + " - " + mPlaylists.get(position).getThumbnail());

                /*RequestOptions requestOptions = new RequestOptions();
                requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(20));
                Glide.with(LibraryFragment.this)
                        .asBitmap()
                        .placeholder(R.drawable.logo)
                        //.load("https://livedoor.blogimg.jp/bookuma/imgs/f/5/f5a998fa.jpg")
                        .load(mPlaylists.get(position).getThumbnail())
                        .apply(requestOptions)
                        .into((ImageView) view.findViewById(R.id.user_playlist_cover));*/

                //((TextView) view.findViewById(R.id.user_playlist_name)).setText(mPlaylists.get(position).getName());
            }});
        getData();

        return view;
    }

    private void getData(){
        PlaylistManager.getInstance(this.getActivity()).getUserPlaylists(this);
        mPlaylists = new ArrayList<>();
    }

    @Override
    public void onFailure(Throwable throwable) {

    }

    @Override
    public void onPlaylistReceived(Playlist playlists) {

    }

    @Override
    public void onNoPlaylists(Throwable throwable) {

    }

    @Override
    public void onPlaylistCreated(Playlist playlist) {

    }

    @Override
    public void onPlaylistFailure(Throwable throwable) {

    }

    @Override
    public void onUserPlaylistsReceived(List<Playlist> playlists) {
        mPlaylists = (ArrayList) playlists;
        UserPlaylistAdapter adapter = new UserPlaylistAdapter(this, mPlaylists);
        mPlaylistRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onNoUserPlaylists(Throwable throwable) {

    }
}

package com.example.myapplication.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.controller.adapters.RecentTracksAdapter;
import com.example.myapplication.model.Track;
import com.example.myapplication.restapi.callback.TrackCallback;
import com.example.myapplication.restapi.manager.TrackManager;

import java.util.ArrayList;
import java.util.List;

public class ExploreFragment extends Fragment implements TrackCallback {

    private RecyclerView mRecyclerView;
    private ArrayList mTracks;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recentList);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RecentTracksAdapter adapter = new RecentTracksAdapter(this, null);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
        getData();

        return view;
    }

    private void getData() {
        TrackManager.getInstance(this.getActivity()).getRecentTracks(this);
        mTracks = new ArrayList<>();
    }

    @Override
    public void onTracksReceived(List<Track> tracks) {
        mTracks = (ArrayList) tracks;
        RecentTracksAdapter adapter = new RecentTracksAdapter(this, mTracks);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onNoTracks(Throwable throwable) {

    }

    @Override
    public void onFailure(Throwable throwable) {

    }
}

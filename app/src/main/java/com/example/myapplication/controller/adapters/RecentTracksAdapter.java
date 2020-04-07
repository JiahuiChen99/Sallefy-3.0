package com.example.myapplication.controller.adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.controller.fragments.ExploreFragment;
import com.example.myapplication.model.Track;
import com.example.myapplication.restapi.callback.TrackCallback;

import java.util.ArrayList;
import java.util.List;

public class RecentTracksAdapter extends RecyclerView.Adapter<RecentTracksAdapter.ViewHolder> implements TrackCallback {

    private static final String TAG = "Recent Tracks";
    private ArrayList<Track> mTracks;
    private ExploreFragment mContext;
    private TrackCallback callback;
    private int NUM_VIEWHOLDERS = 0;

    public RecentTracksAdapter(TrackCallback callback, ExploreFragment context, ArrayList<Track> tracks ) {
        this.callback = callback;
        this.mTracks = tracks;
        this.mContext = context;
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

    }

    @Override
    public void onFailure(Throwable throwable) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivThumbnail;
        TextView tvTrackName;
        LinearLayout mLayout;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            mLayout = itemView.findViewById(R.id.recent_track_item_layout);
            ivThumbnail = (ImageView) itemView.findViewById(R.id.recentImage);
            tvTrackName = (TextView) itemView.findViewById(R.id.recentImageName);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called. Num viewHolders: " + NUM_VIEWHOLDERS);

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent_played, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: Called.");

        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "SONG: " + position);
                callback.onTrackSelected(position, TAG);
            }
        });
        if (mTracks.get(position).getThumbnail() != null) {
            Glide.with(mContext)
                    .asBitmap()
                    .placeholder(R.drawable.logo)
                    .load(mTracks.get(position).getThumbnail())
                    .into(holder.ivThumbnail);
            holder.tvTrackName.setText(mTracks.get(position).getName());
        }
    }

    @Override
    public int getItemCount() {
        return mTracks != null ? mTracks.size():0;
    }

}

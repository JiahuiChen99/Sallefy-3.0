package com.example.myapplication.controller.adapters;

import android.text.TextUtils;
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
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;
import com.example.myapplication.controller.fragments.ExploreFragment;
import com.example.myapplication.model.Track;
import com.example.myapplication.restapi.callback.TrackCallback;

import java.util.ArrayList;
import java.util.List;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.ViewHolder> implements TrackCallback {

    private static final String TAG = "Recommended Tracks";
    private ArrayList<Track> mTracks;
    private ExploreFragment mContext;
    TrackCallback callback;
    private int NUM_VIEWHOLDERS = 0;

    public RecommendAdapter(TrackCallback trackCallback,ExploreFragment context, ArrayList<Track> recommendedTracks ) {
        this.callback = trackCallback;
        this.mTracks = recommendedTracks;
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

    }

    @Override
    public void onNoArtistTracks(Throwable noArtistTracks) {

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
            mLayout = itemView.findViewById(R.id.recommend_track_item_layout);
            ivThumbnail = (ImageView) itemView.findViewById(R.id.recommendImage);
            tvTrackName = (TextView) itemView.findViewById(R.id.recommendedImageName);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called. Num viewHolders: " + NUM_VIEWHOLDERS);

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommended_tracks, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: Called.");

        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clicked: " + mTracks.get(position).getName());
                callback.onTrackSelected(position, TAG);
            }
        });
        if (mTracks.get(position).getThumbnail() != null) {

            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(20));
            Glide.with(mContext)
                    .asBitmap()
                    .placeholder(R.drawable.logo)
                    .load(mTracks.get(position).getThumbnail())
                    .apply(requestOptions)
                    .into(holder.ivThumbnail);
            holder.tvTrackName.setText(mTracks.get(position).getName());
            holder.tvTrackName.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            holder.tvTrackName.setSelected(true);
        }
    }

    @Override
    public int getItemCount() {
        return mTracks != null ? mTracks.size():0;
    }

}

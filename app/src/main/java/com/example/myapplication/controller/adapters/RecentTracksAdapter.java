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

public class RecentTracksAdapter extends RecyclerView.Adapter<RecentTracksAdapter.ViewHolder> {

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

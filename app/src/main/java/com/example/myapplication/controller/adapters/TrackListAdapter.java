package com.example.myapplication.controller.adapters;

import android.content.Context;
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
import com.example.myapplication.model.Track;
import com.example.myapplication.restapi.callback.TrackCallback;

import java.util.ArrayList;
import java.util.List;

public class TrackListAdapter extends RecyclerView.Adapter<TrackListAdapter.ViewHolder> {

    private static final String TAG = "Liked Playlists";
    private ArrayList<Track> mTracks;
    private Context mContext;
    private TrackCallback mCallback;
    private int NUM_VIEWHOLDERS = 0;

    public TrackListAdapter(TrackCallback callback, Context context, ArrayList<Track> tracks ) {
        mTracks = tracks;
        mContext = context;
        mCallback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called. Num viewHolders: " + NUM_VIEWHOLDERS++);


        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        ViewHolder vh = new TrackListAdapter.ViewHolder(itemView);
        Log.d(TAG, "onCreateViewHolder: called. viewHolder hashCode: " + vh.hashCode());
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called. viewHolder hashcode: " + holder.hashCode());

        if (mTracks.size()>0) {
            holder.mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onTrackSelected(position, TAG);
                }
            });
            holder.tvTitle.setText(mTracks.get(position).getName());
            holder.tvTitle.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            holder.tvTitle.setSelected(true);
            holder.tvAuthor.setText(mTracks.get(position).getUserLogin());
            if (mTracks.get(position).getDuration() != null) {
                holder.ivDuration.setText(createTimeLabel(mTracks.get(position).getDuration()));
            }
            if (mTracks.get(position).getThumbnail() != null) {
                RequestOptions requestOptions = new RequestOptions();
                requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(20));
                Glide.with(mContext)
                        .asBitmap()
                        .placeholder(R.drawable.no_user)
                        .load(mTracks.get(position).getThumbnail())
                        .apply(requestOptions)
                        .into(holder.ivPicture);
            }
        }
    }

    private String createTimeLabel(Integer duration){
        String timerLabel = "";
        duration *= 1000;
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;

        timerLabel += min + ":";

        if(sec < 10) timerLabel += "0";

        timerLabel += sec;

        return timerLabel;
    }

    @Override
    public int getItemCount() {
        return mTracks != null ? mTracks.size():0;
    }

    public void updateTrackLikeStateIcon(int position, boolean isLiked) {
        mTracks.get(position).setLiked(isLiked);
        notifyDataSetChanged();
    }

    public void updateTracks (List<Track> tracks) {
        mTracks.clear();
        mTracks.addAll(tracks);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout mLayout;
        TextView tvTitle;
        TextView tvAuthor;
        TextView ivDuration;
        ImageView ivPicture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mLayout = itemView.findViewById(R.id.track_item_layout);
            tvTitle = (TextView) itemView.findViewById(R.id.song_title);
            tvAuthor = (TextView) itemView.findViewById(R.id.song_author);
            ivDuration = (TextView) itemView.findViewById(R.id.song_duration);
            ivPicture = (ImageView) itemView.findViewById(R.id.song_img);
        }
    }


}

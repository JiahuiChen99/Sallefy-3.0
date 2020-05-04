package com.example.myapplication.controller.adapters;

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
import com.example.myapplication.controller.fragments.LibraryLikedPlaylistsFragment;
import com.example.myapplication.model.Playlist;
import com.example.myapplication.restapi.callback.TrackCallback;

import java.util.ArrayList;

public class LikedPlaylistAdapter extends RecyclerView.Adapter<LikedPlaylistAdapter.ViewHolder> {

    public static final String TAG = "User Liked Playlists";
    private ArrayList<Playlist> mPlaylists;
    private LibraryLikedPlaylistsFragment mContext;
    private TrackCallback callback;
    private int NUM_VIEWHOLDERS = 0;

    public LikedPlaylistAdapter(LibraryLikedPlaylistsFragment context, ArrayList<Playlist> playlists){
        this.mContext = context;
        this.mPlaylists = playlists;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivThumbnail;
        TextView tvTrackName;
        LinearLayout mLayout;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            mLayout = itemView.findViewById(R.id.playlist_cover_item_layout);
            ivThumbnail = (ImageView) itemView.findViewById(R.id.user_playlist_cover);
            tvTrackName = (TextView) itemView.findViewById(R.id.user_playlist_name);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called. Num viewHolders: " + NUM_VIEWHOLDERS);

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist_cover, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Like or Dislike playlist
            }
        });
        if (mPlaylists.get(position).getThumbnail() != null) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(20));
            Glide.with(mContext)
                    .asBitmap()
                    .placeholder(R.drawable.logo)
                    .load(mPlaylists.get(position).getThumbnail())
                    .apply(requestOptions)
                    .into(holder.ivThumbnail);

            System.out.println(mPlaylists.get(position).getName());
            holder.tvTrackName.setText(mPlaylists.get(position).getName());
        }
    }

    @Override
    public int getItemCount() {
        return mPlaylists != null ? mPlaylists.size():0;
    }
}

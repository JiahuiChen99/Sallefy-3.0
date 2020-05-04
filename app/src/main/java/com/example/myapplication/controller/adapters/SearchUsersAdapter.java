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
import com.example.myapplication.model.User;
import com.example.myapplication.restapi.callback.UserResourcesCallback;


import java.util.ArrayList;

public class SearchUsersAdapter extends RecyclerView.Adapter<SearchUsersAdapter.ViewHolder> {

    public static final String TAG = "User Playlists";
    private ArrayList<User> users;
    private Context mContext;
    private UserResourcesCallback callback;
    private int NUM_VIEWHOLDERS = 0;
  
    public SearchUsersAdapter(Context context, ArrayList<User> users, UserResourcesCallback callback){
        this.callback = callback;
        this.mContext = context;
        this.users = users;
    }

    public SearchUsersAdapter(Context context, User user, UserResourcesCallback callback){
        this.callback = callback;
        this.mContext = context;
        this.users = new ArrayList<>();
        users.add(user);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivThumbnail;
        ImageView ivFollows;
        ImageView ivPlaylists;
        ImageView ivSongs;
        ImageView ivOptions;
        TextView tvArtistName;
        TextView tvFollows;
        TextView tvPlaylists;
        TextView tvSongs;
        LinearLayout mLayout;


        public ViewHolder(@NonNull View itemView){
            super(itemView);
            mLayout = itemView.findViewById(R.id.artist_long);
            ivThumbnail = (ImageView) itemView.findViewById(R.id.artist_img);
            tvArtistName = (TextView) itemView.findViewById(R.id.artist_title);
            ivFollows = (ImageView) itemView.findViewById(R.id.artist_follows_img);
            ivPlaylists = (ImageView) itemView.findViewById(R.id.artist_playlists_img);
            ivOptions = (ImageView) itemView.findViewById(R.id.artist_options);
            ivSongs = (ImageView) itemView.findViewById(R.id.artist_songs_img);
            tvFollows = (TextView) itemView.findViewById(R.id.artist_follows);
            tvPlaylists = (TextView) itemView.findViewById(R.id.artist_playlists);
            tvSongs = (TextView) itemView.findViewById(R.id.artist_songs);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called. Num viewHolders: " + NUM_VIEWHOLDERS);

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artist_long, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Log.d(TAG, "onBindViewHolder: Called.");

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(20));
        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clicked: " + users.get(position).getLogin());
                callback.onArtistClicked(users.get(position));
            }
        });
        if (users.get(position).getLogin() != null) {
            Glide.with(mContext)
                    .asBitmap()
                    .placeholder(R.drawable.no_user)
                    .load(users.get(position).getImageUrl())
                    .apply(requestOptions)
                    .into(holder.ivThumbnail);
            holder.tvArtistName.setText(users.get(position).getLogin());
            holder.tvArtistName.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            holder.tvArtistName.setSelected(true);

            /*holder.tvFollows.setText(users.get(position).getFollowers().toString());
            holder.tvFollows.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            holder.tvFollows.setSelected(true);
            holder.tvPlaylists.setText(users.get(position).getPlaylists().toString());
            holder.tvPlaylists.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            holder.tvPlaylists.setSelected(true);
            holder.tvSongs.setText(users.get(position).getTracks().toString());

            holder.tvSongs.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            holder.tvSongs.setSelected(true);*/

        }
    }

    @Override
    public int getItemCount() {
        return users != null ? users.size():0;
    }
}


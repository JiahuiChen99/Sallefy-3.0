package com.example.myapplication.controller.adapters;

import android.content.Context;
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
import com.example.myapplication.model.User;

import java.util.ArrayList;

public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ViewHolder> {

    private static final String TAG = "ArtistsAdapter";
    private ArrayList<User> mUsers;
    private Context mContext;
    private int NUM_VIEWHOLDERS = 0;

    public ArtistsAdapter(Context context, ArrayList<User> users ) {
        this.mUsers = users;
        this.mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivThumbnail;
        TextView tvTrackName;
        LinearLayout mLayout;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            mLayout = itemView.findViewById(R.id.artist_item_layout);
            ivThumbnail = (ImageView) itemView.findViewById(R.id.artist_profile_image);
            tvTrackName = (TextView) itemView.findViewById(R.id.artistName);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called. Num viewHolders: " + NUM_VIEWHOLDERS);

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artist_image, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: Called.");

        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clicked: " + mUsers.get(position).getLogin());
            }
        });
        if (mUsers.get(position).getLogin() != null) {
            Glide.with(mContext)
                    .asBitmap()
                    .placeholder(R.drawable.no_user)
                    .load(mUsers.get(position).getImageUrl())
                    .into(holder.ivThumbnail);
            holder.tvTrackName.setText(mUsers.get(position).getLogin());
        }
    }

    @Override
    public int getItemCount() {
        return mUsers != null ? mUsers.size():0;
    }

}

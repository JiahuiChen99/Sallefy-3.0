package com.example.myapplication.controller.adapters;

import android.app.Activity;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;
import com.example.myapplication.model.EmptyItem;
import com.example.myapplication.model.EssencialGenre;
import com.example.myapplication.model.EventItem;
import com.example.myapplication.model.HeaderItem;
import com.example.myapplication.model.ListItem;
import com.example.myapplication.model.SearchGenre;
import com.example.myapplication.model.Track;
import com.example.myapplication.restapi.callback.GenreCallback;
import com.example.myapplication.restapi.callback.TrackCallback;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SearchGenreAdapter extends RecyclerView.Adapter<SearchGenreAdapter.ViewHolder> implements TrackCallback {

    public static final String TAG = "User Playlists";
    private List<ListItem> items;
    private Context mContext;
    private Context activity;
    private GenreCallback callback;
    private TrackCallback tCallback;
    private RecyclerView msongListRecyclerView;
    private ArrayList<Track> tracksEmpty = new ArrayList<>(0);

    public Context getActivity() {
        return activity;
    }
    private int NUM_VIEWHOLDERS = 0;

    public SearchGenreAdapter(Context context, List<ListItem> items, Activity activity, GenreCallback callback, TrackCallback tCallback){
        this.callback = callback;
        this.mContext = context;
        this.items = items;
        this.activity = activity;
        this.tCallback = tCallback;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvGenreName;
        LinearLayout mLayout;

        TextView tvGenreEmpty;

        TextView tvTitle;
        TextView tvAuthor;
        TextView ivDuration;
        ImageView ivPicture;

        public ViewHolder(@NonNull View itemView, Integer id){
            super(itemView);
            if (id == ListItem.TYPE_HEADER) {
                mLayout = itemView.findViewById(R.id.item_genre);
                tvGenreName = (TextView) itemView.findViewById(R.id.textView_genre);
            } else if (id == ListItem.TYPE_EMPTY) {
                mLayout = itemView.findViewById(R.id.item_genre_empty);
                tvGenreEmpty = (TextView) itemView.findViewById(R.id.textView_genre_empty);
            } else {
                mLayout = itemView.findViewById(R.id.track_item_layout);
                tvTitle = (TextView) itemView.findViewById(R.id.song_title);
                tvAuthor = (TextView) itemView.findViewById(R.id.song_author);
                ivDuration = (TextView) itemView.findViewById(R.id.song_duration);
                ivPicture = (ImageView) itemView.findViewById(R.id.song_img);
            }

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called. Num viewHolders: " + NUM_VIEWHOLDERS);

        if (viewType == ListItem.TYPE_HEADER) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_genres, parent, false);
            return new ViewHolder(itemView, viewType);
        } else if (viewType == ListItem.TYPE_EMPTY) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_genres_empty, parent, false);
            return new ViewHolder(itemView, viewType);
        } else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
            return new ViewHolder(itemView, viewType);
        }

    }

    public Context getmContext() {
        return mContext;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        int type = getItemViewType(position);
        if (items.size() > 0) {
            if (type == ListItem.TYPE_HEADER) {
                holder.mLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callback.onGenreSelected(position, TAG);
                    }
                });
                HeaderItem header;
                header = (HeaderItem) items.get(position);
                holder.tvGenreName.setText(header.getGenre());
                holder.tvGenreName.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                holder.tvGenreName.setSelected(true);
            } else if (type == ListItem.TYPE_EMPTY) {
                holder.mLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callback.onGenreSelected(position, TAG);
                    }
                });
                EmptyItem emptyItem;
                emptyItem = (EmptyItem) items.get(position);
                holder.tvGenreEmpty.setText(emptyItem.getGenre());
                holder.tvGenreEmpty.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                holder.tvGenreEmpty.setSelected(true);
            } else {
                holder.mLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tCallback.onTrackSelected(position, TAG);
                    }
                });
                EventItem event;
                event = (EventItem) items.get(position);
                holder.tvTitle.setText(event.getTrack().getName());
                holder.tvTitle.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                holder.tvTitle.setSelected(true);
                holder.tvAuthor.setText(event.getTrack().getUserLogin());
                if (event.getTrack().getDuration() != null) {
                    holder.ivDuration.setText(createTimeLabel(event.getTrack().getDuration()));
                }
                if (event.getTrack().getThumbnail() != null) {
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(20));
                    Glide.with(mContext)
                            .asBitmap()
                            .placeholder(R.drawable.no_user)
                            .load(event.getTrack().getThumbnail())
                            .apply(requestOptions)
                            .into(holder.ivPicture);
                }
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
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size():0;
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
}

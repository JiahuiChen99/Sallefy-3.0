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
import com.example.myapplication.model.EssencialGenre;
import com.example.myapplication.model.SearchGenre;
import com.example.myapplication.model.Track;
import com.example.myapplication.restapi.callback.GenreCallback;
import com.example.myapplication.restapi.callback.TrackCallback;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SearchGenreAdapter extends RecyclerView.Adapter<SearchGenreAdapter.ViewHolder> implements TrackCallback {

    public static final String TAG = "User Playlists";
    private ArrayList<SearchGenre> genres;
    private Context mContext;
    private Context activity;
    private GenreCallback callback;
    private RecyclerView msongListRecyclerView;
    private ArrayList<Track> tracksEmpty = new ArrayList<>(0);

    public Context getActivity() {
        return activity;
    }
    private int NUM_VIEWHOLDERS = 0;

    public SearchGenreAdapter(Context context, ArrayList<SearchGenre> genres, Activity activity, GenreCallback callback){
        this.callback = callback;
        this.mContext = context;
        this.genres = genres;
        this.activity = activity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvGenreName;
        LinearLayout mLayout;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            mLayout = itemView.findViewById(R.id.item_genre);
            tvGenreName = (TextView) itemView.findViewById(R.id.textView_genre);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called. Num viewHolders: " + NUM_VIEWHOLDERS);

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_genres, parent, false);
        msongListRecyclerView = (RecyclerView) itemView.findViewById(R.id.genres_songs);
        LinearLayoutManager songsListManager = new LinearLayoutManager(getmContext(), LinearLayoutManager.VERTICAL, false);
        msongListRecyclerView.setLayoutManager(songsListManager);
        return new ViewHolder(itemView);

    }

    public Context getmContext() {
        return mContext;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        if (genres.size() > 0) {
            holder.mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onGenreSelected(position, TAG);
                }
            });
                holder.tvGenreName.setText(genres.get(position).getGenre());
                holder.tvGenreName.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                holder.tvGenreName.setSelected(true);
                List<Track> tracks = genres.get(position).getTracks();
                //if (genres.get(position).getTracks().get())

                TrackListAdapter adapter = new TrackListAdapter(this, getmContext(), (ArrayList) tracks);
                msongListRecyclerView.setAdapter(adapter);
        }
    }

    @Override
    public int getItemCount() {
        return genres != null ? genres.size():0;
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

package com.example.myapplication.controller.fragments;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.controller.adapters.TrackListAdapter;
import com.example.myapplication.controller.adapters.UserPlaylistAdapter;
import com.example.myapplication.model.Playlist;
import com.example.myapplication.model.Track;
import com.example.myapplication.model.User;
import com.example.myapplication.model.UserToken;
import com.example.myapplication.restapi.callback.PlaylistCallback;
import com.example.myapplication.restapi.callback.TrackCallback;
import com.example.myapplication.restapi.manager.PlaylistManager;
import com.example.myapplication.restapi.manager.TrackManager;
import com.example.myapplication.restapi.manager.UserResourcesManager;
import com.example.myapplication.utils.Sesion;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import java.util.ArrayList;
import java.util.List;

import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

public class ProfileFragment extends Fragment implements PlaylistCallback, TrackCallback {

    private User user;
    private String userName;

    private BoomMenuButton btnMore;

    private TextView tvArtistName;
    private TextView tvFirstName;
    private TextView tvLastName;
    private TextView tvLanguageKey;
    private TextView tvFollowers;
    private TextView tvFollowing;
    private ImageView ivProfileImage;

    private RecyclerView mArtistSongsRecyclerView;
    private RecyclerCoverFlow mArtistAlbumsRecyclerView;
    private RecyclerView mArtistAlbumSongsRecyclerView;
    private ArrayList<Playlist> mAlbums;
    private ArrayList<Track> mSongs;
    private Integer playlistID = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);

        btnMore = view.findViewById(R.id.profile_more_button);
        btnMore.setButtonEnum(ButtonEnum.TextInsideCircle);
        btnMore.setPiecePlaceEnum(PiecePlaceEnum.DOT_3_1);
        btnMore.setButtonPlaceEnum(ButtonPlaceEnum.SC_3_1);
        btnMore.setNormalColor(Color.rgb(255, 105, 105));
        TextInsideCircleButton.Builder builder = new TextInsideCircleButton.Builder()
                .normalImageRes(R.drawable.ic_cloud_upload_black_24dp)
                .normalText("Upload Song");

        builder.imageRect(new Rect(80, 70, 10, 10));
        builder.textSize(10);
        builder.ellipsize(TextUtils.TruncateAt.MARQUEE);
        btnMore.addBuilder(builder);

        builder = new TextInsideCircleButton.Builder()
                .normalImageRes(R.drawable.ic_playlist_add_black_24dp)
                .normalText("New Playlist");
        builder.imageRect(new Rect(80, 70, 10, 10));
        btnMore.addBuilder(builder);

        builder = new TextInsideCircleButton.Builder()
                .normalImageRes(R.drawable.ic_log_out_outline)
                .normalText("Logout");
        builder.imageRect(new Rect(80, 70, 10, 10));
        btnMore.addBuilder(builder);


        ivProfileImage = view.findViewById(R.id.artist_profile_image);
        tvArtistName = view.findViewById(R.id.profile_artist_login);
        tvFirstName = view.findViewById(R.id.profile_artist_first_name) ;
        tvLastName = view.findViewById(R.id.profile_artist_last_name);
        tvLanguageKey = view.findViewById(R.id.profile_artist_language);
        tvFollowers = view.findViewById(R.id.profile_artist_num_followers);
        tvFollowing = view.findViewById(R.id.profile_artist_num_following) ;

        mArtistAlbumsRecyclerView = (RecyclerCoverFlow) view.findViewById(R.id.profile_artist_albums);
        CoverFlowLayoutManger artistAlbumsManager = new CoverFlowLayoutManger(false, false, true, (float) 1);
        UserPlaylistAdapter artistAlbumsAdapter = new UserPlaylistAdapter(getContext(), null);
        mArtistAlbumsRecyclerView.setLayoutManager(artistAlbumsManager);
        mArtistAlbumsRecyclerView.setAdapter(artistAlbumsAdapter);
        mArtistAlbumsRecyclerView.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
                playlistID = position;
                TrackListAdapter albumSongsListAdapter = new TrackListAdapter(ProfileFragment.this, getContext(), (ArrayList<Track>) mAlbums.get(position).getTracks());
                mArtistAlbumSongsRecyclerView.setAdapter(albumSongsListAdapter);
            }
        });

        mArtistAlbumSongsRecyclerView = (RecyclerView) view.findViewById(R.id.profile_artist_albums_songs);
        LinearLayoutManager artistAlbumsSongsManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mArtistAlbumSongsRecyclerView.setLayoutManager(artistAlbumsSongsManager);

        mArtistSongsRecyclerView =(RecyclerView) view.findViewById(R.id.profile_artist_songs);
        LinearLayoutManager artistSongsManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mArtistSongsRecyclerView.setLayoutManager(artistSongsManager);

        this.user = Sesion.getInstance(getContext()).getUser();
        this.userName = Sesion.getInstance(getContext()).getUser().getLogin();
        System.out.println("UserName: " + this.userName);

        getData();
        updateData();

        return view;
    }

    private void updateData(){
        Glide.with(getContext())
                .asBitmap()
                .placeholder(R.drawable.no_user)
                .load(this.user.getImageUrl())
                .into(ivProfileImage);
        tvArtistName.setText(this.user.getLogin());
        tvFirstName.setText(this.user.getFirstName());
        tvLastName.setText(this.user.getLastName());
        tvLanguageKey.setText(this.user.getLangKey());
        tvFollowers.setText(this.user.getFollowers().toString());
        tvFollowing.setText(this.user.getFollowing().toString());
    }

    private void getData(){
        PlaylistManager.getInstance(this.getActivity()).getSpecificUserPlaylists(userName,this);
        UserResourcesManager.getInstance(getContext()).getSpecificArtistSongs( userName, this);

        mSongs = new ArrayList<>();
        mAlbums = new ArrayList<>();
    }

    @Override
    public void onPlaylistReceived(Playlist playlists) {

    }

    @Override
    public void onNoPlaylists(Throwable throwable) {

    }

    @Override
    public void onPlaylistCreated(Playlist playlist) {

    }

    @Override
    public void onPlaylistFailure(Throwable throwable) {

    }

    @Override
    public void onUserPlaylistsReceived(List<Playlist> playlists) {
        mAlbums = (ArrayList) playlists;
        UserPlaylistAdapter adapter = new UserPlaylistAdapter(getContext(), mAlbums);
        mArtistAlbumsRecyclerView.setAdapter(adapter);

        TrackListAdapter albumSongsListAdapter = new TrackListAdapter(ProfileFragment.this, getContext(), (ArrayList<Track>) mAlbums.get(0).getTracks());
        mArtistAlbumSongsRecyclerView.setAdapter(albumSongsListAdapter);
    }

    @Override
    public void onNoUserPlaylists(Throwable throwable) {

    }

    @Override
    public void onUserSpecificLikedPlaylistReceived(Playlist specificPlaylist) {

    }

    @Override
    public void onNoUserSpecificLikedPlaylist(Throwable throwable) {

    }

    @Override
    public void onFailure(Throwable throwable) {

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
        //TODO: Music Player when a song is selected
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
        mSongs = (ArrayList) artistTracks;
        TrackListAdapter adapter = new TrackListAdapter(this, getContext(), mSongs);
        mArtistSongsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onNoArtistTracks(Throwable noArtistTracks) {

    }
}

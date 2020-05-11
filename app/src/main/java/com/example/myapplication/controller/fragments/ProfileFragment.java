package com.example.myapplication.controller.fragments;

import android.content.Intent;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.controller.activities.AddSongActivity;
import com.example.myapplication.controller.adapters.TrackListAdapter;
import com.example.myapplication.controller.adapters.UserPlaylistAdapter;
import com.example.myapplication.controller.music.MusicCallback;
import com.example.myapplication.model.Playlist;
import com.example.myapplication.model.Track;
import com.example.myapplication.model.User;
import com.example.myapplication.restapi.callback.PlaylistCallback;
import com.example.myapplication.restapi.callback.TrackCallback;
import com.example.myapplication.restapi.callback.UserResourcesCallback;
import com.example.myapplication.restapi.manager.PlaylistManager;
import com.example.myapplication.restapi.manager.UserResourcesManager;
import com.example.myapplication.utils.Sesion;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import java.util.ArrayList;
import java.util.List;

import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

public  class ProfileFragment extends Fragment implements PlaylistCallback, TrackCallback, UserResourcesCallback {

    private static final String FOLLOW = "FollowIcon";
    private static final String FOLLOWING = "FollowingIcon";

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
    private Button btnFollowUnfollow;

    private RecyclerView mArtistSongsRecyclerView;
    private RecyclerCoverFlow mArtistAlbumsRecyclerView;
    private RecyclerView mArtistAlbumSongsRecyclerView;
    private ArrayList<Playlist> mAlbums;
    private ArrayList<Track> mSongs;
    private Integer playlistID = 0;


    private LinearLayout followers;
    private LinearLayout following;

    private MusicCallback sendTracksCallback;

    private ConstraintLayout userProfileLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);

        userProfileLayout = view.findViewById(R.id.user_profile_layout);

        following = view.findViewById(R.id.linearLayout8);
        following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserResourcesManager.getInstance(getContext()).getUserFollowing(ProfileFragment.this);
            }
        });
        followers = view.findViewById(R.id.linearLayout6);
        followers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserResourcesManager.getInstance(getContext()).getUserFollowers(ProfileFragment.this);
            }
        });
        btnMore = view.findViewById(R.id.profile_more_button);

        btnMore.setButtonEnum(ButtonEnum.TextInsideCircle);
        btnMore.setPiecePlaceEnum(PiecePlaceEnum.DOT_3_1);
        btnMore.setButtonPlaceEnum(ButtonPlaceEnum.SC_3_1);
        btnMore.setNormalColor(Color.rgb(255, 105, 105));
        TextInsideCircleButton.Builder builder = new TextInsideCircleButton.Builder()
                .normalImageRes(R.drawable.ic_cloud_upload_black_24dp)
                .normalText("Upload Song");

        builder.listener(new OnBMClickListener() {
            @Override
            public void onBoomButtonClick(int index) {
                Intent uploadActivity = new Intent(getContext(), AddSongActivity.class);
                startActivity(uploadActivity);
            }
        });

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
        btnFollowUnfollow = view.findViewById(R.id.profile_artist_follow_button);
        btnFollowUnfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserResourcesManager.getInstance(getContext()).followUnfollowArtist(user.getLogin(), ProfileFragment.this);
            }
        });

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

        Bundle bundle = getArguments();

        this.user = bundle.getParcelable("artist");
        this.userName = this.user.getLogin();
        System.out.println("UserName: " + this.userName);
        if(userName.equalsIgnoreCase(Sesion.getInstance(getContext()).getUser().getLogin())){
            btnMore.setVisibility(View.VISIBLE);
            btnFollowUnfollow.setVisibility(View.INVISIBLE);
        }else{
            btnMore.setVisibility(View.INVISIBLE);
            resize();
        }

        getData();

        return view;
    }

    public void resize(){
        userProfileLayout.setPadding(0,0,0,0);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try {
            sendTracksCallback = (MusicCallback) context;
        }catch (ClassCastException e){
            System.out.println("Error, class doesn't implement the interface");
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        sendTracksCallback = null;
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

        //If we are not looking at our profile
        if(!this.userName.equals(Sesion.getInstance(getContext()).getUser().getLogin())){
            UserResourcesManager.getInstance(getContext()).checkIfFollowed(user.getLogin(), ProfileFragment.this);
        }
    }
    public void followUnfollow(boolean isFollowing){
        if(isFollowing){
            btnFollowUnfollow.setBackgroundResource(R.drawable.ic_following);
            btnFollowUnfollow.setTag(FOLLOWING);
        }else{
            btnFollowUnfollow.setBackgroundResource(R.drawable.ic_follow);
            btnFollowUnfollow.setTag(FOLLOW);
        }
    }

    private void getData(){
        UserResourcesManager.getInstance(getContext()).getUser(userName, this);
        PlaylistManager.getInstance(this.getActivity()).getSpecificUserPlaylists(userName,this);
        UserResourcesManager.getInstance(getContext()).getSpecificArtistSongs( userName, this);

        mSongs = new ArrayList<>();
        mAlbums = new ArrayList<>();
    }

    @Override
    public void onPlaylistReceived(List<Playlist> playlists) {

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
        if(mAlbums.size() != 0){
            UserPlaylistAdapter adapter = new UserPlaylistAdapter(getContext(), mAlbums);
            mArtistAlbumsRecyclerView.setAdapter(adapter);

            TrackListAdapter albumSongsListAdapter = new TrackListAdapter(ProfileFragment.this, getContext(), (ArrayList<Track>) mAlbums.get(0).getTracks());
            mArtistAlbumSongsRecyclerView.setAdapter(albumSongsListAdapter);
        }
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
    public void onUsersReceived(List<User> tracks) {

    }

    @Override
    public void onNoUsers(Throwable throwable) {

    }

    @Override
    public void onFailure(Throwable throwable) {

    }

    @Override
    public void onArtistsReceived(List<User> users) {

    }

    @Override
    public void onUserReceived(User user) {
        this.user = user;
        updateData();
    }

    @Override
    public void onNoUserReceived(Throwable throwable) {

    }

    @Override
    public void onFollowingArtistsReceived(List<User> followingArtists) {

    }

    @Override
    public void onNoFollowingArtists(Throwable noFollowingArtists) {

    }

    @Override
    public void onUserFollowingReceived(List<User> followingArtists) {
        btnMore.setVisibility(View.INVISIBLE);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("users", (ArrayList<User>) followingArtists);
        bundle.putString("type", "following");
        Fragment fragment = new UsersListFragment();
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.user_profile_layout, fragment).commit();
    }

    @Override
    public void onNoUserFollowing(Throwable noFollowingArtists) {

    }

    @Override
    public void onUserFollowersReceived(List<User> followers) {
        btnMore.setVisibility(View.INVISIBLE);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("users", (ArrayList<User>) followers);
        bundle.putString("type", "follower");
        Fragment fragment = new UsersListFragment();
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.user_profile_layout, fragment).commit();
    }

    @Override
    public void onNoUserFollowers(Throwable noFollowers) {

    }

    @Override
    public void onUserFollowedUnfollowed(User user) {
        followUnfollow(user.getFollowed());
    }

    @Override
    public void onNoUserFollowedUnfollowed(Throwable throwable) {

    }

    @Override
    public void onArtistClicked(User clickedArtist) {

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
        //TODO: Discern between Artist Album and Artist Songs!
        sendTracksCallback.setTracks(mSongs, id);
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

    @Override
    public void onTrackUploaded(Track uploadedTrack) {

    }

    @Override
    public void onNoTrackUploaded(Throwable notUploaded) {

    }
}

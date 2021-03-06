package com.example.myapplication.controller.activities;

import android.app.DownloadManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.mediarouter.app.MediaRouteButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;
import com.example.myapplication.controller.fragments.ExploreFragment;
import com.example.myapplication.controller.fragments.PlaylistSongsFragment;
import com.example.myapplication.controller.fragments.ProfileFragment;
import com.example.myapplication.controller.fragments.LibraryFragment;
import com.example.myapplication.controller.fragments.SearchFragment;
import com.example.myapplication.utils.Sesion;
import com.example.myapplication.controller.music.MusicCallback;
import com.example.myapplication.controller.music.MusicService;
import com.example.myapplication.model.Track;
import com.example.myapplication.restapi.callback.TrackCallback;
import com.example.myapplication.restapi.manager.TrackManager;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaLoadRequestData;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.framework.CastButtonFactory;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.CastStateListener;
import com.google.android.gms.cast.framework.SessionManagerListener;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.common.images.WebImage;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PaginaPrincipalActivity extends AppCompatActivity implements MusicCallback, TrackCallback {

    private MusicService musicService;
    private ServiceConnection mConnection = new ServiceConnection() {

        /************************************************************************
         * This is called when the connection with the service has been stablished,
         * giving us the service object we can use to interact with the service
         ************************************************************************/
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicService = ((MusicService.MusicBinder)service).getService();
            musicService.setCallback(PaginaPrincipalActivity.this);
        }

        /************************************************************************
         * We shoulding see this happen, this is called when the connection with
         * the service has been unexpectedly disconnected, its process crashed
         ************************************************************************/
        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicService = null;
        }
    };
    private boolean mIsBound;

    private SlidingUpPanelLayout mLayout;

    private FragmentManager fmFragment;
    private FragmentTransaction ftFragment;
    private BottomNavigationView bnvMenu;

    private String url;
    private String songName;
    private String artistName;
    private String songURL;

    private static final String PLAY_VIEW = "PlayIcon";
    private static final String STOP_VIEW = "StopIcon";
    private static final String LIKED = "LikeIcon";
    private static final String NOT_LIKED = "NotLikedIcon";

    private TextView tvTitle;
    private ImageView ivThumbnail;
    private TextView tvSongName;
    private TextView tvArtist;

    private ImageButton btnBack;
    private ImageButton btnMore;
    private SeekBar mSeekBar;
    private TextView tvStartTime;
    private TextView tvEndTime;

    private ImageButton btnPreviousSong;
    private ImageButton btnBackward;
    private ImageButton btnPlayStop;
    private ImageButton btnForward;
    private ImageButton btnNextSong;

    private ShineButton btnLikeSong;
    private ImageButton btnAddToPlaylist;
    private ImageButton btnDownload;
    private ImageButton btnShareSong;

    private ImageView ivHeaderThumbnail;
    private TextView tvHeaderSongName;
    private TextView tvHeaderArtist;
    private ImageButton btnHeaderPlayStop;

    private LinearLayout expandedHeader;
    private ConstraintLayout shrinkedHeader;
    RelativeLayout shrinkedLayout;

    private CastContext mCastContext;
    private MenuItem mediaRouteMenuItem;
    private CastSession mCastSession;
    private androidx.mediarouter.app.MediaRouteButton mMediaRouteButton;
    private SessionManagerListener<CastSession> mSessionManagerListener;
    private String imageURL;

    private int mDuration;
    private int songID = 0;
    private Runnable mRunnable;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        this.url = getIntent().getStringExtra("songID");
        this.songName = getIntent().getStringExtra("songName");
        this.artistName = getIntent().getStringExtra("songArtist");

        if(url == null){
            this.url = "https://livedoor.blogimg.jp/future48/imgs/f/1/f1c032b5.jpg";
        }


        initService();
        setContentView(R.layout.activity_menu_bar);
        setInicialFragment();
        initViews();
        initViewsSongUI();
        loadData();
        configSliding();

        mMediaRouteButton = (MediaRouteButton) findViewById(R.id.media_route_button);
        CastButtonFactory.setUpMediaRouteButton(getApplicationContext(), mMediaRouteButton);
        mCastContext = CastContext.getSharedInstance(this);
        mSessionManagerListener = new SessionManagerListener<CastSession>() {
            @Override
            public void onSessionStarting(CastSession castSession) {
            }

            @Override
            public void onSessionStarted(CastSession castSession, String s) {
                mCastSession = mCastContext.getSessionManager().getCurrentCastSession();
                loadRemoteMedia(0, true);
            }

            @Override
            public void onSessionStartFailed(CastSession castSession, int i) {

            }

            @Override
            public void onSessionEnding(CastSession castSession) {

            }

            @Override
            public void onSessionEnded(CastSession castSession, int i) {

            }

            @Override
            public void onSessionResuming(CastSession castSession, String s) {

            }

            @Override
            public void onSessionResumed(CastSession castSession, boolean b) {

            }

            @Override
            public void onSessionResumeFailed(CastSession castSession, int i) {

            }

            @Override
            public void onSessionSuspended(CastSession castSession, int i) {

            }
        };

        mCastContext.getSessionManager().addSessionManagerListener(mSessionManagerListener, CastSession.class);
    }

    private void loadRemoteMedia(int position, boolean autoPlay){
        if(mCastSession == null){
            System.out.println("NULLLLLLLLLLLLLLLLLLLLLLLLLL");
            return;
        }
        RemoteMediaClient remoteMediaClient = mCastSession.getRemoteMediaClient();
        if(remoteMediaClient == null){
            System.out.println("NULLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL 2");
            return;
        }
        remoteMediaClient.load(new MediaLoadRequestData.Builder()
                        .setMediaInfo(buildMediaInfo())
                        .setAutoplay(autoPlay)
                        .setCurrentTime(position)
                        .build());
    }

    private MediaInfo buildMediaInfo(){
        MediaMetadata songMetadata = new MediaMetadata(MediaMetadata.MEDIA_TYPE_MUSIC_TRACK);

        songMetadata.putString(MediaMetadata.KEY_TITLE, tvSongName.getText().toString());
        songMetadata.putString(MediaMetadata.KEY_ARTIST, artistName);
        songMetadata.addImage(new WebImage(Uri.parse(imageURL)));
        return new MediaInfo.Builder(musicService.getTrack().getUrl())
                .setStreamDuration(MediaInfo.STREAM_TYPE_BUFFERED)
                .setContentType("audio/mpeg")
                .setMetadata(songMetadata)
                .setStreamDuration(musicService.getTrack().getDuration()*1000)
                .build();
    }

    private void initService(){
        //Establish a connection with the service
        bindService(new Intent(this, MusicService.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    private void doUnbindService(){
        if(mIsBound){
            //Detach our existing connection
            unbindService(mConnection);
            mIsBound = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doUnbindService();
    }

    private void initViews() {

        fmFragment = getSupportFragmentManager();
        ftFragment = fmFragment.beginTransaction();


        bnvMenu = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bnvMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch (item.getItemId()) {
                    case R.id.action_explore:
                        fragment = new ExploreFragment();
                        break;

                    case R.id.action_search:
                        fragment = new SearchFragment();
                        break;

                    case R.id.action_content:
                        fragment = new LibraryFragment();
                        break;
                    case R.id.action_profile:
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("artist", Sesion.getInstance(getApplicationContext()).getUser());
                        fragment = new ProfileFragment();
                        fragment.setArguments(bundle);
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                //replaceFragment(fragment);
                return true;
            }
        });

    }

    private void initViewsSongUI(){
        btnBack = findViewById(R.id.down_button);
        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });
        tvTitle = findViewById(R.id.track_header);
        ivThumbnail = findViewById(R.id.song_thumbnail);
        tvSongName = findViewById(R.id.song_title);
        tvArtist = findViewById(R.id.song_author);
        tvStartTime = findViewById(R.id.start_time);
        tvEndTime = findViewById(R.id.end_time);
        btnPreviousSong = (ImageButton) findViewById(R.id.previous_song);
        btnPreviousSong.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                musicService.updateTrack(-1);
            }
        });
        btnBackward = (ImageButton) findViewById(R.id.backwards);
        btnBackward.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                musicService.seekTo(musicService.getCurrentPosition() - 5000);
            }
        });
        btnPlayStop = (ImageButton) findViewById(R.id.play_stop);
        btnPlayStop.setTag(PLAY_VIEW);
        btnPlayStop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(btnPlayStop.getTag().equals(PLAY_VIEW)){
                    btnPlayStop.setImageResource(R.drawable.ic_pause_outline);
                    btnPlayStop.setTag(STOP_VIEW);
                    musicService.playAudio();
                }else{
                    musicService.pauseAudio();
                    btnPlayStop.setImageResource(R.drawable.ic_play_outline);
                    btnPlayStop.setTag(PLAY_VIEW);
                }
            }
        });
        btnForward = (ImageButton) findViewById(R.id.forward);
        btnForward.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                musicService.seekTo(musicService.getCurrentPosition() + 5000);
            }
        });
        btnNextSong = (ImageButton) findViewById(R.id.next_song);
        btnNextSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicService.updateTrack(1);
            }
        });

        mSeekBar = (SeekBar) findViewById(R.id.song_progress);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    musicService.seekTo(progress);
                }
                if (mDuration > 0) {
                    System.out.println(mDuration + ": " + progress);
                    int newProgress = ((progress*100)/mDuration);
                    System.out.println("New progress: " + newProgress);
                    if(newProgress == 100){
                        btnPlayStop.setImageResource(R.drawable.ic_play_outline);
                        btnPlayStop.setTag(PLAY_VIEW);
                    }
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnLikeSong = (ShineButton) findViewById(R.id.like);
        btnLikeSong.init(this);
        btnLikeSong.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                TrackManager.getInstance(getApplicationContext()).likeTrack(musicService.getTracks().get(songID).getId(), PaginaPrincipalActivity.this);
            }
        });
        btnAddToPlaylist = (ImageButton) findViewById(R.id.add_to_playlist);
        btnAddToPlaylist.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                doTransition();
            }
        });
        btnDownload = (ImageButton) findViewById(R.id.download);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(songURL);

                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setTitle(songName);
                request.setDescription("Downloading ...");
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, songName);

                downloadManager.enqueue(request);
            }
        });
        btnShareSong = (ImageButton) findViewById(R.id.share);
        btnShareSong.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent send = new Intent(Intent.ACTION_SEND);
                send.putExtra(Intent.EXTRA_TEXT, songURL);

                send.setType("text/plain");

                send.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                startActivity(Intent.createChooser(send, "SNS"));
            }
        });
        mHandler = new Handler();


        ivHeaderThumbnail = (ImageView) findViewById(R.id.artist_profile_image);
        tvHeaderSongName = (TextView) findViewById(R.id.header_song_title);
        tvHeaderArtist = (TextView) findViewById(R.id.header_song_artist);
        btnHeaderPlayStop = (ImageButton) findViewById(R.id.header_play_button);
        btnHeaderPlayStop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(btnPlayStop.getTag().equals(PLAY_VIEW)){
                    btnPlayStop.setImageResource(R.drawable.ic_pause_outline);
                    btnPlayStop.setTag(STOP_VIEW);
                    musicService.playAudio();
                }else{
                    musicService.pauseAudio();
                    btnPlayStop.setImageResource(R.drawable.ic_play_outline);
                    btnPlayStop.setTag(PLAY_VIEW);
                }
            }
        });

        shrinkedHeader = findViewById(R.id.shrinked_header);
        shrinkedHeader.setVisibility(View.VISIBLE);

        expandedHeader = findViewById(R.id.linearLayout3);
        expandedHeader.setVisibility(View.INVISIBLE);
    }

    private void doTransition() {
        Track tAux =  musicService.getTracks().get(songID);

        Intent i = new Intent(getApplicationContext(), AddSongToPlaylistActivity.class);
        i.putExtra("track", tAux);
        startActivity(i);
    }

    private void configSliding(){
        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mLayout.setPanelHeight(360);
        shrinkedLayout = findViewById(R.id.shrinked_player);
        //shrinkedLayout.setVisibility(View.INVISIBLE);
        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i("HELLO", "onPanelSlide, offset " + slideOffset);
                //TODO: IF slideOffset 1 = Change UI
                if(slideOffset >= 0.2){
                    shrinkedHeader = findViewById(R.id.shrinked_header);
                    shrinkedHeader.setVisibility(View.INVISIBLE);

                    bnvMenu.setVisibility(View.GONE);
                    expandedHeader = findViewById(R.id.linearLayout3);
                    expandedHeader.setVisibility(View.VISIBLE);
                }
                if(slideOffset == 0){
                    shrinkedHeader = findViewById(R.id.shrinked_header);
                    shrinkedHeader.setVisibility(View.VISIBLE);

                    bnvMenu.setVisibility(View.VISIBLE);
                    expandedHeader = findViewById(R.id.linearLayout3);
                    expandedHeader.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {

            }
        });

        mLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });
    }

    private void loadData(){
        tvSongName.setText(this.songName);
        tvSongName.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tvSongName.setSelected(true);
        tvArtist.setText(this.artistName);
        Glide.with(this)
                .asBitmap()
                .load(this.url)
                .into(ivThumbnail);

        tvHeaderSongName.setText(this.songName);
        tvHeaderSongName.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tvHeaderSongName.setSelected(true);

        tvHeaderArtist.setText(this.artistName);

        Glide.with(this)
                .asBitmap()
                .load(this.url)
                .into(ivHeaderThumbnail);
    }

    private void setInicialFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ExploreFragment()).commit();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onMusicPlayerPrepared() {

    }

    @Override
    public void setTracks(ArrayList<Track> playlist, int currentTrack) {
        musicService.setTracks(playlist, currentTrack);
    }

    @Override
    public void setTrack(Track song) {
        musicService.setTrack(song);
    }


    @Override
    public void updatePlayPauseButton(boolean playing) {
        if(playing){
            btnPlayStop.setImageResource(R.drawable.ic_pause_outline);
            btnPlayStop.setTag(STOP_VIEW);

            btnHeaderPlayStop.setImageResource(R.drawable.ic_pause_outline);
        }else{
            btnPlayStop.setImageResource(R.drawable.ic_play_outline);
            btnPlayStop.setTag(PLAY_VIEW);

            btnHeaderPlayStop.setImageResource(R.drawable.ic_play_outline);
        }
    }

    @Override
    public void updateLikeButton(boolean liked) {
        if(liked){
            btnLikeSong.setChecked(true);
            btnLikeSong.setShapeResource(R.drawable.ic_heart);
            btnLikeSong.setBtnColor(Color.RED);
            btnLikeSong.setBtnFillColor(Color.RED);
            btnLikeSong.setColorFilter(Color.RED);
            btnLikeSong.setTag(LIKED);
        }else{
            btnLikeSong.setChecked(false);
            btnLikeSong.setShapeResource(R.drawable.ic_heart_outline);
            btnLikeSong.setColorFilter(Color.WHITE);
            btnLikeSong.setBtnColor(Color.WHITE);
            btnLikeSong.setBtnFillColor(Color.WHITE);
            btnLikeSong.setTag(NOT_LIKED);
        }
    }

    @Override
    public void updateSongTitle(String title) {
        tvSongName.setText(title);
        tvSongName.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tvSongName.setSelected(true);

        tvHeaderSongName.setText(title);
        tvHeaderSongName.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tvHeaderSongName.setSelected(true);


        songName = title;
    }

    @Override
    public void updateArtist(String artistName) {
        tvArtist.setText(artistName);

        tvHeaderArtist.setText(artistName);
    }

    @Override
    public void updateSongImage(String imageURL) {
        this.imageURL = imageURL;
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(30));
        Glide.with(this)
                .asBitmap()
                .load(imageURL)
                .apply(requestOptions)
                .into(ivThumbnail);

        Glide.with(this)
                .asBitmap()
                .load(imageURL)
                .into(ivHeaderThumbnail);

    }

    @Override
    public void updateSeekBar(int currentPosition) {
        tvStartTime.setText(createTimeLabel(currentPosition));
        mSeekBar.setProgress(currentPosition);

        if(musicService.getMediaPlayer().isPlaying()){
            mRunnable = new Runnable() {
                @Override
                public void run() {
                    updateSeekBar(musicService.getMediaPlayer().getCurrentPosition());
                }
            };
            mHandler.postDelayed(mRunnable, 1000);
        }
    }

    @Override
    public void updateMaxSeekBar(int currentPosition) {
        mDuration = currentPosition;
        mSeekBar.setMax(currentPosition);
        tvEndTime.setText(createTimeLabel(currentPosition));
    }

    @Override
    public void showShrinkedBar(boolean show) {
        if(show){
            shrinkedLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setSongID(int songID) {
        this.songID = songID;
    }

    @Override
    public void updateSongURL(String url) {
        this.songURL = url;
    }

    private String createTimeLabel(Integer duration){
        String timerLabel = "";
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;

        timerLabel += min + ":";

        if(sec < 10) timerLabel += "0";

        timerLabel += sec;

        return timerLabel;
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
        if(like.isLiked()){
            btnLikeSong.setChecked(true);
            btnLikeSong.setShapeResource(R.drawable.ic_heart);
            btnLikeSong.setBtnColor(Color.RED);
            btnLikeSong.setBtnFillColor(Color.RED);
            btnLikeSong.setColorFilter(Color.RED);
            btnLikeSong.setTag(LIKED);
        }else{
            btnLikeSong.setChecked(false);
            btnLikeSong.setShapeResource(R.drawable.ic_heart_outline);
            btnLikeSong.setColorFilter(Color.WHITE);
            btnLikeSong.setBtnColor(Color.WHITE);
            btnLikeSong.setBtnFillColor(Color.WHITE);
            btnLikeSong.setTag(NOT_LIKED);
        }
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
    public void onTrackUploaded(Track uploadedTrack) {

    }

    @Override
    public void onNoTrackUploaded(Throwable notUploaded) {

    }

    @Override
    public void onFailure(Throwable throwable) {

    }
}

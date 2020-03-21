package com.example.myapplication.restapi.manager;

import android.content.Context;
import android.util.Log;


import com.example.myapplication.model.Playlist;
import com.example.myapplication.model.UserToken;
import com.example.myapplication.restapi.callback.PlaylistCallback;
import com.example.myapplication.restapi.service.PlaylistService;
import com.example.myapplication.utils.Sesion;
import com.example.myapplication.utils.Variables;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlaylistManager {

    private static final String TAG = PlaylistManager.class.getName();

    private Context mContext;
    private static PlaylistManager sPlaylist;
    private PlaylistService mService;
    private Retrofit mRetrofit;



    public static PlaylistManager getInstance (Context context) {
        if (sPlaylist == null) {
            sPlaylist = new PlaylistManager(context);
        }

        return sPlaylist;
    }

    public PlaylistManager(Context context) {
        mContext = context;

        mRetrofit = new Retrofit.Builder()
                .baseUrl(Variables.NETWORK.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = mRetrofit.create(PlaylistService.class);
    }


    public void createPlaylist(Playlist playlist, final PlaylistCallback callback) {
        UserToken userToken = Sesion.getInstance(mContext).getUserToken();
        Call<Playlist> call = mService.createPlaylist(playlist, "Bearer " + userToken.getIdToken());
        call.enqueue(new Callback<Playlist>() {
            @Override
            public void onResponse(Call<Playlist> call, Response<Playlist> response) {
                int code = response.code();

                if (response.isSuccessful()) {
                    callback.onPlaylistCreated(response.body());
                } else {
                    try {
                        callback.onPlaylistFailure(new Throwable(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Playlist> call, Throwable t) {
                callback.onPlaylistFailure(t);
            }
        });
    }

    public synchronized void seePlaylists(Integer num, final PlaylistCallback playlistCallback) {
        UserToken userToken = Sesion.getInstance(mContext).getUserToken();

        Call<Playlist> call = mService.getAllPlaylist(num, "Bearer " + userToken.getIdToken());
        call.enqueue(new Callback<Playlist>() {
            @Override
            public void onResponse(Call<Playlist> call, Response<Playlist> response) {
                int code = response.code();

                if (response.isSuccessful()) {
                    playlistCallback.onPlaylistReceived(response.body());
                } else {
                    Log.d(TAG, "Error Not Successful: " + code);
                    playlistCallback.onNoPlaylists(new Throwable("ERROR " + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<Playlist> call, Throwable t) {
                Log.d(TAG, "Error Failure: " + t.getStackTrace());
                playlistCallback.onFailure(new Throwable("ERROR " + t.getStackTrace()));
            }
        });

    }


    public synchronized void modifyPlaylist(Playlist playlist, final PlaylistCallback playlistcallback) {
        UserToken userToken = Sesion.getInstance(mContext).getUserToken();

        Call<Playlist> call = mService.modifyPlaylist(playlist, "Bearer " + userToken.getIdToken());
        call.enqueue(new Callback<Playlist>() {
            @Override
            public void onResponse(Call<Playlist> call, Response<Playlist> response) {
                int code = response.code();

                if (response.isSuccessful()) {
                    playlistcallback.onPlaylistCreated(response.body());
                } else {
                    try {
                        playlistcallback.onPlaylistFailure(new Throwable(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Playlist> call, Throwable t) {
                playlistcallback.onPlaylistFailure(t);
            }
        });
    }
}

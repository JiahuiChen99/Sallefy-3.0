package com.example.myapplication.restapi.manager;

import android.content.Context;
import android.util.Log;


import com.example.myapplication.model.Follow;
import com.example.myapplication.model.Playlist;
import com.example.myapplication.model.UserToken;
import com.example.myapplication.restapi.callback.PlaylistCallback;
import com.example.myapplication.restapi.service.PlaylistService;
import com.example.myapplication.utils.Sesion;
import com.example.myapplication.utils.Variables;

import java.io.IOException;
import java.util.List;

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

    public synchronized void getAllPlaylists(String page, final PlaylistCallback playlistCallback) {
        UserToken userToken = Sesion.getInstance(mContext).getUserToken();

        Call<List<Playlist>> call = mService.getAllPlaylist(page,"Bearer " + userToken.getIdToken());
        call.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                int code = response.code();

                if (response.isSuccessful()) {
                    playlistCallback.onPlaylistReceived(response.body());
                } else {
                    Log.d(TAG, "Error Not Successful: " + code);
                    playlistCallback.onNoPlaylists(new Throwable("ERROR " + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {
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

    public synchronized void getUserPlaylists(final PlaylistCallback playlistcallback) {
        UserToken userToken = Sesion.getInstance(mContext).getUserToken();

        Call<List<Playlist>> call = mService.getUserPlaylists("Bearer " + userToken.getIdToken());
        call.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                int code = response.code();

                if (response.isSuccessful()) {
                    playlistcallback.onUserPlaylistsReceived(response.body());
                } else {
                    try {
                        playlistcallback.onNoUserPlaylists(new Throwable(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {
                playlistcallback.onPlaylistFailure(t);
            }
        });
    }

    public synchronized void getSpecificUserPlaylists(String userLogin, final PlaylistCallback playlistcallback) {
        UserToken userToken = Sesion.getInstance(mContext).getUserToken();

        Call<List<Playlist>> call = mService.getSpecificUserPlaylists(userLogin,"Bearer " + userToken.getIdToken());
        call.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                int code = response.code();

                if (response.isSuccessful()) {
                    playlistcallback.onUserPlaylistsReceived(response.body());
                } else {
                    try {
                        playlistcallback.onNoUserPlaylists(new Throwable(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {
                playlistcallback.onPlaylistFailure(t);
            }
        });
    }

    public synchronized void getLikedPlaylists(final PlaylistCallback playlistcallback) {
        UserToken userToken = Sesion.getInstance(mContext).getUserToken();

        Call<List<Playlist>> call = mService.getLikedPlaylists("Bearer " + userToken.getIdToken());
        call.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                int code = response.code();

                if (response.isSuccessful()) {
                    playlistcallback.onUserPlaylistsReceived(response.body());
                } else {
                    try {
                        playlistcallback.onNoUserPlaylists(new Throwable(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {
                playlistcallback.onPlaylistFailure(t);
            }
        });
    }

    public synchronized void checkIfFollow(final Playlist playlist, final PlaylistCallback playlistCallback){
        Call<Follow> call = mService.checkIfFollow(playlist.getId(), "Bearer " + Sesion.getInstance(mContext).getUserToken().getIdToken());

        call.enqueue(new Callback<Follow>() {
            @Override
            public void onResponse(Call<Follow> call, Response<Follow> response) {
                if(response.isSuccessful()){
                    playlistCallback.onFollowReceived(response.body());
                } else {
                    try {
                        playlistCallback.onFollowFaliure(new Throwable(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Follow> call, Throwable t) {
                playlistCallback.onFollowFaliure(t);
            }
        });
    }

    public synchronized void getSpecificPlaylist(Integer playlistId, final PlaylistCallback playlistcallback) {
        UserToken userToken = Sesion.getInstance(mContext).getUserToken();

        Call<Playlist> call = mService.getSpecificLikedPlaylist(playlistId, "Bearer " + userToken.getIdToken());
        call.enqueue(new Callback<Playlist>() {
            @Override
            public void onResponse(Call<Playlist> call, Response<Playlist> response) {
                int code = response.code();

                if (response.isSuccessful()) {
                    playlistcallback.onUserSpecificLikedPlaylistReceived(response.body());
                } else {
                    try {
                        playlistcallback.onNoUserSpecificLikedPlaylist(new Throwable(response.errorBody().string()));
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

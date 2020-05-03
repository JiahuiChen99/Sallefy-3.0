package com.example.myapplication.restapi.manager;

import android.content.Context;
import android.util.Log;

import com.example.myapplication.model.Track;
import com.example.myapplication.model.User;
import com.example.myapplication.model.UserToken;
import com.example.myapplication.restapi.callback.TrackCallback;
import com.example.myapplication.restapi.callback.UserResourcesCallback;
import com.example.myapplication.restapi.service.UserResourceService;
import com.example.myapplication.utils.Sesion;
import com.example.myapplication.utils.Variables;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserResourcesManager {

    private static final String TAG = "User Resources Manager";
    private Context mContext;
    private static UserResourcesManager sUserResourcesManager;
    private Retrofit mRetrofit;
    private UserResourceService mUserResourceService;


    public static UserResourcesManager getInstance(Context context) {
        if(sUserResourcesManager == null){
            sUserResourcesManager = new UserResourcesManager(context);
        }
        return sUserResourcesManager;
    }

    public UserResourcesManager(Context context) {
        mContext = context;

        mRetrofit = new Retrofit.Builder()
                .baseUrl(Variables.NETWORK.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mUserResourceService = mRetrofit.create(UserResourceService.class);
    }

    public synchronized void getTopArtists(final UserResourcesCallback userResourcesCallback) {
        UserToken userToken = Sesion.getInstance(mContext).getUserToken();

        Call<List<User>> call = mUserResourceService.getPopularUsers("Bearer " + userToken.getIdToken());
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                int code = response.code();

                if(response.isSuccessful()) {
                    userResourcesCallback.onUsersReceived(response.body());
                }else{
                    Log.d(TAG, "Error Not Successful: " + code);
                    userResourcesCallback.onNoUsers(new Throwable("Error " + code + ": " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d(TAG, "Error Not Successful: " + t.getStackTrace());
                userResourcesCallback.onFailure(new Throwable("ERROR: " + t.getStackTrace()));
            }
        });
    }

    public synchronized void getFollowingArtists(final UserResourcesCallback userResourcesCallback) {
        UserToken userToken = Sesion.getInstance(mContext).getUserToken();

        Call<List<User>> call = mUserResourceService.getFollowingArtists("Bearer " + userToken.getIdToken());
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                int code = response.code();

                if(response.isSuccessful()) {
                    userResourcesCallback.onFollowingArtistsReceived(response.body());
                }else{
                    Log.d(TAG, "Error Not Successful: " + code);
                    userResourcesCallback.onNoFollowingArtists(new Throwable("Error " + code + ": " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d(TAG, "Error Not Successful: " + t.getStackTrace());
                userResourcesCallback.onFailure(new Throwable("ERROR: " + t.getStackTrace()));
            }
        });
    }

    public synchronized void getUserFollowing(final UserResourcesCallback userResourcesCallback) {
        UserToken userToken = Sesion.getInstance(mContext).getUserToken();

        Call<List<User>> call = mUserResourceService.getUserFollowing("Bearer " + userToken.getIdToken());
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                int code = response.code();

                if(response.isSuccessful()) {
                    userResourcesCallback.onUserFollowingReceived(response.body());
                }else{
                    Log.d(TAG, "Error Not Successful: " + code);
                    userResourcesCallback.onNoUserFollowing(new Throwable("Error " + code + ": " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d(TAG, "Error Not Successful: " + t.getStackTrace());
                userResourcesCallback.onFailure(new Throwable("ERROR: " + t.getStackTrace()));
            }
        });
    }

    public synchronized void getUserFollowers(final UserResourcesCallback userResourcesCallback) {
        UserToken userToken = Sesion.getInstance(mContext).getUserToken();

        Call<List<User>> call = mUserResourceService.getUserFollowers("Bearer " + userToken.getIdToken());
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                int code = response.code();

                if(response.isSuccessful()) {
                    userResourcesCallback.onUserFollowersReceived(response.body());
                }else{
                    Log.d(TAG, "Error Not Successful: " + code);
                    userResourcesCallback.onNoUserFollowers(new Throwable("Error " + code + ": " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d(TAG, "Error Not Successful: " + t.getStackTrace());
                userResourcesCallback.onFailure(new Throwable("ERROR: " + t.getStackTrace()));
            }
        });
    }


    public synchronized void getFollowingArtistsTopSongs(String artistLogin,final TrackCallback trackCallback) {
        UserToken userToken = Sesion.getInstance(mContext).getUserToken();

        Call<List<Track>> call = mUserResourceService.getFollowingArtistsTopTracks( artistLogin,"Bearer " + userToken.getIdToken());
        call.enqueue(new Callback<List<Track>>() {
            @Override
            public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {
                int code = response.code();

                if (response.isSuccessful()) {
                    trackCallback.onArtistTracksReceived(response.body());
                } else {
                    Log.d(TAG, "Error Not Successful: " + code);
                    trackCallback.onNoArtistTracks(new Throwable("ERROR " + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {
                Log.d(TAG, "Error Failure: " + t.getStackTrace());
                trackCallback.onFailure(new Throwable("ERROR " + t.getStackTrace()));
            }
        });
    }

    public synchronized void getSpecificArtistSongs(String artistLogin, final TrackCallback trackCallback) {
        UserToken userToken = Sesion.getInstance(mContext).getUserToken();

        Call<List<Track>> call = mUserResourceService.getSpecificArtistSongs( artistLogin,"Bearer " + userToken.getIdToken());
        call.enqueue(new Callback<List<Track>>() {
            @Override
            public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {
                int code = response.code();

                if (response.isSuccessful()) {
                    trackCallback.onArtistTracksReceived(response.body());
                } else {
                    Log.d(TAG, "Error Not Successful: " + code);
                    trackCallback.onNoArtistTracks(new Throwable("ERROR " + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {
                Log.d(TAG, "Error Failure: " + t.getStackTrace());
                trackCallback.onFailure(new Throwable("ERROR " + t.getStackTrace()));
            }
        });
    }


    public synchronized void getUsers(final UserResourcesCallback userResourcesCallback) {
        UserToken userToken = Sesion.getInstance(mContext).getUserToken();

        Call<List<User>> call = mUserResourceService.getAllUsers("Bearer " + userToken.getIdToken());
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                int code = response.code();

                if(response.isSuccessful()) {
                    userResourcesCallback.onArtistsReceived(response.body());
                }else{
                    Log.d(TAG, "Error Not Successful: " + code);
                    userResourcesCallback.onNoUsers(new Throwable("Error " + code + ": " + response.raw().message()));
                }
            }
        }
    }
                     
    public synchronized void followUnfollowArtist(String artistLogin, final UserResourcesCallback userResourcesCallback) {
        UserToken userToken = Sesion.getInstance(mContext).getUserToken();

        Call<User> call = mUserResourceService.followUnfollowUser( artistLogin,"Bearer " + userToken.getIdToken());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                int code = response.code();

                if (response.isSuccessful()) {
                    userResourcesCallback.onUserFollowedUnfollowed(response.body());
                } else {
                    Log.d(TAG, "Error Not Successful: " + code);
                    userResourcesCallback.onNoUserFollowedUnfollowed(new Throwable("ERROR " + code + ", " + response.raw().message()));

                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "Error Failure: " + t.getStackTrace());
                userResourcesCallback.onFailure(new Throwable("ERROR " + t.getStackTrace()));
            }
        });
    }

    public synchronized void getSpecificUser(String artistLogin, final UserResourcesCallback userResourceCallback) {
        UserToken userToken = Sesion.getInstance(mContext).getUserToken();

        Call<User> call = mUserResourceService.getSpecificArtist(artistLogin,"Bearer " + userToken.getIdToken());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                int code = response.code();

                if (response.isSuccessful()) {
                    userResourceCallback.onUserReceived(response.body());
                } else {
                    Log.d(TAG, "Error Not Successful: " + code);
                    userResourceCallback.onNoUsers(new Throwable("ERROR " + code + ", " + response.raw().message()));
              @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "Error Failure: " + t.getStackTrace());
                userResourcesCallback.onFailure(new Throwable("ERROR " + t.getStackTrace()));

            }
        });
    }   
  
  
  
  
  public synchronized void checkIfFollowed(String artistLogin, final UserResourcesCallback userResourcesCallback) {
        UserToken userToken = Sesion.getInstance(mContext).getUserToken();

        Call<User> call = mUserResourceService.checkIfFollowed( artistLogin,"Bearer " + userToken.getIdToken());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                int code = response.code();

                if (response.isSuccessful()) {
                    userResourcesCallback.onUserFollowedUnfollowed(response.body());
                } else {
                    Log.d(TAG, "Error Not Successful: " + code);
                    userResourcesCallback.onNoUserFollowedUnfollowed(new Throwable("ERROR " + code + ", " + response.raw().message()));

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "Error Failure: " + t.getStackTrace());
                userResourcesCallback.onFailure(new Throwable("ERROR " + t.getStackTrace()));

            }
        });
    }


    public synchronized void getUser(String artistLogin, final UserResourcesCallback userResourcesCallback) {
        UserToken userToken = Sesion.getInstance(mContext).getUserToken();

        Call<User> call = mUserResourceService.getUser( artistLogin,"Bearer " + userToken.getIdToken());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                int code = response.code();

                if (response.isSuccessful()) {
                    userResourcesCallback.onUserReceived(response.body());
                } else {
                    Log.d(TAG, "Error Not Successful: " + code);
                    userResourcesCallback.onNoUserReceived(new Throwable("ERROR " + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "Error Failure: " + t.getStackTrace());
                userResourcesCallback.onFailure(new Throwable("ERROR " + t.getStackTrace()));
            }
        });
    }
}

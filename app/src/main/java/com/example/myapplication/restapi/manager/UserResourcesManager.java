package com.example.myapplication.restapi.manager;

import android.content.Context;
import android.util.Log;

import com.example.myapplication.model.User;
import com.example.myapplication.model.UserToken;
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
}

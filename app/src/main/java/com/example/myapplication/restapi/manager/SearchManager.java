package com.example.myapplication.restapi.manager;

import android.content.Context;
import android.util.Log;

import com.example.myapplication.model.Playlist;
import com.example.myapplication.model.SearchResult;
import com.example.myapplication.model.UserToken;
import com.example.myapplication.restapi.callback.PlaylistCallback;
import com.example.myapplication.restapi.callback.SearchCallback;
import com.example.myapplication.restapi.service.PlaylistService;
import com.example.myapplication.restapi.service.SearchService;
import com.example.myapplication.utils.Sesion;
import com.example.myapplication.utils.Variables;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchManager {

    private static final String TAG = SearchManager.class.getName();

    private Context mContext;
    private static SearchManager sPlaylist;
    private SearchService mService;
    private Retrofit mRetrofit;



    public static SearchManager getInstance (Context context) {
        if (sPlaylist == null) {
            sPlaylist = new SearchManager(context);
        }

        return sPlaylist;
    }

    public SearchManager(Context context) {
        mContext = context;

        mRetrofit = new Retrofit.Builder()
                .baseUrl(Variables.NETWORK.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = mRetrofit.create(SearchService.class);
    }


    public void searchSong(String url, final SearchCallback callback) {
        UserToken userToken = Sesion.getInstance(mContext).getUserToken();
        Call<SearchResult> call = mService.search(url, "Bearer " + userToken.getIdToken());
        call.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                int code = response.code();

                if (response.isSuccessful()) {
                    callback.onInfoReceived(response.body());
                } else {
                    try {
                        callback.onNoInfo(new Throwable(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                callback.onNoInfo(t);
            }
        });
    }

}

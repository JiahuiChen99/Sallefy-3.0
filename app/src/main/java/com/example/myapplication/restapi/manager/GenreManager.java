package com.example.myapplication.restapi.manager;

import android.content.Context;
import android.util.Log;

import com.example.myapplication.model.EssencialGenre;
import com.example.myapplication.model.UserToken;
import com.example.myapplication.restapi.callback.GenreCallback;
import com.example.myapplication.restapi.service.GenreService;
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

public class GenreManager {

    private static final String TAG = GenreManager.class.getName();

    private Context mContext;
    private static GenreManager sPlaylist;
    private GenreService mService;
    private Retrofit mRetrofit;



    public static GenreManager getInstance (Context context) {
        if (sPlaylist == null) {
            sPlaylist = new GenreManager(context);
        }

        return sPlaylist;
    }

    public GenreManager(Context context) {
        mContext = context;

        mRetrofit = new Retrofit.Builder()
                .baseUrl(Variables.NETWORK.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = mRetrofit.create(GenreService.class);
    }

    public synchronized void getGenre(final GenreCallback genreCallback) {
        UserToken userToken = Sesion.getInstance(mContext).getUserToken();

        Call<List<EssencialGenre>> call = mService.getGenres("Bearer " + userToken.getIdToken());
        call.enqueue(new Callback<List<EssencialGenre>>() {
            @Override
            public void onResponse(Call<List<EssencialGenre>> call, Response<List<EssencialGenre>> response) {
                int code = response.code();

                if (response.isSuccessful()) {
                    genreCallback.onGenreReceived(response.body());
                } else {
                    Log.d(TAG, "Error Not Successful: " + code);
                    genreCallback.onNoGenre(new Throwable("ERROR " + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<EssencialGenre>> call, Throwable t) {
                Log.d(TAG, "Error Failure: " + t.getStackTrace());
                genreCallback.onFailure(new Throwable("ERROR " + t.getStackTrace()));
            }
        });

    }
}

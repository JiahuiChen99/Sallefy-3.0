package com.example.myapplication.restapi.callback;

import com.example.myapplication.model.User;

import java.util.List;

public interface UserResourcesCallback {
    void onUsersReceived(List<User> tracks);
    void onNoUsers(Throwable throwable);
    void onFailure(Throwable throwable);

    void onFollowingArtistsReceived(List<User> followingArtists);
    void onNoFollowingArtists(Throwable noFollowingArtists);
}

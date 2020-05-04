package com.example.myapplication.restapi.callback;

import com.example.myapplication.model.User;

import java.util.List;

public interface UserResourcesCallback {
    void onUsersReceived(List<User> tracks);
    void onUserReceived(User user);
    void onNoUsers(Throwable throwable);
    void onFailure(Throwable throwable);
  
    void onArtistsReceived(List<User> users);
    void onNoUserReceived(Throwable throwable);

    void onFollowingArtistsReceived(List<User> followingArtists);
    void onNoFollowingArtists(Throwable noFollowingArtists);

    void onUserFollowingReceived(List<User> followingArtists);
    void onNoUserFollowing(Throwable noFollowingArtists);

    void onUserFollowersReceived(List<User> followers);
    void onNoUserFollowers(Throwable noFollowers);

    void onUserFollowedUnfollowed(User user);
    void onNoUserFollowedUnfollowed(Throwable throwable);

    void onArtistClicked(User clickedArtist);
}

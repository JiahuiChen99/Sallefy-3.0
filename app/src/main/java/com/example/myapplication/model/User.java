package com.example.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable, Parcelable {

    @SerializedName("activated")
    private Boolean activated;

    @SerializedName("authorities")
    private List<String> authorities = null;

    @SerializedName("createdBy")
    private String createdBy;

    @SerializedName("createdDate")
    private String createdDate;

    @SerializedName("email")
    private String email;

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("id")
    private Integer id;

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("langKey")
    private String langKey;

    @SerializedName("lastModifiedBy")
    private String lastModifiedBy;

    @SerializedName("lastModifiedDate")
    private String lastModifiedDate;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("playlists")
    private Integer playlists;

    @SerializedName("tracks")
    private Integer tracks;

    @SerializedName("followers")
    private Integer followers;

    @SerializedName("following")
    private Integer following;

    @SerializedName("login")
    private String login;

    @SerializedName("followed")
    private Boolean followed;

    public Boolean getFollowed() {
        return followed;
    }

    public void setFollowed(Boolean followed) {
        this.followed = followed;
    }

    public User() {
    }

    public User(Boolean activated, List<String> authorities, String createdBy, String createdDate, String email, String firstName, Integer id, String imageUrl, String langKey, String lastModifiedBy, String lastModifiedDate, String lastName, Integer playlists, Integer tracks, Integer followers, Integer following, String login, Boolean followed) {
        this.activated = activated;
        this.authorities = authorities;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.email = email;
        this.firstName = firstName;
        this.id = id;
        this.imageUrl = imageUrl;
        this.langKey = langKey;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.lastName = lastName;
        this.playlists = playlists;
        this.tracks = tracks;
        this.followers = followers;
        this.following = following;
        this.login = login;
        this.followed = followed;
    }

    protected User(Parcel in) {
        byte tmpActivated = in.readByte();
        activated = tmpActivated == 0 ? null : tmpActivated == 1;
        authorities = in.createStringArrayList();
        createdBy = in.readString();
        createdDate = in.readString();
        email = in.readString();
        firstName = in.readString();
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        imageUrl = in.readString();
        langKey = in.readString();
        lastModifiedBy = in.readString();
        lastModifiedDate = in.readString();
        lastName = in.readString();
        if (in.readByte() == 0) {
            playlists = null;
        } else {
            playlists = in.readInt();
        }
        if (in.readByte() == 0) {
            tracks = null;
        } else {
            tracks = in.readInt();
        }
        if (in.readByte() == 0) {
            followers = null;
        } else {
            followers = in.readInt();
        }
        if (in.readByte() == 0) {
            following = null;
        } else {
            following = in.readInt();
        }
        login = in.readString();
        if(in.readByte() == 0){
            followed = null;
        }else{
            followed = in.readBoolean();
        }
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getPlaylists() {
        return playlists;
    }

    public void setPlaylists(Integer playlists) {
        this.playlists = playlists;
    }

    public Integer getTracks() {
        return tracks;
    }

    public void setTracks(Integer tracks) {
        this.tracks = tracks;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    public Integer getFollowing() {
        return following;
    }

    public void setFollowing(Integer following) {
        this.following = following;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (activated == null ? 0 : activated ? 1 : 2));
        dest.writeStringList(authorities);
        dest.writeString(createdBy);
        dest.writeString(createdDate);
        dest.writeString(email);
        dest.writeString(firstName);
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(imageUrl);
        dest.writeString(langKey);
        dest.writeString(lastModifiedBy);
        dest.writeString(lastModifiedDate);
        dest.writeString(lastName);
        if (playlists == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(playlists);
        }
        if (tracks == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(tracks);
        }
        if (followers == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(followers);
        }
        if (following == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(following);
        }
        dest.writeString(login);
        if(followed == null){
            dest.writeByte((byte) 0);
        }else{
            dest.writeBoolean(followed);
        }
    }
}
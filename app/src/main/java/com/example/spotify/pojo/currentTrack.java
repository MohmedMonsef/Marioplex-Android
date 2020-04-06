package com.example.spotify.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class currentTrack {
    @SerializedName("track")
    @Expose
    private myTrack track;
    @SerializedName("isLiked")
    @Expose
    private Boolean isLiked;
    @SerializedName("album")
    @Expose
    private myAlbum album;
    @SerializedName("isPlaylist")
    @Expose
    private Boolean isPlaylist;
    @SerializedName("playlistId")
    @Expose
    private String playlistId;
    @SerializedName("isPlayable")
    @Expose
    private Boolean isplayable;

    public myTrack getTrack() {
        return track;
    }

    public void setTrack(myTrack track) {
        this.track = track;
    }

    public Boolean getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(Boolean isLiked) {
        this.isLiked = isLiked;
    }

    public myAlbum getAlbum() {
        return album;
    }

    public void setAlbum(myAlbum album) {
        this.album = album;
    }

    public Boolean getIsPlaylist() {
        return isPlaylist;
    }

    public void setIsPlaylist(Boolean isPlaylist) {
        this.isPlaylist = isPlaylist;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public Boolean getIsplayable() {
        return isplayable;
    }

    public void setIsplayable(Boolean isplayable) {
        this.isplayable = isplayable;
    }
}

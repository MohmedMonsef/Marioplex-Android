package com.example.spotify.pojo;

import com.google.gson.annotations.SerializedName;

public class addTrackToPlaylistBody {
    @SerializedName("tracks")
    private String trackID;

    public String getTrackID() {
        return trackID;
    }

    public void setTrackID(String trackID) {
        this.trackID = trackID;
    }

}

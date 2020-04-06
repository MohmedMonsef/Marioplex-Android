package com.example.spotify.SpotifyClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExternalUrls__ {

    @SerializedName("spotify")
    @Expose
    private String spotify;

    public String getSpotify() {
        return spotify;
    }

    public void setSpotify(String spotify) {
        this.spotify = spotify;
    }

    public ExternalUrls__ withSpotify(String spotify) {
        this.spotify = spotify;
        return this;
    }

}
package com.example.spotify.SpotifyClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExternalUrls_ {

    @SerializedName("spotify")
    @Expose
    private String spotify;

    /**
     * No args constructor for use in serialization
     *
     */
    public ExternalUrls_() {
    }

    /**
     *
     * @param spotify
     */
    public ExternalUrls_(String spotify) {
        super();
        this.spotify = spotify;
    }

    public String getSpotify() {
        return spotify;
    }

    public void setSpotify(String spotify) {
        this.spotify = spotify;
    }

}

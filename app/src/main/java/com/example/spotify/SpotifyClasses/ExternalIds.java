package com.example.spotify.SpotifyClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExternalIds {

    @SerializedName("isrc")
    @Expose
    private String isrc;

    public String getIsrc() {
        return isrc;
    }

    public void setIsrc(String isrc) {
        this.isrc = isrc;
    }

    public ExternalIds withIsrc(String isrc) {
        this.isrc = isrc;
        return this;
    }

}
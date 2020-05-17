package com.example.spotify.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LibraryArtists {
    @SerializedName("Artists")
    @Expose
    private List<BasicArtist> artists = null;

    public List<BasicArtist> getArtists() {
        return artists;
    }

    public void setArtists(List<BasicArtist> artists) {
        this.artists = artists;
    }

}

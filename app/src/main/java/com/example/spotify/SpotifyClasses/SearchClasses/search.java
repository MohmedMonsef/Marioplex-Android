
package com.example.spotify.SpotifyClasses.SearchClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class search {

    @SerializedName("artists")
    @Expose
    private Artists artists;
    @SerializedName("tracks")
    @Expose
    private Tracks tracks;

    public Artists getArtists() {
        return artists;
    }

    public void setArtists(Artists artists) {
        this.artists = artists;
    }

    public Tracks getTracks() {
        return tracks;
    }

    public void setTracks(Tracks tracks) {
        this.tracks = tracks;
    }

}

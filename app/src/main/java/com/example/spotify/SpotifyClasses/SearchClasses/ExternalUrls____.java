
package com.example.spotify.SpotifyClasses.SearchClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExternalUrls____ {

    @SerializedName("spotify")
    @Expose
    private String spotify;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ExternalUrls____() {
    }

    /**
     * 
     * @param spotify
     */
    public ExternalUrls____(String spotify) {
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

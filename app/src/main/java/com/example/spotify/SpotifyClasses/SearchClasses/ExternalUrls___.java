
package com.example.spotify.SpotifyClasses.SearchClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExternalUrls___ {

    @SerializedName("spotify")
    @Expose
    private String spotify;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ExternalUrls___() {
    }

    /**
     * 
     * @param spotify
     */
    public ExternalUrls___(String spotify) {
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

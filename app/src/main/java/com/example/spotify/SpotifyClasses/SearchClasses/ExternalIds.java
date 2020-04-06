
package com.example.spotify.SpotifyClasses.SearchClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExternalIds {

    @SerializedName("isrc")
    @Expose
    private String isrc;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ExternalIds() {
    }

    /**
     * 
     * @param isrc
     */
    public ExternalIds(String isrc) {
        super();
        this.isrc = isrc;
    }

    public String getIsrc() {
        return isrc;
    }

    public void setIsrc(String isrc) {
        this.isrc = isrc;
    }

}

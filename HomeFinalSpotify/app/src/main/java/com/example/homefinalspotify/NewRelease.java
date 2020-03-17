package com.example.homefinalspotify;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewRelease {

    @SerializedName("albums")
    @Expose
    private Albums albums;

    /**
     * No args constructor for use in serialization
     *
     */
    public NewRelease() {
    }

    /**
     *
     * @param albums
     */
    public NewRelease(Albums albums) {
        super();
        this.albums = albums;
    }

    public Albums getAlbums() {
        return albums;
    }

    public void setAlbums(Albums albums) {
        this.albums = albums;
    }

}


package com.example.fragspotify.BackClasses.Backclasses.backsearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Album {

    @SerializedName("album")
    @Expose
    private Album_ album;
    @SerializedName("artist")
    @Expose
    private Artist_ artist;

    public Album_ getAlbum() {
        return album;
    }

    public void setAlbum(Album_ album) {
        this.album = album;
    }

    public Artist_ getArtist() {
        return artist;
    }

    public void setArtist(Artist_ artist) {
        this.artist = artist;
    }

}

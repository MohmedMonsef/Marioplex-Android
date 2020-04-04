
package com.example.fragspotify.BackClasses.Backclasses.backsearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Track {

    @SerializedName("track")
    @Expose
    private Track_ track;
    @SerializedName("artist")
    @Expose
    private Artist__ artist;
    @SerializedName("album")
    @Expose
    private Album__ album;

    public Track_ getTrack() {
        return track;
    }

    public void setTrack(Track_ track) {
        this.track = track;
    }

    public Artist__ getArtist() {
        return artist;
    }

    public void setArtist(Artist__ artist) {
        this.artist = artist;
    }

    public Album__ getAlbum() {
        return album;
    }

    public void setAlbum(Album__ album) {
        this.album = album;
    }

}

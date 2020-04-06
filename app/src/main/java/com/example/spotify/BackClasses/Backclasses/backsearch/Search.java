
package com.example.spotify.BackClasses.Backclasses.backsearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Search {

    @SerializedName("artist")
    @Expose
    private List<Artist> artist = null;
    @SerializedName("album")
    @Expose
    private List<Album> album = null;
    @SerializedName("playlist")
    @Expose
    private List<Playlist> playlist = null;
    @SerializedName("track")
    @Expose
    private List<Track> track = null;

    public List<Artist> getArtist() {
        return artist;
    }

    public void setArtist(List<Artist> artist) {
        this.artist = artist;
    }

    public List<Album> getAlbum() {
        return album;
    }

    public void setAlbum(List<Album> album) {
        this.album = album;
    }

    public List<Playlist> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(List<Playlist> playlist) {
        this.playlist = playlist;
    }

    public List<Track> getTrack() {
        return track;
    }

    public void setTrack(List<Track> track) {
        this.track = track;
    }

}

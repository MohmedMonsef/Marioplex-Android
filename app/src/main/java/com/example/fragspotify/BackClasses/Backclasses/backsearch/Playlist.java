
package com.example.fragspotify.BackClasses.Backclasses.backsearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Playlist {

    @SerializedName("playlist")
    @Expose
    private Playlist_ playlist;
    @SerializedName("owner")
    @Expose
    private Owner owner;

    public Playlist_ getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist_ playlist) {
        this.playlist = playlist;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

}

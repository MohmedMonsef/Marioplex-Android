
package com.example.spotify.BackClasses.Backclasses.backcategoryplaylist;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryPlaylist {

    @SerializedName("playlists")
    @Expose
    private List<Playlist> playlists = null;

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

}

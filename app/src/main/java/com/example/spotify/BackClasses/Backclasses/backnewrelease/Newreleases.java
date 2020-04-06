
package com.example.spotify.BackClasses.Backclasses.backnewrelease;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Newreleases {

    @SerializedName("albums")
    @Expose
    private List<Album> albums = null;

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

}

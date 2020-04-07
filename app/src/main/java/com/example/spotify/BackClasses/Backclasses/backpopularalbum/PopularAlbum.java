
package com.example.spotify.BackClasses.Backclasses.backpopularalbum;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PopularAlbum {

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

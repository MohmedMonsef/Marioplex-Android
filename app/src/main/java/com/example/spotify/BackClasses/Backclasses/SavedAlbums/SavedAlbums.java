
package com.example.spotify.BackClasses.Backclasses.SavedAlbums;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SavedAlbums {

    @SerializedName("savedAlbums")
    @Expose
    private List<SavedAlbum> savedAlbums = null;

    public List<SavedAlbum> getSavedAlbums() {
        return savedAlbums;
    }

    public void setSavedAlbums(List<SavedAlbum> savedAlbums) {
        this.savedAlbums = savedAlbums;
    }

}

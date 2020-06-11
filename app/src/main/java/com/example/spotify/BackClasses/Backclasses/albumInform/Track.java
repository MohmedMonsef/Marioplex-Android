
package com.example.spotify.BackClasses.Backclasses.albumInform;

import com.example.spotify.pojo.ImageInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Track {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("playable")
    @Expose
    private Boolean Playable;
    @SerializedName("images")
    @Expose
    private List<ImageInfo> images = null;
    @SerializedName("isLiked")
    @Expose
    private Boolean isLiked ;

    public Boolean  getLiked() { return isLiked; }

    public void setLiked(Boolean liked) { isLiked = liked; }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ImageInfo> getImages() {
        return images;
    }

    public void setImages(List<ImageInfo> images) {
        this.images = images;
    }

    public Boolean getPlayable() {
        return Playable;
    }

    public void setPlayable(Boolean playable) {
        Playable = playable;
    }
}


package com.example.spotify.BackClasses.Backclasses.backcategoryplaylist;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Playlist {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("images")
    @Expose
    private List<Object> images = null;
    @SerializedName("ownerId")
    @Expose
    private String ownerId;
    @SerializedName("ownerName")
    @Expose
    private String ownerName;

    public String getId() {
        return id;
    }

    public Playlist(String id, String name, List<Object> images, String ownerId, String ownerName)
    {
        this.id = id;
        this.name = name;
        this.images = images;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
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

    public List<Object> getImages() {
        return images;
    }

    public void setImages(List<Object> images) {
        this.images = images;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

}

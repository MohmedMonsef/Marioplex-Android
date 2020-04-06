
package com.example.spotify.BackClasses.Backclasses.backsearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Playlist {

    @SerializedName("ownerId")
    @Expose
    private String ownerId;
    @SerializedName("ownerName")
    @Expose
    private String ownerName;
    @SerializedName("ownerImages")
    @Expose
    private List<Object> ownerImages = null;
    @SerializedName("ownerType")
    @Expose
    private String ownerType;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("images")
    @Expose
    private List<Object> images = null;

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

    public List<Object> getOwnerImages() {
        return ownerImages;
    }

    public void setOwnerImages(List<Object> ownerImages) {
        this.ownerImages = ownerImages;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Object> getImages() {
        return images;
    }

    public void setImages(List<Object> images) {
        this.images = images;
    }

}

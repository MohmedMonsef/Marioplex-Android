package com.example.spotify.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BasicPlaylist {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ownerId")
    @Expose
    private String ownerId;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("collaborative")
    @Expose
    private Boolean collaborative;
    @SerializedName("isPublic")
    @Expose
    private Boolean isPublic;
    @SerializedName("images")
    @Expose
    private List<ImageInfo> images = null;
    @SerializedName("type")
    @Expose
    private String type;

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

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

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Boolean getCollaborative() {
        return collaborative;
    }

    public void setCollaborative(Boolean collaborative) {
        this.collaborative = collaborative;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public List<ImageInfo> getImages() {
        return images;
    }

    public void setImages(List<ImageInfo> images) {
        this.images = images;
    }
}

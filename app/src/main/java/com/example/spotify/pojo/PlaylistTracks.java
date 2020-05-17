package com.example.spotify.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlaylistTracks {
    @SerializedName("id")
    @Expose
    private String id;
//    @SerializedName("isfollowed")
//    @Expose
//    private Boolean isfollowed;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ownerId")
    @Expose
    private String ownerId;
    @SerializedName("collaborative")
    @Expose
    private Boolean collaborative;
    @SerializedName("ownerName")
    @Expose
    private String ownerName;
    @SerializedName("isPublic")
    @Expose
    private Boolean isPublic;
    @SerializedName("isfollowed")
    @Expose
    private Boolean isLiked;
    @SerializedName("images")
    @Expose
    private List<ImageInfo> images = null;
    @SerializedName("tracks")
    @Expose
    private List<BasicTrack> tracks = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

//    public void setIsfollowed(Boolean isfollowed) { this.isfollowed = isfollowed; }
//
//    public Boolean getIsfollowed() { return isfollowed; }

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

    public Boolean getCollaborative() {
        return collaborative;
    }

    public void setCollaborative(Boolean collaborative) {
        this.collaborative = collaborative;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Boolean getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(Boolean isLiked) {
        this.isLiked = isLiked;
    }

    public List<ImageInfo> getImages() {
        return images;
    }

    public void setImages(List<ImageInfo> images) {
        this.images = images;
    }

    public List<BasicTrack> getTracks() {
        return tracks;
    }

    public void setTracks(List<BasicTrack> tracks) {
        this.tracks = tracks;
    }
}

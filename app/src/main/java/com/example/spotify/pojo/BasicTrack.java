package com.example.spotify.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BasicTrack {
    @SerializedName("trackid")
    @Expose
    private String trackid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("artistId")
    @Expose
    private String artistId;
    @SerializedName("artistName")
    @Expose
    private String artistName;
    @SerializedName("albumId")
    @Expose
    private String albumId;
    @SerializedName("albumName")
    @Expose
    private String albumName;
    @SerializedName("isLiked")
    @Expose
    private Boolean isLiked;
    @SerializedName("images")
    @Expose
    private List<Object> images = null;

    public String getTrackid() {
        return trackid;
    }

    public void setTrackid(String trackid) {
        this.trackid = trackid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public Boolean getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(Boolean isLiked) {
        this.isLiked = isLiked;
    }

    public List<Object> getImages() {
        return images;
    }

    public void setImages(List<Object> images) {
        this.images = images;
    }
}

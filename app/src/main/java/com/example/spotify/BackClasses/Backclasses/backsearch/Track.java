
package com.example.spotify.BackClasses.Backclasses.backsearch;

import com.example.spotify.pojo.ImageInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Track {

    @SerializedName("artistId")
    @Expose
    private String artistId;
    @SerializedName("artistName")
    @Expose
    private String artistName;
    @SerializedName("artistimages")
    @Expose
    private List<ImageInfo> artistimages = null;
    @SerializedName("artistType")
    @Expose
    private String artistType;
    @SerializedName("albumId")
    @Expose
    private String albumId;
    @SerializedName("albumName")
    @Expose
    private String albumName;
    @SerializedName("albumImages")
    @Expose
    private List<ImageInfo> albumImages = null;
    @SerializedName("albumType")
    @Expose
    private String albumType;
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
    private List<ImageInfo> images = null;

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

    public List<ImageInfo> getArtistimages() {
        return artistimages;
    }

    public void setArtistimages(List<ImageInfo> artistimages) {
        this.artistimages = artistimages;
    }

    public String getArtistType() {
        return artistType;
    }

    public void setArtistType(String artistType) {
        this.artistType = artistType;
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

    public List<ImageInfo> getAlbumImages() {
        return albumImages;
    }

    public void setAlbumImages(List<ImageInfo> albumImages) {
        this.albumImages = albumImages;
    }

    public String getAlbumType() {
        return albumType;
    }

    public void setAlbumType(String albumType) {
        this.albumType = albumType;
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

    public List<ImageInfo> getImages() {
        return images;
    }

    public void setImages(List<ImageInfo> images) {
        this.images = images;
    }

}

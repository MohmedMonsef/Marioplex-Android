
package com.example.spotify.BackClasses.Backclasses.backpopularartist;

import com.example.spotify.pojo.ImageInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Artist {

    @SerializedName("genre")
    @Expose
    private List<String> genre = null;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("images")
    @Expose
    private List<ImageInfo> images = null;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("info")
    @Expose
    private String info;

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}

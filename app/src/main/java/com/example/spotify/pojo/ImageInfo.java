package com.example.spotify.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageInfo {

    @SerializedName("height")
    @Expose
    private int height;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("width")
    @Expose
    private int width;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getID() {
        return id;
    }

    public void setID(String url) {
        this.id = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

}

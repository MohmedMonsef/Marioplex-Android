package com.example.spotify.pojo;

import com.google.gson.annotations.SerializedName;

public class createPlaylistBody {
    @SerializedName("name")
    private String name;

    @SerializedName("Describtion")
    private String descibtion;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescibtion() {
        return descibtion;
    }

    public void setDescibtion(String descibtion) {
        this.descibtion = descibtion;
    }
}

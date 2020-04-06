package com.example.spotify.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class snapshot {
    @SerializedName("hasTracks")
    @Expose
    private List<String> hasTracks = null;
    @SerializedName("action")
    @Expose
    private String action;

    public List<String> getHasTracks() {
        return hasTracks;
    }

    public void setHasTracks(List<String> hasTracks) {
        this.hasTracks = hasTracks;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}

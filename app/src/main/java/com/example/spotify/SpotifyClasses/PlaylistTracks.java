package com.example.spotify.SpotifyClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaylistTracks {
    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("total")
    @Expose
    private Integer total;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}

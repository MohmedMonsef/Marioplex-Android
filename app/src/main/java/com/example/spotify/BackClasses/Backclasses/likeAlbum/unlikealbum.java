package com.example.spotify.BackClasses.Backclasses.likeAlbum;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class unlikealbum
{
    @SerializedName("ids")
    @Expose
    private String ids;
    public String getIds() {
        return ids;
    }
    public void setIds(String ids) {
        this.ids = ids;
    }
    public unlikealbum(String ids) {
        this.ids = ids;
    }
}

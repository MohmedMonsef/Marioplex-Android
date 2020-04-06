
package com.example.spotify.SpotifyClasses.SearchClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Followers {

    @SerializedName("href")
    @Expose
    private Object href;
    @SerializedName("total")
    @Expose
    private int total;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Followers() {
    }

    /**
     * 
     * @param total
     * @param href
     */
    public Followers(Object href, int total) {
        super();
        this.href = href;
        this.total = total;
    }

    public Object getHref() {
        return href;
    }

    public void setHref(Object href) {
        this.href = href;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}

package com.example.spotify.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class myAlbum {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("artist")
    @Expose
    private myArtist artist;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public myArtist getArtist() {
        return artist;
    }

    public void setArtist(myArtist artist) {
        this.artist = artist;
    }
}

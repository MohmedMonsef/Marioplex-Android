package com.example.spotify.SpotifyClasses.SearchClasses;

public class aclass
{
String type;
String name;
String image;

    public aclass(String type, String name, String image) {
        this.type = type;
        this.name = name;
        this.image = image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

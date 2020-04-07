package com.example.spotify.BackClasses.Backclasses;

public class aclass
{
String type;
String name;
String image;
String _id;

    public aclass(String type, String name, String image , String _id) {
        this.type = type;
        this.name = name;
        this.image = image;
        this._id = _id;
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

    public  String getid(){return _id;}

    public void set_id(String  id){_id = id;}
}

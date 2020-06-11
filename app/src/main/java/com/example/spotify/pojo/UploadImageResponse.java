package com.example.spotify.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadImageResponse {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("imageId")
    @Expose
    private String imageID;

    public String getSuccess() {
        return success;
    }

    public String getImageID() {
        return imageID;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public void setImageID1(String imageID) {
        this.imageID = imageID;
    }
}

package com.example.spotify.Fragments.ALBUM_FRAGMENT;

import androidx.lifecycle.MutableLiveData;

import com.example.spotify.BackClasses.Backclasses.albumInform.AlbumObject;

public class albumData {
    public static albumData instance;
    private MutableLiveData<AlbumObject> albumObject = new MutableLiveData<>();
    private MutableLiveData<Boolean> InfoReady;

    public static albumData getinstance()
    {
        if(instance == null){
            instance = new albumData();
        }
        return instance;
    }

    public MutableLiveData<AlbumObject> getalbumObject(){
        return albumObject;
    }

    public void setalbumObject(AlbumObject p) {
        albumObject.setValue(p);
    }

    public MutableLiveData<Boolean> getInfoReady(){
        return InfoReady;
    }

    public void setInfoReady(Boolean r) {
        InfoReady.setValue(r);
    }
    public void clearinstance(){
        albumObject = null;
        instance = null;
    }
}

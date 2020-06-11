package com.example.spotify.Fragments.ALBUM_FRAGMENT;

import androidx.lifecycle.MutableLiveData;

import com.example.spotify.BackClasses.Backclasses.albumTrack.AlbumTracks;

public class albumDataTrack {
    public static albumDataTrack instance;
    private MutableLiveData<AlbumTracks> albumObject = new MutableLiveData<>();
    private MutableLiveData<Boolean> InfoReady;

    public static albumDataTrack getinstance1()
    {
        if(instance == null){
            instance = new albumDataTrack();
        }
        return instance;
    }

    public MutableLiveData<AlbumTracks> getalbumObject1(){
        return albumObject;
    }

    public void setalbumObject1(AlbumTracks p) {
        albumObject.setValue(p);
    }

    public MutableLiveData<Boolean> getInfoReady1(){
        return InfoReady;
    }

    public void setInfoReady1(Boolean r) {
        InfoReady.setValue(r);
    }
    public void clearinstance1(){
        albumObject = null;
        instance = null;
    }
}

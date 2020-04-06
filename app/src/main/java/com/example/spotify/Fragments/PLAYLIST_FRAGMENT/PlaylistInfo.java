package com.example.spotify.Fragments.PLAYLIST_FRAGMENT;

import androidx.lifecycle.MutableLiveData;
import com.example.spotify.pojo.PlaylistTracks;


public class PlaylistInfo {
    public static PlaylistInfo instance;
    private MutableLiveData<PlaylistTracks> playlistTracks = new MutableLiveData<>();
    private MutableLiveData<Boolean> InfoReady;

    public static PlaylistInfo getinstance(){
        if(instance == null){
            instance = new PlaylistInfo();
        }
        return instance;
    }

    public MutableLiveData<PlaylistTracks> getplaylistTracks(){
        return playlistTracks;
    }

    public void setPlaylistTracks(PlaylistTracks p) {
        playlistTracks.setValue(p);
    }

    public MutableLiveData<Boolean> getInfoReady(){
        return InfoReady;
    }

    public void setInfoReady(Boolean r) {
        InfoReady.setValue(r);
    }
}

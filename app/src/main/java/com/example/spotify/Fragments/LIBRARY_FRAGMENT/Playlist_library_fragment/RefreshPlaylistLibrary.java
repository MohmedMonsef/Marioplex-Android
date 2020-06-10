package com.example.spotify.Fragments.LIBRARY_FRAGMENT.Playlist_library_fragment;

import androidx.lifecycle.MutableLiveData;

public class RefreshPlaylistLibrary {
    public static RefreshPlaylistLibrary instance;
    private MutableLiveData<Boolean> RefreshFlag = new MutableLiveData<>();


    public static RefreshPlaylistLibrary getInstance(){
        if(instance == null){
            instance = new RefreshPlaylistLibrary();
        }
        return instance;
    }

    public MutableLiveData<Boolean> getRefreshFlag() {
        return RefreshFlag;
    }

    public void setRefreshFlag(Boolean refreshFlag) {
        RefreshFlag.setValue(refreshFlag);
    }
}

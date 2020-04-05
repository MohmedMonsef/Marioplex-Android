package com.example.spotify.media;

import androidx.lifecycle.MutableLiveData;

import com.example.spotify.SpotifyClasses.Track;


public class TrackInfo {
    private static TrackInfo instance;
    private MutableLiveData<Track> track = new MutableLiveData<Track>();
    private String name;
    private MutableLiveData<Boolean> isPlaying = new MutableLiveData<>();
    private MutableLiveData<Integer> duration = new MutableLiveData<>();
   // private MutableLiveData<Integer> currentPosition = new MutableLiveData<>();



    public static TrackInfo getInstance() {
        if(instance == null)
            instance = new TrackInfo();
        return instance;
    }

    public void setTrack(Track trackv) {
        track.setValue(trackv);
    }

    public MutableLiveData<Track> getTrack(){
        return track;
    }

    public void setName(String n){
        name = n;
    }

    public String getName(){
        return name;
    }

    public void setIsPlaying(Boolean b){
        isPlaying.setValue(b);
    }
    public MutableLiveData<Boolean> getIsPlaying(){
        return isPlaying;
    }
    public MutableLiveData<Integer> getDuration(){
        return duration;
    }
    public void setDuration(int d){
        duration.setValue(d);
    }

}

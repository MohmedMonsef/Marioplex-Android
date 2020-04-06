package com.example.spotify.media;

import androidx.lifecycle.MutableLiveData;

import com.example.spotify.pojo.currentTrack;


public class TrackInfo {
    private static TrackInfo instance;
    private MutableLiveData<currentTrack> track = new MutableLiveData<>();//TODO here
    private String name;
    private MutableLiveData<Boolean> isPlaying = new MutableLiveData<>();
    private MutableLiveData<Boolean> timerSet = new MutableLiveData<>();
    private MutableLiveData<Integer> duration = new MutableLiveData<>();
   // private MutableLiveData<Integer> currentPosition = new MutableLiveData<>();



    public static TrackInfo getInstance() {
        if(instance == null)
            instance = new TrackInfo();
        return instance;
    }
    //TODO here
    public void setTrack(currentTrack trackv) {
        track.setValue(trackv);
        String s ="";
    }
    //TODO here
    public MutableLiveData<currentTrack> getTrack(){
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
    public void setTimerSet(boolean s){timerSet.setValue(s);}
    public MutableLiveData<Boolean> getTimerSet(){return timerSet;}

}

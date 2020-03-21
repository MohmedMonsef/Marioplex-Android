package media;

import androidx.lifecycle.MutableLiveData;

import pojo.Track;

public class TrackInfo {
    private static TrackInfo instance;
    private MutableLiveData<Track> track = new MutableLiveData<Track>();
    private String name;

    //private MutableLiveData<int> currentPosition;

    public static TrackInfo getInstance() {
        if(instance == null)
            instance = new TrackInfo();
        return instance;
    }

    public void setTrack(Track trackv) {
        MutableLiveData<Track> t = new MutableLiveData<>();
        t.setValue(trackv);
        track = t;
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

}

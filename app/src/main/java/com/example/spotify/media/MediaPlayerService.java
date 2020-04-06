package com.example.spotify.media;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.spotify.Interfaces.EndPointAPI;
import com.example.spotify.Interfaces.Retrofit;
import com.example.spotify.login.user;
import com.example.spotify.pojo.currentTrack;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class MediaPlayerService extends Service implements MediaPlayer.OnCompletionListener , MediaPlayer.OnPreparedListener ,
        MediaPlayer.OnBufferingUpdateListener ,MediaPlayer.OnErrorListener , MediaPlayer.OnSeekCompleteListener  {

    //AudioManager.OnAudioFocusChangeListener

    private final IBinder iBinder = new LocalBinder();
    private MediaPlayer mediaPlayer ;
    private String audioFile ="https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3";
    private Boolean isPlaying = false;
    private int resumePosition;
//    private String TrackID1 ="7ouMYWpwJ422jRcDASZB7P";
//    private String TrackID2 ="4VqPOruhp5EdPBeR92t6lQ";
//    private String TrackID3 ="2takcwOaAZWiXQijPHIx7B";
//    private Tracks tracks;
    private boolean stopInTrackEnd;

    private boolean prepared;
//TODO change the base url here

//    private Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl("http://52.205.254.29/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build();
//    private EndPointAPI endPointAPI = retrofit.create(EndPointAPI.class);
    private Toast toast;
    private TrackInfo track = TrackInfo.getInstance();
    private CountDownTimer sleepTimer = null;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        try {
//            //An audio file is passed to the service through putExtra();
//            audioFile = intent.getExtras().getString("media");
//        } catch (NullPointerException e) {
//            stopSelf();
//        }

//        if (audioFile != null && audioFile != "")
//            initMediaPlayer();
//
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initMediaPlayer();
        track.setTimerSet(false);
        stopInTrackEnd = false;

    }

    private void initMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        //Set up MediaPlayer event listeners
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnSeekCompleteListener(this);
        //Reset so that the MediaPlayer is not pointing to another data source
        mediaPlayer.reset();

        ///////////////////request the current track//////////////////////////
        //Call<currentTrack> call = endPointAPI.getCurrentlyPlaying(user.getToken());
        Call<currentTrack> call = Retrofit.getInstance().getEndPointAPI().getCurrentlyPlaying(user.getToken());
        getCurrentlyPlaying(call);
        //////////////////////////////////////////////////////////////////////

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            // Set the data source to the mediaFile location
            audioFile = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3";
            mediaPlayer.setDataSource(audioFile);
            prepared = false;

        } catch (IOException e) {
            e.printStackTrace();
            stopSelf();
        }
        mediaPlayer.prepareAsync();
    }

    void getCurrentlyPlaying(Call<currentTrack> call){
        call.enqueue(new Callback<currentTrack>() {
            @Override
            public void onResponse(Call<currentTrack> call, Response<currentTrack> response) {
                if (!response.isSuccessful()) {
                    toast = Toast.makeText(getApplicationContext(),"Code: "+response.code(),Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                else if(response.body()==null){
                    toast = Toast.makeText(getApplicationContext(),"response body = null",Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    track.setTrack(response.body());
                    //audioFile = track.getTrack().getValue().getUri();
//                    try {
//                        mediaPlayer.setDataSource(audioFile);
//                         prepared = false
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        stopSelf();
//                    }
                   // mediaPlayer.prepareAsync();
                }
            }
            @Override
            public void onFailure(Call<currentTrack> call, Throwable t) {
                toast = Toast.makeText(getApplicationContext(),t.getMessage()+" 'failed '",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

//    void getTracks(String trackID){
//        Call<Tracks> call = endPointAPI.getTracks(trackID);
//
//        call.enqueue(new Callback<Tracks>() {
//            @Override
//            public void onResponse(Call<Tracks> call, Response<Tracks> response) {
//                if(!response.isSuccessful()){
//                    toast = Toast.makeText(getApplicationContext(),"Code: "+response.code(),Toast.LENGTH_SHORT);
//                    toast.show();
//                    return;
//                }
//                else if(response.body()==null){
//                    toast = Toast.makeText(getApplicationContext(),"response body = null",Toast.LENGTH_SHORT);
//                    toast.show();
//                }
//                else {
//                    tracks = response.body();
//                    track.setTrack(tracks.getTracks().get(0));
//                }
//            }
//            @Override
//            public void onFailure(Call<Tracks> call, Throwable t) {
//                toast = Toast.makeText(getApplicationContext(),t.getMessage()+" 'failed '",Toast.LENGTH_SHORT);
//                toast.show();
//            }
//        });
//    }
    public void next(){
        ///////////////////request the current track//////////////////////////
        //Call<currentTrack> call = endPointAPI.getNext(user.getToken());
        Call<currentTrack> call = Retrofit.getInstance().getEndPointAPI().getNext(user.getToken());

        getCurrentlyPlaying(call);
        //////////////////////////////////////////////////////////////////////
        pauseMedia();
        mediaPlayer.reset();

        try {
            // Set the data source to the mediaFile location
            audioFile = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3";
            mediaPlayer.setDataSource(audioFile);
            prepared = false;

        } catch (IOException e) {
            e.printStackTrace();
            stopSelf();
        }
        mediaPlayer.prepareAsync();

    }

    public void previous(){
        ///////////////////request the current track//////////////////////////
        //Call<currentTrack> call = endPointAPI.getPrevious(user.getToken());
        Call<currentTrack> call = Retrofit.getInstance().getEndPointAPI().getPrevious(user.getToken());
        getCurrentlyPlaying(call);
        //////////////////////////////////////////////////////////////////////
        pauseMedia();
        mediaPlayer.reset();
        try {
            // Set the data source to the mediaFile location
            audioFile = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-16.mp3";
            mediaPlayer.setDataSource(audioFile);
            prepared = false;
        } catch (IOException e) {
            e.printStackTrace();
            stopSelf();
        }
        mediaPlayer.prepareAsync();
    }
    public Boolean getIsPlaying(){
        return isPlaying;
    }
    public Boolean getStopInTrackEnd(){return stopInTrackEnd;}
    public void setStopInTrackEnd(boolean b){stopInTrackEnd = b;}

    public void playMedia() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            isPlaying = true;
            TrackInfo.getInstance().setIsPlaying(true);
        }
    }

    public void stopMedia() {
        if (mediaPlayer == null) return;
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            isPlaying = false;
            TrackInfo.getInstance().setIsPlaying(false);
        }
        //cancelTimer();
    }

    public void pauseMedia() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPlaying = false;
            TrackInfo.getInstance().setIsPlaying(false);
            resumePosition = mediaPlayer.getCurrentPosition();
        }
        //cancelTimer();
    }

    public int getCurrentPosition(){
        if(mediaPlayer!=null&&prepared) {
            return mediaPlayer.getCurrentPosition();
        }
        else{
            return 0;
        }
    }

    public int getDuration(){
        if(mediaPlayer !=null&&prepared) {
            return mediaPlayer.getDuration();
        }
        else {
            return 1;
        }
    }

    public void seekTo(int s){
        if(mediaPlayer != null) {
            mediaPlayer.seekTo(s * 1000);
            mediaPlayer.start();
            isPlaying = true;
            TrackInfo.getInstance().setIsPlaying(true);
        }
    }

    public void resumeMedia() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(resumePosition);
            mediaPlayer.start();
            isPlaying = true;
            TrackInfo.getInstance().setIsPlaying(true);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        stopMedia();
//        if(stopInTrackEnd) {
//            setStopInTrackEnd(false);
//            track.setTimerSet(false);
//            stopMedia();
//        }
//        else {
//            track.setIsPlaying(false);
//            next();
//        }
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        playMedia();
        TrackInfo.getInstance().setDuration(mediaPlayer.getDuration());
        prepared = true;
//        toast = Toast.makeText(getApplicationContext() , "audio is prepared " , Toast.LENGTH_SHORT);
//        toast.show();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        //Invoked when there has been an error during an asynchronous operation
        switch (what) {
            case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                Log.d("MediaPlayer Error", "MEDIA ERROR NOT VALID FOR PROGRESSIVE PLAYBACK " + extra);
                break;
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                Log.d("MediaPlayer Error", "MEDIA ERROR SERVER DIED " + extra);
                break;
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                Log.d("MediaPlayer Error", "MEDIA ERROR UNKNOWN " + extra);
                break;
        }
        return false;
    }

//    @Override
//    public void onAudioFocusChange(int focusChange) {
//
//    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        //when the app is removed from the recently used apps stop the service
        super.onTaskRemoved(rootIntent);
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer !=null){
            stopMedia();
            mediaPlayer.release();
        }
        cancelTimer();
    }

    public class LocalBinder extends Binder {
        public MediaPlayerService getservice(){
            return MediaPlayerService.this;
        }
    }

    public void startTimer(long milliSeconds){
        if(mediaPlayer != null) {
            sleepTimer = new CountDownTimer(milliSeconds, 60000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    toast = Toast.makeText(getApplicationContext(),"one minute passed",Toast.LENGTH_SHORT);
                    toast.show();
                }

                @Override
                public void onFinish() {
                    if (mediaPlayer != null) {
                        pauseMedia();
                        track.setTimerSet(false);
                        toast = Toast.makeText(getApplicationContext(),"sleep timer ended",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }.start();
        }
        track.setTimerSet(true);
    }

    public void cancelTimer(){
        if(sleepTimer!=null){
            sleepTimer.cancel();
            track.setTimerSet(false);
        }
        if(stopInTrackEnd){
            setStopInTrackEnd(false);
        }
//        toast = Toast.makeText(getApplicationContext() , "cancel timer called" , Toast.LENGTH_SHORT);
//        toast.show();
    }

}

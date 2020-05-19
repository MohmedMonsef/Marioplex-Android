package com.example.spotify.media;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.spotify.Interfaces.Retrofit;
import com.example.spotify.login.user;
import com.example.spotify.pojo.currentTrack;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MediaPlayerService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener,
        MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnErrorListener, MediaPlayer.OnSeekCompleteListener {

    private final IBinder iBinder = new LocalBinder();
    private MediaPlayer mediaPlayer;
    private Boolean isPlaying = false;
    private int playFlag;
    private int resumePosition;
    private boolean stopInTrackEnd;
    private Map<String, String> headers = new HashMap<String, String>();


    private boolean prepared;
    private Toast toast;
    private TrackInfo track = TrackInfo.getInstance();
    private CountDownTimer sleepTimer = null;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * initializes some variables when the service is created
     */
    @Override
    public void onCreate() {
        headers.put("x-auth-token", user.getToken());
        super.onCreate();
        initMediaPlayer();
        playFlag = 0;
        track.setTimerSet(false);
        track.setIsQueue(false);
        track.setTryAgain(false);
        stopInTrackEnd = false;

    }

    /**
     * initializes the media player and sends request to get the current track then plays it
     */

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
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        ///////////////////request the current track//////////////////////////
        Call<currentTrack> call = Retrofit.getInstance().getEndPointAPI().getCurrentlyPlaying(user.getToken());
        getCurrentlyPlaying(call);

    }

    public void playCurrentTrack(Call<currentTrack> call) {
        getCurrentlyPlaying(call);
    }

    /**
     * sends request to get the current track
     *
     * @param call
     */

    void getCurrentlyPlaying(Call<currentTrack> call) {
        call.enqueue(new Callback<currentTrack>() {
            @Override
            public void onResponse(Call<currentTrack> call, Response<currentTrack> response) {
                if (!response.isSuccessful()) {
                    if (response.code() == 404) {
                        track.setIsQueue(false);
                    }
                    return;
                } else {
                    track.setTrack(response.body());
                    track.setIsQueue(true);
                    track.setIsLiked(response.body().getIsLiked());
                    mediaPlayer.reset();
                    try {
                        // Set the data source to the mediaFile location
                        String TID = response.body().getTrack().getId();
                        String s = Retrofit.getInstance().getBaseurl() + "api/tracks/android/" + TID + "?type=medium";
                        mediaPlayer.setDataSource(MediaPlayerService.this, Uri.parse(s), headers);
                        prepared = false;
                        mediaPlayer.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                        stopSelf();
                    }
                }
            }

            @Override
            public void onFailure(Call<currentTrack> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "something went wrong .check your internet connection", Toast.LENGTH_SHORT).show();
                track.setTryAgain(true);

            }
        });
    }


    /**
     * sends request to get the next track
     */
    public void next() {
        ///////////////////request the current track//////////////////////////
        Call<currentTrack> call = Retrofit.getInstance().getEndPointAPI().getNext(user.getToken());
        getCurrentlyPlaying(call);
        pauseMedia();

    }

    /**
     * sends request to get the previous track
     */

    public void previous() {
        ///////////////////request the current track//////////////////////////
        Call<currentTrack> call = Retrofit.getInstance().getEndPointAPI().getPrevious(user.getToken());
        getCurrentlyPlaying(call);
        pauseMedia();
    }

    public Boolean getIsPlaying() {
        return isPlaying;
    }

    public Boolean getStopInTrackEnd() {
        return stopInTrackEnd;
    }

    public void setStopInTrackEnd(boolean b) {
        stopInTrackEnd = b;
    }

    /**
     * plays the song
     */
    public void playMedia() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            isPlaying = true;
            TrackInfo.getInstance().setIsPlaying(true);
        }
    }

    /**
     * stops the media player
     */
    public void stopMedia() {
        if (mediaPlayer == null) return;
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            isPlaying = false;
            TrackInfo.getInstance().setIsPlaying(false);
        }
    }

    /**
     * pauses the media player
     */

    public void pauseMedia() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPlaying = false;
            TrackInfo.getInstance().setIsPlaying(false);
            resumePosition = mediaPlayer.getCurrentPosition();
        }
    }

    /**
     * @return the current position of the song to update the progress bar
     */
    public int getCurrentPosition() {
        if (mediaPlayer != null && prepared) {
            return mediaPlayer.getCurrentPosition();
        } else {
            return 0;
        }
    }

    /**
     * @return the duration of the song
     */
    public int getDuration() {
        if (mediaPlayer != null && prepared) {
            return mediaPlayer.getDuration();
        } else {
            return 1;
        }
    }

    /**
     * navigates the media player to the given position and starts playing the song
     *
     * @param s
     */
    public void seekTo(int s) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(s * 1000);
            mediaPlayer.start();
            isPlaying = true;
            TrackInfo.getInstance().setIsPlaying(true);
        }
    }

    /**
     * resume the song
     */

    public void resumeMedia() {
        if (!mediaPlayer.isPlaying()) {
            //mediaPlayer.seekTo(resumePosition);
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

    /**
     * when the song is completed it stops
     *
     * @param mp
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        stopMedia();
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {

    }

    /**
     * when the media player is prepared it starts the song
     * @param mp
     */
    @Override
    public void onPrepared(MediaPlayer mp) {

        playMedia();
        prepared = true;
        TrackInfo.getInstance().setDuration(mediaPlayer.getDuration());
        if (playFlag == 0) {
            pauseMedia();
        }
        playFlag = 1;
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

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            stopMedia();
            mediaPlayer.release();
        }
        cancelTimer();
    }

    public class LocalBinder extends Binder {
        public MediaPlayerService getservice() {
            return MediaPlayerService.this;
        }
    }

    /**
     * starts the sleep timer and on finish pauses the media player
     * takes time in millisecond
     *
     * @param milliSeconds
     */

    public void startTimer(long milliSeconds) {
        if (mediaPlayer != null) {
            sleepTimer = new CountDownTimer(milliSeconds, 60000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    if (mediaPlayer != null) {
                        pauseMedia();
                        track.setTimerSet(false);
                        toast = Toast.makeText(getApplicationContext(), "sleep timer ended", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }.start();
        }
        track.setTimerSet(true);
    }

    /**
     * cancels the sleep timer
     */
    public void cancelTimer() {
        if (sleepTimer != null) {
            sleepTimer.cancel();
            track.setTimerSet(false);
        }
        if (stopInTrackEnd) {
            setStopInTrackEnd(false);
        }
    }

}
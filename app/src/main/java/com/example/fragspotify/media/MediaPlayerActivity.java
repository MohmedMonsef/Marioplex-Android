package com.example.fragspotify.media;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;

import com.example.fragspotify.Activities.MainActivity;
import com.example.fragspotify.Interfaces.EndPointAPI;
import com.example.fragspotify.R;
import com.example.fragspotify.SpotifyClasses.Artist_;
import com.example.fragspotify.SpotifyClasses.Image;
import com.example.fragspotify.SpotifyClasses.Track;
import com.example.fragspotify.media.MediaPlayerService;
import com.example.fragspotify.media.TrackInfo;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MediaPlayerActivity extends AppCompatActivity {

    private RelativeLayout top_layout;
    private TextView song_name;
    private TextView song_artist;
    private ImageView song_image;
    private TextView playlist_name;
    private TextView header;
    private ImageView play_pause;
    private SeekBar seek_bar;
    private TextView start_time;
    private TextView end_time;
    private ImageView next;
    private ImageView previous;
    private ImageView arrow;
    private ImageView song_settings_button;
    private ConstraintLayout song_settings;
    private LinearLayout sleep;
    private LinearLayout settings_like;
    private LinearLayout settings_add_to_playlist;
    private LinearLayout view_artist;
    private RelativeLayout sleep_sheet_hider;
    private ConstraintLayout sleep_timer;
    private Button five;
    private LinearLayout ten;
    private LinearLayout fifteen;
    private LinearLayout thirty;
    private LinearLayout fortyfive;
    private LinearLayout hour;
    private LinearLayout end_of_track;
    private LinearLayout turn_of_timer;
    private ImageView timer_image;
    BottomSheetBehavior sleepTimer;
    BottomSheetBehavior sheetBehavior;

    private TrackInfo track;
    private Track t;
    private String nametest;
    private EndPointAPI endPointAPI;
    private Retrofit retrofit;
    private MediaPlayerService player;
    private Handler mHandler = new Handler();
    boolean serviceBound = false;
    Toast toast;


    //Binding this Client to the AudioPlayer Service
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MediaPlayerService.LocalBinder binder = (MediaPlayerService.LocalBinder) service;
            player = binder.getservice();
            serviceBound = true;

            //Toast.makeText(MediaPlayerActivity.this, "Service Bound", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);

        //Bind the service
        bindService();

        //get Views
        getViews();

        sheetBehavior = BottomSheetBehavior.from(song_settings);
        sleepTimer = BottomSheetBehavior.from(sleep_timer);
        setSheetBehavior();
        setSleepTimerBehaviour();

        //////////////////////////////////////////////////////////////////
        ////////////////////settings listeners////////////////////////////
        //////////////////////////////////////////////////////////////////
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer(300000);
            }
        });
        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer(600000);
            }
        });
        fifteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer(900000);
            }
        });
        thirty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer(1800000);
            }
        });
        fortyfive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer(2700000);
            }
        });
        hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer(3600000);
            }
        });

        //till yoy fix the next and previous problems
//        end_of_track.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                player.setStopInTrackEnd(true);
//                track.setTimerSet(true);
//                sleepTimer.setState(BottomSheetBehavior.STATE_HIDDEN);
//                toast = Toast.makeText(getApplicationContext(),"Your sleep timer is set",Toast.LENGTH_SHORT);
//                toast.show();
//            }
//        });
        turn_of_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               player.cancelTimer();
               sleepTimer.setState(BottomSheetBehavior.STATE_HIDDEN);
               toast = Toast.makeText(getApplicationContext(),"Sleep timer is turned off",Toast.LENGTH_SHORT);
               toast.show();
            }
        });

        settings_add_to_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaPlayerActivity.this, AddToPlaylistActivity.class);
                startActivity(intent);
            }
        });
        //////////////////////////////////////////////////////////////////

        track = TrackInfo.getInstance();

        header.setText(track.getName());


        //Observers
        if(track.getTrack()!=null) {
            track.getTrack().observe(this, new Observer<Track>() {
                @Override
                public void onChanged(Track track) {
                    UpdateUI();
                }
            });
        }

        track.getIsPlaying().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    play_pause.setImageResource(R.drawable.pause);
                }
                else {
                    play_pause.setImageResource(R.drawable.play);
                }
            }
        });

        track.getDuration().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                seek_bar.setMax(integer/1000);
            }
        });

        track.getTimerSet().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    timer_image.setImageResource(R.drawable.sleep_timer_activiated);
                    turn_of_timer.setVisibility(View.VISIBLE);
                }
                else{
                    timer_image.setImageResource(R.drawable.sleep_timer);
                    turn_of_timer.setVisibility(View.GONE);
                }
            }
        });

        //Click Listeners
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(player.getIsPlaying()){
                    track.setIsPlaying(false);
                    player.pauseMedia();
                    play_pause.setImageResource(R.drawable.play);
                }
                else{
                    track.setIsPlaying(true);
                    player.resumeMedia();
                    play_pause.setImageResource(R.drawable.pause);
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                track.setIsPlaying(false);
                player.next();
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                track.setIsPlaying(false);
                player.previous();
            }
        });

        //UPDATE THE SEEK BAR AND THE START AND END TIME EVERY SECOND
        MediaPlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(player != null){
                    int mCurrentPosition = player.getCurrentPosition();
                    int duration = player.getDuration();
                    seek_bar.setProgress((mCurrentPosition/1000));
                    start_time.setText(getTimeString(mCurrentPosition));
                    end_time.setText(getTimeString(duration-mCurrentPosition));
                }
                //mHandler.postDelayed(this, 100);
                mHandler.post(this);
            }
        });




        //SEEK BAR LISTENER TO NAVIGATE THROW THE SONG
        seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(player!=null && fromUser) {
                    player.seekTo(progress);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


    }

    //CONVERTS THE TIME FORMAT FROM MILLISECONDS TO MM:SS
    private String getTimeString(long millis) {
        StringBuffer buf = new StringBuffer();

        int minutes = (int) ((millis % (1000 * 60 * 60)) / (1000 * 60));
        int seconds = (int) (((millis % (1000 * 60 * 60)) % (1000 * 60)) / 1000);

        buf.append(String.format("%02d", minutes))
                .append(":")
                .append(String.format("%02d", seconds));

        return buf.toString();
    }

    private void bindService(){
        Intent serviceIntent1 = new Intent(this , MediaPlayerService.class);
        // serviceIntent1.putExtra("media" , media);
        bindService(serviceIntent1 , serviceConnection , Context.BIND_AUTO_CREATE);

    }

    void UpdateUI(){

        song_name.setText(track.getTrack().getValue().getName());

        List<Artist_> artists = track.getTrack().getValue().getArtists();
        String artistsNames = "";
        for(Artist_ artist_ : artists)
        {
            artistsNames+=artist_.getName() +" ";
        }
        song_artist.setText(artistsNames);


        playlist_name.setText(track.getTrack().getValue().getAlbum().getName());
        header.setText("PLAYING SONG");

        List<Image> images= track.getTrack().getValue().getAlbum().getImages();
        String Imageurl = images.get(0).getUrl();
        Picasso.get().load(Imageurl).into(song_image);


    }

    void setSheetBehavior(){
        sheetBehavior.setHideable(true);
        sheetBehavior.setPeekHeight(0);
        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        song_settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                } else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
            }
        });

        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
    }
    void setSleepTimerBehaviour(){
        sleepTimer.setHideable(true);
        sleepTimer.setPeekHeight(0);
        sleepTimer.setState(BottomSheetBehavior.STATE_HIDDEN);
        sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sleepTimer.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    sleepTimer.setState(BottomSheetBehavior.STATE_EXPANDED);

                } else {
                    sleepTimer.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
            }
        });

        sleep_sheet_hider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                sleepTimer.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });

        sleepTimer.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
    }

    //FINDS ALL VIEWS BY ID
    private void getViews(){
        top_layout = (RelativeLayout)findViewById(R.id.top_layout);
        song_name = (TextView)findViewById(R.id.song_name);
        song_artist = (TextView)findViewById(R.id.song_artist);
        song_image = (ImageView) findViewById(R.id.song_image);
        playlist_name = (TextView)findViewById(R.id.playlist_name);
        header = (TextView)findViewById(R.id.header);
        play_pause=(ImageView) findViewById(R.id.play);
        seek_bar = (SeekBar)findViewById(R.id.seek_bar);
        start_time = (TextView)findViewById(R.id.start_time);
        end_time = (TextView)findViewById(R.id.end_time);
        next = (ImageView)findViewById(R.id.next);
        previous = (ImageView)findViewById(R.id.previous);
        arrow = (ImageView) findViewById(R.id.arrow);
        song_settings = (ConstraintLayout)findViewById(R.id.song_settings);
        sleep_timer = (ConstraintLayout)findViewById(R.id.sleep_timer);
        song_settings_button = (ImageView)findViewById(R.id.song_settings_button);
        sleep = (LinearLayout)findViewById(R.id.sleep);
        settings_like = (LinearLayout)findViewById(R.id.settings_like);
        settings_add_to_playlist = (LinearLayout)findViewById(R.id.settings_add_to_playlist);
        view_artist = (LinearLayout)findViewById(R.id.view_artist);
        sleep_sheet_hider = (RelativeLayout)findViewById(R.id.sleep_sheet_hider);
        timer_image = (ImageView)findViewById(R.id.timer_image);
        five = (Button)findViewById(R.id.five);
        ten = (LinearLayout)findViewById(R.id.ten_minutes);
        fifteen = (LinearLayout)findViewById(R.id.fifteen_minutes);
        thirty = (LinearLayout)findViewById(R.id.thirty_minutes);
        fortyfive = (LinearLayout)findViewById(R.id.fortyfive_inutes);
        hour = (LinearLayout)findViewById(R.id.hour);
        end_of_track = (LinearLayout)findViewById(R.id.end_of_track);
        turn_of_timer = (LinearLayout)findViewById(R.id.turn_of_timer);

    }


    void startTimer(long milliSeconds){
        player.startTimer(milliSeconds);   //3 seconds
        sleepTimer.setState(BottomSheetBehavior.STATE_HIDDEN);
        toast = Toast.makeText(getApplicationContext(),"Your sleep timer is set",Toast.LENGTH_SHORT);
        toast.show();
    }

    //SENDS A REQUEST TO THE ENDPOINT API AND GETS A TRACK AND UPDATED THE UI
    void getATrack(){
        Call<Track> call = endPointAPI.getATrack();

        call.enqueue(new Callback<Track>() {
            @Override
            public void onResponse(Call<Track> call, Response<Track> response) {
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
                    t = track.getTrack().getValue();
                    if(t!=null){
                        header.setText(t.getName()+" . "+"Artist _ name");
                    }
//                    track.getTrack().observe(MediaPlayerActivity.this, new Observer<Track>() {
//                        @Override
//                        public void onChanged(Track track) {
//                            song_name.setText(track.getName());
//                        }
//                    });
                }

            }

            @Override
            public void onFailure(Call<Track> call, Throwable t) {
                toast = Toast.makeText(getApplicationContext(),t.getMessage()+" 'failed '",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    };

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}

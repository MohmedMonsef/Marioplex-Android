package com.example.spotify.media;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
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
import androidx.palette.graphics.Palette;

import com.example.spotify.Activities.MainActivity;
import com.example.spotify.Interfaces.EndPointAPI;
import com.example.spotify.Interfaces.Retrofit;
import com.example.spotify.R;
import com.example.spotify.login.user;
import com.example.spotify.pojo.ImageInfo;
import com.example.spotify.pojo.currentTrack;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private ImageView setting_image;
    private TextView setting_song_name;
    private TextView setting_artist_id;
    private ImageView favorite;
    private ImageView favorite2;
    private LinearLayout activity_views_container;
    private RelativeLayout settings_upper_relative_layout;
    private LinearLayout setting_container;
    BottomSheetBehavior sleepTimer;
    BottomSheetBehavior sheetBehavior;

    private TrackInfo track;
    private String nametest;
    private MediaPlayerService player;
    private Handler mHandler = new Handler();
    boolean serviceBound = false;
    private EndPointAPI endPointAPI = Retrofit.getInstance().getEndPointAPI();
    Toast toast;

    private Palette.Swatch vibrantSwatch;
    private Palette.Swatch lightVibrantSwatch;
    private Palette.Swatch darkVibrantSwatch;
    private Palette.Swatch mutedSwatch;
    private Palette.Swatch lightMutedSwatch;
    private Palette.Swatch darkMutedSwatch;

    /**
     * Binding this Client to the AudioPlayer Service
     */
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MediaPlayerService.LocalBinder binder = (MediaPlayerService.LocalBinder) service;
            player = binder.getservice();
            serviceBound = true;
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

        sheetBehavior  = BottomSheetBehavior.from(song_settings);
        sleepTimer = BottomSheetBehavior.from(sleep_timer);
        setSheetBehavior();
        setSleepTimerBehaviour();

        //////////////////////////////////////////////////////////////////
        ////////////////////settings listeners////////////////////////////
        //////////////////////////////////////////////////////////////////
        /**
         * on press sets the duration of the sleep timer
         */
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


        /**
         * on press the sleep timer bottom sheet expands
         */
        turn_of_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.cancelTimer();
                sleepTimer.setState(BottomSheetBehavior.STATE_HIDDEN);
                toast = Toast.makeText(getApplicationContext(),"Sleep timer is turned off",Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        /**
         * on press the add to playlist activity opens
         */

        settings_add_to_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaPlayerActivity.this, AddToPlaylistActivity.class);
                if(TrackInfo.getInstance().getTrack()!=null && TrackInfo.getInstance().getTrack().getValue()!=null &&TrackInfo.getInstance().getTrack().getValue().getTrack()!=null){
                    intent.putExtra("track_id", TrackInfo.getInstance().getTrack().getValue().getTrack().getId());
                }
                else{
                    intent.putExtra("track_id", "");
                }
                intent.putExtra("from" , "MediaPlayerActivity");
                startActivity(intent);
            }
        });
        //////////////////////////////////////////////////////////////////

        track = TrackInfo.getInstance();

        header.setText(track.getName());

        /////////////////////////////////////Observers//////////////////////////////////
        /**
         * observes for any changes in the current track info to update the UI
         */
        if(track.getTrack()!=null) {
            track.getTrack().observe(this, new Observer<currentTrack>() {
                @Override
                public void onChanged(currentTrack track) {
                    UpdateUI();
                }
            });
        }
        /**
         * observes if there is a track playing to update the pause/play button image
         */

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
        /**
         * sets the progress bar duration with the current track duration
         */

        track.getDuration().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                seek_bar.setMax(integer/1000);
            }
        });

        /**
         * observes if there is a timer set to update the UI
         */
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

        ////////////////////////////////Click Listeners/////////////////////////////////////
        /**
         * listener for the back arrow button to close the media player activity
         */
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
        /**
         * listener for the click on the play/pause button
         */
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

        /**
         * listener for the click on the next button
         */
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                track.setIsPlaying(false);
                player.next();
            }
        });
        /**
         * listener for the click on the previous button
         */

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                track.setIsPlaying(false);
                player.previous();
            }
        });

        /**
         * listener for the click on the heart image to like/unlike the track
         */
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(track.getTrack().getValue().getIsLiked()){
                    UnLikeTrack(track.getTrack().getValue().getTrack().getId());
                }
                else {
                    LikeTrack(track.getTrack().getValue().getTrack().getId());
                }
            }
        });

        /**
         * listener for the click on the setting heart image to like/unlike the track
         */
        favorite2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(track.getTrack().getValue().getIsLiked()){
                    UnLikeTrack(track.getTrack().getValue().getTrack().getId());
                }
                else {
                    LikeTrack(track.getTrack().getValue().getTrack().getId());
                }
            }
        });


        /**
         * UPDATE THE SEEK BAR AND THE START AND END TIME EVERY 100 MILLISECOND
         */
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
                mHandler.postDelayed(this, 100);
                //mHandler.post(this);
            }
        });


        /**
         * SEEK BAR LISTENER TO NAVIGATE THROW THE SONG
         */
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
    /**
     * sends request to like the current track and on response it updates the heart image
     * @param trackID
     */

    private void LikeTrack(String trackID){
        favorite.setEnabled(false);
        favorite2.setEnabled(false);
        Call<Void> call = endPointAPI.LikeTrack(trackID , user.getToken());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                favorite.setEnabled(true);
                favorite2.setEnabled(true);
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"something went wrong .try again",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    favorite.setImageResource(R.drawable.like);
                    favorite2.setImageResource(R.drawable.like);
                    TrackInfo.getInstance().setIsLiked(true);
                    Toast.makeText(getApplicationContext(),"Added to Liked Songs",Toast.LENGTH_SHORT).show();
                    track.getTrack().getValue().setIsLiked(true);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"something went wrong .check your internet connection",Toast.LENGTH_SHORT).show();
                favorite.setEnabled(true);
                favorite2.setEnabled(true);
            }
        });
    }

    /**
     * sends request to unlike the current track and on response it updates the heart image
     * @param trackID
     */

    private void UnLikeTrack(String trackID){
        favorite.setEnabled(false);
        favorite2.setEnabled(false);
        Call<Void> call = endPointAPI.UNLikeTrack(trackID , user.getToken());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                favorite.setEnabled(true);
                favorite2.setEnabled(true);
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"something went wrong .try again",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    favorite.setImageResource(R.drawable.favorite_border);
                    favorite2.setImageResource(R.drawable.favorite_border);
                    TrackInfo.getInstance().setIsLiked(false);
                    Toast.makeText(getApplicationContext(),"Removed from Liked Songs",Toast.LENGTH_SHORT).show();
                    track.getTrack().getValue().setIsLiked(false);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"something went wrong .check your internet connection",Toast.LENGTH_SHORT).show();
                favorite.setEnabled(true);
                favorite2.setEnabled(true);
            }
        });
    }

    /**
     * CONVERTS THE TIME FORMAT FROM MILLISECONDS TO MM:SS
     * @param millis
     * @return
     */
    private String getTimeString(long millis) {
        StringBuffer buf = new StringBuffer();

        int minutes = (int) ((millis % (1000 * 60 * 60)) / (1000 * 60));
        int seconds = (int) (((millis % (1000 * 60 * 60)) % (1000 * 60)) / 1000);

        buf.append(String.format("%02d", minutes))
                .append(":")
                .append(String.format("%02d", seconds));

        return buf.toString();
    }

    /**
     * bindes the media player service to the activity
     */

    private void bindService(){
        Intent serviceIntent1 = new Intent(this , MediaPlayerService.class);
        bindService(serviceIntent1 , serviceConnection , Context.BIND_AUTO_CREATE);

    }

    /**
     * get a color that from the image and set the background with that color
     * @param v  the image view
     * @param put  the background's layout
     */
    void getPaletteAndSetBackgroundColor(ImageView v , final LinearLayout put){
        Bitmap bitmap = ((BitmapDrawable) v.getDrawable()).getBitmap();

        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                if(palette.getMutedSwatch() != null){
                    GradientDrawable gd = new GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM,
                            new int[] {palette.getMutedSwatch().getRgb(),0xFF000000});
                    gd.setCornerRadius(0f);
                    put.setBackgroundDrawable( gd);
                }
                else if( palette.getDarkVibrantSwatch() != null){
                    GradientDrawable gd = new GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM,
                            new int[] {palette.getDarkVibrantSwatch().getRgb(),0xFF000000});
                    gd.setCornerRadius(0f);
                    put.setBackgroundDrawable( gd);
                }
                else if(palette.getLightMutedSwatch() != null) {
                    GradientDrawable gd = new GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM,
                            new int[] {palette.getLightMutedSwatch().getRgb(),0xFF000000});
                    gd.setCornerRadius(0f);
                    put.setBackgroundDrawable( gd);
                }
                else if(palette.getDarkMutedSwatch() != null){
                    GradientDrawable gd = new GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM,
                            new int[] {palette.getDarkMutedSwatch().getRgb(),0xFF000000});
                    gd.setCornerRadius(0f);
                    put.setBackgroundDrawable( gd);
                }
                else if(palette.getLightVibrantSwatch() != null){
                    GradientDrawable gd = new GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM,
                            new int[] {palette.getLightVibrantSwatch().getRgb(),0xFF000000});
                    gd.setCornerRadius(0f);
                    put.setBackgroundDrawable( gd);
                }
                else if(palette.getVibrantSwatch()!=null){
                    GradientDrawable gd = new GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM,
                            new int[] {palette.getVibrantSwatch().getRgb(),0xFF000000});
                    gd.setCornerRadius(0f);
                    put.setBackgroundDrawable( gd);
                }
            }
        });
    }

    /**
     * updates the media player activity's UI
     */
    void UpdateUI(){
        String trackName = "";
        if(track.getTrack().getValue().getTrack()!=null) {
            trackName = track.getTrack().getValue().getTrack().getName();
        }
        song_name.setText(trackName);
        setting_song_name.setText(trackName);

        String artistName = "";
        if(track.getTrack().getValue().getAlbum()!=null && track.getTrack().getValue().getAlbum().getArtist()!=null && track.getTrack().getValue().getTrack()!=null){
            artistName = track.getTrack().getValue().getAlbum().getArtist().getName();
        }
        song_artist.setText(artistName);
        setting_artist_id.setText(artistName);


        playlist_name.setText(track.getTrack().getValue().getAlbum().getName());
        header.setText("PLAYING SONG");


        List<ImageInfo> images= track.getTrack().getValue().getTrack().getImages();
        String imageID = "12d";
        if(images !=null&& images.size() !=0){
             imageID = images.get(0).getID();
        }
        final String Imageurl = Retrofit.getInstance().getBaseurl() + "api/images/" + imageID + "?belongs_to=track";
        Picasso.get().load(Imageurl).into(song_image, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                //song_image.setImageResource(R.drawable.testimage2);
                getPaletteAndSetBackgroundColor(song_image , activity_views_container);
            }

            @Override
            public void onError(Exception e) {
                Picasso.get().load(Imageurl).into(song_image, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        getPaletteAndSetBackgroundColor(song_image , activity_views_container);
                    }

                    @Override
                    public void onError(Exception e) {
                        song_image.setImageResource(R.drawable.ic_smile1);
                    }
                });
            }
        });


        Picasso.get().load(Imageurl).into(setting_image, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                getPaletteAndSetBackgroundColor(setting_image , setting_container);
            }

            @Override
            public void onError(Exception e) {
                Picasso.get().load(Imageurl).into(setting_image, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        getPaletteAndSetBackgroundColor(setting_image , setting_container);
                    }

                    @Override
                    public void onError(Exception e) {
                        setting_image.setImageResource(R.drawable.ic_smile1);
                    }
                });
            }
        });

        if(track.getTrack().getValue().getIsLiked()){
            favorite.setImageResource(R.drawable.like);
            favorite2.setImageResource(R.drawable.like);
        }
        else{
            favorite.setImageResource(R.drawable.favorite_border);
            favorite2.setImageResource(R.drawable.favorite_border);
        }
    }

    /**
     * sets the expand and collapse behavior of the song settings fragment
     */
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
        settings_upper_relative_layout.setOnClickListener(new View.OnClickListener() {
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

    /**
     * sets the expand and collapse behavior of the sleep timer fragment
     */
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

    /**
     * gets all the views i will use
     */
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
        setting_image = (ImageView)findViewById(R.id.setting_image);
        setting_song_name = (TextView)findViewById(R.id.setting_song_name);
        setting_artist_id = (TextView)findViewById(R.id.setting_artist_id);
        settings_upper_relative_layout = (RelativeLayout)findViewById(R.id.settings_upper_relative_layout);
        favorite = (ImageView)findViewById(R.id.favorite);
        favorite2 = (ImageView)findViewById(R.id.favorite2);
        activity_views_container = (LinearLayout)findViewById(R.id.activity_views_container);
        setting_container = (LinearLayout)findViewById(R.id.setting_container);

    }

    /**
     * starts the sleep timer
     * takes the time in milliseconds
     * @param milliSeconds
     */

    void startTimer(long milliSeconds){
        player.startTimer(milliSeconds);   //3 seconds
        sleepTimer.setState(BottomSheetBehavior.STATE_HIDDEN);
        toast = Toast.makeText(getApplicationContext(),"Your sleep timer is set",Toast.LENGTH_SHORT);
        toast.show();
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
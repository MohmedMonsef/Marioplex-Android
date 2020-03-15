package com.example.spotify;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.text.Layout;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/* //////////////////////WARNING/////////////////////////
    -THE TOKEN COULD BE EXPIRED SO THE TRACK DATA WON'T BE DISPLAYED
    -GET A TOKEN AND REPLACE IT IN THE HEADER STRING IN THE ENDPOINTAPI.JAVA INTERFACE
    -CHECK THE INTERNET CONNECTION SO THE AUDIO COULD BE UPLOADED FROM THE INTERNET

 */
public class MainActivity extends AppCompatActivity {

    private SlidingUpPanelLayout slidingUpPanelLayout ;
    private LinearLayout bottom_layout;
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
    private ImageView bottom_image_id;
    private TextView song_artist_name;
    private ImageView bottom_favorite;
    private ImageView bottom_play_pause;
    private ImageView next;
    private ImageView previous;

    private int currentTrackId = 0;
    private int duration;
    private Handler mHandler = new Handler();
    private EndPointAPI endPointAPI;
    private Retrofit retrofit;
    private Track track;
    private Tracks tracks;
    private Toast toast;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find some Views by id
        getViews();

        //Expand and Collapse states updated
        updateBottomSheet();
        toast = Toast.makeText(getApplicationContext(),"welcome",Toast.LENGTH_SHORT);
        toast.show();

        //RETROFIT OBJECT TO SEND QUERIES AND PARSE RESPONSES
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.spotify.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //CREATE AN OBJECT FROM THE INTERFACE THAT HAS THE REQUESTS FUNCTIONS
        endPointAPI = retrofit.create(EndPointAPI.class);

        //REQUEST A TRACK FROM THE API
   //     getATrack();

        //REQUEST TRACKS FROM THE API
        getTracks();

        //SET THE AUDIO SOURCE TO THE MEDIA PLAYER
        final String trackUrl = "https://drive.google.com/uc?export=download&id=1ZcXYB9Uuol139qW-eV5NNNpZjLpDkuwF";
        playandpuse(trackUrl);

        //THE PLAY AND PAUSE BUTTON FUNCTION
        play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    play_pause.setImageResource(R.drawable.play);
                    bottom_play_pause.setImageResource(R.drawable.play_down);
                }
                else {
                    mediaPlayer.start();
                    play_pause.setImageResource(R.drawable.pause);
                    bottom_play_pause.setImageResource(R.drawable.pause_down);
                }
            }
        });
         bottom_play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    bottom_play_pause.setImageResource(R.drawable.play_down);
                    play_pause.setImageResource(R.drawable.play);
                }
                else {
                    mediaPlayer.start();
                    bottom_play_pause.setImageResource(R.drawable.pause_down);
                    play_pause.setImageResource(R.drawable.pause);
                }
            }
        });

        //NEXT BUTTON
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tracksSize = tracks.getTracks().size();
                if(++currentTrackId < tracksSize) {
                    updateTracksUI(tracks.getTracks().get(currentTrackId));
                    releaseMediaPlayer();
                    String trackurl = "https://drive.google.com/uc?export=download&id=1PngaMC-VE9MlD8uSSwg_Ua10N-evIg4w";
                    playandpuse(trackurl);
                }
                else {
                    currentTrackId = 0;
                    releaseMediaPlayer();
                    updateTracksUI(tracks.getTracks().get(currentTrackId));
                    String trackurl = "https://drive.google.com/uc?export=download&id=1PngaMC-VE9MlD8uSSwg_Ua10N-evIg4w";
                    playandpuse(trackurl);
                }
            }
        });
        //PREVIOUS BUTTON
                previous.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int tracksSize = tracks.getTracks().size();
                        if(--currentTrackId >-1) {
                            updateTracksUI(tracks.getTracks().get(currentTrackId));
                            releaseMediaPlayer();
                            String trackurl = "https://drive.google.com/uc?export=download&id=1PngaMC-VE9MlD8uSSwg_Ua10N-evIg4w";
                            playandpuse(trackurl);
                        }
                        else {
                            currentTrackId = tracksSize-1;
                            releaseMediaPlayer();
                            updateTracksUI(tracks.getTracks().get(currentTrackId));
                            String trackurl = "https://drive.google.com/uc?export=download&id=1PngaMC-VE9MlD8uSSwg_Ua10N-evIg4w";
                            playandpuse(trackurl);
                        }
                    }
                });


        //REPEAT THE TRACK WHEN FINISHED
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.seekTo(0);
                mediaPlayer.start();

            }
        });


        //UPDATE THE SEEK BAR AND THE START AND END TIME EVERY SECOND
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer != null){
                    int mCurrentPosition = mediaPlayer.getCurrentPosition();
                   seek_bar.setProgress((mCurrentPosition/1000));
                   start_time.setText(getTimeString(mCurrentPosition));
                   end_time.setText(getTimeString(duration-mCurrentPosition));
                }
                mHandler.postDelayed(this, 1000);
            }
        });

        //SEEK BAR LISTENER TO NAVIGATE THROW THE SONG
        seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaPlayer!=null && fromUser) {
                    mediaPlayer.seekTo(progress * 1000);
                    if(mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                    }
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

    private void playandpuse(String trackURL){
        mediaPlayer=new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try{
            mediaPlayer.setDataSource(trackURL);
            mediaPlayer.prepareAsync();

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Toast toast=Toast.makeText(getApplicationContext(),"audio is prepared",Toast.LENGTH_SHORT);
                    toast.show();
                    seek_bar.setMax(mediaPlayer.getDuration()/1000);
                    duration = mediaPlayer.getDuration();
                    mediaPlayer.start();
                    play_pause.setImageResource(R.drawable.pause);
                    bottom_play_pause.setImageResource(R.drawable.pause_down);
                    //mediaPlayer.start();

                }

            });
//            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                @Override
//                public void onCompletion(MediaPlayer mp) {
//                    //mediaPlayer.release();
//                }
//            });
        }
        catch (IOException ex){
            Toast toast=Toast.makeText(getApplicationContext(),"el url 8alat ya o5ty ",Toast.LENGTH_SHORT);
            toast.show();
        }
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
                    track = response.body();
                    updateTrackUI();
                }

            }

            @Override
            public void onFailure(Call<Track> call, Throwable t) {
               toast = Toast.makeText(getApplicationContext(),t.getMessage()+" 'failed '",Toast.LENGTH_SHORT);
               toast.show();
            }
        });
    };

//SENDS A REQUEST TO THE ENDPOINT API AND GETS A TRACK
    void getTracks(){
        Call<Tracks> call = endPointAPI.getTracks("7ouMYWpwJ422jRcDASZB7P,4VqPOruhp5EdPBeR92t6lQ,2takcwOaAZWiXQijPHIx7B");

        call.enqueue(new Callback<Tracks>() {
            @Override
            public void onResponse(Call<Tracks> call, Response<Tracks> response) {
                if(!response.isSuccessful()){
                    toast = Toast.makeText(getApplicationContext(),"Code: "+response.code(),Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                else if(response.body()==null){
                    toast = Toast.makeText(getApplicationContext(),"response body = null",Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    tracks = response.body();
                    updateTracksUI(tracks.getTracks().get(0));
                }
            }
            @Override
            public void onFailure(Call<Tracks> call, Throwable t) {
                toast = Toast.makeText(getApplicationContext(),t.getMessage()+" 'failed '",Toast.LENGTH_SHORT);
                song_artist_name.setText(t.getMessage());
                toast.show();
            }
        });
    }

    //FINDS ALL VIEWS BY ID
    private void getViews(){
        bottom_layout = (LinearLayout) findViewById(R.id.bottom_layout);
        top_layout = (RelativeLayout)findViewById(R.id.top_layout);
        slidingUpPanelLayout = (SlidingUpPanelLayout)findViewById(R.id.sliding_panel);
        song_name = (TextView)findViewById(R.id.song_name);
        song_artist = (TextView)findViewById(R.id.song_artist);
        song_image = (ImageView) findViewById(R.id.song_image);
        playlist_name = (TextView)findViewById(R.id.playlist_name);
        header = (TextView)findViewById(R.id.header);
        play_pause=(ImageView) findViewById(R.id.play);
        seek_bar = (SeekBar)findViewById(R.id.seek_bar);
        start_time = (TextView)findViewById(R.id.start_time);
        end_time = (TextView)findViewById(R.id.end_time);
        bottom_image_id = (ImageView)findViewById(R.id.bottom_image_id);
        song_artist_name = (TextView)findViewById(R.id.song_artist_name);
        bottom_favorite = (ImageView) findViewById(R.id.bottom_favorite);
        bottom_play_pause = (ImageView)findViewById(R.id.bottom_play_pause);
        next = (ImageView)findViewById(R.id.next);
        previous = (ImageView)findViewById(R.id.previous);
    }

    //UPDATE THE NFO OF THE UI WITH THE TRACK INFO
    private void updateTrackUI()
    {
        song_name.setText(track.getName());

        List<Artist_> artists = track.getArtists();
        String artistsNames = "";
        for(Artist_ artist_ : artists)
        {
         artistsNames+=artist_.getName() +" ";
        }
        song_artist.setText(artistsNames);
        song_artist_name.setText(track.getName()+" . "+artistsNames);
     //   song_artist_name.setText("jhghjgjghghgjhjghjhjgghgjhgjhgjgjhjhvnbvnmvnbvnbvnvbvmnbvnvbvnvmbnvbnbvnvnbnvbvmnbvmbnvbvbvv");


        playlist_name.setText(track.getAlbum().getName());
        header.setText("PLAYING SONG");

        List<Image> images= track.getAlbum().getImages();
        String Imageurl = images.get(0).getUrl();
        new DownLoadImageTask(song_image).execute(Imageurl);
        new DownLoadImageTask(bottom_image_id).execute(Imageurl);
    }

    //C//UPDATE THE NFO OF THE UI WITH THE TRACK INFO
    private void updateTracksUI(Track mtrack)
    {
        song_name.setText(mtrack.getName());

        List<Artist_> artists = mtrack.getArtists();
        String artistsNames = "";
        for(Artist_ artist_ : artists)
        {
         artistsNames+=artist_.getName() +" ";
        }
        song_artist.setText(artistsNames);
        song_artist_name.setText(mtrack.getName()+" . "+artistsNames);
     //   song_artist_name.setText("jhghjgjghghgjhjghjhjgghgjhgjhgjgjhjhvnbvnmvnbvnbvnvbvmnbvnvbvnvmbnvbnbvnvnbnvbvmnbvmbnvbvbvv");


        playlist_name.setText(mtrack.getAlbum().getName());
        header.setText("PLAYING SONG");

        List<Image> images= mtrack.getAlbum().getImages();
        String Imageurl = images.get(0).getUrl();
        new DownLoadImageTask(song_image).execute(Imageurl);
        new DownLoadImageTask(bottom_image_id).execute(Imageurl);
    }

    //COLLAPSES AND EXPANDS THE BOTTOM SHEET ON CLICK
    private void updateBottomSheet(){
        slidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState,
                                            SlidingUpPanelLayout.PanelState newState) {

                if(previousState.equals(SlidingUpPanelLayout.PanelState.COLLAPSED )&&
                        !newState.equals(SlidingUpPanelLayout.PanelState.COLLAPSED))
                {   slidingUpPanelLayout.setDragView(top_layout);
                    bottom_layout.setVisibility(View.GONE);
                }
                if(previousState.equals(SlidingUpPanelLayout.PanelState.EXPANDED )&&
                        !newState.equals(SlidingUpPanelLayout.PanelState.EXPANDED))
                {
                    bottom_layout.setVisibility(View.VISIBLE);
                    slidingUpPanelLayout.setDragView(bottom_layout);
                }

            }
        });
    }

    //RELEASES THE MEDIA PLAYER OBJECT (DESTRUCTOR)
    private void releaseMediaPlayer(){

        if(mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    //LOADS AN IMAGE FROM A URL IN THE BACKGROUND
    private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }

    //WHEN THE ACTIVITY IS DESTROYED RELEASE THE MEDIA PLAYER
    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
    }
}

/*TO BE CHANGED IN INTEGRATION
    THE AUDIO FILE WILL BE TRACK.GETURL()
    IMAGEURL WILL BE TRACK.GETIMAGEURL()
    IN NEXT AND PREVIOUS CLICK LISTNERS THE URL SHOULD CHANGE TO TRACKS.GETTRACKS().GETURL()
 */
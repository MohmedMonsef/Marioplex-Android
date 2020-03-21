package media;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
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

import com.example.spotifycorrected.MainActivity;
import com.example.spotifycorrected.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import pojo.Artist_;
import pojo.EndPointAPI;
import pojo.Image;
import pojo.Track;
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
    BottomSheetBehavior sheetBehavior;

    private TrackInfo track;
    private Track t;
    private String nametest;
    private EndPointAPI endPointAPI;
    private Retrofit retrofit;
    Toast toast;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);

        getViews();

        sheetBehavior = BottomSheetBehavior.from(song_settings);
        setSheetBehavior();

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext() , MainActivity.class);
                startActivity(intent);
            }
        });

        track = TrackInfo.getInstance();

        header.setText(track.getName());

        ///////////////////////////////////////////////////////////////////
        //RETROFIT OBJECT TO SEND QUERIES AND PARSE RESPONSES
//        retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.spotify.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        //CREATE AN OBJECT FROM THE INTERFACE THAT HAS THE REQUESTS FUNCTIONS
//        endPointAPI = retrofit.create(EndPointAPI.class);
//        getATrack();

        if(track.getTrack()!=null) {
            track.getTrack().observe(this, new Observer<Track>() {
                @Override
                public void onChanged(Track track) {
                    UpdateUI();
                }
            });
        }

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
        new DownLoadImageTask(song_image).execute(Imageurl);
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
        song_settings_button = (ImageView)findViewById(R.id.song_settings_button);
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



}

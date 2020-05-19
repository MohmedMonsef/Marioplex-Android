package com.example.spotify.Fragments.LIBRARY_FRAGMENT.Playlist_library_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.spotify.Activities.MainActivity;
import com.example.spotify.Interfaces.EndPointAPI;
import com.example.spotify.Interfaces.Retrofit;
import com.example.spotify.R;
import com.example.spotify.login.user;
import com.example.spotify.pojo.UploadImageResponse;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Playlist_Library_Settings extends AppCompatActivity {
    private LinearLayout settings_delete;
    private LinearLayout settings_upload_photo;
    private ImageView setting_playlist_image;
    private TextView playlist_name_settings;
    private ImageView settings_playlist_library_arrow;
    private String playlistName ;
    private String imageID ;
    private String SourceId = "";
    private EndPointAPI endPointAPI = Retrofit.getInstance().getEndPointAPI();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_library_settings);
        getViews();
        SourceId = getIntent().getStringExtra("SourceID");
        imageID = getIntent().getStringExtra("ImageID");
        playlistName = getIntent().getStringExtra("playlistName");
        updateUI();

        /////////////////////////listeners///////////////////////////////
        /**
         * listener for the delete view to send a request to delete the playlist
         */
        settings_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeletePlaylist();
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        /**
         * listener for the upload image view to go to  the upload image activity
         */
        settings_upload_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Upload_Image.class);
                intent.putExtra("SourceID" , SourceId);
                intent.putExtra("from" , "playlist");
                startActivity(intent);
            }
        });

        /**
         * listener for the click on the back arrow to go back the playlist fragment
         */
        settings_playlist_library_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
    }

    /**
     * updates the image and the name of the playlist
     */

    void updateUI(){
        playlist_name_settings.setText(playlistName);
        String Imageurl = Retrofit.getInstance().getBaseurl() + "api/images/" + imageID + "?belongs_to=playlist";
        Picasso.get().load(Imageurl).into(setting_playlist_image);
    }

    /**
     * sends a request to delete the playlist
     */
    void DeletePlaylist(){
        Call<UploadImageResponse> call = endPointAPI.deletePlaylist(SourceId , user.getToken());
        call.enqueue(new Callback<UploadImageResponse>() {
            @Override
            public void onResponse(Call<UploadImageResponse> call, Response<UploadImageResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "something went wrong .try again", Toast.LENGTH_SHORT).show();
                    return;}
                else{
                    Toast.makeText(getApplicationContext() , "playlist is deleted successfully" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UploadImageResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext() , "something went wrong. check your internet connection " , Toast.LENGTH_SHORT).show();
            }
        });


    }

    /**
     * gets all the views I will use in the fragment
     */

    void getViews(){
        settings_delete = findViewById(R.id.settings_delete);
        settings_upload_photo = findViewById(R.id.settings_upload_photo);
        setting_playlist_image = findViewById(R.id.setting_playlist_image);
        playlist_name_settings = findViewById(R.id.playlist_name_settings);
        settings_playlist_library_arrow = findViewById(R.id.settings_playlist_library_arrow);
    }
}

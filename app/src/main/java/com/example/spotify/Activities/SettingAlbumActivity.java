package com.example.spotify.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spotify.R;

public class SettingAlbumActivity extends AppCompatActivity {
String ArtistName;
String AlbumName;
boolean liked;
String AlbumId;
ImageView album_image_album_fragment_setting,like_album_setting,view_artist,like_album_all;
TextView album_owner_setting,album_name_middle_setting,like_album_setting_text,view_artist_text,like_album_all_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_album);


    }
    void reciveDataFromAlbumFragment()
    {
        Intent i =getIntent();
        ArtistName=i.getStringExtra("ARTIST_NAME");
        AlbumId=i.getStringExtra("ALBUM_ID");
        AlbumName=i.getStringExtra("Album_Name");

    }
}

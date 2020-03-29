package com.example.fragspotify.media;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fragspotify.R;

public class CreatePlaylistActivity extends AppCompatActivity {

    private Button cancel_create_playlist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_playlist);

        getViews();

        //////////////////////////listeners/////////////////////////////
        cancel_create_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreatePlaylistActivity.this , MediaPlayerActivity.class);
                startActivity(intent);
            }
        });
        ////////////////////////////////////////////////////////////////
    }

    void getViews(){
        cancel_create_playlist = (Button)findViewById(R.id.cancel_create_playlist);
    }
}

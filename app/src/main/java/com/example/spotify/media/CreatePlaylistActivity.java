package com.example.spotify.media;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.spotify.Interfaces.EndPointAPI;
import com.example.spotify.Interfaces.Retrofit;
import com.example.spotify.R;
import com.example.spotify.pojo.playlist;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreatePlaylistActivity extends AppCompatActivity {

    private Button cancel_create_playlist;
    private EditText playlist_name_edit_text;
//    private Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl("http://52.205.254.29/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build();
    private EndPointAPI endPointAPI = Retrofit.getInstance().getEndPointAPI();
    private String trackID;
    private playlist createdPlaylist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_playlist);

        getViews();

        trackID = getIntent().getStringExtra("track_id");

        //////////////////////////listeners/////////////////////////////
        cancel_create_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreatePlaylistActivity.this , MediaPlayerActivity.class);
                startActivity(intent);
            }
        });

        playlist_name_edit_text.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    if(playlist_name_edit_text.getText().toString().matches("")){
                        Toast.makeText(getApplicationContext() , "Enter the playlist's name" , Toast.LENGTH_SHORT).show();
                    }
                    else{
                        //TODO uncomment the block to create and add the playlist

                        //creatPlaylist(playlist_name_edit_text.getText().toString());

                        Intent intent = new Intent(CreatePlaylistActivity.this , MediaPlayerActivity.class);
                        startActivity(intent);
                    }

                    return true;
                }
                return false;
            }
        });
        ////////////////////////////////////////////////////////////////
    }

    void creatPlaylist(String playlistName){
        Call<playlist> call = endPointAPI.CreatePlaylist(playlistName);

        call.enqueue(new Callback<playlist>() {
            @Override
            public void onResponse(Call<playlist> call, Response<playlist> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Code: "+response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(response.body()==null){
                    Toast.makeText(getApplicationContext(),"response body = null",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"playlist is created",Toast.LENGTH_SHORT).show();
                    createdPlaylist = response.body();
                    addToPlaylist(createdPlaylist.getId());
                }
            }
            @Override
            public void onFailure(Call<playlist> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage()+" ' failed '",Toast.LENGTH_SHORT).show();
            }
        });

    }

    void addToPlaylist(String playlistID){
        //TODO uncomment the below block in integeration

        if(trackID ==""){
            Toast.makeText(getApplicationContext(),"track isn't loaded yet check your internet connection",Toast.LENGTH_SHORT).show();
        }
        else{
            addTrackToPlaylist(playlistID , trackID);
        }

    }

    void addTrackToPlaylist(String pid , String tid){
        Call<playlist> call = endPointAPI.AddTrackToAPlaylist(pid , tid);

        call.enqueue(new Callback<playlist>() {
            @Override
            public void onResponse(Call<playlist> call, Response<playlist> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Code: "+response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(response.body()==null){
                    Toast.makeText(getApplicationContext(),"response body = null",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"track is added succesfully",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<playlist> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage()+" ' failed '",Toast.LENGTH_SHORT).show();
            }
        });
    }
    void getViews(){
        cancel_create_playlist = (Button)findViewById(R.id.cancel_create_playlist);
        playlist_name_edit_text = (EditText)findViewById(R.id.playlist_name_edit_text);
    }
}

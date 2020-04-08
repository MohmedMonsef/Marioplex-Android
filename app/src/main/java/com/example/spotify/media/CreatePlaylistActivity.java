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
import com.example.spotify.login.user;
import com.example.spotify.pojo.addTrackToPlaylistBody;
import com.example.spotify.pojo.playlist;
import com.example.spotify.pojo.createPlaylistBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreatePlaylistActivity extends AppCompatActivity {

    private Button cancel_create_playlist;
    private EditText playlist_name_edit_text;
    //    private Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl("http://192.168.1.7:3000/")
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
        /**
         * closes the create playlist activity and return to the media player activity
         */
        cancel_create_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreatePlaylistActivity.this , MediaPlayerActivity.class);
                startActivity(intent);
            }
        });
        /**
         * takes the playlist name and it ti's not empty it sends the request to create the playlist
         */

        playlist_name_edit_text.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    if(playlist_name_edit_text.getText().toString().matches("")){
                        Toast.makeText(getApplicationContext() , "Enter the playlist's name" , Toast.LENGTH_SHORT).show();
                    }
                    else{
                        //TODO uncomment the block to create and add the playlist

                        creatPlaylist(playlist_name_edit_text.getText().toString());

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

    /**
     * takes the playlist name and sends request to create the playlist
     * @param playlistName
     */

    void creatPlaylist(String playlistName){
        createPlaylistBody mcreatePlaylistBody = new createPlaylistBody();
        mcreatePlaylistBody.setDescibtion("");
        mcreatePlaylistBody.setName(playlistName);
        Call<playlist> call = endPointAPI.CreatePlaylist(mcreatePlaylistBody , user.getToken());

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

    /**
     * checks if the plaulist id is empty then display message else it adds the track to playlist
     * @param playlistID
     */

    void addToPlaylist(String playlistID){
        //TODO uncomment the below block in integeration

        if(trackID ==""){
            Toast.makeText(getApplicationContext(),"track isn't loaded yet check your internet connection",Toast.LENGTH_SHORT).show();
        }
        else{
            addTrackToPlaylist(playlistID , trackID);
        }

    }

    /**
     * takes the track id and the playlist id and adds the track to the playlist
     * @param pid playlist id
     * @param tid track id
     */
    void addTrackToPlaylist(String pid , String tid){
        addTrackToPlaylistBody t = new addTrackToPlaylistBody();
        t.setTrackID(tid);
        Call<Object> call = endPointAPI.AddTrackToAPlaylist(pid , t, user.getToken());

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"something wrong happened try again",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    Toast.makeText(getApplicationContext(),"track is added to playlist",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"something wrong happened check internet connection",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * gets all the views i will use
     */
    void getViews(){
        cancel_create_playlist = (Button)findViewById(R.id.cancel_create_playlist);
        playlist_name_edit_text = (EditText)findViewById(R.id.playlist_name_edit_text);
    }
}
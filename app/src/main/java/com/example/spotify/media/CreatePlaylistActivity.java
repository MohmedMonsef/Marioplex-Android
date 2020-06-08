package com.example.spotify.media;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.spotify.Activities.MainActivity;
import com.example.spotify.Interfaces.EndPointAPI;
import com.example.spotify.Interfaces.Retrofit;
import com.example.spotify.R;
import com.example.spotify.login.user;
import com.example.spotify.pojo.addTrackToPlaylistBody;
import com.example.spotify.pojo.createPlaylistBody;
import com.example.spotify.pojo.playlist;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePlaylistActivity extends AppCompatActivity {

    private Button cancel_create_playlist;
    private Button create_playlist_button;
    private EditText playlist_name_edit_text;
    private EndPointAPI endPointAPI = Retrofit.getInstance().getEndPointAPI();
    private String trackID;
    private String from;
    private playlist createdPlaylist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_playlist);

        getViews();

        trackID = getIntent().getStringExtra("track_id");
        from = getIntent().getStringExtra("from");


        //////////////////////////listeners/////////////////////////////
        /**
         * closes the create playlist activity and return to the media player activity
         */
        cancel_create_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                if(from.equals("MediaPlayerActivity")) {
                    intent = new Intent(CreatePlaylistActivity.this, MediaPlayerActivity.class);
                }
                else if ((from.equals("TrackFragment"))||(from.equals("Playlist_library"))) {
                    intent = new Intent(getBaseContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                }
                startActivity(intent);
            }
        });

        /**
         * listener for create playlist button
         */
        create_playlist_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(playlist_name_edit_text.getText().toString().isEmpty()){
                        Toast.makeText(getApplicationContext() , "Enter the playlist's name" , Toast.LENGTH_SHORT).show();
                    }
                    else{
                        creatPlaylist(playlist_name_edit_text.getText().toString());

                        Intent intent = null;
                        if(from.equals("MediaPlayerActivity")) {
                            intent = new Intent(CreatePlaylistActivity.this, MediaPlayerActivity.class);
                        }
                        else if ((from.equals("TrackFragment"))||(from.equals("Playlist_library"))) {
                            intent = new Intent(getBaseContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        }
                        startActivity(intent);

                    }
            }
        });


        playlist_name_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    create_playlist_button.setBackgroundResource(R.drawable.rounded_button_grey);
                }
                else{
                    create_playlist_button.setBackgroundResource(R.drawable.rounded_button);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //////////////////////////////////////////////////////////////
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
                    Toast.makeText(getApplicationContext(),"something went wrong.try again.",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    Toast.makeText(getApplicationContext(),"playlist is created",Toast.LENGTH_SHORT).show();
                    createdPlaylist = response.body();
                    if(!from.equals("Playlist_library")){
                        addToPlaylist(createdPlaylist.getId());
                    }
                }
            }
            @Override
            public void onFailure(Call<playlist> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"something went wrong .check your internet connection.",Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * checks if the plaulist id is empty then display message else it adds the track to playlist
     * @param playlistID
     */

    void addToPlaylist(String playlistID){

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
        create_playlist_button = (Button)findViewById(R.id.create_playlist_button);
    }
}
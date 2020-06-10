package com.example.spotify.Fragments.LIBRARY_FRAGMENT.Playlist_library_fragment;

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
import com.example.spotify.pojo.editPlaylistNameBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditPlaylistName extends AppCompatActivity {
    private EndPointAPI endPointAPI = Retrofit.getInstance().getEndPointAPI();
    private EditText playlist_new_name_edit_text;
    private Button cancel_edit_playlist_name;
    private Button rename_playlist_button;
    private String playlist_ID = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_playlist);
        getViews();

        playlist_ID = getIntent().getStringExtra("SourceID");
        cancel_edit_playlist_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        /**
         * listener for the typing in the edit text view to change enable and disable the edit button
         */
        playlist_new_name_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    rename_playlist_button.setBackgroundResource(R.drawable.rounded_button_grey);
                }
                else{
                    rename_playlist_button.setBackgroundResource(R.drawable.rounded_button);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        rename_playlist_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playlist_new_name_edit_text.getText().toString().matches("")){
                    Toast.makeText(getApplicationContext() , "Enter the playlist's new name" , Toast.LENGTH_SHORT).show();
                }
                else{
                    editPlaylistName(playlist_new_name_edit_text.getText().toString() , playlist_ID);

                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);

                }
            }
        });

    }

    /**
     * sends a request to edit the playlist name
     * @param newName
     * @param playlistID
     */
    void editPlaylistName(String newName , String playlistID){
        editPlaylistNameBody editplaylistNameBody = new editPlaylistNameBody();
        editplaylistNameBody.setNewName(newName);
        Call<Void> call = endPointAPI.editPlaylisttName(playlistID , user.getToken() , editplaylistNameBody);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"something went wrong.try again.",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    Toast.makeText(getApplicationContext(),"playlist name is updated",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"something went wrong .check your internet connection.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * gets all the views I will use in the fragment
     */

    void getViews(){
        playlist_new_name_edit_text = findViewById(R.id.playlist_new_name_edit_text);
        cancel_edit_playlist_name = findViewById(R.id.cancel_edit_playlist_name);
        rename_playlist_button = findViewById(R.id.rename_playlist_button);
    }
}

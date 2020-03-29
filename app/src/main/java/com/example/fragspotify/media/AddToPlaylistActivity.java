package com.example.fragspotify.media;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fragspotify.Interfaces.EndPointAPI;
import com.example.fragspotify.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddToPlaylistActivity extends AppCompatActivity {

    private ImageView back_button_to_mediaplayer;
    private Button new_playlist_button;
    private ListView playlists_list_view;
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.spotify.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private EndPointAPI endPointAPI = retrofit.create(EndPointAPI.class);
    String[] playlistsnames = {"kaleo" , "one direction" , "maher zain"};
    int[] images = {R.drawable.add_music,R.drawable.down_arrow,R.drawable.pause_down};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_playlist);

        getviews();

        /////////////////////////List view attaching//////////////////////////////
        CustomAdapter customAdapter = new CustomAdapter();
        playlists_list_view.setAdapter(customAdapter);


        //////////////////////////////////////////////////////////////////////////

        /////////////////////////Listeners//////////////////////////////
        back_button_to_mediaplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddToPlaylistActivity.this , MediaPlayerActivity.class);
                startActivity(intent);
            }
        });

        new_playlist_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddToPlaylistActivity.this , CreatePlaylistActivity.class);
                startActivity(intent);
            }
        });

        playlists_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //take the playlist id and add the song to it



                Intent intent = new Intent(AddToPlaylistActivity.this , MediaPlayerActivity.class);
                startActivity(intent);
            }
        });
        ////////////////////////////////////////////////////////////////
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    void getviews(){
        back_button_to_mediaplayer = (ImageView)findViewById(R.id.back_button_to_mediaplayer);
        new_playlist_button = (Button)findViewById(R.id.new_playlist_button);
        playlists_list_view = (ListView)findViewById(R.id.playlists_list_view);
    }



    private class CustomAdapter extends BaseAdapter{

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.playlist_list_view_layout, parent, false);
            ImageView user_playlist_image = (ImageView)convertView.findViewById(R.id.user_playlist_image);
            TextView user_playlist_name = (TextView)convertView.findViewById(R.id.user_playlist_name);
            TextView playlist_user_name = (TextView)convertView.findViewById(R.id.playlist_user_name);

            user_playlist_image.setImageResource(images[position]);
            user_playlist_name.setText(playlistsnames[position]);
            playlist_user_name.setText("by "+"hager ali");

            return convertView;
        }
        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
    }
}

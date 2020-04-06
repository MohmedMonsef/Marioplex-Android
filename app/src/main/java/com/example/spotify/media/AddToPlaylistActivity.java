package com.example.spotify.media;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.spotify.Interfaces.EndPointAPI;
import com.example.spotify.Interfaces.Retrofit;
import com.example.spotify.R;
import com.example.spotify.SpotifyClasses.Image;
import com.example.spotify.SpotifyClasses.UserPlaylists;
import com.example.spotify.pojo.playlist;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddToPlaylistActivity extends AppCompatActivity {

    private ImageView back_button_to_mediaplayer;
    private Button new_playlist_button;
    private ListView playlists_list_view;
    private Toast toast;
    private UserPlaylists userPlaylists;
    private ProgressBar playlists_progress_bar;
    private String trackID;

//    private Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl("http://52.205.254.29/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build();
    private EndPointAPI endPointAPI = Retrofit.getInstance().getEndPointAPI();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_playlist);

        getviews();

        trackID = getIntent().getStringExtra("track_id");



        /////////////////////////List view attaching will be made in the on response callback///////
        //////make the request then attach it to the listview//////

        getPlaylists();

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
                intent.putExtra("track_id", trackID);
                startActivity(intent);
            }
        });

        playlists_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //take the playlist id and add the song to it
                //TODO uncomment the below block in integeration

//                if(userPlaylists !=null && userPlaylists.getItems() !=null){
//                    String playlist_id = userPlaylists.getItems().get(position).getId();
//                    if(trackID ==""){
//                        toast = Toast.makeText(getApplicationContext(),"track isn't loaded yet check your internet connection",Toast.LENGTH_SHORT);
//                        toast.show();
//                    }
//                    else{
//                        addTrackToPlaylist(playlist_id , trackID);
//                    }
//
//                }
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

    void addTrackToPlaylist(String pid , String tid){
        Call<playlist> call = endPointAPI.AddTrackToAPlaylist(pid , tid);

        call.enqueue(new Callback<playlist>() {
            @Override
            public void onResponse(Call<playlist> call, Response<playlist> response) {
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
                    toast = Toast.makeText(getApplicationContext(),"track is added succesfully",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            @Override
            public void onFailure(Call<playlist> call, Throwable t) {
                toast = Toast.makeText(getApplicationContext(),t.getMessage()+" ' failed '",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    void getviews(){
        back_button_to_mediaplayer = (ImageView)findViewById(R.id.back_button_to_mediaplayer);
        new_playlist_button = (Button)findViewById(R.id.new_playlist_button);
        playlists_list_view = (ListView)findViewById(R.id.playlists_list_view);
        playlists_progress_bar = (ProgressBar)findViewById(R.id.playlists_progress_bar);
    }

    void getPlaylists(){
        Call<UserPlaylists> call = endPointAPI.getUserPlaylists();

        call.enqueue(new Callback<UserPlaylists>() {
            @Override
            public void onResponse(Call<UserPlaylists> call, Response<UserPlaylists> response) {
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
                    userPlaylists = response.body();
                    CustomAdapter customAdapter = new CustomAdapter();
                    playlists_list_view.setAdapter(customAdapter);

                    playlists_progress_bar.setVisibility(View.GONE);
                    toast = Toast.makeText(getApplicationContext(),"response came and is being parsed",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<UserPlaylists> call, Throwable t) {
                toast = Toast.makeText(getApplicationContext(),t.getMessage()+" ' failed '",Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }



    private class CustomAdapter extends BaseAdapter{

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.playlist_list_view_layout, parent, false);
            ImageView user_playlist_image = (ImageView)convertView.findViewById(R.id.user_playlist_image);
            TextView user_playlist_name = (TextView)convertView.findViewById(R.id.user_playlist_name);
            TextView playlist_user_name = (TextView)convertView.findViewById(R.id.playlist_user_name);


            if(userPlaylists.getItems()!=null) {

                List<Image> images= userPlaylists.getItems().get(position).getImages();
                String Imageurl = images.get(0).getUrl();
                Picasso.get().load(Imageurl).into(user_playlist_image);

                user_playlist_name.setText(userPlaylists.getItems().get(position).getName());

                playlist_user_name.setText("by " + userPlaylists.getItems().get(position).getOwner().getDisplayName());
            }
            return convertView;
        }
        @Override
        public int getCount() {
            if(userPlaylists.getItems() != null){
                return userPlaylists.getItems().size();
            }
            else{
                return 0;
            }
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

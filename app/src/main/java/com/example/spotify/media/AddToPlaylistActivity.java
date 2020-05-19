package com.example.spotify.media;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.spotify.Activities.MainActivity;
import com.example.spotify.Interfaces.EndPointAPI;
import com.example.spotify.Interfaces.Retrofit;
import com.example.spotify.R;
import com.example.spotify.login.user;
import com.example.spotify.pojo.BasicPlaylist;
import com.example.spotify.pojo.ImageInfo;
import com.example.spotify.pojo.addTrackToPlaylistBody;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddToPlaylistActivity extends AppCompatActivity {

    private ImageView back_button_to_mediaplayer;
    private Button new_playlist_button;
    private ListView playlists_list_view;
    private Toast toast;
    private List<BasicPlaylist> userPlaylists;
    private ProgressBar playlists_progress_bar;
    private String trackID;
    private String from;
    private Button try_again;
    private LinearLayout something_wrong_layout2;
    private TextView something_wrong_text2;
    private EndPointAPI endPointAPI = Retrofit.getInstance().getEndPointAPI();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_playlist);

        getviews();

        trackID = getIntent().getStringExtra("track_id");
        from = getIntent().getStringExtra("from");


        /////////////////////////List view attaching will be made in the on response callback///////
        //////make the request then attach it to the listview//////
        playlists_progress_bar.setVisibility(View.VISIBLE);
        something_wrong_layout2.setVisibility(View.GONE);
        playlists_list_view.setVisibility(View.GONE);
        getPlaylists();

        //////////////////////////////////////////////////////////////////////////

        /////////////////////////Listeners//////////////////////////////
        /**
         * back button to go to the previous fragment
         */
        back_button_to_mediaplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                if (from.equals("MediaPlayerActivity")) {
                    intent = new Intent(AddToPlaylistActivity.this, MediaPlayerActivity.class);
                } else if (from.equals("TrackFragment")) {
                    intent = new Intent(getBaseContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                }
                startActivity(intent);
            }
        });

        /**
         * listener for the create playlist button when pressed it goes to the create playlist activity
         */

        new_playlist_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddToPlaylistActivity.this, CreatePlaylistActivity.class);
                intent.putExtra("track_id", trackID);
                intent.putExtra("from", from);
                startActivity(intent);
            }
        });

        /**
         * listener for the view pressed in the playlist views and sends request to add the current track to it
         */
        playlists_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //take the playlist id and add the song to it
                if (userPlaylists != null && userPlaylists.size() != 0) {
                    String playlist_id = userPlaylists.get(position).getId();
                    if (trackID == "") {
                        Toast.makeText(getApplicationContext(), "track isn't loaded yet check your internet connection", Toast.LENGTH_SHORT).show();
                    } else {
                        addTrackToPlaylist(playlist_id, trackID);
                    }

                }
                Intent intent = null;
                if (from.equals("MediaPlayerActivity")) {
                    intent = new Intent(AddToPlaylistActivity.this, MediaPlayerActivity.class);
                } else if (from.equals("TrackFragment")) {
                    intent = new Intent(getBaseContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                }
                startActivity(intent);
            }
        });
        /**
         * listener for the try again button
         * on press it sends the request to get the playlists again
         */

        try_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playlists_progress_bar.setVisibility(View.VISIBLE);
                something_wrong_layout2.setVisibility(View.GONE);
                playlists_list_view.setVisibility(View.GONE);
                getPlaylists();
            }
        });

        ////////////////////////////////////////////////////////////////
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * takes the current track id and adds the track to the playlist the user pressed on
     *
     * @param pid playlist id
     * @param tid track id
     */
    void addTrackToPlaylist(String pid, String tid) {
        addTrackToPlaylistBody t = new addTrackToPlaylistBody();
        t.setTrackID(tid);
        Call<Object> call = endPointAPI.AddTrackToAPlaylist(pid, t, user.getToken());

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "something wrong happened try again", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(getApplicationContext(), "track is added to playlist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "something wrong happened check internet connection", Toast.LENGTH_SHORT).show();

            }
        });
    }

    /**
     * get all th views i will use
     */

    void getviews() {
        back_button_to_mediaplayer = (ImageView) findViewById(R.id.back_button_to_mediaplayer);
        new_playlist_button = (Button) findViewById(R.id.new_playlist_button);
        playlists_list_view = (ListView) findViewById(R.id.playlists_list_view);
        playlists_progress_bar = (ProgressBar) findViewById(R.id.playlists_progress_bar);
        try_again = (Button) findViewById(R.id.try_again);
        something_wrong_layout2 = findViewById(R.id.something_wrong_layout2);
        something_wrong_text2 = findViewById(R.id.something_wrong_text);
    }

    /**
     * gets current user's playlists and updates the UI on response
     */
    void getPlaylists() {
        Call<List<BasicPlaylist>> call = endPointAPI.getCurrentUserPlaylists(user.getToken());
        call.enqueue(new Callback<List<BasicPlaylist>>() {
            @Override
            public void onResponse(Call<List<BasicPlaylist>> call, Response<List<BasicPlaylist>> response) {
                if (!response.isSuccessful()) {
                    something_wrong_layout2.setVisibility(View.VISIBLE);
                    playlists_progress_bar.setVisibility(View.GONE);
                    playlists_list_view.setVisibility(View.GONE);

                    return;
                }else {
                    userPlaylists = response.body();
                    filterPlaylists();
                    CustomAdapter customAdapter = new CustomAdapter();
                    playlists_list_view.setAdapter(customAdapter);
                    something_wrong_layout2.setVisibility(View.GONE);
                    playlists_list_view.setVisibility(View.VISIBLE);
                    playlists_progress_bar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<BasicPlaylist>> call, Throwable t) {
                something_wrong_layout2.setVisibility(View.VISIBLE);
                playlists_progress_bar.setVisibility(View.GONE);
                playlists_list_view.setVisibility(View.GONE);
            }
        });

    }

    /**
     * view only the created playlists because the user can't add track to a followed playlist
     */
    void filterPlaylists() {
        List<BasicPlaylist> temp = new ArrayList<>();
        for (int i = 0; i < userPlaylists.size(); i++) {
            if (userPlaylists.get(i).getType().equals("created")) {
                temp.add(userPlaylists.get(i));
            }
        }
        userPlaylists = temp;
    }


    private class CustomAdapter extends BaseAdapter {

        @Override
        /**
         * updates the UI with the playlists
         */
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.playlist_list_view_layout, parent, false);
            ImageView user_playlist_image = (ImageView) convertView.findViewById(R.id.user_playlist_image);
            TextView user_playlist_name = (TextView) convertView.findViewById(R.id.user_playlist_name);
            TextView playlist_user_name = (TextView) convertView.findViewById(R.id.playlist_user_name);


            if (userPlaylists != null) {

                List<ImageInfo> images = userPlaylists.get(position).getImages();
                String imageID = "12d";
                if (images != null && images.size() != 0) {
                    imageID = images.get(0).getID();
                }
                String Imageurl = Retrofit.getInstance().getBaseurl() + "api/images/" + imageID + "?belongs_to=playlist";
                Picasso.get().load(Imageurl).into(user_playlist_image);

                user_playlist_name.setText(userPlaylists.get(position).getName());

                playlist_user_name.setText("by " + userPlaylists.get(position).getOwner());
            }
            return convertView;
        }

        @Override
        public int getCount() {
            if (userPlaylists != null) {
                return userPlaylists.size();
            } else {
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

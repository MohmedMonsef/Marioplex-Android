package com.example.spotify.Activities;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.spotify.Fragments.PLAYLIST_FRAGMENT.PlaylistFragment;
import com.example.spotify.R;
import com.example.spotify.SpotifyClasses.Image;
import com.example.spotify.media.AddToPlaylistActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlaylistPreviewActivity extends AppCompatActivity {

    private ImageView back_arrow_from_preview;
    private TextView playlist_name_preview_top;
    private TextView playlist_name_preview;
    private ListView playlist_list_view_preview;
    private ProgressBar progress_bar_playlist_preview;

    private String[] song_names = {"Salam" , "when we meet" , "peace" , "because of you" , "hjkshks" , "kshdkhjflkdhls" , "kshlfjdhlskf" , "kdhjkfhld"};
    private String[] artist_names = {"ali" ,"kaleo" , "coldplay" , "sam smith" , "kshdfjf" ,"kdhlfjhs"  , "ksdhfjkdsh"};
    private String[] images = {};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_preview);
        /////////////////////get all the views i will use/////////////////////////
        getViews();


        /////////////////////some listeners\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        back_arrow_from_preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        /////////////////test the list view///////////////////////////
        CustomAdapter customAdapter = new CustomAdapter();
        playlist_list_view_preview.setAdapter(customAdapter);

    }
    void getViews(){
        back_arrow_from_preview = findViewById(R.id.back_arrow_from_preview);
        playlist_name_preview_top = findViewById(R.id.playlist_name_preview_top);
        playlist_name_preview = findViewById(R.id.playlist_name_preview);
        playlist_list_view_preview = findViewById(R.id.playlist_list_view_preview);
        progress_bar_playlist_preview = findViewById(R.id.progress_bar_playlist_preview);
    }


    private class CustomAdapter extends BaseAdapter {

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.playlist_preview_list_view_layout, parent, false);
            ImageView preview_playlist_image = (ImageView)convertView.findViewById(R.id.preview_song_image);
            TextView preview_song_name = (TextView)convertView.findViewById(R.id.preview_song_name);
            TextView preview_artist_name = (TextView)convertView.findViewById(R.id.preview_song_artist);


           // if(userPlaylists.getItems()!=null) {
            if(images!=null & images.length !=0) {
                //List<Image> images = userPlaylists.getItems().get(position).getImages();
                //String Imageurl = images.get(0).getUrl();
                Picasso.get().load(images[position]).into(preview_playlist_image);
            }
            String song = "";
            String artist = "";
            if(song_names.length > position) {
                song = song_names[position];
            }
            if(artist_names.length >position) {
                artist = artist_names[position];
            }

            preview_song_name.setText(song);
            preview_artist_name.setText(artist);


           // }
            return convertView;
        }
        @Override
        public int getCount() {
//            if(userPlaylists.getItems() != null){
//                return userPlaylists.getItems().size();
//            }
//            else{
//                return 0;
//            }
            return song_names.length;
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

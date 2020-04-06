package com.example.spotify.Activities;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.spotify.Fragments.PLAYLIST_FRAGMENT.PlaylistFragment;
import com.example.spotify.Fragments.PLAYLIST_FRAGMENT.PlaylistInfo;
import com.example.spotify.Interfaces.EndPointAPI;
import com.example.spotify.Interfaces.Retrofit;
import com.example.spotify.R;
import com.example.spotify.SpotifyClasses.Image;
import com.example.spotify.login.user;
import com.example.spotify.media.AddToPlaylistActivity;
import com.example.spotify.pojo.PlaylistTracks;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaylistPreviewActivity extends AppCompatActivity {

    private ImageView back_arrow_from_preview;
    private TextView playlist_name_preview_top;
    private TextView playlist_name_preview;
    private ListView playlist_list_view_preview;
    private ProgressBar progress_bar_playlist_preview;
    private EndPointAPI endPointAPI = Retrofit.getInstance().getEndPointAPI();

    //private String[] song_names = {"Salam" , "when we meet" , "peace" , "because of you" , "hjkshks" , "kshdkhjflkdhls" , "kshlfjdhlskf" , "kdhjkfhld"};
    //private String[] artist_names = {"ali" ,"kaleo" , "coldplay" , "sam smith" , "kshdfjf" ,"kdhlfjhs"  , "ksdhfjkdsh"};
    //private String[] images = {};
    private PlaylistTracks mplaylistTracks;

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



//        playlist_list_view_preview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//                ImageView like = view.findViewById(R.id.preview_like);
//
//            }
//        });

        /////////////////test the list view///////////////////////////
        //CustomAdapter customAdapter = new CustomAdapter();
        //playlist_list_view_preview.setAdapter(customAdapter);

        ///////////////////////observers\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        if(PlaylistInfo.getinstance().getplaylistTracks()!=null) {
            PlaylistInfo.getinstance().getplaylistTracks().observe(this, new Observer<PlaylistTracks>() {
                @Override
                public void onChanged(PlaylistTracks playlistTracks) {
                    mplaylistTracks = playlistTracks;
                    CustomAdapter customAdapter = new CustomAdapter();
                    playlist_list_view_preview.setAdapter(customAdapter);
                }
            });
        }
    }
    void getViews(){
        back_arrow_from_preview = findViewById(R.id.back_arrow_from_preview);
        playlist_name_preview_top = findViewById(R.id.playlist_name_preview_top);
        playlist_name_preview = findViewById(R.id.playlist_name_preview);
        playlist_list_view_preview = findViewById(R.id.playlist_list_view_preview);
        progress_bar_playlist_preview = findViewById(R.id.progress_bar_playlist_preview);
    }

    private void LikeTrack(String trackID ,final ImageView view1 ,final int pos){
        Call<Void> call = endPointAPI.LikeTrack(trackID , user.getToken());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"something went wrong .try again",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    view1.setImageResource(R.drawable.like);
                    mplaylistTracks.getTracks().get(pos).setIsLiked(true);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"something went wrong .check your internet connection",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void UnLikeTrack(String trackID ,final ImageView view1 , final int pos){
        Call<Void> call = endPointAPI.UNLikeTrack(trackID , user.getToken());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"something went wrong .try again",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    view1.setImageResource(R.drawable.favorite_border);
                    mplaylistTracks.getTracks().get(pos).setIsLiked(false);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"something went wrong .check your internet connection",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private class CustomAdapter extends BaseAdapter {

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.playlist_preview_list_view_layout, parent, false);
            ImageView preview_playlist_image = (ImageView)convertView.findViewById(R.id.preview_song_image);
            TextView preview_song_name = (TextView)convertView.findViewById(R.id.preview_song_name);
            TextView preview_artist_name = (TextView)convertView.findViewById(R.id.preview_song_artist);
            ImageView preview_like = (ImageView)convertView.findViewById(R.id.preview_like);


//           // if(userPlaylists.getItems()!=null) {
//            if(images!=null & images.length !=0) {
//                //List<Image> images = userPlaylists.getItems().get(position).getImages();
//                //String Imageurl = images.get(0).getUrl();
//                Picasso.get().load(images[position]).into(preview_playlist_image);
//            }

            List<Object> images= mplaylistTracks.getImages();
            if(images != null && images.size()!=0) {
                String Imageurl = images.get(0).toString();
                Picasso.get().load(Imageurl).into(preview_playlist_image);
            }

            String song = "";
            String artist = "";
//            if(song_names.length > position) {
//                song = song_names[position];
//            }
//            if(artist_names.length >position) {
//                artist = artist_names[position];
//            }

            song = mplaylistTracks.getTracks().get(position).getName();
            artist = mplaylistTracks.getTracks().get(position).getArtistName();
            preview_song_name.setText(song);
            preview_artist_name.setText(artist);

            if(mplaylistTracks.getTracks().get(position).getIsLiked()){
                preview_like.setImageResource(R.drawable.like);
            }
            else{
                preview_like.setImageResource(R.drawable.favorite_border);
            }

            preview_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mplaylistTracks.getTracks().get(position).getIsLiked()){
                        UnLikeTrack(mplaylistTracks.getTracks().get(position).getTrackid() ,(ImageView)v , position);
                    }
                    else{
                        LikeTrack(mplaylistTracks.getTracks().get(position).getTrackid() ,(ImageView)v , position);
                    }
                }
            });


           // }
            return convertView;
        }
        @Override
        public int getCount() {
            if(mplaylistTracks!=null && mplaylistTracks.getTracks() != null){
                return mplaylistTracks.getTracks().size();
            }
            else{
                return 0;
            }
            //return song_names.length;
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

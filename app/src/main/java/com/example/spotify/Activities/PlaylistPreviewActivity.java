package com.example.spotify.Activities;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.spotify.Fragments.PLAYLIST_FRAGMENT.PlaylistInfo;
import com.example.spotify.Interfaces.EndPointAPI;
import com.example.spotify.Interfaces.Retrofit;
import com.example.spotify.R;
import com.example.spotify.login.user;
import com.example.spotify.media.TrackInfo;
import com.example.spotify.pojo.BasicTrack;
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

    private PlaylistTracks mplaylistTracks;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_preview);
        /////////////////////get all the views i will use/////////////////////////
        getViews();


        /////////////////////some listeners\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        /**
         * listener for the press on the back arrow to exit the activity
         */
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

        ///////////////////////observers\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        /**
         * observes for the response of the get playlists requests and updates the UI
         */
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

    /**
     * gets all the views i will use
     */
    void getViews(){
        back_arrow_from_preview = findViewById(R.id.back_arrow_from_preview);
        playlist_name_preview_top = findViewById(R.id.playlist_name_preview_top);
        playlist_name_preview = findViewById(R.id.playlist_name_preview);
        playlist_list_view_preview = findViewById(R.id.playlist_list_view_preview);
        progress_bar_playlist_preview = findViewById(R.id.progress_bar_playlist_preview);
    }

    /**
     * sends request to like the track and updates the UI on response
     * @param trackID
     * @param view1
     * @param pos
     */

    private void LikeTrack(String trackID ,final ImageView view1 ,final int pos){
        view1.setEnabled(false);
        Call<Void> call = endPointAPI.LikeTrack(trackID , user.getToken());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                view1.setEnabled(true);
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"something went wrong .try again",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    view1.setImageResource(R.drawable.like);
                    Toast.makeText(getApplicationContext(),"Added to Liked Songs",Toast.LENGTH_SHORT).show();
                    mplaylistTracks.getTracks().get(pos).setIsLiked(true);
                    if(CheckTrack(pos)){
                        TrackInfo.getInstance().getTrack().getValue().setIsLiked(true);
                        TrackInfo.getInstance().setIsLiked(true);
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"something went wrong .check your internet connection",Toast.LENGTH_SHORT).show();
                view1.setEnabled(true);
            }
        });
    }

    /**
     * sends request to unlike the track and updates the UI on response
     * @param trackID
     * @param view1
     * @param pos
     */

    private void UnLikeTrack(String trackID ,final ImageView view1 , final int pos){
        view1.setEnabled(false);
        Call<Void> call = endPointAPI.UNLikeTrack(trackID , user.getToken());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                view1.setEnabled(true);
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"something went wrong .try again",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    view1.setImageResource(R.drawable.favorite_border);
                    Toast.makeText(getApplicationContext(),"Removed from Liked Songs",Toast.LENGTH_SHORT).show();
                    mplaylistTracks.getTracks().get(pos).setIsLiked(false);
                    if(CheckTrack(pos)){
                        TrackInfo.getInstance().getTrack().getValue().setIsLiked(false);
                        TrackInfo.getInstance().setIsLiked(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"something went wrong .check your internet connection",Toast.LENGTH_SHORT).show();
                view1.setEnabled(true);
            }
        });
    }

    /**
     * checks if the track the user pressed on is the same track that's currently playing
     * @param position
     * @return
     */
    Boolean CheckTrack(int position){
        TrackInfo trackInfo = TrackInfo.getInstance();
        if(trackInfo.getIsQueue() != null &&
                trackInfo.getIsQueue().getValue() &&
                trackInfo.getTrack()!=null &&
                trackInfo.getTrack().getValue()!=null &&
                trackInfo.getTrack().getValue().getTrack().getId().equals(mplaylistTracks.getTracks().get(position).getTrackid())) {
            return true;
        }
        return false;

    }



    private class CustomAdapter extends BaseAdapter {

        @Override
        /**
         * updates the UI with the playlists when the response comes
         */
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.playlist_preview_list_view_layout, parent, false);
            ImageView preview_playlist_image = (ImageView)convertView.findViewById(R.id.preview_song_image);
            TextView preview_song_name = (TextView)convertView.findViewById(R.id.preview_song_name);
            TextView preview_artist_name = (TextView)convertView.findViewById(R.id.preview_song_artist);
            ImageView preview_like = (ImageView)convertView.findViewById(R.id.preview_like);


            List<Object> images= mplaylistTracks.getImages();
            if(images != null && images.size()!=0) {
                String Imageurl = images.get(0).toString();
                Picasso.get().load(Imageurl).into(preview_playlist_image);
            }

            String song = "";
            String artist = "";

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

            TrackInfo trackInfo = TrackInfo.getInstance();
            if(CheckTrack(position))
            {
                if(trackInfo.getTrack().getValue().getIsLiked()){
                    preview_like.setImageResource(R.drawable.like);
                    mplaylistTracks.getTracks().get(position).setIsLiked(true);
                }
                else{
                    preview_like.setImageResource(R.drawable.favorite_border);
                    mplaylistTracks.getTracks().get(position).setIsLiked(false);
                }
            }



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

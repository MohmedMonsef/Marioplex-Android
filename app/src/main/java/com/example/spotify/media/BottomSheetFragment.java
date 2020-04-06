package com.example.spotify.media;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.spotify.R;
import com.example.spotify.SpotifyClasses.Artist_;
import com.example.spotify.SpotifyClasses.Image;
import com.example.spotify.SpotifyClasses.Track;
import com.squareup.picasso.Picasso;

import java.util.List;


public class BottomSheetFragment extends Fragment {

    ImageView bottom_image_id;
    TextView song_artist_name;
    ImageView bottom_play_pause;
    private TrackInfo track;
    private MediaPlayerService player;
    boolean serviceBound = false;
    //Binding this Client to the AudioPlayer Service
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MediaPlayerService.LocalBinder binder = (MediaPlayerService.LocalBinder) service;
            player = binder.getservice();
            serviceBound = true;

         //   Toast.makeText(getContext(), "Service Bound", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.bottom_sheet_fragment,container,false);

        //Bind the service
        bindService();

        //get views
        bottom_image_id = root.findViewById(R.id.bottom_image_id);
        song_artist_name = root.findViewById(R.id.song_artist_name);
        bottom_play_pause = root.findViewById(R.id.bottom_play_pause);

        track = TrackInfo.getInstance();
        track.setName("Hager");
        song_artist_name.setText(track.getName());


        //update UI first time
//        if(track.getIsPlaying().getValue()){
//            bottom_play_pause.setImageResource(R.drawable.pause_down);
//        }
//        else {
//            bottom_play_pause.setImageResource(R.drawable.play_down);
//        }


        //Click Listeners
        bottom_play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(player.getIsPlaying()){
                    track.setIsPlaying(false);
                    player.pauseMedia();
                    bottom_play_pause.setImageResource(R.drawable.play_down);
                }
                else{
                    track.setIsPlaying(true);
                    player.resumeMedia();
                    bottom_play_pause.setImageResource(R.drawable.pause_down);
                }
            }
        });

        bottom_image_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MediaPlayerActivity.class);
                startActivity(intent);
            }
        });

        //Observers

        track.getTrack().observe(this, new Observer<Track>() {
            @Override
            public void onChanged(Track track) {
                UpdateUI();
            }
        });

        track.getIsPlaying().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    bottom_play_pause.setImageResource(R.drawable.pause_down);
                }
                else {
                    bottom_play_pause.setImageResource(R.drawable.play_down);
                }
            }
        });


        return root;
    }

    void UpdateUI(){

        List<Artist_> artists = track.getTrack().getValue().getArtists();
        String artistsNames = "";
        for(Artist_ artist_ : artists)
        {
            artistsNames+=artist_.getName() +" ";
        }
        song_artist_name.setText(track.getTrack().getValue().getName()+" . "+artistsNames);


        List<Image> images= track.getTrack().getValue().getAlbum().getImages();
        String Imageurl = images.get(0).getUrl();
        Picasso.get().load(Imageurl).into(bottom_image_id);

    }
    private void bindService(){
        Intent serviceIntent1 = new Intent(getContext() , MediaPlayerService.class);
        getActivity().bindService(serviceIntent1 , serviceConnection ,Context.BIND_AUTO_CREATE);

    }

}

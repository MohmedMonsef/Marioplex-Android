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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.spotify.Interfaces.EndPointAPI;
import com.example.spotify.Interfaces.Retrofit;
import com.example.spotify.R;
import com.example.spotify.login.user;
import com.example.spotify.pojo.ImageInfo;
import com.example.spotify.pojo.currentTrack;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BottomSheetFragment extends Fragment {

    private ImageView bottom_image_id;
    private TextView song_artist_name;
    private ImageView bottom_play_pause;
    private ImageView bottom_favorite;
    private LinearLayout bottom_layout;
    private EndPointAPI endPointAPI = Retrofit.getInstance().getEndPointAPI();
    private TrackInfo track;
    private MediaPlayerService player;
    boolean serviceBound = false;
    /**
     * Binding this Client to the AudioPlayer Service
     */
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MediaPlayerService.LocalBinder binder = (MediaPlayerService.LocalBinder) service;
            player = binder.getservice();
            serviceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.bottom_sheet_fragment, container, false);

        //Bind the service
        bindService();

        //get views
        bottom_image_id = root.findViewById(R.id.bottom_image_id);
        song_artist_name = root.findViewById(R.id.song_artist_name);
        bottom_play_pause = root.findViewById(R.id.bottom_play_pause);
        bottom_layout = root.findViewById(R.id.bottom_layout);
        bottom_favorite = root.findViewById(R.id.bottom_favorite);

        track = TrackInfo.getInstance();
        track.setName("song name");
        song_artist_name.setText(track.getName());


        //Click Listeners
        /**
         * listener for the click on the play and pause button
         */
        bottom_play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player.getIsPrePared()) {
                    if (player.getIsPlaying()) {
                        track.setIsPlaying(false);
                        player.pauseMedia();
                        bottom_play_pause.setImageResource(R.drawable.play_down);
                    } else {
                        track.setIsPlaying(true);
                        player.resumeMedia();
                        bottom_play_pause.setImageResource(R.drawable.pause_down);
                    }
                }
                else{
                    Toast.makeText(getContext() , "audio is loading" , Toast.LENGTH_SHORT).show();
                }
            }

        });
        /**
         * listener for the click on the image to open the bottom sheet
         */

        bottom_image_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MediaPlayerActivity.class);
                startActivity(intent);
            }
        });
        /**
         * listener for the like button to send the like/unlike request
         */

        bottom_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (track.getTrack().getValue().getIsLiked()) {
                    UnLikeTrack(track.getTrack().getValue().getTrack().getId());
                } else {
                    LikeTrack(track.getTrack().getValue().getTrack().getId());
                }
            }
        });

        //Observers
        /**
         * observer for any change in track information to update the UI
         */
        track.getTrack().observe(this, new Observer<currentTrack>() {
            @Override
            public void onChanged(currentTrack track) {
                UpdateUI();
            }
        });

        track.getIsPlaying().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    bottom_play_pause.setImageResource(R.drawable.pause_down);
                } else {
                    bottom_play_pause.setImageResource(R.drawable.play_down);
                }
            }
        });

        /**
         * if the track is liked update the UI
         */

        track.getIsLiked().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    bottom_favorite.setImageResource(R.drawable.like);
                } else {
                    bottom_favorite.setImageResource(R.drawable.favorite_border);
                }
            }
        });
        /**
         * if the get current track fails it repeats it
         */

        track.gettryAgain().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    player.playCurrentTrack(endPointAPI.getCurrentlyPlaying(user.getToken()));
                }
            }
        });

        return root;
    }

    /**
     * sends like request for the current track and updates the UI on response
     *
     * @param trackID
     */

    private void LikeTrack(String trackID) {
        bottom_favorite.setEnabled(false);
        Call<Void> call = endPointAPI.LikeTrack(trackID, user.getToken());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                bottom_favorite.setEnabled(true);
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), "something went wrong .try again", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    bottom_favorite.setImageResource(R.drawable.like);
                    Toast.makeText(getContext(), "Added to Liked Songs", Toast.LENGTH_SHORT).show();
                    track.getTrack().getValue().setIsLiked(true);
                    TrackInfo.getInstance().setIsLiked(true);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "something went wrong .check your internet connection", Toast.LENGTH_SHORT).show();
                bottom_favorite.setEnabled(true);
            }
        });
    }

    /**
     * sends unlike request for the current track and updates the UI on response
     *
     * @param trackID
     */
    private void UnLikeTrack(String trackID) {
        bottom_favorite.setEnabled(false);
        Call<Void> call = endPointAPI.UNLikeTrack(trackID, user.getToken());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                bottom_favorite.setEnabled(true);
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), "something went wrong .try again", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    bottom_favorite.setImageResource(R.drawable.favorite_border);

                    Toast.makeText(getContext(), "Removed from Liked Songs", Toast.LENGTH_SHORT).show();
                    track.getTrack().getValue().setIsLiked(false);
                    TrackInfo.getInstance().setIsLiked(false);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "something went wrong .check your internet connection", Toast.LENGTH_SHORT).show();
                bottom_favorite.setEnabled(true);
            }
        });
    }

    /**
     * updates the ui when the track info changes
     */
    void UpdateUI() {

        String artistName = "";
        String songName = "";
        if (track.getTrack().getValue().getAlbum() != null && track.getTrack().getValue().getAlbum().getArtist() != null && track.getTrack().getValue().getTrack() != null) {
            artistName = track.getTrack().getValue().getAlbum().getArtist().getName();
            songName = track.getTrack().getValue().getTrack().getName();
        }
        song_artist_name.setText(songName + " . " + artistName);


        List<ImageInfo> images = track.getTrack().getValue().getTrack().getImages();
        String imageID = "12d";
        if (images != null && images.size() != 0) {
            imageID = images.get(0).getID();
        }
        String Imageurl = Retrofit.getInstance().getBaseurl() + "api/images/" + imageID + "?belongs_to=track";
        Picasso.get().load(Imageurl).into(bottom_image_id);

        if (track.getTrack().getValue().getIsLiked()) {
            bottom_favorite.setImageResource(R.drawable.like);
        } else {
            bottom_favorite.setImageResource(R.drawable.favorite_border);
        }
    }

    private void bindService() {
        Intent serviceIntent1 = new Intent(getContext(), MediaPlayerService.class);
        getActivity().bindService(serviceIntent1, serviceConnection, Context.BIND_AUTO_CREATE);

    }

}
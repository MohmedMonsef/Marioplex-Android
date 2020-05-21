package com.example.spotify.Activities;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.spotify.Fragments.PLAYLIST_FRAGMENT.PlaylistInfo;
import com.example.spotify.Interfaces.EndPointAPI;
import com.example.spotify.Interfaces.Retrofit;
import com.example.spotify.R;
import com.example.spotify.login.user;
import com.example.spotify.media.MediaPlayerService;
import com.example.spotify.media.TrackInfo;
import com.example.spotify.pojo.ImageInfo;
import com.example.spotify.pojo.PlaylistTracks;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaylistPreviewActivity extends AppCompatActivity {

    private ImageView back_arrow_from_preview;
    private TextView playlist_name_preview_top;
    private TextView playlist_name_preview;
    private ListView playlist_list_view_preview;
    private MediaPlayer mediaPlayer;
    private ProgressBar progress_bar_playlist_preview;
    private ProgressBar loadingProgressbar;
    private ImageView preview_song_image1;
    private MutableLiveData<Boolean> prepare = new MutableLiveData<>();
    private int idOfView;
    private EndPointAPI endPointAPI = Retrofit.getInstance().getEndPointAPI();

    private PlaylistTracks mplaylistTracks;

    private MediaPlayerService player;
    boolean serviceBound = false;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_preview);
        /////////////////////get all the views i will use/////////////////////////
        getViews();
        initMediaPlayer();

        //Bind the service
        bindService();

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

        ///////////////////////observers\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        /**
         * observes for the response of the get playlists requests and updates the UI
         */
        if (PlaylistInfo.getinstance().getplaylistTracks() != null) {
            PlaylistInfo.getinstance().getplaylistTracks().observe(this, new Observer<PlaylistTracks>() {
                @Override
                public void onChanged(PlaylistTracks playlistTracks) {
                    mplaylistTracks = playlistTracks;
                    playlist_name_preview.setText(mplaylistTracks.getName());
                    CustomAdapter customAdapter = new CustomAdapter();
                    playlist_list_view_preview.setAdapter(customAdapter);
                    customAdapter.notifyDataSetChanged();
                }
            });
        }
        /**
         * observer for the preparation of the preview to update the UI
         */
        prepare.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                CustomAdapter customAdapter = new CustomAdapter();
                playlist_list_view_preview.setAdapter(customAdapter);
                customAdapter.notifyDataSetChanged();
            }
        });
    }

    private void bindService() {
        Intent serviceIntent1 = new Intent(this, MediaPlayerService.class);
        bindService(serviceIntent1, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!prepare.getValue() && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!prepare.getValue() && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            mediaPlayer.stop();
            mediaPlayer = null;
        }
    }

    /**
     * gets all the views i will use
     */
    void getViews() {
        back_arrow_from_preview = findViewById(R.id.back_arrow_from_preview);
        playlist_name_preview_top = findViewById(R.id.playlist_name_preview_top);
        playlist_name_preview = findViewById(R.id.playlist_name_preview);
        playlist_list_view_preview = findViewById(R.id.playlist_list_view_preview);
        progress_bar_playlist_preview = findViewById(R.id.progress_bar_playlist_preview);
        loadingProgressbar = findViewById(R.id.loadingProgressbar);
        preview_song_image1 = findViewById(R.id.preview_song_image);
    }

    /**
     * sends request to like the track and updates the UI on response
     *
     * @param trackID
     * @param view1
     * @param pos
     */

    private void LikeTrack(String trackID, final ImageView view1, final int pos) {
        view1.setEnabled(false);
        Call<Void> call = endPointAPI.LikeTrack(trackID, user.getToken());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                view1.setEnabled(true);
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "something went wrong .try again", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    view1.setImageResource(R.drawable.like);
                    Toast.makeText(getApplicationContext(), "Added to Liked Songs", Toast.LENGTH_SHORT).show();
                    mplaylistTracks.getTracks().get(pos).setIsLiked(true);
                    if (CheckTrack(pos)) {
                        TrackInfo.getInstance().getTrack().getValue().setIsLiked(true);
                        TrackInfo.getInstance().setIsLiked(true);
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "something went wrong .check your internet connection", Toast.LENGTH_SHORT).show();
                view1.setEnabled(true);
            }
        });
    }

    /**
     * sends request to unlike the track and updates the UI on response
     *
     * @param trackID
     * @param view1
     * @param pos
     */

    private void UnLikeTrack(String trackID, final ImageView view1, final int pos) {
        view1.setEnabled(false);
        Call<Void> call = endPointAPI.UNLikeTrack(trackID, user.getToken());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                view1.setEnabled(true);
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "something went wrong .try again", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    view1.setImageResource(R.drawable.favorite_border);
                    Toast.makeText(getApplicationContext(), "Removed from Liked Songs", Toast.LENGTH_SHORT).show();
                    mplaylistTracks.getTracks().get(pos).setIsLiked(false);
                    if (CheckTrack(pos)) {
                        TrackInfo.getInstance().getTrack().getValue().setIsLiked(false);
                        TrackInfo.getInstance().setIsLiked(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "something went wrong .check your internet connection", Toast.LENGTH_SHORT).show();
                view1.setEnabled(true);
            }
        });
    }

    /**
     * checks if the track the user pressed on is the same track that's currently playing
     *
     * @param position
     * @return
     */
    Boolean CheckTrack(int position) {
        TrackInfo trackInfo = TrackInfo.getInstance();
        if (trackInfo.getIsQueue() != null &&
                trackInfo.getIsQueue().getValue() &&
                trackInfo.getTrack() != null &&
                trackInfo.getTrack().getValue() != null &&
                trackInfo.getTrack().getValue().getTrack().getId().equals(mplaylistTracks.getTracks().get(position).getTrackid())) {
            return true;
        }
        return false;

    }

    /**
     * initializes the media player to bind an audio to it
     */
    void initMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.reset();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        prepare.setValue(false);
    }


    private class CustomAdapter extends BaseAdapter {

        @Override
        /**
         * updates the UI with the playlists when the response comes
         */
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.playlist_preview_list_view_layout, parent, false);
            final ImageView preview_playlist_image = (ImageView) convertView.findViewById(R.id.preview_song_image);
            TextView preview_song_name = (TextView) convertView.findViewById(R.id.preview_song_name);
            TextView preview_artist_name = (TextView) convertView.findViewById(R.id.preview_song_artist);
            ImageView preview_like = (ImageView) convertView.findViewById(R.id.preview_like);
            RelativeLayout preview_container = convertView.findViewById(R.id.preview_container);
            final ImageView tran_play = convertView.findViewById(R.id.tran_play);
            final ProgressBar loadingProgressbar1 = convertView.findViewById(R.id.loadingProgressbar);


            //updating the UI with the tracks's images
            List<ImageInfo> images = mplaylistTracks.getTracks().get(position).getImages();

            String imageID = "12D";
            if (images != null && images.size() != 0) {
                imageID = images.get(0).getID();
            }
            String Imageurl = Retrofit.getInstance().getBaseurl() + "api/images/" + imageID + "?belongs_to=track";
            Picasso.get().load(Imageurl).into(preview_playlist_image);
            ////////////////////////////////////////////

            String song = "";
            String artist = "";

            song = mplaylistTracks.getTracks().get(position).getName();
            artist = mplaylistTracks.getTracks().get(position).getArtistName();
            preview_song_name.setText(song);
            preview_artist_name.setText(artist);

            if (mplaylistTracks.getTracks().get(position).getIsLiked() != null)
            {       if (mplaylistTracks.getTracks().get(position).getIsLiked()) {
                    preview_like.setImageResource(R.drawable.like);
                } else {
                    preview_like.setImageResource(R.drawable.favorite_border);
                }
            }
            /**
             * listener for the click on the like button to like/unlike the track
             */
            preview_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mplaylistTracks.getTracks().get(position).getIsLiked()) {
                        UnLikeTrack(mplaylistTracks.getTracks().get(position).getTrackid(), (ImageView) v, position);
                    } else {
                        LikeTrack(mplaylistTracks.getTracks().get(position).getTrackid(), (ImageView) v, position);
                    }
                }
            });
            /**
             * a click listener for the click on the image or the name of the track to play the track preview
             */
            preview_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!prepare.getValue()) {
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.pause();
                            mediaPlayer.stop();
                            mediaPlayer.reset();
                        } else {
                            Map<String, String> headers = new HashMap<String, String>();
                            headers.put("x-auth-token", user.getToken());
                            mediaPlayer.reset();
                            try {
                                //String TID = "5e9b64e4e9c8d87fdc2ecbd8";
                                String TID = mplaylistTracks.getTracks().get(position).getTrackid();
                                String s = Retrofit.getInstance().getBaseurl() + "api/tracks/android/" + TID + "?type=review";
                                mediaPlayer.setDataSource(PlaylistPreviewActivity.this, Uri.parse(s), headers);
                                loadingProgressbar1.setVisibility(View.VISIBLE);
                                preview_playlist_image.setVisibility(View.INVISIBLE);
                                tran_play.setVisibility(View.INVISIBLE);
                                prepare.setValue(true);
                                idOfView = position;
                                mediaPlayer.prepareAsync();

                            } catch (IOException e) {
                                Toast.makeText(getApplicationContext(), "something is wrong", Toast.LENGTH_SHORT);
                            }
                        }
                    }
                }
            });

            /**
             * listener for the audio preparation to remove the progress bar and start playing the preview
             */
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Toast.makeText(getApplicationContext(), "prepared ", Toast.LENGTH_SHORT);
                    if (player.getIsPlaying()) {
                        player.pauseMedia();
                        TrackInfo.getInstance().setIsPlaying(false);
                    }
                    loadingProgressbar1.setVisibility(View.INVISIBLE);
                    preview_playlist_image.setVisibility(View.VISIBLE);
                    prepare.setValue(false);

                    mediaPlayer.start();

                }
            });
            if (prepare.getValue() && position == idOfView) {
                loadingProgressbar1.setVisibility(View.VISIBLE);
                preview_playlist_image.setVisibility(View.INVISIBLE);
                tran_play.setVisibility(View.INVISIBLE);
            } else {
                loadingProgressbar1.setVisibility(View.INVISIBLE);
                //preview_playlist_image.setImageResource(R.drawable.ic_smile1);
                preview_playlist_image.setVisibility(View.VISIBLE);
                tran_play.setVisibility(View.VISIBLE);
            }


            TrackInfo trackInfo = TrackInfo.getInstance();
            if (CheckTrack(position)) {
                if (trackInfo.getTrack().getValue().getIsLiked()) {
                    preview_like.setImageResource(R.drawable.like);
                    mplaylistTracks.getTracks().get(position).setIsLiked(true);
                } else {
                    preview_like.setImageResource(R.drawable.favorite_border);
                    mplaylistTracks.getTracks().get(position).setIsLiked(false);
                }
            }


            // }
            return convertView;
        }

        @Override
        public int getCount() {
            if (mplaylistTracks != null && mplaylistTracks.getTracks() != null) {
                return mplaylistTracks.getTracks().size();
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
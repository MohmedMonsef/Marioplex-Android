package com.example.spotify.Fragments;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;

import com.example.spotify.Activities.PlaylistPreviewActivity;
import com.example.spotify.Fragments.PLAYLIST_FRAGMENT.PlaylistInfo;
import com.example.spotify.Fragments.SEARCH_FRAGMENT.searchfragment;
import com.example.spotify.Interfaces.EndPointAPI;
import com.example.spotify.Interfaces.Retrofit;
import com.example.spotify.R;
import com.example.spotify.login.user;
import com.example.spotify.media.AddToPlaylistActivity;
import com.example.spotify.media.MediaPlayerService;
import com.example.spotify.media.TrackInfo;
import com.example.spotify.pojo.BasicTrack;
import com.example.spotify.pojo.ImageInfo;
import com.example.spotify.pojo.PlaylistTracks;
import com.example.spotify.pojo.currentTrack;
import com.example.spotify.pojo.myTrack;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackFragment extends Fragment {
    private ImageView back_arrow_track;
    private LinearLayout track_contents_layout;
    private RelativeLayout top_layout_track;
    private TextView track_name_track_fragment;
    private ImageView track_settings_button;
    private ImageView like_track;
    private ImageView track_image_track_fragment;
    private TextView track_name_middle;
    private TextView track_singer;
    private Button shuffle_play_button_track;
    private TextView artist_name_song_name_track;
    private ProgressBar progress_bar_track;
    private LinearLayout something_wrong_layout_track;
    private TextView something_wrong_text_track;
    private Button something_wrong_button_track;
    private Button add_to_playlist_button;
    private LinearLayout track_container;


    private String TrackID = "";
    private String TrackName = "";
    private String TrackImage = "";
    private String CurrentTrackID = "";
    private int CurrentTrackPosInPlaylist;
    private MediaPlayerService player;
    private Boolean serviceBound = false;
    private EndPointAPI endPointAPI = Retrofit.getInstance().getEndPointAPI();
    private List<currentTrack> songTracks;


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
        View root = inflater.inflate(R.layout.fragment_track, container, false);
        ////////////////////get playlist id from the bundle\\\\\\\\\\\\\\\\\\\\\\\\
        TrackID = getArguments().getString("TrackID");
        //TrackID = "5e9b5e2de9c8d87fdc2eca81";
        TrackName = getArguments().getString("TrackName");
        TrackImage = getArguments().getString("TrackImage");

        /////////////////////get all the views i will use/////////////////////////
        getViews(root);

        //////////////////////Bind the service\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        bindService();


        /////////////////////some listeners\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

        /**
         * when pressed the activity closes
         */
        back_arrow_track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ////////////////return to my home fragment\\\\\\\\\\\\\\\\\\\\\
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_fragment, new searchfragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        /**
         * listener for the click on the like/unlike button
         */
        like_track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PlaylistInfo.getinstance().getplaylistTracks().getValue().getTracks().get(0).getIsLiked()) {
                    unLikeTrack();
                } else {
                    LikeTrack();
                }
            }
        });

        /**
         * listener for the click on the add to playlist button to go to the add to playlist activity
         */
        add_to_playlist_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddToPlaylistActivity.class);
                intent.putExtra("from", "TrackFragment");
                intent.putExtra("track_id", TrackID);
                startActivity(intent);
            }
        });

        /**
         * listener for the track names text
         * on press the preview activity opens
         */

        artist_name_song_name_track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlaylistPreviewActivity.class);
                startActivity(intent);
            }
        });

        /**
         * requests the get current playlist info requests if something goes wrong
         */
        something_wrong_button_track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //////////////////////////show progress bar\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
                track_contents_layout.setVisibility(View.GONE);
                something_wrong_layout_track.setVisibility(View.GONE);
                progress_bar_track.setVisibility(View.VISIBLE);
                /////////////////////////get Plsylist's tracks information\\\\\\\\\\\\\\\\\\\\\\\
                GetSongTracksInfo(TrackID);
            }
        });

        /**
         * listener for the shuffle play button and on press it sends a request to turn on the shuffle mode
         */

        shuffle_play_button_track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckTrackInPlaylist() && !player.getIsPlaying()) {
                    TrackInfo.getInstance().setIsPlaying(true);
                    player.resumeMedia();
                } else if (songTracks != null && songTracks.size() != 0) {
                    CreateQueue(songTracks.get(0).getTrack().getId());
                }
            }
        });



        //////////////////////////show progress bar\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        track_contents_layout.setVisibility(View.GONE);
        something_wrong_layout_track.setVisibility(View.GONE);
        progress_bar_track.setVisibility(View.VISIBLE);
        /////////////////////////get Plsylist's tracks information\\\\\\\\\\\\\\\\\\\\\\\
        GetSongTracksInfo(TrackID);


        return root;
    }

    /**
     * send a request to create queue of the current playlist's tracks and takes a track id and sets it as the current track
     *
     * @param trackID
     */
    void CreateQueue(String trackID) {
        Call<Void> call = endPointAPI.CreateQueue(songTracks.get(0).getAlbum().getId(), trackID, false, user.getToken());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), "something went wrong while creating the queue. try again." , Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    shuffleTracks();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "something went wrong while creating the queue.check your internet connection.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * send a request to turn on the shuffle mode and to play a random track from the playlist
     */

    void shuffleTracks() {
        Call<Void> call = endPointAPI.toggleShuffle(true, user.getToken());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), "something went wrong . try again.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    ///////call mediaplayer get current playing \\\\\\\\\\
                    Call<currentTrack> call1 = endPointAPI.getNext(user.getToken());
                    player.playCurrentTrack(call1);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "something went wrong.check your internet connection.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * send a request to get the relates songs to the current song
     * @param TrackID
     */
    void GetSongTracksInfo(String TrackID) {
        Call<List<currentTrack>> call = endPointAPI.getSongTracks(TrackID, user.getToken());
        call.enqueue(new Callback<List<currentTrack>>() {
            @Override
            public void onResponse(Call<List<currentTrack>> call, Response<List<currentTrack>> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    progress_bar_track.setVisibility(View.GONE);
                    track_contents_layout.setVisibility(View.GONE);
                    something_wrong_text_track.setText("something went wrong .try again");
                    something_wrong_layout_track.setVisibility(View.VISIBLE);
                    return;
                } else {
                    songTracks = response.body();
                    FillPlaylistTracks(songTracks);

                    track_contents_layout.setVisibility(View.VISIBLE);
                    something_wrong_layout_track.setVisibility(View.GONE);
                    progress_bar_track.setVisibility(View.GONE);
                    updateUI();
                }
            }

            @Override
            public void onFailure(Call<List<currentTrack>> call, Throwable t) {
                progress_bar_track.setVisibility(View.GONE);
                track_contents_layout.setVisibility(View.GONE);
                something_wrong_text_track.setText("something went wrong .check your internet connection");
                something_wrong_layout_track.setVisibility(View.VISIBLE);

            }
        });
    }

    /**
     * put the data int a singleton class to update the UI in the preview activity
     * @param SongTracks
     */
    void FillPlaylistTracks(List<currentTrack> SongTracks) {

        PlaylistTracks p = new PlaylistTracks();
        myTrack t = SongTracks.get(0).getTrack();
        p.setId(t.getId());
        p.setIsLiked(SongTracks.get(0).getIsLiked());
        p.setImages(t.getImages());
        p.setName(t.getName());
        p.setType("song");
        List<BasicTrack> basic = new ArrayList<>();
        for (int i = 0; i < SongTracks.size(); i++) {
            t = SongTracks.get(i).getTrack();
            BasicTrack basict = new BasicTrack();
            basict.setIsLiked(SongTracks.get(i).getIsLiked());
            basict.setAlbumId(SongTracks.get(i).getAlbum().getId());
            basict.setAlbumName(SongTracks.get(i).getAlbum().getName());
            basict.setArtistId(SongTracks.get(i).getAlbum().getArtist().getId());
            basict.setArtistName(SongTracks.get(i).getAlbum().getArtist().getName());
            basict.setTrackid(t.getId());
            basict.setName(t.getName());
            basict.setImages(t.getImages());
            basic.add(basict);
        }
        p.setTracks(basic);
        PlaylistInfo.getinstance().setPlaylistTracks(p);
    }


    /**
     * send a request to like(follow) the playlist
     */
    void LikeTrack() {
        Call<Void> call = endPointAPI.LikeTrack(TrackID, user.getToken());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), "something went wrong .try again", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    like_track.setImageResource(R.drawable.like);
                    PlaylistInfo.getinstance().getplaylistTracks().getValue().getTracks().get(0).setIsLiked(true);
                    //SongTracks.setIsLiked(true);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "something went wrong .check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * send a request to unlike(unfollow) the playlist
     */
    void unLikeTrack() {
        Call<Void> call = endPointAPI.UNLikeTrack(TrackID, user.getToken());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), "something went wrong .try again", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    like_track.setImageResource(R.drawable.favorite_border);
                    PlaylistInfo.getinstance().getplaylistTracks().getValue().getTracks().get(0).setIsLiked(false);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "something went wrong .check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * get a color that from the image and set the background with that color
     * @param v  the image view
     * @param put  the background's layout
     */
    void getPaletteAndSetBackgroundColor(ImageView v, final LinearLayout put) {
        Bitmap bitmap = ((BitmapDrawable) v.getDrawable()).getBitmap();

        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                if (palette.getMutedSwatch() != null) {
                    GradientDrawable gd = new GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM,
                            new int[]{palette.getMutedSwatch().getRgb(), 0xFF000000});
                    gd.setCornerRadius(0f);
                    put.setBackgroundDrawable(gd);
                } else if (palette.getDarkVibrantSwatch() != null) {
                    GradientDrawable gd = new GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM,
                            new int[]{palette.getDarkVibrantSwatch().getRgb(), 0xFF000000});
                    gd.setCornerRadius(0f);
                    put.setBackgroundDrawable(gd);
                } else if (palette.getLightMutedSwatch() != null) {
                    GradientDrawable gd = new GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM,
                            new int[]{palette.getLightMutedSwatch().getRgb(), 0xFF000000});
                    gd.setCornerRadius(0f);
                    put.setBackgroundDrawable(gd);
                } else if (palette.getDarkMutedSwatch() != null) {
                    GradientDrawable gd = new GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM,
                            new int[]{palette.getDarkMutedSwatch().getRgb(), 0xFF000000});
                    gd.setCornerRadius(0f);
                    put.setBackgroundDrawable(gd);
                } else if (palette.getLightVibrantSwatch() != null) {
                    GradientDrawable gd = new GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM,
                            new int[]{palette.getLightVibrantSwatch().getRgb(), 0xFF000000});
                    gd.setCornerRadius(0f);
                    put.setBackgroundDrawable(gd);
                } else if (palette.getVibrantSwatch() != null) {
                    GradientDrawable gd = new GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM,
                            new int[]{palette.getVibrantSwatch().getRgb(), 0xFF000000});
                    gd.setCornerRadius(0f);
                    put.setBackgroundDrawable(gd);
                }
            }
        });
    }

    /**
     * update the UI with the data
     */
    void updateUI() {

        List<ImageInfo> images = songTracks.get(0).getTrack().getImages();
        String imageID = "12D";
        if (images != null && images.size() != 0) {
            imageID = images.get(0).getID();
        }
        String Imageurl = Retrofit.getInstance().getBaseurl() + "api/images/" + imageID + "?belongs_to=track";
        Picasso.get().load(Imageurl).into(track_image_track_fragment, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                getPaletteAndSetBackgroundColor(track_image_track_fragment, track_container);
            }

            @Override
            public void onError(Exception e) {
            }
        });


        track_name_middle.setText(songTracks.get(0).getTrack().getName());

        track_singer.setText("Song by " + songTracks.get(0).getAlbum().getArtist().getName());

        String songsNames = "";
        List<currentTrack> tracks = songTracks;
        int n = 7;

        if (songTracks.size() < 7) {
            n = songTracks.size();
        } else if (songTracks.size() == 0) {
            n = 0;
        }
        for (int i = 0; i < n; i++) {
            songsNames += tracks.get(i).getAlbum().getArtist().getName() + " ";
            songsNames += tracks.get(i).getTrack().getName() + " . ";
        }
        if (songTracks.size() != 0) {
            songsNames += "and more";
        }
        artist_name_song_name_track.setText(songsNames);


        if (songTracks.get(0).getIsLiked()) {
            like_track.setImageResource(R.drawable.like);
        } else {
            like_track.setImageResource(R.drawable.favorite_border);
        }
    }

    /**
     * checks if the currently playing track is from the current playlist
     *
     * @return
     */
    Boolean CheckTrackInPlaylist() {
        if (TrackInfo.getInstance().getIsQueue() != null &&
                TrackInfo.getInstance().getIsQueue().getValue() &&
                TrackInfo.getInstance().getTrack() != null &&
                TrackInfo.getInstance().getTrack().getValue().getTrack() != null) {
            CurrentTrackID = TrackInfo.getInstance().getTrack().getValue().getTrack().getId();

            List<currentTrack> tracks = songTracks;

            for (int i = 0; i < tracks.size(); i++) {
                if (tracks.get(i).getTrack().getId().equals(CurrentTrackID)) {
                    CurrentTrackPosInPlaylist = i;
                    return true;
                }
            }

        }
        return false;
    }

    /**
     * gets all the views i will use
     * @param root
     */

    void getViews(View root) {
        back_arrow_track = root.findViewById(R.id.back_arrow_track);
        like_track = root.findViewById(R.id.like_track);
        track_settings_button = root.findViewById(R.id.track_settings_button);
        track_image_track_fragment = root.findViewById(R.id.track_image_track_fragment);
        track_name_middle = root.findViewById(R.id.track_name_middle);
        track_singer = root.findViewById(R.id.track_singer);
        shuffle_play_button_track = root.findViewById(R.id.shuffle_play_button_track);
        artist_name_song_name_track = root.findViewById(R.id.artist_name_song_name_track);
        progress_bar_track = root.findViewById(R.id.progress_bar_track);
        track_contents_layout = root.findViewById(R.id.track_contents_layout);
        top_layout_track = root.findViewById(R.id.top_layout_track);
        something_wrong_layout_track = root.findViewById(R.id.something_wrong_layout_track);
        something_wrong_text_track = root.findViewById(R.id.something_wrong_text_track);
        something_wrong_button_track = root.findViewById(R.id.something_wrong_button_track);
        add_to_playlist_button = root.findViewById(R.id.add_to_playlist_button);
        track_container = root.findViewById(R.id.track_container);
    }

    /**
     * binding the fragment to the media player service to stop the shuffle play
     */
    private void bindService() {
        Intent serviceIntent1 = new Intent(getContext(), MediaPlayerService.class);
        getActivity().bindService(serviceIntent1, serviceConnection, Context.BIND_AUTO_CREATE);

    }


}

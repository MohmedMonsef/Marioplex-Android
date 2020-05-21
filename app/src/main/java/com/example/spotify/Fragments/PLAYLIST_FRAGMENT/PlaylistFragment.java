package com.example.spotify.Fragments.PLAYLIST_FRAGMENT;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.palette.graphics.Palette;

import com.example.spotify.Activities.PlaylistPreviewActivity;
import com.example.spotify.Fragments.HOME_FRAGMENT.backhome;
import com.example.spotify.Fragments.LIBRARY_FRAGMENT.libraryFragment;
import com.example.spotify.Interfaces.EndPointAPI;
import com.example.spotify.Interfaces.Retrofit;
import com.example.spotify.R;
import com.example.spotify.login.user;
import com.example.spotify.media.MediaPlayerService;
import com.example.spotify.media.TrackInfo;
import com.example.spotify.pojo.BasicTrack;
import com.example.spotify.pojo.ImageInfo;
import com.example.spotify.pojo.PlaylistTracks;
import com.example.spotify.pojo.currentTrack;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaylistFragment extends Fragment {

    private ImageView back_arrow_playlist;
    private ImageView like_playlist;
    private ImageView playlist_settings_button;
    private ImageView playlist_image_playlist_fragment;
    private TextView playlist_name_middle;
    private TextView playlist_owner;
    private Button shuffle_play_button;
    private Button preview_button;
    private TextView artist_name_song_name_playlist;
    private LinearLayout playlist_contents_layout;
    private ProgressBar progress_bar_playlist;
    private LinearLayout something_wrong_layout;
    private TextView something_wrong_text;
    private Button something_wrong_button;

    private EndPointAPI endPointAPI = Retrofit.getInstance().getEndPointAPI();
    private PlaylistTracks playlistTracks;
    private String playlistID = "5e8a5a444fbd0152f8bd23d3";
    private String playlistOwner = "spotify";
    private MediaPlayerService player;
    private String CurrentTrackID = "";
    private int CurrentTrackPosInPlaylist;
    private Boolean serviceBound = false;
    String from = "";


    private ServiceConnection serviceConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
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
        View root = inflater.inflate(R.layout.fragment_playlist,container,false);
        ////////////////////get playlist id from the bundle\\\\\\\\\\\\\\\\\\\\\\\\
        playlistID = getArguments().getString("playlistID");
        from = getArguments().getString("from");
        if(from == null ){from = "";}
        /////////////////////get all the views i will use/////////////////////////
        getViews(root);
        //////////////////////Bind the service\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        bindService();
        /////////////////////some listeners\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        /**
         * when pressed the activity closes
         */
        back_arrow_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ////////////////return to my home fragment\\\\\\\\\\\\\\\\\\\\\
                if(from.equals("Playlist_library")){
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_fragment,new libraryFragment())
                            .addToBackStack(null)
                            .commit();
                }
                else {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_fragment, new backhome())
                            .addToBackStack(null)
                            .commit();
                    }

            }
        });
        /**
         * listener for the preview button
         * on click the preview activity opens
         */

        preview_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlaylistPreviewActivity.class);
                startActivity(intent);
            }
        });
        /**
         * listener for the track names text
         * on press the preview activity opens
         */

        artist_name_song_name_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlaylistPreviewActivity.class);
                startActivity(intent);
            }
        });
        /**
         * listener for the shuffle play button and on press it sends a request to turn on the shuffle mode
         */

        shuffle_play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckTrackInPlaylist()){
                    if(player.getIsPlaying()){
                        TrackInfo.getInstance().setIsPlaying(false);
                        player.pauseMedia();
                        shuffle_play_button.setText("shuffle play");
                    }
                    else{
                        TrackInfo.getInstance().setIsPlaying(true);
                        player.resumeMedia();
                        shuffle_play_button.setText("pause");
                    }
                }
                else if(playlistTracks !=null && playlistTracks.getTracks()!=null && playlistTracks.getTracks().size()!=0){
                        CreateQueue(playlistTracks.getTracks().get(0).getTrackid());
                }
            }
        });

        /**
         * listener for the like button to follow/unfollow the playlist
         */
        like_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playlistTracks.getIsLiked()) {
                    unLikePlaylist();
                }
                else{
                    LikePlaylist();
                }
            }
        });

        /**
         * requests the get current playlist info requests if something goes wrong
         */
             something_wrong_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //////////////////////////show progress bar\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
                playlist_contents_layout.setVisibility(View.GONE);
                something_wrong_layout.setVisibility(View.GONE);
                progress_bar_playlist.setVisibility(View.VISIBLE);
                /////////////////////////get Plsylist's tracks information\\\\\\\\\\\\\\\\\\\\\\\
                GetPlaylistTracksInfo(playlistID);
            }
        });

        ///////////////////////////observers\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        /**
         * updates the shuffle play button text with pause if the track is playing and with shuffle play if the track is paused
         */
        TrackInfo.getInstance().getIsPlaying().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(playlistTracks!=null && CheckTrackInPlaylist()){
                    if(aBoolean){
                        shuffle_play_button.setText("pause");
                    }
                    else{
                        shuffle_play_button.setText("shuffle play");
                    }
                }
            }
        });



        //////////////////////////show progress bar\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        playlist_contents_layout.setVisibility(View.GONE);
        something_wrong_layout.setVisibility(View.GONE);
        progress_bar_playlist.setVisibility(View.VISIBLE);
        /////////////////////////get Plsylist's tracks information\\\\\\\\\\\\\\\\\\\\\\\
        GetPlaylistTracksInfo(playlistID);



        return root;
    }

    /**
     * send a request to create queue of the current playlist's tracks and takes a track id and sets it as the current track
     * @param trackID
     */
    void CreateQueue(String trackID){
        Call<Void> call = endPointAPI.CreateQueue(playlistID , trackID , true , user.getToken());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(),"something went wrong while creating the queue. try again.",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    shuffleTracks();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(),"something went wrong while creating the queue.check your internet connection.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * send a request to turn on the shuffle mode and to play a random track from the playlist
     */

    void shuffleTracks(){
        Call<Void> call = endPointAPI.toggleShuffle(true , user.getToken());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(),"something went wrong . try again.",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(),"something went wrong.check your internet connection.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * sends a request to get the current playlist's tracks
     * @param playlistID
     */

    void GetPlaylistTracksInfo(String playlistID){
        Call<List<PlaylistTracks>> call = endPointAPI.getPlaylistTracks(playlistID , user.getToken());
        call.enqueue(new Callback<List<PlaylistTracks>>() {
            @Override
            public void onResponse(Call<List<PlaylistTracks>> call, Response<List<PlaylistTracks>> response) {
                if(!response.isSuccessful() ||response.body() == null){
                    progress_bar_playlist.setVisibility(View.GONE);
                    playlist_contents_layout.setVisibility(View.GONE);
                    something_wrong_text.setText("something went wrong .try again");
                    something_wrong_layout.setVisibility(View.VISIBLE);
                    return;
                }
                else
                    {
                    playlistTracks = response.body().get(0);
                    PlaylistInfo.getinstance().setPlaylistTracks(playlistTracks);
                    playlist_contents_layout.setVisibility(View.VISIBLE);
                    something_wrong_layout.setVisibility(View.GONE);
                    progress_bar_playlist.setVisibility(View.GONE);
                    updateUI();
                }
            }

            @Override
            public void onFailure(Call<List<PlaylistTracks>> call, Throwable t) {
                progress_bar_playlist.setVisibility(View.GONE);
                playlist_contents_layout.setVisibility(View.GONE);
                something_wrong_text.setText("something went wrong .check your internet connection");
                something_wrong_layout.setVisibility(View.VISIBLE);

            }
        });
    }

    /**
     * send a request to like(follow) the playlist
     */
    void LikePlaylist(){
        Call<Void> call = endPointAPI.LikePlaylist(playlistID , user.getToken());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if(!response.isSuccessful()){
                    Toast.makeText(getContext(),"something went wrong .try again",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    like_playlist.setImageResource(R.drawable.like);
                    playlistTracks.setIsLiked(true);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(),"something went wrong .check your internet connection",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * send a request to unlike(unfollow) the playlist
     */
    void unLikePlaylist(){
        Call<Void> call = endPointAPI.UNLikePlaylist(playlistID , user.getToken());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if(!response.isSuccessful()){
                    Toast.makeText(getContext(),"something went wrong .try again",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    like_playlist.setImageResource(R.drawable.favorite_border);
                    playlistTracks.setIsLiked(false);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(),"something went wrong .check your internet connection",Toast.LENGTH_SHORT).show();
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
     * updates the UI with the playlist and tracks info
     */

    void updateUI(){
        List<ImageInfo> images= playlistTracks.getImages();
        String imageID ="12d";
        if(images != null && images.size()!=0) {
            imageID = images.get(0).getID();
        }
        String Imageurl = Retrofit.getInstance().getBaseurl() + "api/images/" + imageID + "?belongs_to=playlist";
        Picasso.get().load(Imageurl).into(playlist_image_playlist_fragment, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                getPaletteAndSetBackgroundColor(playlist_image_playlist_fragment, playlist_contents_layout);
            }

            @Override
            public void onError(Exception e) {
            }
        });
        playlist_name_middle.setText(playlistTracks.getName());
        playlist_owner.setText("BY " + playlistOwner);
        String songsNames = "";
        List<BasicTrack> tracks = playlistTracks.getTracks();
        int n  = 7;
        if(playlistTracks.getTracks().size() < 7){
            n = playlistTracks.getTracks().size();
        }
        else if(playlistTracks.getTracks().size() == 0){
            n=0;
        }
        for(int i =0 ; i<n; i++){
            songsNames+= tracks.get(i).getArtistName()+" ";
            songsNames+= tracks.get(i).getName()+" . ";
        }
        if(playlistTracks.getTracks().size() !=0){
            songsNames+="and more";
        }
        artist_name_song_name_playlist.setText(songsNames);

        if(CheckTrackInPlaylist()){
            if(player.getIsPlaying()){
                shuffle_play_button.setText("pause");
            }
            else{
                shuffle_play_button.setText("shuffle play");
            }
        }

        if(playlistTracks.getIsLiked()){
            like_playlist.setImageResource(R.drawable.like);
        }
        else{
            like_playlist.setImageResource(R.drawable.favorite_border);
        }
    }


    /**
     * checks if the currently playing track is from the current playlist
     * @return
     */
    Boolean CheckTrackInPlaylist(){
        if(TrackInfo.getInstance().getIsQueue()!=null &&
           TrackInfo.getInstance().getIsQueue().getValue() &&
           TrackInfo.getInstance().getTrack()!=null &&
           TrackInfo.getInstance().getTrack().getValue().getTrack()!=null){
            CurrentTrackID = TrackInfo.getInstance().getTrack().getValue().getTrack().getId();

            List<BasicTrack> tracks = playlistTracks.getTracks();

            for(int i =0 ; i<tracks.size(); i++){
                if(tracks.get(i).getTrackid().equals(CurrentTrackID)){
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

    void getViews(View root)
    {
        back_arrow_playlist = root.findViewById(R.id.back_arrow_playlist);
        like_playlist = root.findViewById(R.id.like_playlist);
        playlist_settings_button = root.findViewById(R.id.playlist_settings_button);
        playlist_image_playlist_fragment = root.findViewById(R.id.playlist_image_playlist_fragment);
        playlist_name_middle = root.findViewById(R.id.playlist_name_middle);
        playlist_owner = root.findViewById(R.id.playlist_owner);
        shuffle_play_button = root.findViewById(R.id.shuffle_play_button);
        preview_button = root.findViewById(R.id.preview_button);
        artist_name_song_name_playlist = root.findViewById(R.id.artist_name_song_name_playlist);
        progress_bar_playlist = root.findViewById(R.id.progress_bar_playlist);
        playlist_contents_layout = root.findViewById(R.id.playlist_contents_layout);
        something_wrong_layout = root.findViewById(R.id.something_wrong_layout);
        something_wrong_text = root.findViewById(R.id.something_wrong_text);
        something_wrong_button = root.findViewById(R.id.something_wrong_button);
    }
    private void bindService(){
        Intent serviceIntent1 = new Intent(getContext() , MediaPlayerService.class);
        getActivity().bindService(serviceIntent1 , serviceConnection , Context.BIND_AUTO_CREATE);

    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        PlaylistInfo.getinstance().clearinstance();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}

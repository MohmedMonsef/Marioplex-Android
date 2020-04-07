package com.example.spotify.Fragments.PLAYLIST_FRAGMENT;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.spotify.Activities.PlaylistPreviewActivity;
import com.example.spotify.BackClasses.BackFragment.backhome;
import com.example.spotify.Fragments.HOME_FRAGMENT.homeFragment;
import com.example.spotify.Interfaces.EndPointAPI;
import com.example.spotify.Interfaces.Retrofit;
import com.example.spotify.R;
import com.example.spotify.SpotifyClasses.Image;
import com.example.spotify.login.user;
import com.example.spotify.media.AddToPlaylistActivity;
import com.example.spotify.media.MediaPlayerActivity;
import com.example.spotify.media.MediaPlayerService;
import com.example.spotify.media.TrackInfo;
import com.example.spotify.pojo.BasicTrack;
import com.example.spotify.pojo.PlaylistTracks;
import com.example.spotify.pojo.currentTrack;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
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
        View root = inflater.inflate(R.layout.fragment_playlist,container,false);
        ////////////////////get playlist id from the bundle\\\\\\\\\\\\\\\\\\\\\\\\
        playlistID = getArguments().getString("playlistID");

        /////////////////////get all the views i will use/////////////////////////
        getViews(root);

        //////////////////////Bind the service\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        bindService();

        /////////////////////some listeners\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        back_arrow_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ////////////////return to my home fragment\\\\\\\\\\\\\\\\\\\\\
                getActivity().getFragmentManager().beginTransaction()
                        .replace(R.id.frame_fragment,new backhome())
                        .addToBackStack(null)
                        .commit();
            }
        });

        preview_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlaylistPreviewActivity.class);
                startActivity(intent);
            }
        });

        artist_name_song_name_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlaylistPreviewActivity.class);
                startActivity(intent);
            }
        });

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
                    if(TrackInfo.getInstance().getIsQueue()!=null&& TrackInfo.getInstance().getIsQueue().getValue()){
                        shuffleTracks();
                    }
                    else{
                        CreateQueue(playlistTracks.getTracks().get(0).getTrackid());
                    }
                }
            }
        });

        //TODO till the make makes it

//        like_playlist.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(playlistTracks.getIsLiked()) {
//                    unLikePlaylist();
//                }
//                else{
//                    LikePlaylist();
//                }
//            }
//        });

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

    void CreateQueue(String trackID){
        Call<Void> call = endPointAPI.CreateQueue(playlistID , trackID , true , user.getToken());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(),"Code: "+response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }
//                else if(response.body()==null){
//                    Toast.makeText(getContext(),"response body = null",Toast.LENGTH_SHORT).show();
//                }
                else {
                    shuffleTracks();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage()+" ' failed '",Toast.LENGTH_SHORT).show();
            }
        });
    }

    void shuffleTracks(){
        Call<Void> call = endPointAPI.toggleShuffle(true , user.getToken());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(),"Code: "+response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }
//                else if(response.body()==null){
//                    Toast.makeText(getContext(),"response body = null",Toast.LENGTH_SHORT).show();
//                }
                else {
                    ///////call mediaplayer get current playing \\\\\\\\\\
                    Call<currentTrack> call1 = endPointAPI.getNext(user.getToken());
                    player.playCurrentTrack(call1);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage()+" ' failed '",Toast.LENGTH_SHORT).show();
            }
        });
    }

    void GetPlaylistTracksInfo(String playlistID){
        Call<List<PlaylistTracks>> call = endPointAPI.getPlaylistTracks(playlistID , user.getToken());
        call.enqueue(new Callback<List<PlaylistTracks>>() {
            @Override
            public void onResponse(Call<List<PlaylistTracks>> call, Response<List<PlaylistTracks>> response) {
                if(!response.isSuccessful() ||response.body() == null){
                    //Toast.makeText(getContext(),"Code: "+response.code(),Toast.LENGTH_SHORT).show();
                    progress_bar_playlist.setVisibility(View.GONE);
                    playlist_contents_layout.setVisibility(View.GONE);
                    something_wrong_text.setText("something went wrong .try again");
                    something_wrong_layout.setVisibility(View.VISIBLE);
                    return;
                }
//                else if(response.body()==null){
//                    Toast.makeText(getContext(),"response body = null",Toast.LENGTH_SHORT).show();
//                }
                else {
                    playlistTracks = response.body().get(0);
                    PlaylistInfo.getinstance().setPlaylistTracks(playlistTracks);
                    playlist_contents_layout.setVisibility(View.VISIBLE);
                    something_wrong_layout.setVisibility(View.GONE);
                    progress_bar_playlist.setVisibility(View.GONE);
                    playlistTracks.setIsLiked(true);
                    updateUI();
                }
            }

            @Override
            public void onFailure(Call<List<PlaylistTracks>> call, Throwable t) {
                //Toast.makeText(getContext(),t.getMessage()+" ' failed '",Toast.LENGTH_SHORT).show();
                progress_bar_playlist.setVisibility(View.GONE);
                playlist_contents_layout.setVisibility(View.GONE);
                something_wrong_text.setText("something went wrong .check your internet connection");
                something_wrong_layout.setVisibility(View.VISIBLE);

            }
        });
    }

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

    void updateUI(){
        List<Object> images= playlistTracks.getImages();
        if(images != null && images.size()!=0) {
            String Imageurl = images.get(0).toString();
            Picasso.get().load(Imageurl).into(playlist_image_playlist_fragment);
        }
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

//TODO till the back makes it

//        if(playlistTracks.getIsLiked()){
//            like_playlist.setImageResource(R.drawable.like);
//        }
//        else{
//            like_playlist.setImageResource(R.drawable.favorite_border);
//        }
    }


    ///////checks if the currently playing track is from the current playlist
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

    void getViews(View root){
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
    public void onDestroyView() {
        super.onDestroyView();
        PlaylistInfo.getinstance().clearinstance();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

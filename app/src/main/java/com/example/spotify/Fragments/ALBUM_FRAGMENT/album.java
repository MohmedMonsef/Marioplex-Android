package com.example.spotify.Fragments.ALBUM_FRAGMENT;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.spotify.Activities.PlaylistPreviewActivity;
import com.example.spotify.BackClasses.Backclasses.albumInform.AlbumObject;
import com.example.spotify.BackClasses.Backclasses.albumInform.Track;
import com.example.spotify.Fragments.HOME_FRAGMENT.backhome;
import com.example.spotify.Fragments.PLAYLIST_FRAGMENT.PlaylistInfo;
import com.example.spotify.Interfaces.EndPointAPI;
import com.example.spotify.Interfaces.Retrofit;
import com.example.spotify.R;
import com.example.spotify.login.user;
import com.example.spotify.media.MediaPlayerService;
import com.example.spotify.media.TrackInfo;
import com.example.spotify.pojo.BasicTrack;
import com.example.spotify.pojo.PlaylistTracks;
import com.example.spotify.pojo.currentTrack;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class album extends Fragment
{
    private ImageView back_arrow_album;
    private ImageView like_album;
    private ImageView album_settings_button;
    private ImageView album_image_album_fragment;
    private ImageView artist_album_image;


    private TextView album_name_middle;
    private TextView album_owner;
    private Button shuffle_play_button_album;
    private TextView preview_text_album;
    private TextView album_name_album_fragment;
    private TextView date_album;
    private TextView artist_album_name;
    String Artist_Name;
    String Album_Name;
    private EndPointAPI endPointAPI = Retrofit.getInstance().getEndPointAPI();
   // private AlbumTracks AlbumObject;
    private AlbumObject AlbumObject;
    private String albumID = "5e8a5a444fbd0152f8bd23d3";
    private MediaPlayerService player;
    private String CurrentTrackID = "";
    private int CurrentTrackPosInPlaylist;
    private Boolean serviceBound = false;
    private ServiceConnection serviceConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        ////*******************************RecyclerView***********************////
        albumID = getArguments().getString("albumID");
        /////////////////////get all the views i will use/////////////////////////
          back_arrow_album=view.findViewById(R.id.back_arrow_album);
          like_album=view.findViewById(R.id.like_album);
          album_settings_button=view.findViewById(R.id.album_settings_button);
          album_image_album_fragment=view.findViewById(R.id.album_image_album_fragment);
          artist_album_image=view.findViewById(R.id.artist_album_image);
         album_name_middle=view.findViewById(R.id.album_name_middle);
         album_owner=view.findViewById(R.id.album_owner);
         shuffle_play_button_album=view.findViewById(R.id.shuffle_play_button_album);
         preview_text_album=view.findViewById(R.id.preview_text_album);
         album_name_album_fragment=view.findViewById(R.id.album_name_album_fragment);
         date_album=view.findViewById(R.id.date_album);
        artist_album_image=view.findViewById(R.id.artist_album_image);
        artist_album_name=view.findViewById(R.id.artist_album_name);
        //////////////////////Bind the service\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        bindService();
        GetAlbumsTracksInfo(albumID);

        /////////////////////some listeners\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        /**
         * when pressed the activity closes
         */
        back_arrow_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ////////////////return to my home fragment\\\\\\\\\\\\\\\\\\\\\
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_fragment,new backhome())
                        .addToBackStack(null)
                        .commit();
            }
        });
        /**
         * listener for the preview button
         * on click the preview activity opens
         */

        preview_text_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), AlbumPreviewActivity.class);
//                intent.putExtra("ARTIST_NAME",Artist_Name);
//                intent.putExtra("ALBUM_ID",albumID);
//                intent.putExtra("Album_Name",Album_Name);
//                startActivity(intent);
                Intent intent = new Intent(getActivity(), PlaylistPreviewActivity.class);
                startActivity(intent);
            }
        });

       /* album_settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingAlbumActivity.class);
                intent.putExtra("ARTIST_NAME",Artist_Name);
                intent.putExtra("ALBUM_ID",albumID);
                intent.putExtra("Album_Name",Album_Name);
                startActivity(intent);
            }
        });*/
        shuffle_play_button_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckTrackInAlbum()){
                    if(player.getIsPlaying()){
                        TrackInfo.getInstance().setIsPlaying(false);
                        player.pauseMedia();
                        shuffle_play_button_album.setText("shuffle play");
                    }
                    else{
                        TrackInfo.getInstance().setIsPlaying(true);
                        player.resumeMedia();
                        shuffle_play_button_album.setText("pause");
                    }
                }
                else if(AlbumObject !=null && AlbumObject.getTrack()!=null && AlbumObject.getTrack().size()!=0){
                    if(TrackInfo.getInstance().getIsQueue()!=null&& TrackInfo.getInstance().getIsQueue().getValue()){
                        shuffleTracks();
                    }
                    else{
                        CreateQueue(AlbumObject.getTrack().get(0).getId());
                    }
                }
            }
        });

        TrackInfo.getInstance().getIsPlaying().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(AlbumObject!=null && CheckTrackInAlbum()){
                    if(aBoolean){
                        shuffle_play_button_album.setText("pause");
                    }
                    else{
                        shuffle_play_button_album.setText("shuffle play");
                    }
                }
            }
        });

    /*    like_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AlbumObject.isIsSaved()) {
  //              UnLikeAlbum();
                }
                else{
                    LikeAlbum();
                }
            }
      });
*/
        return view;

    }
    private void bindService(){
        Intent serviceIntent1 = new Intent(getContext() , MediaPlayerService.class);
        getActivity().bindService(serviceIntent1 , serviceConnection , Context.BIND_AUTO_CREATE);

    }
    Boolean CheckTrackInAlbum(){
        if(TrackInfo.getInstance().getIsQueue()!=null &&
                TrackInfo.getInstance().getIsQueue().getValue() &&
                TrackInfo.getInstance().getTrack()!=null &&
                TrackInfo.getInstance().getTrack().getValue().getTrack()!=null){
            CurrentTrackID = TrackInfo.getInstance().getTrack().getValue().getTrack().getId();

            List<Track> tracks = AlbumObject.getTrack();

            for(int i =0 ; i<tracks.size(); i++){
                if(tracks.get(i).getId().equals(CurrentTrackID)){
                    CurrentTrackPosInPlaylist = i;
                    return true;
                }
            }

        }
        return false;
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

    void CreateQueue(String trackID){
        Call<Void> call = endPointAPI.CreateQueue(albumID , trackID , true , user.getToken());
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

    void GetAlbumsTracksInfo(String albumID) {
        Call<AlbumObject> call = endPointAPI.getAlbumObject(albumID, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ZTgwYzZhZjE0Yzg1NjZkNmNkOWI0MDAiLCJwcm9kdWN0IjoiZnJlZSIsInVzZXJUeXBlIjoiQXJ0aXN0IiwiaWF0IjoxNTg2MDI2NjAyLCJleHAiOjQ3MzI1MTMwMDJ9.ztEjNCgbkyJ2-9WB6ojwLgDfhWsZ-ZGJVFUB8dYMz8s");
        call.enqueue(new Callback<AlbumObject>() {
            @Override
            public void onResponse(Call<AlbumObject> call, Response<AlbumObject> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(getContext(), "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                    {
                    AlbumObject = response.body();
                    FillPlaylistTracks(AlbumObject);
                    //albumData.getinstance().setalbumObject(AlbumObject);
                    AlbumObject.setIsSaved(true);
                    updateUI();
                }
            }

            @Override
            public void onFailure(Call<AlbumObject> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage() + " ' failed '", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void FillPlaylistTracks(AlbumObject albumTracks){

        PlaylistTracks p = new PlaylistTracks();
        p.setId(albumTracks.getId());
        p.setIsLiked(albumTracks.isIsSaved());
        p.setImages(albumTracks.getImages());
        p.setName(albumTracks.getName());
        p.setType("album");
        List<BasicTrack> basic = new ArrayList<>();
        Track t ;
        for(int i = 0 ; i < albumTracks.getTrack().size() ; i++){
            t = albumTracks.getTrack().get(i);
            BasicTrack basict = new BasicTrack();
            if(t.getLiked() == 1) {
                basict.setIsLiked(true);
            }
            else {
                basict.setIsLiked(false);
            }
            basict.setAlbumId(albumTracks.getId());
            basict.setAlbumName(albumTracks.getName());
            basict.setArtistId(albumTracks.getArtistId());
            basict.setArtistName(albumTracks.getArtistName());
            basict.setTrackid(t.getId());
            basict.setName(t.getName());
            basict.setImages(t.getImages());
            basic.add(basict);
        }
        p.setTracks(basic);
        PlaylistInfo.getinstance().setPlaylistTracks(p);
    }


    void updateUI(){
        List<Object> images= AlbumObject.getImages();
        if(images != null && images.size()!=0) {
            String Imageurl = images.get(0).toString();
            Picasso.get().load(Imageurl).into(album_image_album_fragment);
        }

        artist_album_name.setText( AlbumObject.getArtistName());
        album_name_middle.setText(AlbumObject.getName());
        album_owner.setText("BY " + AlbumObject.getArtistName());
        Artist_Name=AlbumObject.getArtistName();
        Album_Name=AlbumObject.getName();
        String songsNames = "";
        List<com.example.spotify.BackClasses.Backclasses.albumInform.Track> tracks = AlbumObject.getTrack();
        int n  = 7;
        if(AlbumObject.getTrack().size() < 7)
        {
            n = AlbumObject.getTrack().size();
        }
        else if(AlbumObject.getTrack().size() == 0)
        {
            n=0;
        }
        for(int i =0 ; i<n; i++)
        {
            //songsNames+= tracks.get(i).getName()+" ";
            songsNames+= tracks.get(i).getName()+" . ";
        }
        if(AlbumObject.getTrack().size() !=0)
        {
            songsNames+="and more";
        }
        preview_text_album.setText(songsNames);

        if(CheckTrackInAlbum()){
            if(player.getIsPlaying()){
                shuffle_play_button_album.setText("pause");
            }
            else{
                shuffle_play_button_album.setText("shuffle play");
            }
        }
       }



    /**
     * send a request to UNlike(follow) the ALBUM
     */
  /*  void UnLikeAlbum(){
        Call<Void> call = endPointAPI.UN_LIKE_ALBUM(albumID , user.getToken());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if(!response.isSuccessful()){
                    Toast.makeText(getContext(),"something went wrong .try again",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    like_album.setImageResource(R.drawable.favorite_border);
                    AlbumObject.setIsSaved(false);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(),"something went wrong .check your internet connection",Toast.LENGTH_SHORT).show();
            }
        });
    }
*/
    /**
     * send a request to like(unfollow) the ALBUM
     */
    void LikeAlbum(){
        Call<Void> call = endPointAPI.LIKE_ALBUM(albumID , user.getToken());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if(!response.isSuccessful()){
                    Toast.makeText(getContext(),"something went wrong .try again",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    like_album.setImageResource(R.drawable.like);
                    AlbumObject.setIsSaved(true);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(),"something went wrong .check your internet connection",Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        albumData.getinstance().clearinstance();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
package com.example.spotify.Fragments.LIBRARY_FRAGMENT.Playlist_library_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotify.Interfaces.EndPointAPI;
import com.example.spotify.Interfaces.Retrofit;
import com.example.spotify.R;
import com.example.spotify.login.user;
import com.example.spotify.media.CreatePlaylistActivity;
import com.example.spotify.pojo.BasicPlaylist;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Playlist_library extends Fragment implements LifecycleOwner {
    private EndPointAPI endPointAPI = Retrofit.getInstance().getEndPointAPI();
    private List<BasicPlaylist> userPlaylists;
    private PlaylistLibraryAdapter playlistLibraryAdapter;
    private ProgressBar library_playlist_progress_bar;
    private LinearLayout something_wrong_layout3;
    private RecyclerView library_playlist_recycleview;
    private Button try_again3;
    private LinearLayoutManager layoutManager;
    private LinearLayout library_create_playliste_layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_playlist_library, container, false);

        getViews(view);

        /////////////////////////List view attaching will be made in the on response callback///////
        //////make the request then attach it to the listview//////
        library_playlist_progress_bar.setVisibility(View.VISIBLE);
        something_wrong_layout3.setVisibility(View.GONE);
        library_playlist_recycleview.setVisibility(View.GONE);
        getPlaylists();

        /**
         * listener for the try again button
         * on press it sends the request to get the playlists again
         */

        try_again3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                library_playlist_progress_bar.setVisibility(View.VISIBLE);
                something_wrong_layout3.setVisibility(View.GONE);
                library_playlist_recycleview.setVisibility(View.GONE);
                getPlaylists();
            }
        });

        /**
         * listener for the click on the create playlist layout
         */
        library_create_playliste_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , CreatePlaylistActivity.class);
                intent.putExtra("track_id", "");
                intent.putExtra("from" , "Playlist_library");
                startActivity(intent);
            }
        });




        return view;

    }

    /**
     * sends a request to get the current user's followed and created playlists
     */

    void getPlaylists(){
        Call<List<BasicPlaylist>> call = endPointAPI.getCurrentUserPlaylists( user.getToken());
        call.enqueue(new Callback<List<BasicPlaylist>>() {
            @Override
            public void onResponse(Call<List<BasicPlaylist>> call, Response<List<BasicPlaylist>> response) {
                if(!response.isSuccessful()){
                    something_wrong_layout3.setVisibility(View.VISIBLE);
                    library_playlist_progress_bar.setVisibility(View.GONE);
                    library_playlist_recycleview.setVisibility(View.GONE);

                    return;
                } else {
                    userPlaylists = response.body();

                    playlistLibraryAdapter = new PlaylistLibraryAdapter(getActivity(), userPlaylists);
                    library_playlist_recycleview.setAdapter(playlistLibraryAdapter);
                    library_playlist_recycleview.setHasFixedSize(true);

                    something_wrong_layout3.setVisibility(View.GONE);
                    library_playlist_recycleview.setVisibility(View.VISIBLE);
                    library_playlist_progress_bar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<BasicPlaylist>> call, Throwable t) {
                something_wrong_layout3.setVisibility(View.VISIBLE);
                library_playlist_progress_bar.setVisibility(View.GONE);
                library_playlist_recycleview.setVisibility(View.GONE);
            }
        });

    }

    /**
     * gets all the views I will use in the fragment
     * @param root
     */
    void getViews(View root){
        library_playlist_recycleview = root.findViewById(R.id.library_playlist_recycleview);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        library_playlist_recycleview.setLayoutManager(layoutManager);
        library_playlist_progress_bar = root.findViewById(R.id.library_playlist_progress_bar);
        something_wrong_layout3 = root.findViewById(R.id.something_wrong_layout3);
        try_again3 = root.findViewById(R.id.try_again3);
        library_create_playliste_layout = root.findViewById(R.id.library_create_playliste_layout);
    }


}
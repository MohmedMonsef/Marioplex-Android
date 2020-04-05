package com.example.spotify.Fragments.PLAYLIST_FRAGMENT;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.spotify.Activities.PlaylistPreviewActivity;
import com.example.spotify.Fragments.HOME_FRAGMENT.homeFragment;
import com.example.spotify.R;
import com.example.spotify.media.MediaPlayerActivity;

public class PlaylistFragment extends Fragment {

    ImageView back_arrow_playlist;
    ImageView like_playlist;
    ImageView playlist_settings_button;
    ImageView playlist_image_playlist_fragment;
    TextView playlist_name_middle;
    TextView playlist_owner;
    Button shuffle_play_button;
    Button preview_button;
    TextView artist_name_song_name_playlist;
    LinearLayout playlist_contents_layout;
    ProgressBar progress_bar_playlist;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_playlist,container,false);
        /////////////////////get all the views i will use/////////////////////////
        getViews(root);

        /////////////////////some listeners\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        back_arrow_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ////////////////return to my home fragment\\\\\\\\\\\\\\\\\\\\\
                getActivity().getFragmentManager().beginTransaction()
                        .replace(R.id.frame_fragment,new homeFragment())
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



        return root;
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
    }
}

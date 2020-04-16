package com.example.spotify.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.spotify.Fragments.SEARCH_FRAGMENT.searchfragment;
import com.example.spotify.R;

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


    private String TrckID = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_track,container,false);
        ////////////////////get playlist id from the bundle\\\\\\\\\\\\\\\\\\\\\\\\
        TrckID = getArguments().getString("TrackID");

        /////////////////////get all the views i will use/////////////////////////
        getViews(root);


        /**
         * when pressed the activity closes
         */
        back_arrow_track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ////////////////return to my home fragment\\\\\\\\\\\\\\\\\\\\\
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_fragment,new searchfragment())
                        .addToBackStack(null)
                        .commit();
            }
        });


        return root;
    }

    void getViews(View root){
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
    }
}

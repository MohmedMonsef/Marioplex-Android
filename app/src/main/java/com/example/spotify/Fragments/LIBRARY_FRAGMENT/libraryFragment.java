package com.example.spotify.Fragments.LIBRARY_FRAGMENT;


import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

//import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.spotify.Activities.MainActivity;
import com.example.spotify.Fragments.LIBRARY_FRAGMENT.Album_library_fragment.Album_Library;
import com.example.spotify.Fragments.LIBRARY_FRAGMENT.Artist_library_fragment.Artist_library;
import com.example.spotify.Fragments.LIBRARY_FRAGMENT.Playlist_library_fragment.Playlist_library;
import com.example.spotify.R;

import static android.widget.Toast.makeText;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class libraryFragment extends Fragment implements LifecycleOwner {

    TextView playlist,album,artist;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_library, container, false);
        ////*******************************RecyclerView***********************////
        playlist=view.findViewById(R.id.playlist_library);
        album=view.findViewById(R.id.album_library);
        artist=view.findViewById(R.id.artist_library);
        playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loadFragment(new Playlist_library());

            }
        });

        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loadFragment(new Album_Library());

            }
        });
        artist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
loadFragment(new Artist_library());
            }
        });
        return view;

    }
    public void loadFragment(Fragment fragment)
    {
        // create a FragmentManager
        FragmentManager fm = getFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.library_fragment,fragment);
        // save the changes
        fragmentTransaction.commit();
    }



    /*
    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return null;
    }

     */
}
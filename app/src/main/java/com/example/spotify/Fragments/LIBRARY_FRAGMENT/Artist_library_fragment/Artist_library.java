package com.example.spotify.Fragments.LIBRARY_FRAGMENT.Artist_library_fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.spotify.R;


public class Artist_library extends Fragment implements LifecycleOwner {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_artist_library, container, false);
        ////*******************************RecyclerView***********************////
        return view;

    }

}
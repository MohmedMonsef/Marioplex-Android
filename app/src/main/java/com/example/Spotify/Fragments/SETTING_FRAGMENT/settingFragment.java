package com.example.Spotify.Fragments.SETTING_FRAGMENT;


import android.os.Bundle;

import androidx.annotation.NonNull;

import android.app.Fragment;

//import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.Spotify.Activities.MainActivity;
import com.example.Spotify.R;

import static android.widget.Toast.makeText;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class settingFragment extends Fragment implements LifecycleOwner {

    private viewmodelSetting settingViewmodel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        settingViewmodel =ViewModelProviders.of((MainActivity) getActivity()).get(viewmodelSetting.class);
        final TextView textView = view.findViewById(R.id.text_search);
        ////*******************************RecyclerView***********************////
        return view;

    }




    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return null;
    }
}

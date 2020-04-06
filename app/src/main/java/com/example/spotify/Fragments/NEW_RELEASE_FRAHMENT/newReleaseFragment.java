package com.example.spotify.Fragments.NEW_RELEASE_FRAHMENT;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.app.Fragment;

//import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.spotify.Activities.MainActivity;
import com.example.spotify.R;

import static android.widget.Toast.makeText;

    /**
     * A simple {@link android.app.Fragment} subclass.
     * Activities that contain this fragment must implement the
     * to handle interaction events.
     * create an instance of this fragment.
     */
    public class newReleaseFragment extends Fragment implements LifecycleOwner {

        private viewmodelNewRelease newReleaseViewModel;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_new_releasse, container, false);
            newReleaseViewModel = ViewModelProviders.of((MainActivity) getActivity()).get(viewmodelNewRelease.class);
            final TextView textView = view.findViewById(R.id.text_newRelease);
            ////*******************************RecyclerView***********************////
            return view;

        }




        @NonNull
        @Override
        public Lifecycle getLifecycle() {
            return null;
        }


    }
package com.example.spotify.Fragments.SETTING_FRAGMENT;


import android.os.Bundle;

import androidx.annotation.NonNull;

import android.app.Fragment;

//import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spotify.Activities.MainActivity;
import com.example.spotify.Fragments.ProfileFragment;
import com.example.spotify.R;
import com.example.spotify.login.user;

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

        //user related
        ((ImageView)view.findViewById(R.id.profile_picture)).setImageResource(R.drawable.logo);
        ((TextView)view.findViewById(R.id.user_name)).setText(user.getName());
        view.findViewById(R.id.view_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ProfileFragment();
                getFragmentManager().beginTransaction().replace(R.id.frame_fragment,fragment).commit();
            }
        });
        return view;
    }




    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return null;
    }
}

package com.example.spotify.Fragments;

import android.app.Fragment;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spotify.R;
import com.example.spotify.login.user;


public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_profile, container, false);

        ((ImageView)rootView.findViewById(R.id.profile_picture)).setImageResource(R.drawable.logo);

        final EditText userName = (EditText)rootView.findViewById(R.id.user_name);
        userName.setText(user.getName());
        userName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == 66)
                    userName.clearFocus();
                return false;
            }
        });

        return rootView;
    }
}

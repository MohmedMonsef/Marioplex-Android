package com.example.spotify.Fragments.SETTING_FRAGMENT;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;

import com.example.spotify.Activities.MainActivity;
import com.example.spotify.Fragments.ProfileFragment;
import com.example.spotify.Interfaces.Retrofit;
import com.example.spotify.R;
import com.example.spotify.login.IntroActivity;
import com.example.spotify.login.user;
import com.squareup.picasso.Picasso;

//import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class settingFragment extends Fragment implements LifecycleOwner {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        final TextView textView = view.findViewById(R.id.text_search);
        ////*******************************RecyclerView***********************////

        //user related
        ((ImageView)view.findViewById(R.id.profile_picture)).setImageResource(R.drawable.logo);

        /**
         * updates the profile image view with the user's image if the user has an image
         */
        if(user.getImages()!=null&&user.getImages().size()!=0){
            String imageId = user.getImages().get(0).getID();
            String Imageurl = Retrofit.getInstance().getBaseurl() + "api/images/" + imageId + "?belongs_to=user";
            Picasso.get().load(Imageurl).into((ImageView)view.findViewById(R.id.profile_picture));
        }

        ((TextView)view.findViewById(R.id.user_name)).setText(user.getName());
        view.findViewById(R.id.view_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ProfileFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment,fragment).commit();
            }
        });

        ((TextView)view.findViewById(R.id.logout_text1)).setText(user.getName());

        view.findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        return view;
    }

    void logout(){
        deleteToken();
        startActivity(new Intent(getActivity(),IntroActivity.class));
        getActivity().finish();
        return;
    }

    void deleteToken(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("token", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("token");
        editor.commit();
    }


    /*
    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return null;
    }

     */
}

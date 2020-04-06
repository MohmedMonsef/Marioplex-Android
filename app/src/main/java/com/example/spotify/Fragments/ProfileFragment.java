package com.example.spotify.Fragments;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.spotify.Activities.MainActivity;
import com.example.spotify.Interfaces.EndPointAPI;
import com.example.spotify.Interfaces.Retrofit;
import com.example.spotify.R;
import com.example.spotify.login.apiClasses.updateProfile;
import com.example.spotify.login.user;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {


    /*public ProfileFragment() {
        // Required empty public constructor
    }*/


    public ProfileFragment(){

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView =  inflater.inflate(R.layout.fragment_profile, container, false);

        ((ImageView)rootView.findViewById(R.id.profile_picture)).setImageResource(R.drawable.logo);

        final EditText userName = (EditText)rootView.findViewById(R.id.user_name);
        userName.setText(user.getName());
        userName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == 66) {
                    userName.clearFocus();
                    updateProfile data = new updateProfile();
                    data.setDisplay_Name(userName.getText().toString());
                    updateProfile(data);
                }
                return false;
            }
        });

        rootView.findViewById(R.id.profile_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userName.isFocused()) {
                    userName.clearFocus();
                    updateProfile data = new updateProfile();
                    data.setDisplay_Name(userName.getText().toString());
                    updateProfile(data);
                }
            }
        });

        ((TextView)rootView.findViewById(R.id.no_of_playlists)).setText(""+user.getPlaylists().length);

        return rootView;
    }

    private void updateProfile(updateProfile data){
        EndPointAPI api = Retrofit.getInstance().getEndPointAPI();

        api.updateProfile(user.getToken(),data).enqueue(new Callback<ResponseBody>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(getContext(),"Saved",Toast.LENGTH_SHORT).show();
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(),"Failure",Toast.LENGTH_SHORT).show();
            }
        });


    }
}

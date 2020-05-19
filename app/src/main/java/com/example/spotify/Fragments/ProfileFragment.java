package com.example.spotify.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.spotify.Fragments.LIBRARY_FRAGMENT.Playlist_library_fragment.Upload_Image;
import com.example.spotify.Interfaces.EndPointAPI;
import com.example.spotify.Interfaces.Retrofit;
import com.example.spotify.R;
import com.example.spotify.login.apiClasses.updateProfile;
import com.example.spotify.login.user;
import com.example.spotify.pojo.playlist;
import com.squareup.picasso.Picasso;

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
        user.fetchUserData();
        // Inflate the layout for this fragment
        final View rootView =  inflater.inflate(R.layout.fragment_profile, container, false);

        ((ImageView)rootView.findViewById(R.id.profile_picture)).setImageResource(R.drawable.logo);

        if(user.getImages()!=null&&user.getImages().size()!=0){
            String imageId = user.getImages().get(0).getID();
            String Imageurl = Retrofit.getInstance().getBaseurl() + "api/images/" + imageId + "?belongs_to=user";
            Picasso.get().load(Imageurl).into((ImageView)rootView.findViewById(R.id.profile_picture));
        }

        (rootView.findViewById(R.id.profile_picture)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Upload_Image.class);
                intent.putExtra("SourceID" , user.getId());
                intent.putExtra("from" , "user");
                getContext().startActivity(intent);
            }
        });

        final EditText userName = (EditText)rootView.findViewById(R.id.user_name);
        userName.setText(user.getName());
        userName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == 66) {
                    hideSoftKeyboard();
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
                    hideSoftKeyboard();
                    userName.clearFocus();
                    updateProfile data = new updateProfile();
                    data.setDisplay_Name(userName.getText().toString());
                    updateProfile(data);
                }
            }
        });

        playlist[] playlists = user.getPlaylists();
        int no_of_playlists = 0;
        if(playlists != null){
            no_of_playlists = playlists.length;
        }
        ((TextView)rootView.findViewById(R.id.no_of_playlists)).setText(String.valueOf(no_of_playlists));

        return rootView;
    }

    /**
     * updates user data with passed new data
     * @param data user new data
     */
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

    void hideSoftKeyboard(){
        // Check if no view has focus:
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}

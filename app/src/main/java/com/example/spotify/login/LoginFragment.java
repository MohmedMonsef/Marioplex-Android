package com.example.spotify.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.spotify.Activities.MainActivity;
import com.example.spotify.Interfaces.EndPointAPI;
import com.example.spotify.R;
import com.example.spotify.login.apiClasses.LoginCredentials;
import com.example.spotify.login.apiClasses.LoginResponse;
import com.example.spotify.login.apiClasses.userProfile;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;



public class LoginFragment extends Fragment {

    private static Retrofit mRetrofit;
    private static EndPointAPI mEndPointAPI;
    private static String token = null;


    /*public LoginFragment() {
        // Required empty public constructor
    }*/

    LoginFragment(Retrofit retrofit, EndPointAPI endPointAPI){
        mRetrofit = retrofit;
        mEndPointAPI = endPointAPI;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        rootView.findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        return rootView;
    }

    public void login(){
        String email = ((EditText) getView().findViewById(R.id.email)).getText().toString();
        String password = ((EditText) getView().findViewById(R.id.password)).getText().toString();

        LoginCredentials loginCredentials = new LoginCredentials();
        loginCredentials.setEmail(email);
        loginCredentials.setPassword(password);

        Call<LoginResponse> call = mEndPointAPI.login(loginCredentials);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    token = response.body().getToken();
                    user.setToken(token);
                    Toast.makeText(getContext(),"Sucess " +response.code(),Toast.LENGTH_SHORT).show();
                    user.fetchUserData();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().finish();
                }
                else {
                    Toast.makeText(getContext(),"Failed to login " + response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("Intro Activity",t.getMessage());
                Toast.makeText(getContext(),"Failed to connect " + t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*
    public void fetchUserData(){
        if(token == null)
            return;

        mApiSpotify.profile(token).enqueue(new Callback<ArrayList<userProfile>>() {
            @Override
            public void onResponse(Call<ArrayList<userProfile>> call, Response<ArrayList<userProfile>> response) {
                if(response.isSuccessful()){
                    user.setToken(token);
                    user.setName(response.body().get(0).getDisplayName());
                    user.setEmail(response.body().get(0).getEmail());
                    user.setDateOfBirth(response.body().get(0).getBirthDate());
                    user.setGender(response.body().get(0).getGender());
                    user.setCountry(response.body().get(0).getCountry());
                    user.setProduct(response.body().get(0).getProduct());
                    user.setImages(response.body().get(0).getImages());
                }
                else {
                    Toast.makeText(getContext(),"Failed to get profile",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<userProfile>> call, Throwable t) {
                Toast.makeText(getContext(),"Failed to connect",Toast.LENGTH_SHORT).show();
            }
        });



    }*/


}

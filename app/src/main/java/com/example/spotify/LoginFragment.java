package com.example.spotify;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;



public class LoginFragment extends Fragment {

    private static Retrofit mRetrofit;
    private static ApiSpotify mApiSpotify;
    private static String token;


    /*public LoginFragment() {
        // Required empty public constructor
    }*/

    public LoginFragment(Retrofit retrofit,ApiSpotify apiSpotify){
        mRetrofit = retrofit;
        mApiSpotify = apiSpotify;
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

        Call<Token> call = mApiSpotify.login(loginCredentials);

        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if(response.isSuccessful()){
                    token = response.body().getToken();
                    Toast.makeText(getContext(),"Sucess " + response.code(),Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(),"Failed to login",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Log.e("Intro Activity",t.getMessage());
                Toast.makeText(getContext(),"Failed to connect",Toast.LENGTH_SHORT).show();
            }
        });
    }
}

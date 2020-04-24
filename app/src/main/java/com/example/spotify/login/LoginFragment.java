package com.example.spotify.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
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

    String email,password;

    boolean emailValid = false;
    boolean passwordValid = false;
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
        final View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        final View loginButton = rootView.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        loginButton.setEnabled(false);

        ((EditText)rootView.findViewById(R.id.email)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                email = s.toString();
                emailValid = validateEmail(email);
                if(emailValid && passwordValid){
                    loginButton.setEnabled(true);
                }
                else{
                    loginButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ((EditText)rootView.findViewById(R.id.password)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password = s.toString();
                passwordValid = password.length() > 0;
                if(emailValid && passwordValid){
                    loginButton.setEnabled(true);
                }
                else{
                    loginButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        rootView.findViewById(R.id.forgot_password_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.intro_fragment,new forgotPasswordFragment()).addToBackStack(null).commit();
            }
        });

        return rootView;
    }

    /**
     * Login using provided email and password
     * On login success launches main app activity
     * On login failure displays a toast message
     */
    public void login(){
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
                    saveToken();
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

    boolean validateEmail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    void saveToken(){
        String token = user.getToken();
        if(token!=null){
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("token", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("token",token);
            editor.apply();
        }
    }

}

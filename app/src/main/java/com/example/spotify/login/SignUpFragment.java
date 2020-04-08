package com.example.spotify.login;

import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spotify.Interfaces.EndPointAPI;
import com.example.spotify.R;
import com.example.spotify.login.apiClasses.SignUpData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class SignUpFragment extends Fragment {

    private static Retrofit mRetrofit;
    private static EndPointAPI mEndPointAPI;

    String email,password,birthday,username,gender;

    /*public SignUpFragment() {
        // Required empty public constructor
    }*/

    SignUpFragment(Retrofit retrofit, EndPointAPI EndPointAPI){
        mRetrofit = retrofit;
        mEndPointAPI = EndPointAPI;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);

        rootView.findViewById(R.id.signUpButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        final TextView maleButton = (TextView)rootView.findViewById(R.id.male_button);
        final TextView femaleButton = (TextView)rootView.findViewById(R.id.female_button);
        maleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maleButton.setBackgroundColor(Color.GRAY);
                maleButton.setTextColor(Color.WHITE);
                femaleButton.setBackgroundColor(Color.WHITE);
                femaleButton.setTextColor(Color.GRAY);
                gender = "Male";
            }
        });

        femaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maleButton.setBackgroundColor(Color.WHITE);
                maleButton.setTextColor(Color.GRAY);
                femaleButton.setBackgroundColor(Color.GRAY);
                femaleButton.setTextColor(Color.WHITE);
                gender = "Female";
            }
        });

        return rootView;
    }

    /**
     * Validate sign up form data and send it to backend server
     * Displays a Toast message indicating sign up request result
     */
    private void signUp(){

        try {
            email = ((EditText) getView().findViewById(R.id.sign_up_email)).getText().toString();
            password = ((EditText) getView().findViewById(R.id.sign_up_password)).getText().toString();
            birthday = ((EditText) getView().findViewById(R.id.sign_up_date_of_birth)).getText().toString();
            username = ((EditText) getView().findViewById(R.id.sign_up_name)).getText().toString();
            //gender = ((EditText) getView().findViewById(R.id.sign_up_gender)).getText().toString();
        }
        catch (NullPointerException e){
            Toast.makeText(getContext(),"fill in the fields",Toast.LENGTH_SHORT).show();
            return;
        }
        String country = "Egypt";

        // form validation
        if(!email.matches("(.+)@(.+).(.+)")){
            Toast.makeText(getContext(),"incorrect email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.length() < 4){
            Toast.makeText(getContext(),"password can't be less than 4 characters",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!birthday.matches("\\d{1,2}/\\d{1,2}/\\d{4}")){
            Toast.makeText(getContext(),"incorrect date format",Toast.LENGTH_SHORT).show();
            return;
        }
        //String[] days = birthday.split("\\\\");
        if(gender == null){
            Toast.makeText(getContext(),"Choose your gender",Toast.LENGTH_SHORT).show();
            return;
        }

        SignUpData signUpData = new SignUpData(username,password,country,email,gender,birthday);

        mEndPointAPI.signUp(signUpData).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Log.v("Intro Activity",response.body().toString());
                    Toast.makeText(getContext(),"Sucess " + response.code(),Toast.LENGTH_SHORT).show();

                }
                else {
                    Log.i("Intro Activity",response.errorBody().toString());
                    Toast.makeText(getContext(),"Failed " + response.code() +" "+ response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Intro Activity",t.getMessage());
                Toast.makeText(getContext(),"Failed to connect",Toast.LENGTH_SHORT).show();
            }
        });

    }
}

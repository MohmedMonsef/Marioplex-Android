package com.example.spotify;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {

    private static Retrofit retrofit;
    private static ApiSpotify apiSpotify;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.39:3000").addConverterFactory(GsonConverterFactory.create()).build();

        apiSpotify = retrofit.create(ApiSpotify.class);

    }

    public void signUp(View view){

        String email = ((EditText)findViewById(R.id.sign_up_email)).getText().toString();
        String password = ((EditText)findViewById(R.id.sign_up_password)).getText().toString();
        String birthday = ((EditText)findViewById(R.id.sign_up_date_of_birth)).getText().toString();
        String username = ((EditText)findViewById(R.id.sign_up_name)).getText().toString();
        String gender = ((EditText)findViewById(R.id.sign_up_gender)).getText().toString();
        String country = "Egypt";


        SignUpData signUpData = new SignUpData(username,password,country,email,gender,birthday);

        apiSpotify.signUp(signUpData).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Log.v("Login Activity",response.body().toString());
                    Toast.makeText(SignUpActivity.this,"Sucess " + response.code(),Toast.LENGTH_LONG).show();
                }
                else {
                    Log.i("Login Activity",response.body().toString());
                    Toast.makeText(SignUpActivity.this,"Failed " + response.code(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Login Activity",t.getMessage());
                Toast.makeText(SignUpActivity.this,"Failed to connect",Toast.LENGTH_LONG).show();
            }
        });

    }
}

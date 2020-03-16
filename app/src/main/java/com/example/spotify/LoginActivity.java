package com.example.spotify;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

public class LoginActivity extends AppCompatActivity {

    private static Retrofit retrofit;
    private static ApiSpotify apiSpotify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.37:5000").addConverterFactory(GsonConverterFactory.create()).build();

        apiSpotify = retrofit.create(ApiSpotify.class);
    }

    public void Login(View view){
        String email = ((EditText) findViewById(R.id.email)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();

        Credentials credentials = new Credentials();
        credentials.setEmail(email);
        credentials.setPassword(password);

        apiSpotify.login(credentials).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Log.v("Login Activity",response.body().toString());
                }
                else {
                    Log.i("Login Activity",response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Login Activity",t.getMessage());
            }
        });


    }


}

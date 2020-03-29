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

public class LoginActivity extends AppCompatActivity {

    private static Retrofit retrofit;
    private static ApiSpotify apiSpotify;
    private static String token = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.39:3000").addConverterFactory(GsonConverterFactory.create()).build();

        apiSpotify = retrofit.create(ApiSpotify.class);
    }

    public void Login(View view){
        String email = ((EditText) findViewById(R.id.email)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();

        LoginCredentials loginCredentials = new LoginCredentials();
        loginCredentials.setEmail(email);
        loginCredentials.setPassword(password);

        Call<Token> call = apiSpotify.login(loginCredentials);

        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if(response.isSuccessful()){
                    token = response.body().getToken();
                    Toast.makeText(LoginActivity.this,"Sucess " + response.code(),Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(LoginActivity.this,"Failed to login",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Log.e("Login Activity",t.getMessage());
                Toast.makeText(LoginActivity.this,"Failed to connect",Toast.LENGTH_LONG).show();
            }
        });


    }


}

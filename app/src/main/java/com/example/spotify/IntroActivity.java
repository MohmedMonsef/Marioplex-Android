package com.example.spotify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IntroActivity extends AppCompatActivity {

    private static Retrofit retrofit;
    private static ApiSpotify apiSpotify;
    private static LoginFragment loginFragment = null;
    //private static String token = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.39:3000").addConverterFactory(GsonConverterFactory.create()).build();

        apiSpotify = retrofit.create(ApiSpotify.class);
    }


    public void showLoginFragment(View view) {
        loginFragment = new LoginFragment(retrofit,apiSpotify);
        hideIntroLayout();
        getSupportFragmentManager().beginTransaction().replace(R.id.intro_fragment,loginFragment).commit();
    }

    public void LaunchSignUpActivity(View view) {
        Intent intent = new Intent(this,SignUpActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if(loginFragment != null){
            getSupportFragmentManager().beginTransaction().remove(loginFragment).commit();
            loginFragment = null;
            showIntroLayout();
        }
        else{
            super.onBackPressed();
        }
    }


    public void hideIntroLayout(){
        findViewById(R.id.intro).setVisibility(View.GONE);
    }
    public void showIntroLayout(){
        findViewById(R.id.intro).setVisibility(View.VISIBLE);
    }
}

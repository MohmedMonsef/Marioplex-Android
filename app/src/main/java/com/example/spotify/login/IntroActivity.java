package com.example.spotify.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.spotify.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IntroActivity extends AppCompatActivity {

    //final private static int ANIMATION_DURATION = 200;

    private static Retrofit retrofit = null;
    private static ApiSpotify apiSpotify = null;
    private static LoginFragment loginFragment = null;
    private static SignUpFragment signUpFragment= null;
    //private static String token = null;
    private static float displayWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.35:3000").addConverterFactory(GsonConverterFactory.create()).build();

        apiSpotify = retrofit.create(ApiSpotify.class);

        calculateDisplayWidth();

        findViewById(R.id.intro_fragment).setTranslationX(displayWidth);
    }


    public void showLoginFragment(View view) {
        loginFragment = new LoginFragment(retrofit,apiSpotify);
        showFragment(loginFragment);
    }


    public void showSignUpFragment(View view){
        signUpFragment = new SignUpFragment(retrofit,apiSpotify);
        showFragment(signUpFragment);
    }

    @Override
    public void onBackPressed() {
        if(loginFragment != null){
            hideFragment(loginFragment);
            loginFragment = null;
        }
        else if(signUpFragment != null){
            hideFragment(signUpFragment);
            signUpFragment = null;
        }
        else{
            super.onBackPressed();
        }
    }


    public void showFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.intro_fragment,fragment).commit();
        findViewById(R.id.intro).animate().translationX(-displayWidth);
        findViewById(R.id.intro_fragment).animate().translationX(0);
    }

    public void hideFragment(final Fragment fragment){
        findViewById(R.id.intro).animate().translationX(0);
        findViewById(R.id.intro_fragment).animate().translationX(displayWidth).withEndAction(new Runnable() {
            @Override
            public void run() {
                getSupportFragmentManager().beginTransaction().remove(fragment);
             }
        });
    }

    public void calculateDisplayWidth(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        displayWidth = displayMetrics.widthPixels;
    }
}

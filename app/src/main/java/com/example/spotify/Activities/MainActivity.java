package com.example.spotify.Activities;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.spotify.Fragments.HOME_FRAGMENT.homeFragment;
import com.example.spotify.Fragments.LIBRARY_FRAGMENT.libraryFragment;
import com.example.spotify.Fragments.PREMIUM_FRAGMENT.premiumFragment;
import com.example.spotify.Fragments.SEARCH_FRAGMENT.searchFragment;
import com.example.spotify.R;
import com.example.spotify.login.ApiSpotify;
import com.example.spotify.media.MediaPlayerService;
import com.example.spotify.media.TrackInfo;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {

    private TrackInfo track;
    private MediaPlayerService player;
    boolean serviceBound = false;

    static Retrofit retrofit;
    static ApiSpotify apiSpotify;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
                ////*******************************BottomNavigation***********************////
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        //get instance from the singleton class and start the service
        track = TrackInfo.getInstance();
        if(!serviceBound) {
            startService();
        }

        retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.35:3000").addConverterFactory(GsonConverterFactory.create()).build();
        apiSpotify = retrofit.create(ApiSpotify.class);
    }
    public void loadFragment(Fragment fragment) {
        // create a FragmentManager
        FragmentManager fm = getFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
         // replace the FrameLayout with new Fragment
         //fragmentTransaction.add(R.id.frame_fragment,fragment);
         fragmentTransaction.replace(R.id.frame_fragment,fragment);
         fragmentTransaction.commit(); // save the changes

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    loadFragment(new homeFragment());
                    return true;
                case R.id.navigation_search:
                    loadFragment(new searchFragment());
                    return true;
                case R.id.navigation_library:
                    loadFragment(new libraryFragment());
                    return true;
                case R.id.navigation_premium:
                    loadFragment(new premiumFragment());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean("ServiceState", serviceBound);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        serviceBound = savedInstanceState.getBoolean("ServiceState");
    }

    private void startService(){
        Intent serviceIntent = new Intent(this , MediaPlayerService.class);
        // serviceIntent.putExtra("media" , media);
        startService(serviceIntent);
    }

    public static Retrofit getRetrofit() {
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.35:3000").addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

    public static ApiSpotify getApiSpotify() {
        if(apiSpotify == null){
            apiSpotify = getRetrofit().create(ApiSpotify.class);
        }
        return apiSpotify;
    }
}

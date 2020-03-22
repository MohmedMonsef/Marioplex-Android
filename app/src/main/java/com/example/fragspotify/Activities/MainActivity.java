package com.example.fragspotify.Activities;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fragspotify.Fragments.HOME_FRAGMENT.homeFragment;
import com.example.fragspotify.Fragments.LIBRARY_FRAGMENT.libraryFragment;
import com.example.fragspotify.Fragments.PREMIUM_FRAGMENT.premiumFragment;
import com.example.fragspotify.Fragments.SEARCH_FRAGMENT.searchFragment;
import com.example.fragspotify.R;
import com.example.fragspotify.media.MediaPlayerService;
import com.example.fragspotify.media.TrackInfo;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static android.content.ContentValues.TAG;
import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {

    private TrackInfo track;
    private MediaPlayerService player;
    boolean serviceBound = false;


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
}

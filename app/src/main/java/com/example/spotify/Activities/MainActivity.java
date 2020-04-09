package com.example.spotify.Activities;


import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import com.example.spotify.Fragments.HOME_FRAGMENT.backhome;
import com.example.spotify.Fragments.SEARCH_FRAGMENT.searchfragment;
import com.example.spotify.Fragments.LIBRARY_FRAGMENT.libraryFragment;
import com.example.spotify.Fragments.PREMIUM_FRAGMENT.premiumFragment;
import com.example.spotify.Interfaces.EndPointAPI;
import com.example.spotify.R;
import com.example.spotify.media.MediaPlayerService;
import com.example.spotify.media.TrackInfo;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Retrofit;

import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {

    private TrackInfo track;
    private MediaPlayerService player;
    private FrameLayout bottom_sheet_frame_layout;
    boolean serviceBound = false;
    static Retrofit retrofit;
    static EndPointAPI endPointAPI;

    /**
     *
     * @param savedInstanceState --> a bundle that load the needed object
     * one of the life cycle step of the activity when it is ready
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ////*******************************BottomNavigation***********************////
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(new backhome());// start the main activity with home fragment
        //get instance from the singleton class and start the service
        track = TrackInfo.getInstance();
        if(!serviceBound) {
            startService();
        }
        //hides bottom sheet  if there is no queue created yet
        HideBottomSheet();

        endPointAPI = com.example.spotify.Interfaces.Retrofit.getInstance().getEndPointAPI();

    }

    /**
     *
     * @param fragment -->it is fragment that i go to
     * transfer between the fragments
     */
    public void loadFragment(Fragment fragment)
    {
        // create a FragmentManager
        FragmentManager fm = getSupportFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.frame_fragment,fragment);
        // save the changes
        fragmentTransaction.commit();
    }

    /**
     * a listener on the navigation bar
     * when a button is pressed it call the selected fragment from
     * {backhome,
     * searchfragment
     * libraryFragment
     * premiumFragment
     * }
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        /**
         *
         * @param item --> the pressed button
         * when a button in the navigation bar is pressed it call the selected fragment from
         * {
         * backhome,
         * searchfragment
         * libraryFragment
         * premiumFragment
         *  }
         * @return true if fragment calling succeeded and false if there is an error
         */
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            switch (item.getItemId())
            {
                case R.id.navigation_home:
                    loadFragment(new backhome());
                    return true;
                case R.id.navigation_search:
                    loadFragment(new searchfragment());
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

    void HideBottomSheet(){
        bottom_sheet_frame_layout = findViewById(R.id.bottom_sheet_frame_layout);
        track.getIsQueue().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean){
                    bottom_sheet_frame_layout.setVisibility(View.GONE);
                }
                else{
                    bottom_sheet_frame_layout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

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

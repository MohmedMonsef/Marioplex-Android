package com.example.spotifycorrected;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import media.MediaPlayerService;
import media.TrackInfo;
import pojo.EndPointAPI;
import pojo.Track;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TrackInfo track;

    private MediaPlayerService player;
    boolean serviceBound = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get instance from the sigleton class
        track = TrackInfo.getInstance();

        if(!serviceBound) {
            startService();
        }

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

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void startService(){
        Intent serviceIntent = new Intent(this , MediaPlayerService.class);
       // serviceIntent.putExtra("media" , media);
        startService(serviceIntent);
    }


}

package com.example.homefinalspotify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {


    NewRelease NewReleaseList;
    RecyclerView recyclerView;
    Adapter recyclerAdapter;

    private ActionBar toolbar;

    private TextView textViewResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ////*******************************BottomNavigation***********************////
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ////*******************************RecyclerView***********************////
        NewReleaseList = new NewRelease();
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        ////*******************************To check the state***********************////
        textViewResult = findViewById(R.id.Popular_new_releases);
        ////*******************************Retrofit****************************////
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.spotify.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        classinterface apiService = retrofit.create(classinterface.class);
        Call<NewRelease> call = apiService.getNewRelease();
        call.enqueue(new Callback<NewRelease>() {
            @Override
            public void onResponse(Call<NewRelease> call, Response<NewRelease> response) {
                makeText(MainActivity.this, "response inside connected", Toast.LENGTH_LONG).show();

                if (response.code() == 401)
                    textViewResult.setText(response.errorBody().toString() + "401");
                else if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                NewReleaseList = response.body();
                if (response.body() == null)
                    textViewResult.setText("responce body = null");
                else if (NewReleaseList == null)
                    textViewResult.setText(response.body().toString() + " track = null");
                else {
                    Log.d("TAG", "Response = " + NewReleaseList);
                    makeText(MainActivity.this, "response list corrected", Toast.LENGTH_LONG).show();
                    recyclerAdapter = new Adapter(MainActivity.this, NewReleaseList.getAlbums().getItems());
                    recyclerView.setAdapter(recyclerAdapter);

                }

            }

            @Override
            public void onFailure(Call<NewRelease> call, Throwable t) {
                textViewResult.setText(t.getMessage() + "failed");
                makeText(MainActivity.this, "error connected", Toast.LENGTH_LONG).show();
            }
        });



    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_search:
                    return true;
                case R.id.navigation_library:
                    return true;
                case R.id.navigation_premium:
                    return true;
            }
            return false;
        }
    };
    }


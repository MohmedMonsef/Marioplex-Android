package com.example.spotify.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spotify.Adapters.adapterAlbumTracks;
import com.example.spotify.BackClasses.Backclasses.albumTrack.AlbumTracks;
import com.example.spotify.Interfaces.EndPointAPI;
import com.example.spotify.Interfaces.Retrofit;
import com.example.spotify.R;
import com.example.spotify.login.user;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.PendingIntent.getActivity;
import static androidx.test.InstrumentationRegistry.getContext;

public class AlbumPreviewActivity extends AppCompatActivity {
    private ImageView back_arrow_from_preview_album;
    private TextView album_name_preview_top;
    private TextView album_name_preview;
    private AlbumTracks albumtracks;
    private com.example.spotify.Adapters.adapterAlbumTracks adapterAlbumTracks;
    RecyclerView recyclerView;
    private EndPointAPI endPointAPI = Retrofit.getInstance().getEndPointAPI();
    String ArtistName;
    String AlbumId;
    String AlbumName;
    //private PlaylistTracks mplaylistTracks;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_preview);

        back_arrow_from_preview_album = findViewById(R.id.back_arrow_from_preview_album);
        album_name_preview_top = findViewById(R.id.album_name_preview_top);
        album_name_preview = findViewById(R.id.album_name_preview);
        recyclerView = findViewById(R.id.recycleAlbumPreviw);
        LinearLayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        back_arrow_from_preview_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        reciveDataFromAlbumFragment();
        album_name_preview.setText(AlbumName);
        SetRetrofitAlbumTrack(AlbumId,ArtistName);

    }

    void reciveDataFromAlbumFragment()
    {
        Intent i =getIntent();
        ArtistName=i.getStringExtra("ARTIST_NAME");
        AlbumId=i.getStringExtra("ALBUM_ID");
        AlbumName=i.getStringExtra("Album_Name");

    }
    private void SetRetrofitAlbumTrack(String AlbumId, final String ArtistName)
    {
        Call<AlbumTracks> call = endPointAPI.getAlbumTracks(AlbumId , user.getToken());
        call.enqueue(new Callback<AlbumTracks>()
        {

            /**
             *
             * @param call --> interface request
             * @param response --> interface response
             * called when every changed requests and set the data
             */
            @Override
            public void onResponse(Call<AlbumTracks> call, Response<AlbumTracks> response)
            {
                //error in the server
                if (response.code() == 401)
                    Toast.makeText(getApplicationContext(),response.errorBody().toString() + "401",Toast.LENGTH_SHORT).show();
                 //may internet disconnected
                else if (!response.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(),"Code: " + response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }
                //if responcse is successful and the server send response
                albumtracks = response.body();
                //error in GET request url
                if (response.body() == null)
                    Toast.makeText(getApplicationContext(),"responce body = null" + "401",Toast.LENGTH_SHORT).show();
                 //error in binding interface
                else if (albumtracks == null)
                Toast.makeText(getApplicationContext(),response.body().toString() + " albumtracks = null",Toast.LENGTH_SHORT).show();
                 //Successful
                else
                {
                    adapterAlbumTracks = new adapterAlbumTracks(getApplicationContext(), albumtracks,ArtistName);
                    recyclerView.setAdapter(adapterAlbumTracks);
                    recyclerView.setHasFixedSize(true);
                }

            }
            /**
             *
             * @param call -->interface request
             * @param t -->type of error of the request
             * called when every errored requests and set its type
             */
            @Override
            public void onFailure(Call<AlbumTracks> call, Throwable t)
            {
                Toast.makeText(getApplicationContext(),t.getMessage() + "failed" + "401",Toast.LENGTH_SHORT).show();
            }
        });


    }

}

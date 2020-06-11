package com.example.spotify.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spotify.BackClasses.Backclasses.albumInform.AlbumObject;
import com.example.spotify.BackClasses.Backclasses.likeAlbum.likealbum;
import com.example.spotify.Constants;
import com.example.spotify.Fragments.ALBUM_FRAGMENT.album;
import com.example.spotify.Interfaces.EndPointAPI;
import com.example.spotify.Interfaces.Retrofit;
import com.example.spotify.R;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.test.InstrumentationRegistry.getContext;

public class SettingAlbumActivity extends AppCompatActivity {
String ArtistName;
String AlbumName;
int liked;
String Img_ID,Album_ID;
private AlbumObject AlbumObject;
ImageView album_image_album_fragment_setting,like_album_setting,view_artist,back_arrow_album_setting;
private EndPointAPI endPointAPI = Retrofit.getInstance().getEndPointAPI();
TextView album_owner_setting,album_name_middle_setting,like_album_setting_text,view_artist_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_album);
        album_image_album_fragment_setting=findViewById(R.id.album_image_album_fragment_setting);
        //like_album_setting=findViewById(R.id.like_album_setting);
        view_artist=findViewById(R.id.view_artist);
        album_owner_setting=findViewById(R.id.album_owner_setting);
        album_name_middle_setting=findViewById(R.id.album_name_middle_setting);
        back_arrow_album_setting=findViewById(R.id.back_arrow_album_setting);
        reciveDataFromAlbumFragment();
        GetAlbumsTracksInfo(Album_ID);
        /*if(liked==1){
            like_album_setting.setImageResource(R.drawable.like);
        }
        else{
            like_album_setting.setImageResource(R.drawable.favorite_border);
        }
        */
        album_owner_setting.setText(AlbumName);
        album_name_middle_setting.setText(AlbumName);
        String Imageurl = Retrofit.getInstance().getBaseurl() + "api/images/" + Img_ID + "?belongs_to=album";
        Picasso.get().load(Imageurl).into(album_image_album_fragment_setting);
  /*      like_album_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(liked==1) {
                    UnLikeAlbum();
                }
                else{
                    LikeAlbum();
                }
            }
        });
*/
        back_arrow_album_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // back();
                finish();
            }


        });


        findViewById(R.id.share_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = Constants.FRONTEND_BASE_URL + "HomeWebPlayer/album/" + Album_ID;
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, url);
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        });
    }
    void reciveDataFromAlbumFragment()
    {
        Intent i =getIntent();
        ArtistName=i.getStringExtra("ARTIST_NAME");
        Img_ID=i.getStringExtra("Img_ID");
        Album_ID=i.getStringExtra("Album_ID");
        AlbumName=i.getStringExtra("Album_Name");
        liked=i.getIntExtra("Album Like",0);
    }

    /**
     * send a request to UNlike(follow) the ALBUM
     */
    void UnLikeAlbum(){
        likealbum unlikealbum1 = new likealbum(Album_ID);
        Call<Void> call = endPointAPI.UN_LIKE_ALBUM(unlikealbum1,"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ZWIwOTAxZTMwYTlhMDFmMTQ0YjcyMzUiLCJwcm9kdWN0IjoicHJlbWl1bSIsInVzZXJUeXBlIjoiQXJ0aXN0IiwiaWF0IjoxNTg5OTczOTMzLCJleHAiOjMxNDY0ODg4NzgwMjYwODk1MDB9.gpPtSyJDhiKYB8Lduhnet3upLiXW23HT7KU5Z7oXE8c");
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "something went wrong .try again", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {

                    like_album_setting.setImageResource(R.drawable.favorite_border);
                    AlbumObject.setIsSaved(false);
                    back();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(),"something went wrong .check your internet connection",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * send a request to like(unfollow) the ALBUM
     */
    void LikeAlbum(){
        likealbum likealbum1 = new likealbum(Album_ID);
        Call<Void> call = endPointAPI.LIKE_ALBUM(likealbum1 , "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ZWIwOTAxZTMwYTlhMDFmMTQ0YjcyMzUiLCJwcm9kdWN0IjoicHJlbWl1bSIsInVzZXJUeXBlIjoiQXJ0aXN0IiwiaWF0IjoxNTg5OTczOTMzLCJleHAiOjMxNDY0ODg4NzgwMjYwODk1MDB9.gpPtSyJDhiKYB8Lduhnet3upLiXW23HT7KU5Z7oXE8c");
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "something went wrong .try again", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    like_album_setting.setImageResource(R.drawable.like);
                    AlbumObject.setIsSaved(true);
                    back();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(),"something went wrong .check your internet connection",Toast.LENGTH_SHORT).show();
            }
        });
    }

    void GetAlbumsTracksInfo(String albumID) {
        Call<AlbumObject> call = endPointAPI.getAlbumObject(albumID,"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ZWIwOTAxZTMwYTlhMDFmMTQ0YjcyMzUiLCJwcm9kdWN0IjoicHJlbWl1bSIsInVzZXJUeXBlIjoiQXJ0aXN0IiwiaWF0IjoxNTg5OTczOTMzLCJleHAiOjMxNDY0ODg4NzgwMjYwODk1MDB9.gpPtSyJDhiKYB8Lduhnet3upLiXW23HT7KU5Z7oXE8c");
        call.enqueue(new Callback<AlbumObject>() {
            @Override
            public void onResponse(Call<AlbumObject> call, Response<AlbumObject> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    return;
                } else
                    {
                    AlbumObject = response.body();

                }
            }

            @Override
            public void onFailure(Call<AlbumObject> call, Throwable t) {

            }
        });
    }

    /**
     * return back to album page
     */
    void back()
    {
  /*    Bundle bundle = new Bundle();
        bundle.putString("albumID" , Album_ID);
        bundle.putString("artistName" , ArtistName);
        androidx.fragment.app.Fragment f = new album();
        f.setArguments(bundle);
        AppCompatActivity activity = (AppCompatActivity) getContext();
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_fragment, f)
                .addToBackStack(null).commit();

*/
        Bundle bundle = new Bundle();
        bundle.putString("albumID" , Album_ID);
        bundle.putString("artistName" , ArtistName);
        Fragment f = new album();
        f.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.frame_fragment,f);
        // save the changes
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


 //       Bundle bundle = new Bundle();
   //     bundle.putInt("likedalbum" , liked);
//        finish();

    }

    /**
     * go to album fragment
     * @param fragment
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
}

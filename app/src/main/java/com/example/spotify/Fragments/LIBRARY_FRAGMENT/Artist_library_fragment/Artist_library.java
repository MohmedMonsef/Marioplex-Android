package com.example.spotify.Fragments.LIBRARY_FRAGMENT.Artist_library_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotify.Interfaces.EndPointAPI;
import com.example.spotify.Interfaces.Retrofit;
import com.example.spotify.R;
import com.example.spotify.login.user;
import com.example.spotify.pojo.LibraryArtists;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Artist_library extends Fragment implements LifecycleOwner {


    private EndPointAPI endPointAPI = Retrofit.getInstance().getEndPointAPI();
    private LibraryArtists userartists;
    private ArtistiLibraryAdapter artistLibraryAdapter;
    private ProgressBar library_artist_progress_bar;
    private LinearLayout something_wrong_layout4;
    private RecyclerView library_artist_recycleview;
    private Button try_again4;
    private LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_artist_library, container, false);

        getViews(view);

        /////////////////////////List view attaching will be made in the on response callback///////
        //////make the request then attach it to the listview//////
        library_artist_progress_bar.setVisibility(View.VISIBLE);
        something_wrong_layout4.setVisibility(View.GONE);
        library_artist_recycleview.setVisibility(View.GONE);
        getArtists();

        /**
         * listener for the try again button
         * on press it sends the request to get the artists again
         */

        try_again4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                library_artist_progress_bar.setVisibility(View.VISIBLE);
                something_wrong_layout4.setVisibility(View.GONE);
                library_artist_recycleview.setVisibility(View.GONE);
                getArtists();
            }
        });



        return view;

    }



    void getArtists(){
        Call<LibraryArtists> call = endPointAPI.getArtists( user.getToken());
        call.enqueue(new Callback<LibraryArtists>() {
            @Override
            public void onResponse(Call<LibraryArtists> call, Response<LibraryArtists> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(),"Code: "+response.code(),Toast.LENGTH_SHORT).show();
                    something_wrong_layout4.setVisibility(View.VISIBLE);
                    library_artist_progress_bar.setVisibility(View.GONE);
                    library_artist_recycleview.setVisibility(View.GONE);

                    return;
                }
                else if(response.body()==null){
                    Toast.makeText(getContext(),"response body = null",Toast.LENGTH_SHORT).show();
                }
                else {
                    userartists = response.body();

                    artistLibraryAdapter = new ArtistiLibraryAdapter(getActivity(), userartists.getArtists());
                    library_artist_recycleview.setAdapter(artistLibraryAdapter);
                    library_artist_recycleview.setHasFixedSize(true);

                    something_wrong_layout4.setVisibility(View.GONE);
                    library_artist_recycleview.setVisibility(View.VISIBLE);
                    library_artist_progress_bar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<LibraryArtists> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage()+" ' failed '",Toast.LENGTH_SHORT).show();

                something_wrong_layout4.setVisibility(View.VISIBLE);
                library_artist_progress_bar.setVisibility(View.GONE);
                library_artist_recycleview.setVisibility(View.GONE);


            }
        });

    }

    void getViews(View root){
        library_artist_recycleview = root.findViewById(R.id.library_artist_recycleview);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        library_artist_recycleview.setLayoutManager(layoutManager);
        library_artist_progress_bar = root.findViewById(R.id.library_artist_progress_bar);
        something_wrong_layout4 = root.findViewById(R.id.something_wrong_layout4);
        try_again4 = root.findViewById(R.id.try_again4);
    }

}
package com.example.spotify.Fragments.LIBRARY_FRAGMENT.Album_library_fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.spotify.Adapters.adapterSavedAlbum;
import com.example.spotify.BackClasses.Backclasses.SavedAlbums.SavedAlbums;
import com.example.spotify.Interfaces.backinterfaces;
import com.example.spotify.R;
import com.example.spotify.login.user;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class Album_Library extends Fragment implements LifecycleOwner {

    RecyclerView recyclerView;
    com.example.spotify.Adapters.adapterSavedAlbum adapterSavedAlbum;
   // LinearLayoutManager layoutManager;
    SavedAlbums savedAlbums;
    TextView textViewResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_album__library, container, false);
        savedAlbums = new SavedAlbums();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerSavedAlbum);
        textViewResult=view.findViewById(R.id.textlibrary);
        LinearLayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        ////*******************************RecyclerView***********************////
        SetRetrofit();
        return view;

    }
    ////*******************************Retrofit****************************////
    private void SetRetrofit()
    {

        Retrofit retrofit = com.example.spotify.Interfaces.Retrofit.getInstance().getRetrofit();
        backinterfaces apiService = retrofit.create(backinterfaces.class);
        Call<SavedAlbums> call = apiService.getSavedAlbums(user.getToken());
        call.enqueue(new Callback<SavedAlbums>()
        {
            /**
             *
             * @param call --> interface request
             * @param response --> interface response
             * called when every changed requests and set the data
             */
            @Override
            public void onResponse(Call<SavedAlbums> call, Response<SavedAlbums> response)
            {
                //error in the server
                if (response.code() == 401)
                    textViewResult.setText(response.errorBody().toString() + "401");
                    //may internet disconnected
                else if (!response.isSuccessful())
                {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }
                //if responcse is successful and the server send response
                savedAlbums = response.body();
                //error in GET request url
                if (response.body() == null)
                    textViewResult.setText("responce body = null");
                    //error in binding interface
                else if (savedAlbums == null)
                    textViewResult.setText(response.body().toString() + " SavedAlbums = null");
                    //Successful
                else
                {
                    Log.d("TAG", "Response = " + savedAlbums);
                    adapterSavedAlbum = new adapterSavedAlbum(getActivity(), savedAlbums.getSavedAlbums());
                    recyclerView.setAdapter(adapterSavedAlbum);
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
            public void onFailure(Call<SavedAlbums> call, Throwable t)
            {
                textViewResult.setText("Failed to connect to server");
            }
        });
    }
}
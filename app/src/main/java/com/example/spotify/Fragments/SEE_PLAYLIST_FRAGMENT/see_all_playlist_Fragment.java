package com.example.spotify.Fragments.SEE_PLAYLIST_FRAGMENT;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spotify.Activities.MainActivity;
import com.example.spotify.Adapters.adapterSearch;
import com.example.spotify.Adapters.adapterSearch1;
import com.example.spotify.BackClasses.Backclasses.backsearch.Search;
import com.example.spotify.Fragments.SEARCH_LIST_FRAGMENT.searchListfragment;
import com.example.spotify.Fragments.SEARCH_LIST_FRAGMENT.viewmodelSearchList;
import com.example.spotify.Interfaces.backinterfaces;
import com.example.spotify.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class see_all_playlist_Fragment extends Fragment implements LifecycleOwner {
    Search searchList;
    RecyclerView recyclerView,recyclerView1;
    adapterSearch1 recyclerAdapter,recyclerAdapter1;
    private TextView textViewResult;
    private Retrofit retrofit;
    private backinterfaces apiService;
    private viewmodelSearchList searchViewModel;
    TextView ArtistText;
    String wordRecieve;
    ImageView back_button_to_seeAllPlaylist;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_see_all_playlist_, container, false);
        searchViewModel = ViewModelProviders.of((MainActivity) getActivity()).get(viewmodelSearchList.class);
        //searchView.setOnSearchClickListener(View.OnClickListener on);
        final TextView textView = view.findViewById(R.id.text_home);
        ArtistText=view.findViewById(R.id.textalbumsearch);
        retrofit = com.example.spotify.Interfaces.Retrofit.getInstance().getRetrofit();
        apiService = retrofit.create(backinterfaces.class);
        ////*******************************RecyclerView***********************////
        searchList = new Search();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleSearchSeeAllPlaylist);
        recyclerView1 = (RecyclerView) view.findViewById(R.id.recycleSearchSeeAllPlaylist1);
        LinearLayoutManager layoutManager,layoutManager1;
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView1.setLayoutManager(layoutManager1);
        Bundle args=getArguments();
        wordRecieve = getArguments().getString("DATA_RECIEVE_Playlist");
        ArtistText.setText("' "+wordRecieve+"'"+"  in Playlist ");
        back_button_to_seeAllPlaylist=view.findViewById(R.id.back_button_to_seeAllPlaylist);
        back_button_to_seeAllPlaylist.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Fragment newFragment = new searchListfragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_fragment, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        SetRetrofitsearchalbum(wordRecieve);
        ////*******************************To check the state***********************////
        textViewResult = view.findViewById(R.id.texta);
        return view;
    }

    public void SetRetrofitsearchalbum(String s)
    {
        Call<Search> call = apiService.getSearch(s, "playlist" , "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ZTgwYzZhZjE0Yzg1NjZkNmNkOWI0MDAiLCJwcm9kdWN0IjoiZnJlZSIsInVzZXJUeXBlIjoiQXJ0aXN0IiwiaWF0IjoxNTg2MDI2NjAyLCJleHAiOjQ3MzI1MTMwMDJ9.ztEjNCgbkyJ2-9WB6ojwLgDfhWsZ-ZGJVFUB8dYMz8s");
        call.enqueue(new Callback<Search>()
        {
            /**
             *
             * @param call --> interface request
             * @param response --> interface response
             * called when every changed requests and set the data
             */
            @Override
            public void onResponse(Call<Search> call, Response<Search> response)
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
                searchList = response.body();
                //error in GET request url
                if (response.body() == null)
                    textViewResult.setText("responce body = null");
                    //error in binding interface
                else if (searchList == null)
                    textViewResult.setText(response.body().toString() + " search = null");
                    //Successful
                else
                {
                    Log.d("TAG", "Response = " + searchList);
                    //set the search recyclerview
                    recyclerAdapter = new adapterSearch1(getActivity(), searchList);
                    recyclerAdapter1 = new adapterSearch1(getActivity(), searchList);
                    recyclerView.setAdapter(recyclerAdapter);
                    recyclerView.setHasFixedSize(true);
                    recyclerView1.setAdapter(recyclerAdapter1);
                    recyclerView1.setHasFixedSize(true);

                }
            }

            /**
             *
             * @param call -->interface request
             * @param t -->type of error of the request
             * called when every errored requests and set its type
             */
            @Override
            public void onFailure(Call<Search> call, Throwable t)
            {
                textViewResult.setText(t.getMessage() + "hey there failed");
            }
        });
    }




}
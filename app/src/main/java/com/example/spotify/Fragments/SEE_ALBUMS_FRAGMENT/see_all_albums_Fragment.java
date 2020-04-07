package com.example.spotify.Fragments.SEE_ALBUMS_FRAGMENT;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotify.Activities.MainActivity;
import com.example.spotify.Adapters.adapterSearch;
import com.example.spotify.Interfaces.backinterfaces;
import com.example.spotify.BackClasses.Backclasses.backsearch.Search;
import com.example.spotify.Fragments.SEARCH_LIST_FRAGMENT.viewmodelSearchList;
import com.example.spotify.R;

import retrofit2.Retrofit;


public class see_all_albums_Fragment extends android.app.Fragment implements LifecycleOwner {


    Search searchList;
    RecyclerView recyclerView;
    adapterSearch recyclerAdapter;
    private TextView textViewResult;
    LinearLayout l1;
    int dochange;
    private Retrofit retrofit;
    private backinterfaces apiService;
    private viewmodelSearchList searchViewModel;
TextView Artist;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_listfragment, container, false);
        searchViewModel = ViewModelProviders.of((MainActivity) getActivity()).get(viewmodelSearchList.class);
        //searchView.setOnSearchClickListener(View.OnClickListener on);
        final TextView textView = view.findViewById(R.id.text_home);
       Artist=view.findViewById(R.id.textalbumsearch);
/////##########$$$$$$$$$$$$$$$$$$
        retrofit = com.example.spotify.Interfaces.Retrofit.getInstance().getRetrofit();
        apiService = retrofit.create(backinterfaces.class);

/*
                    Call<Search> call = apiService.getSearch(s, "artist,album,playlist,track" , "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ZTgwYzZhZjE0Yzg1NjZkNmNkOWI0MDAiLCJwcm9kdWN0IjoiZnJlZSIsInVzZXJUeXBlIjoiQXJ0aXN0IiwiaWF0IjoxNTg2MDI2NjAyLCJleHAiOjQ3MzI1MTMwMDJ9.ztEjNCgbkyJ2-9WB6ojwLgDfhWsZ-ZGJVFUB8dYMz8s");
                    call.enqueue(new Callback<Search>() {
                        @Override
                        public void onResponse(Call<Search> call, Response<Search> response) {
                            if (response.code() == 401)
                                textViewResult.setText(response.errorBody().toString() + "401");
                            else if (!response.isSuccessful()) {
                                textViewResult.setText("Code: " + response.code());
                                return;
                            }
                            searchList = response.body();
                            //        Tracklist = response.body();
                            if (response.body() == null)
                                textViewResult.setText("responce body = null");
                            else if (searchList == null)
                                textViewResult.setText(response.body().toString() + " track = null");
                            else {
                                Log.d("TAG", "Response = " + searchList);
                                recyclerAdapter = new adapterSearch(getActivity(), searchList);
                                recyclerView.setAdapter(recyclerAdapter);
                                recyclerView.setHasFixedSize(true);
                            }
                        }

                        @Override
                        public void onFailure(Call<Search> call, Throwable t) {
                            textViewResult.setText(t.getMessage() + "hey there failed");
                        }
                    });



*/




        ////*******************************RecyclerView***********************////
        searchList = new Search();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleSearch);
        LinearLayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        //SetRetrofit();
        ////*******************************To check the state***********************////
        textViewResult = view.findViewById(R.id.texta);

        return view;

    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return null;


    }

}
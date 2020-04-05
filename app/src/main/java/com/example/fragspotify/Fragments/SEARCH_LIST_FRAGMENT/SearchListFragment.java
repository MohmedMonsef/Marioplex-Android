package com.example.fragspotify.Fragments.SEARCH_LIST_FRAGMENT;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.app.Fragment;

//import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.fragspotify.Activities.MainActivity;
import com.example.fragspotify.Adapters.adapterSearch;

import com.example.fragspotify.Interfaces.classinterface;
import com.example.fragspotify.R;
import com.example.fragspotify.SpotifyClasses.SearchClasses.Search;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.Toast.makeText;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */

public class SearchListFragment extends Fragment implements LifecycleOwner {


    Search searchList;
    RecyclerView recyclerView;
    adapterSearch recyclerAdapter;
    private TextView textViewResult;
    private Retrofit retrofit;
    private classinterface apiService;
    SearchView searchView;
    private SearchView.OnQueryTextListener queryTextListener;
    MenuItem item;
    private viewmodelSearchList searchViewModel;
    EditText editText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_list, container, false);
        searchViewModel = ViewModelProviders.of((MainActivity) getActivity()).get(viewmodelSearchList.class);
        //searchView.setOnSearchClickListener(View.OnClickListener on);
        final TextView textView = view.findViewById(R.id.text_home);
        ////*****************************toolbar************************************////

        editText=(EditText) view.findViewById(R.id.yamoshal);

/////##########$$$$$$$$$$$$$$$$$$
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.7:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(classinterface.class);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                if(s.length()==0)
                    searchList=null;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()!=0) {
                    Log.i("onQueryTextChange", s.toString());

                    Call<Search> call = apiService.getSearch(s.toString(), "artist,album,playlist,track", "US", 5, 0 , "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ZTgwYzZhZjE0Yzg1NjZkNmNkOWI0MDAiLCJwcm9kdWN0IjoiZnJlZSIsInVzZXJUeXBlIjoiQXJ0aXN0IiwiaWF0IjoxNTg2MDI2NjAyLCJleHAiOjQ3MzI1MTMwMDJ9.ztEjNCgbkyJ2-9WB6ojwLgDfhWsZ-ZGJVFUB8dYMz8s");
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
                                //     recyclerAdapterArtist = new AdapterArtist(getActivity(), Atristlist.getArtists().getItems());
                                //   recyclerView.setAdapter(recyclerAdapterArtist);
                                recyclerView.setHasFixedSize(true);
                            }
                        }

                        @Override
                        public void onFailure(Call<Search> call, Throwable t) {
                            textViewResult.setText(t.getMessage() + "hey there failed");
                        }
                    });
                }
                searchList=null;

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==0)
                    searchList=null;

            }
        });



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
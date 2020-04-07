package com.example.spotify.Fragments.SEARCH_LIST_FRAGMENT;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
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
import com.example.spotify.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */

public class searchListfragment extends Fragment implements LifecycleOwner {


    Search searchList;
    RecyclerView recyclerView;
    adapterSearch recyclerAdapter;
    private TextView textViewResult,talbum,tartist,tplaylist,tsong;
    LinearLayout l1;
     int dochange;
    private Retrofit retrofit;
    private backinterfaces apiService;
    private viewmodelSearchList searchViewModel;
    EditText editText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_listfragment, container, false);
        searchViewModel = ViewModelProviders.of((MainActivity) getActivity()).get(viewmodelSearchList.class);
        final TextView textView = view.findViewById(R.id.text_home);
        l1=(LinearLayout) view.findViewById(R.id.searchlistlayout);
        l1.setBackground(getResources().getDrawable(R.drawable.item));
        editText=(EditText) view.findViewById(R.id.searchbarlist);
        retrofit = com.example.spotify.Interfaces.Retrofit.getInstance().getRetrofit();
        apiService = retrofit.create(backinterfaces.class);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                if(s.length()==0)
                    searchList=null;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
            dochange=s.length();
                if(s.length()!=0) {
                    Log.i("onQueryTextChange", s.toString());
                    Call<Search> call = apiService.getSearch(s.toString(), "artist,album,playlist,track" , "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ZTgwYzZhZjE0Yzg1NjZkNmNkOWI0MDAiLCJwcm9kdWN0IjoiZnJlZSIsInVzZXJUeXBlIjoiQXJ0aXN0IiwiaWF0IjoxNTg2MDI2NjAyLCJleHAiOjQ3MzI1MTMwMDJ9.ztEjNCgbkyJ2-9WB6ojwLgDfhWsZ-ZGJVFUB8dYMz8s");
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
                                if(dochange==1)
                                {l1.setBackground(getResources().getDrawable(R.drawable.searchb2));}
                                if(dochange==2)
                                {l1.setBackground(getResources().getDrawable(R.drawable.searchb3));}
                                if(dochange==3)
                                {l1.setBackground(getResources().getDrawable(R.drawable.searchb4));}
                                if(dochange==4)
                                {l1.setBackground(getResources().getDrawable(R.drawable.searchb1));}
                                recyclerView.setAdapter(recyclerAdapter);
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

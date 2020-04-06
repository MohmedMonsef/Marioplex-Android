package com.example.spotify.BackClasses.BackFragment;

import android.app.Fragment;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotify.Activities.MainActivity;
import com.example.spotify.BackClasses.BackAdapter.adapterSearch;
import com.example.spotify.BackClasses.BackInterfaces.backinterfaces;
import com.example.spotify.BackClasses.Backclasses.backsearch.Search;
import com.example.spotify.Fragments.SEARCH_LIST_FRAGMENT.viewmodelSearchList;
import com.example.spotify.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    private TextView textViewResult;
    private Retrofit retrofit;
    private backinterfaces apiService;
    SearchView searchView;
    private SearchView.OnQueryTextListener queryTextListener;
    MenuItem item;
    private viewmodelSearchList searchViewModel;
    EditText editText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_listfragment, container, false);
        searchViewModel = ViewModelProviders.of((MainActivity) getActivity()).get(viewmodelSearchList.class);
        //searchView.setOnSearchClickListener(View.OnClickListener on);
        final TextView textView = view.findViewById(R.id.text_home);
        ////*****************************toolbar************************************////

        editText=(EditText) view.findViewById(R.id.yamoshal);

/////##########$$$$$$$$$$$$$$$$$$
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
            public void onTextChanged(CharSequence s, int start, int before, int count) {
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


    /*
     @Override
     public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setHasOptionsMenu(true);
     }

     private void SetRetrofit() {
         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl("https://api.spotify.com/")
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();
         EndPointAPI apiService = retrofit.create(EndPointAPI.class);
         Call<search> call = apiService.getSearch("Muse", "artist", "US", 5, 0);
         call.enqueue(new Callback<search>() {
             @Override
             public void onResponse(Call<search> call, Response<search> response) {
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
                     recyclerAdapter = new AdapterArtist(getActivity(), searchList.getArtists().getItems());
                     recyclerView.setAdapter(recyclerAdapter);
                     //     recyclerAdapterArtist = new AdapterArtist(getActivity(), Atristlist.getArtists().getItems());
                     //   recyclerView.setAdapter(recyclerAdapterArtist);
                     recyclerView.setHasFixedSize(true);
                 }
             }

      /*       @Override
        /*     public void onFailure(Call<search> call, Throwable t) {
          //       textViewResult.setText(t.getMessage() + "failed");
             }
         });
     }



 /////////////////////////////////////////////////////////////////////////////////////

 /*
     @Override
     public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
         super.onCreateOptionsMenu(menu, inflater);
         menu.clear();
         inflater.inflate(R.menu.searchnav, menu);
        // @SuppressLint("ServiceCast") InputMethodManager im = (InputMethodManager) getActivity().getSystemService(SEARCH_SERVICE);
         ////////////////////
         //   item = menu.findItem(R.id.action_search);
         searchView = new SearchView(((getActivity()).getActionBar().getThemedContext()));
         searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
         // item.setActionView(searchView);
         searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
             @Override
             public boolean onQueryTextSubmit(String query)
             {
                 Retrofit retrofit = new Retrofit.Builder()
                         .baseUrl("https://api.spotify.com/")
                         .addConverterFactory(GsonConverterFactory.create())
                         .build();
                 EndPointAPI apiService = retrofit.create(EndPointAPI.class);
                 Call<search> call = apiService.getSearch(query,"artist","US",5,0);
                 call.enqueue(new Callback<search>() {
                     @Override
                     public void onResponse(Call<search> call, Response<search> response)
                     {
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
                         else if (searchList==null)
                             textViewResult.setText(response.body().toString() + " track = null");
                         else {
                             Log.d("TAG", "Response = " + searchList);
                             recyclerAdapter = new AdapterArtist(getActivity(), searchList.getArtists().getItems());
                             recyclerView.setAdapter(recyclerAdapter);
                             //     recyclerAdapterArtist = new AdapterArtist(getActivity(), Atristlist.getArtists().getItems());
                             //   recyclerView.setAdapter(recyclerAdapterArtist);
                             recyclerView.setHasFixedSize(true);
                         }
                     }
                     @Override
                     public void onFailure(Call<search> call, Throwable t) {
                         textViewResult.setText(t.getMessage() + "failed");
                     }
                 });
                 return false;
             }
             @Override
             public boolean onQueryTextChange(String newText)
             {

                 return false;

             }
         });

     }
 */
    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return null;


    }
}
/*

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.searchnav, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i("onQueryTextChange", newText);
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://api.spotify.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    EndPointAPI apiService = retrofit.create(EndPointAPI.class);
                    Call<search> call = apiService.getSearch(newText,"artist","US",5,0);
                    call.enqueue(new Callback<search>() {
                        @Override
                        public void onResponse(Call<search> call, Response<search> response)
                        {
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
                            else if (searchList==null)
                                textViewResult.setText(response.body().toString() + " track = null");
                            else {
                                Log.d("TAG", "Response = " + searchList);
                                recyclerAdapter = new AdapterArtist(getActivity(), searchList.getArtists().getItems());
                                recyclerView.setAdapter(recyclerAdapter);
                                //     recyclerAdapterArtist = new AdapterArtist(getActivity(), Atristlist.getArtists().getItems());
                                //   recyclerView.setAdapter(recyclerAdapterArtist);
                                recyclerView.setHasFixedSize(true);
                            }
                        }
                        @Override
                        public void onFailure(Call<search> call, Throwable t) {
                            textViewResult.setText(t.getMessage() + "failed");
                        }
                    });

                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://api.spotify.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    EndPointAPI apiService = retrofit.create(EndPointAPI.class);
                    Call<search> call = apiService.getSearch(query,"artist","US",5,0);
                    call.enqueue(new Callback<search>() {
                        @Override
                        public void onResponse(Call<search> call, Response<search> response)
                        {
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
                            else if (searchList==null)
                                textViewResult.setText(response.body().toString() + " track = null");
                            else {
                                Log.d("TAG", "Response = " + searchList);
                                recyclerAdapter = new AdapterArtist(getActivity(), searchList.getArtists().getItems());
                                recyclerView.setAdapter(recyclerAdapter);
                                //     recyclerAdapterArtist = new AdapterArtist(getActivity(), Atristlist.getArtists().getItems());
                                //   recyclerView.setAdapter(recyclerAdapterArtist);
                                recyclerView.setHasFixedSize(true);
                            }
                        }
                        @Override
                        public void onFailure(Call<search> call, Throwable t) {
                            textViewResult.setText(t.getMessage() + "failed");
                        }
                    });

                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // Not implemented here
                return false;
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }








}
    /*
    ////*******************************Retrofit****************************////
   /* private void SetRetrofit(String query)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.spotify.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        EndPointAPI apiService = retrofit.create(EndPointAPI.class);
        Call<search>= apiService.getSearch(query,) ;
        call.enqueue(new Callback<search>() {
            @Override
            public void onResponse(Call<search> call, Response<search> response) {
                if (response.code() == 401)
                    textViewResult.setText(response.errorBody().toString() + "401");
                else if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }
                searchList = response.body();
                if (response.body() == null)
                    textViewResult.setText("responce body = null");
                else if (searchList == null)
                    textViewResult.setText(response.body().toString() + " track = null");
                else {
                    Log.d("TAG", "Response = " + searchList);
                    recyclerAdapter = new AdapterArtist(getActivity(), searchList.getArtists().getItems());
                    recyclerView.setAdapter(recyclerAdapter);
                    recyclerView.setHasFixedSize(true);
                }

            }

            @Override
            public void onFailure(Call<search> call, Throwable t) {
                textViewResult.setText(t.getMessage() + "failed");
            }
        });


    }
    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return null;
    }

    public void loadFragmentSetting(Fragment fragment) {
        // create a FragmentManager
        FragmentManager fm = getFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        //fragmentTransaction.add(R.id.frame_fragment,fragment);
        fragmentTransaction.replace(R.id.frame_fragment,fragment);
        fragmentTransaction.commit(); // save the changes

    }
}
  /*  @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return null;
    }
//////////////*********************************//////////////////////////////////////
/*private SearchView searchView;
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private search movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        movieAdapter = new MovieAdapter();
        recyclerView.setAdapter(movieAdapter);

        movieList = new search();
        ApiInterface apiService = ApiClient.getClient().create(EndPointAPI.class);
        Call<List<search>> call = apiService.getMovies();

        call.enqueue(new Callback<List<search>>() {
            @Override
            public void onResponse(Call<List<search>> call, Response<List<search>> response) {
                movieList = response.body();
                Log.d("TAG","Response = "+movieList);
                movieAdapter.setMovieList(getApplicationContext(),movieList);
            }

            @Override
            public void onFailure(Call<List<search>> call, Throwable t) {
                Log.d("TAG","Response = "+t.toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchnav, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                movieAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
/*
///////////////////************************************/////////////////////////////////////////

/*
    private viewmodelSearchList searchViewmodellist;
    private TextView textViewResult;
    MenuItem item;
    RecyclerView recyclerView;
    AdabterSearch recyclerAdapter;
    //AdapterArtist recyclerAdapterArtist;
    search Atristlist;
    search Tracklist;
    SearchView searchView; MenuItem item;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_list, container, false);
        searchViewmodellist =ViewModelProviders.of((MainActivity) getActivity()).get(viewmodelSearchList.class);
        textViewResult=view.findViewById(R.id.texta);
        ////*******************************RecyclerView***********************////
/*        Atristlist = new search();
      //  Tracklist=new search();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleSearch);
        LinearLayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
/////////////////////*******************//////////////////////////////////////////////
/*        return view;

    }


    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.searchnav, menu);
         item = menu.findItem(R.id.search);
         searchView = new SearchView(((getActivity()).getActionBar().getThemedContext()));
        item.setActionView(searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://api.spotify.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                EndPointAPI apiService = retrofit.create(EndPointAPI.class);
                Call<search> call = apiService.getSearch(query,"artist","US",10,5);
                call.enqueue(new Callback<search>() {
                    @Override
                    public void onResponse(Call<search> call, Response<search> response)
                    {
                        if (response.code() == 401)
                            textViewResult.setText(response.errorBody().toString() + "401");
                        else if (!response.isSuccessful()) {
                            textViewResult.setText("Code: " + response.code());
                            return;
                        }
                        Atristlist = response.body();
                        //        Tracklist = response.body();
                        if (response.body() == null)
                            textViewResult.setText("responce body = null");
                        else if (Atristlist==null)
                            textViewResult.setText(response.body().toString() + " track = null");
                        else {
                            Log.d("TAG", "Response = " + Atristlist);
                            recyclerAdapter = new AdabterSearch(getActivity(), Atristlist);
                            recyclerView.setAdapter(recyclerAdapter);
                       //     recyclerAdapterArtist = new AdapterArtist(getActivity(), Atristlist.getArtists().getItems());
                         //   recyclerView.setAdapter(recyclerAdapterArtist);
                            recyclerView.setHasFixedSize(true);
                        }
                    }
                    @Override
                    public void onFailure(Call<search> call, Throwable t) {
                        textViewResult.setText(t.getMessage() + "failed");
                    }
                });
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText)
            {
                return false;
            }
        });

    }

    ////*******************************Retrofit****************************////
  /*  private void SetRetrofit()
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.spotify.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        EndPointAPI apiService = retrofit.create(EndPointAPI.class);
        Call<search> call = apiService.getSearch();
        call.enqueue(new Callback<search>() {
            @Override
            public void onResponse(Call<search> call, Response<search> response)
            {
                if (response.code() == 401)
                    textViewResult.setText(response.errorBody().toString() + "401");
                else if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }
                Atristlist = response.body();
        //        Tracklist = response.body();
                if (response.body() == null)
                    textViewResult.setText("responce body = null");
                else if (Atristlist==null)
                    textViewResult.setText(response.body().toString() + " track = null");
                else {
                    Log.d("TAG", "Response = " + Atristlist);
                    //recyclerAdapter = new AdabterSearch(getActivity(), Tracklist.getTracks().getItems());
                    //recyclerView.setAdapter(recyclerAdapter);
                    recyclerAdapterArtist = new AdapterArtist(getActivity(), Atristlist.getArtists().getItems());
                    recyclerView.setAdapter(recyclerAdapterArtist);
                    recyclerView.setHasFixedSize(true);


                    }

            }

            @Override
            public void onFailure(Call<search> call, Throwable t) {
                textViewResult.setText(t.getMessage() + "failed");
            }
        });


    }
*/






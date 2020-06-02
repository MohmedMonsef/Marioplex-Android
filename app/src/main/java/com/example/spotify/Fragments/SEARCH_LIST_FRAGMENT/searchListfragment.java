package com.example.spotify.Fragments.SEARCH_LIST_FRAGMENT;

import androidx.fragment.app.Fragment;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotify.Activities.MainActivity;
import com.example.spotify.Adapters.adapterSearch;
import com.example.spotify.Fragments.SEARCH_FRAGMENT.searchfragment;
import com.example.spotify.Fragments.SEE_ALBUMS_FRAGMENT.see_all_albums_Fragment;
import com.example.spotify.Fragments.SEE_ARTISTS_FRAGMENT.see_all_artist_Fragment;
import com.example.spotify.Fragments.SEE_PLAYLIST_FRAGMENT.see_all_playlist_Fragment;
import com.example.spotify.Fragments.SEE_SONG_FRAGMENT.see_all_song_Fragment;
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

public class searchListfragment extends Fragment implements LifecycleOwner
{
    Search searchList;
    RecyclerView recyclerView;
    adapterSearch recyclerAdapter;
    ImageView back_button_to_searchlist;
    private TextView textViewResult,talbum,tartist,tplaylist,tsong;
    LinearLayout l1;
    int dochange;
    String word;
    private Retrofit retrofit;
    private backinterfaces apiService;
    EditText editText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_search_listfragment, container, false);
        final TextView textView = view.findViewById(R.id.text_home);
        l1=(LinearLayout) view.findViewById(R.id.searchlistlayout);
        l1.setBackground(getResources().getDrawable(R.drawable.item));
        editText=(EditText) view.findViewById(R.id.searchbarlist);
        retrofit = com.example.spotify.Interfaces.Retrofit.getInstance().getRetrofit();
        apiService = retrofit.create(backinterfaces.class);
        ////*******************************RecyclerView***********************////
        searchList = new Search();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleSearch);
        LinearLayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        ////************************************************************************////
        talbum=view.findViewById(R.id.textalbums);
        talbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loadSearchFragment("album");
            }
        });
        ////************************************************************************////
        tartist=view.findViewById(R.id.textartist);
        tartist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loadSearchFragment("artist");
            }
        });
        ////************************************************************************////
        tsong=view.findViewById(R.id.texttrack);
        tsong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loadSearchFragment("song");
            }
        });
        ////************************************************************************////
        tplaylist=view.findViewById(R.id.textplaylist);
        tplaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loadSearchFragment("playlist");
            }
        });
        //*************************************************************************///
        back_button_to_searchlist=view.findViewById(R.id.back_button_to_searchlist);
        back_button_to_searchlist.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Fragment newFragment = new searchfragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_fragment, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        ////*******************************To check the state***********************////
        textViewResult = view.findViewById(R.id.texta);
        /**
         * a listener for change in search in edit text
         */
        editText.addTextChangedListener(new TextWatcher()
        {
            /**
             *
             * @param s --> the current CharSequence in the edit text
             * @param start -->start position in the CharSequence
             * @param count -->size of the CharSequence
             * @param after -->after current  position in the CharSequenceg
             *  a handeler before changing in the edit text
             */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                if(s.length()==0)
                    searchList=null;
            }

            /**
             *
             * @param s --> the current CharSequence in the edit text
             * @param start -->start position in the CharSequence
             * @param count -->size of the CharSequence
             * @param before -->before current  position in the CharSequenceg
             *  a handeler for changing in the edit text
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                //flag to change the background color
            dochange=s.length();
            word=s.toString();
                if(s.length()!=0)
                {
                    Log.i("onQueryTextChange", s.toString());
                    Call<Search> call = apiService.getSearch(s.toString(), "artist,album,playlist,track" , "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ZTgwYzZhZjE0Yzg1NjZkNmNkOWI0MDAiLCJwcm9kdWN0IjoiZnJlZSIsInVzZXJUeXBlIjoiQXJ0aXN0IiwiaWF0IjoxNTg2MDI2NjAyLCJleHAiOjQ3MzI1MTMwMDJ9.ztEjNCgbkyJ2-9WB6ojwLgDfhWsZ-ZGJVFUB8dYMz8s");
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
                                recyclerAdapter = new adapterSearch(getActivity(), searchList);
                                //change the background
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
                searchList=null;

            }

            /**
             *
             * @param s --> current Editable in edit text
             */
            @Override
            public void afterTextChanged(Editable s)
            {
                //auto complete with first album index
               // s.append(searchList.getAlbum().get(0).getName());
                if(s.length()==0)
                    searchList=null;

            }
        });
                return view;

    }

    public void loadSearchFragment(String type)
    {
        Fragment newFragment;
        FragmentTransaction transaction;
        Bundle args=new Bundle();
        if(type=="album")
        {
            newFragment = new see_all_albums_Fragment();
            args.putString("DATA_RECIEVE_Album",word);
            newFragment.setArguments(args);
             transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_fragment, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if(type=="artist")
        {
            newFragment = new see_all_artist_Fragment();
            args.putString("DATA_RECIEVE_Artist",word);
            newFragment.setArguments(args);
            transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_fragment, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if(type=="song")
        {
            newFragment = new see_all_song_Fragment();
            args.putString("DATA_RECIEVE_Song",word);
            newFragment.setArguments(args);
            transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_fragment, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if(type=="playlist")
        {
            newFragment = new see_all_playlist_Fragment();
            args.putString("DATA_RECIEVE_Playlist",word);
            newFragment.setArguments(args);
            transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_fragment, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }


    }

}

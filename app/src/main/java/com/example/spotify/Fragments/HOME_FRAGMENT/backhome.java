package com.example.spotify.Fragments.HOME_FRAGMENT;


import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotify.Activities.MainActivity;
import com.example.spotify.Adapters.adapterNewreleases;
import com.example.spotify.Adapters.adapterPopularAlbum;
import com.example.spotify.Adapters.adapterPopularArtist;
import com.example.spotify.Adapters.adapterPopularPlaylist;
import com.example.spotify.Interfaces.backinterfaces;
import com.example.spotify.BackClasses.Backclasses.backnewrelease.Newreleases;
import com.example.spotify.BackClasses.Backclasses.backpopularalbum.PopularAlbum;
import com.example.spotify.BackClasses.Backclasses.backpopularartist.PopularArtist;
import com.example.spotify.BackClasses.Backclasses.backpopularplaylist.PopularPlaylist;
import com.example.spotify.Fragments.SETTING_FRAGMENT.settingFragment;
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
public class backhome extends Fragment implements LifecycleOwner
{
    Newreleases NewReleaseList;
    PopularAlbum popularAlbumList;
    PopularPlaylist popularPlaylist;
    PopularArtist popularArtistlist;
    RecyclerView recyclerView,
            recyclerView1,
            recyclerView2,
            recyclerView3;
    adapterNewreleases recyclerAdapter;
    adapterPopularArtist recyclerAdapterArtist;
    adapterPopularAlbum recyclerAdapterAlbum;
    adapterPopularPlaylist recyclerAdapterPlaylist;
    private TextView textViewResult;
    Toolbar toolbar;

    /**
     *
     * @param inflater -->layout for this fragment
     * @param container -->ViewGroup that contain layout of the fragment(linear layout)
     * @param savedInstanceState -->saved object needed before calling the fragment (type of storage)
     *
     * @return -->view of this fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_back_home, container, false);
        final TextView textView = view.findViewById(R.id.text_home);
        ////*****************************toolbar************************************////
        toolbar = (Toolbar)view.findViewById(R.id.toolbar); // get the reference of Toolbar
        toolbar.setTitle(""); // set Title for Toolbar
        /**
         * a listener for change in setting button
         */
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener()
        {
            /**
             *
             * @param item-->item in toolbar
             * @return true if fragment calling succeeded and false if there is an error
             */
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                int id = item.getItemId();
                if (id == R.id.action_settings)
                {
                    //go to setting fragments
                    loadFragmentSetting(new settingFragment());
                }
                return true;

            }
        });


        ////*******************************RecyclerView of NewReleaseList***********************////
        NewReleaseList = new Newreleases();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle);
        LinearLayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        ////*******************************RecyclerView of popularAlbumList***********************////
        popularAlbumList=new PopularAlbum();
        recyclerView1 = (RecyclerView) view.findViewById(R.id.recyclealbum);
        LinearLayoutManager layoutManager1;
        layoutManager1 = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView1.setLayoutManager(layoutManager1);
        ////*******************************RecyclerView of popularPlaylist***********************////
        popularPlaylist=new PopularPlaylist();
        recyclerView2 = (RecyclerView) view.findViewById(R.id.recycleplaylist);
        LinearLayoutManager layoutManager2;
        layoutManager2 = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView2.setLayoutManager(layoutManager2);
        ////*******************************RecyclerView of popularArtistlist***********************////
        popularArtistlist=new PopularArtist();
        recyclerView3 = (RecyclerView) view.findViewById(R.id.recycleartist);
        LinearLayoutManager layoutManager3;
        layoutManager3 = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView3.setLayoutManager(layoutManager3);
        //calling the retrofit function
        SetRetrofit();
        setRetrofitralbum();
        setRetrofitartist();
        setRetrofitplaylist();
        ////*******************************To check the state***********************////
        textViewResult = view.findViewById(R.id.Popular_new_releases);
        return view;

    }

    ////*******************************Retrofit****************************////
    private void SetRetrofit()
    {

        Retrofit retrofit = com.example.spotify.Interfaces.Retrofit.getInstance().getRetrofit();
        backinterfaces apiService = retrofit.create(backinterfaces.class);
        Call<Newreleases> call = apiService.getNewRelease();
        call.enqueue(new Callback<Newreleases>()
        {
            /**
             *
             * @param call --> interface request
             * @param response --> interface response
             * called when every changed requests and set the data
             */
            @Override
            public void onResponse(Call<Newreleases> call, Response<Newreleases> response)
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
                NewReleaseList = response.body();
                //error in GET request url
                if (response.body() == null)
                    textViewResult.setText("responce body = null");
                //error in binding interface
                else if (NewReleaseList == null)
                    textViewResult.setText(response.body().toString() + " NewReleaseList = null");
                //Successful
                else
                    {
                    Log.d("TAG", "Response = " + NewReleaseList);
                    recyclerAdapter = new adapterNewreleases(getActivity(), NewReleaseList.getAlbums());
                    recyclerAdapterAlbum=new adapterPopularAlbum(getActivity(),popularAlbumList.getAlbums());
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
            public void onFailure(Call<Newreleases> call, Throwable t)
            {
                textViewResult.setText(t.getMessage() + "failed");
            }
        });
    }

    ////*******************************set Retrofitr album****************************////
    private void setRetrofitralbum()

    {
    Retrofit retrofit = com.example.spotify.Interfaces.Retrofit.getInstance().getRetrofit();
    backinterfaces apiService = retrofit.create(backinterfaces.class);
    Call<PopularAlbum> call = apiService.getPopularAlbum();
    call.enqueue(new Callback<PopularAlbum>()
    {
        /**
         *
         * @param call --> interface request
         * @param response --> interface response
         * called when every changed requests and set the data
         */
        @Override
        public void onResponse(Call<PopularAlbum> call, Response<PopularAlbum> response)
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
            popularAlbumList = response.body();
            //error in GET request url
            if (response.body() == null)
                textViewResult.setText("responce body = null");
            //error in binding interface
            else if (popularAlbumList == null)
                textViewResult.setText(response.body().toString() + " popularAlbumList = null");
            //Successful
            else
                {
                Log.d("TAG", "Response = " + popularAlbumList);
                recyclerAdapterAlbum=new adapterPopularAlbum(getActivity(),popularAlbumList.getAlbums());
                recyclerView1.setAdapter(recyclerAdapterAlbum);
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
        public void onFailure(Call<PopularAlbum> call, Throwable t)
        {
            textViewResult.setText(t.getMessage() + "failed");

        }

    });


}
    ////*******************************set Retrofitr playlist****************************////
    private void setRetrofitplaylist()
    {
        Retrofit retrofit = com.example.spotify.Interfaces.Retrofit.getInstance().getRetrofit();
        backinterfaces apiService = retrofit.create(backinterfaces.class);
        Call<PopularPlaylist> call = apiService.getPopularPlaylist();
        call.enqueue(new Callback<PopularPlaylist>()
        {
            /**
             *
             * @param call --> interface request
             * @param response --> interface response
             * called when every changed requests and set the data
             */
            @Override
            public void onResponse(Call<PopularPlaylist> call, Response<PopularPlaylist> response)
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
                popularPlaylist = response.body();
                //error in GET request url
                if (response.body() == null)
                    textViewResult.setText("responce body = null");
                //error in binding interface
                else if (popularPlaylist == null)
                    textViewResult.setText(response.body().toString() + " popularPlaylist = null");
                //Successful
                else
                    {
                    Log.d("TAG", "Response = " + popularPlaylist);
                    recyclerAdapterPlaylist=new adapterPopularPlaylist(getActivity(),popularPlaylist.getPlaylists());
                    recyclerView2.setAdapter(recyclerAdapterPlaylist);
                    recyclerView2.setHasFixedSize(true);
                    }

            }
            /**
             *
             * @param call -->interface request
             * @param t -->type of error of the request
             * called when every errored requests and set its type
             */
            @Override
            public void onFailure(Call<PopularPlaylist> call, Throwable t)
            {
                textViewResult.setText(t.getMessage() + "failed");

            }


        });


    }

    ////*******************************set Retrofitr artist****************************////
    private void setRetrofitartist()
    {

        Retrofit retrofit = com.example.spotify.Interfaces.Retrofit.getInstance().getRetrofit();
        backinterfaces apiService = retrofit.create(backinterfaces.class);
        Call<PopularArtist> call = apiService.getPopularArtist();
        call.enqueue(new Callback<PopularArtist>()
        {
            /**
             *
             * @param call --> interface request
             * @param response --> interface response
             * called when every changed requests and set the data
             */
            @Override
            public void onResponse(Call<PopularArtist> call, Response<PopularArtist> response)
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
                popularArtistlist = response.body();
                //error in GET request url
                if (response.body() == null)
                    textViewResult.setText("responce body = null");
                //error in binding interface
                else if (popularArtistlist == null)
                    textViewResult.setText(response.body().toString() + " track = null");
                //Successful
                else
                    {
                    Log.d("TAG", "Response = " + popularArtistlist);
                    recyclerAdapterArtist=new adapterPopularArtist(getActivity(),popularArtistlist.getArtists());
                    recyclerView3.setAdapter(recyclerAdapterArtist);
                    recyclerView3.setHasFixedSize(true);
                    }

            }
            /**
             *
             * @param call -->interface request
             * @param t -->type of error of the request
             * called when every errored requests and set its type
             */
            @Override
            public void onFailure(Call<PopularArtist> call, Throwable t)
            {
                textViewResult.setText(t.getMessage() + "failed");

            }


        });


    }

    /*
    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return null;
    }

     */


    /**
     *
     * @param fragment -->it is fragment that i go to
     * transfer between the fragments
     */
    public void loadFragmentSetting(Fragment fragment)
    {
        // create a FragmentManager
        FragmentManager fm = getActivity().getSupportFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.frame_fragment,fragment);
        // save the changes
        fragmentTransaction.commit();

    }

}
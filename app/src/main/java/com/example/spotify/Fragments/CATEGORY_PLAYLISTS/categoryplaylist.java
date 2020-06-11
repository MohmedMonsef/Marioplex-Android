package com.example.spotify.Fragments.CATEGORY_PLAYLISTS;

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
import com.example.spotify.Adapters.adapterCategoryPlaylists;
import com.example.spotify.BackClasses.Backclasses.backcategoryplaylist.CategoryPlaylist;
import com.example.spotify.Fragments.SEARCH_FRAGMENT.searchfragment;
import com.example.spotify.Interfaces.backinterfaces;
import com.example.spotify.R;
import com.example.spotify.login.user;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class categoryplaylist extends Fragment implements LifecycleOwner
{
    CategoryPlaylist Categoryplaylist,Categoryplaylist1;
    RecyclerView recyclerView,recyclerView1;
    adapterCategoryPlaylists recyclerAdapter,recyclerAdapter1;
    private TextView textViewResult;
    String categoryid,categoryname;
    ImageView back_button_to_category_playlist;

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

        categoryid = getArguments().getString("CategoryID");
        categoryname=getArguments().getString("CategoryName");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categoryplaylist, container, false);
        textViewResult = view.findViewById(R.id.textcategorplaylist);
        textViewResult.setText(categoryname);
        ////*******************************RecyclerView***********************////
        Categoryplaylist = new CategoryPlaylist();
        Categoryplaylist1 = new CategoryPlaylist();

        recyclerView = (RecyclerView) view.findViewById(R.id.recycleCategoryPlaylist1);
        LinearLayoutManager layoutManager,layoutManager1;
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView1 = (RecyclerView) view.findViewById(R.id.recycleCategoryPlaylist2);
        layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView1.setLayoutManager(layoutManager1);
        back_button_to_category_playlist=view.findViewById(R.id.back_button_to_category_playlist);
        back_button_to_category_playlist.setOnClickListener(new View.OnClickListener()
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

        SetRetrofit(categoryid);
        SetRetrofit1(categoryid);
        return view;

    }

    private void SetRetrofit1(String Id)
    {

        Retrofit retrofit = com.example.spotify.Interfaces.Retrofit.getInstance().getRetrofit();
        backinterfaces apiService = retrofit.create(backinterfaces.class);
        Call<CategoryPlaylist> call = apiService.getCategoryPlaylist(Id,20,21, user.getToken());
        call.enqueue(new Callback<CategoryPlaylist>()
        {

            /**
             *
             * @param call --> interface request
             * @param response --> interface response
             * called when every changed requests and set the data
             */
            @Override
            public void onResponse(Call<CategoryPlaylist> call, Response<CategoryPlaylist> response)
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
                Categoryplaylist1 = response.body();
                //error in GET request url
                if (response.body() == null)
                    textViewResult.setText("responce body = null");
                    //error in binding interface
                else if (Categoryplaylist1 == null)
                    textViewResult.setText(response.body().toString() + " CategoriesList = null");
                    //Successful
                else
                {
                    Log.d("TAG", "Response = " + Categoryplaylist1);
                    recyclerAdapter1 = new adapterCategoryPlaylists(getActivity(), Categoryplaylist1);
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
            public void onFailure(Call<CategoryPlaylist> call, Throwable t)
            {
                textViewResult.setText("Failed to connect to server");
            }
        });


    }

    ////*******************************Retrofit****************************////
    /**
     * Set the retrofit function
     */
    private void SetRetrofit(String Id)
    {

        Retrofit retrofit = com.example.spotify.Interfaces.Retrofit.getInstance().getRetrofit();
        backinterfaces apiService = retrofit.create(backinterfaces.class);
        Call<CategoryPlaylist> call = apiService.getCategoryPlaylist(Id,20,0, user.getToken());
        call.enqueue(new Callback<CategoryPlaylist>()
        {

            /**
             *
             * @param call --> interface request
             * @param response --> interface response
             * called when every changed requests and set the data
             */
            @Override
            public void onResponse(Call<CategoryPlaylist> call, Response<CategoryPlaylist> response)
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
                Categoryplaylist = response.body();
                //error in GET request url
                if (response.body() == null)
                    textViewResult.setText("responce body = null");
                    //error in binding interface
                else if (Categoryplaylist == null)
                    textViewResult.setText(response.body().toString() + " CategoriesList = null");
                    //Successful
                else
                {
                    Log.d("TAG", "Response = " + Categoryplaylist);
                    recyclerAdapter = new adapterCategoryPlaylists(getActivity(), Categoryplaylist);
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
            public void onFailure(Call<CategoryPlaylist> call, Throwable t)
            {
                textViewResult.setText(t.getMessage() + "failed");
            }
        });


    }

}

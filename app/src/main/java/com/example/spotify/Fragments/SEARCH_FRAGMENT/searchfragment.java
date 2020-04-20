package com.example.spotify.Fragments.SEARCH_FRAGMENT;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotify.Activities.MainActivity;
import com.example.spotify.Adapters.adapterCategories;
import com.example.spotify.Fragments.SEARCH_LIST_FRAGMENT.searchListfragment;
import com.example.spotify.Interfaces.backinterfaces;
import com.example.spotify.BackClasses.Backclasses.backcategory.Category;
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
public class searchfragment extends Fragment implements LifecycleOwner
{
    Category CategoriesList;
    RecyclerView recyclerView;
    adapterCategories recyclerAdapter;
    private TextView textViewResult;
    ImageView searchimg;
    private viewmodelSearch searchViewmodel;

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
        View view = inflater.inflate(R.layout.fragment_searchfragment, container, false);
        searchViewmodel = ViewModelProviders.of((MainActivity) getActivity()).get(viewmodelSearch.class);
        final TextView textView = view.findViewById(R.id.text_setting);
        searchimg = view.findViewById(R.id.searchImg);
        textViewResult = view.findViewById(R.id.textcategor);
        ////*******************************RecyclerView***********************////
        CategoriesList = new Category();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleCategory);
        LinearLayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        SetRetrofit();
        /**
         * a listener for change in searchimage
         */
        searchimg.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param view --> current layout
             * go to next fragments
             */
            @Override
            public void onClick(View view)
            {
                // Create new fragment and transaction
                Fragment newFragment = new searchListfragment();
                // consider using Java coding conventions (upper first char class names!!!)
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                // Replace whatever is in the fragment_container view with this fragment,
                transaction.replace(R.id.frame_fragment, newFragment);
                // and add the transaction to the back stack
                transaction.addToBackStack(null);
                // Commit the transaction
                transaction.commit();
            }

        });

        return view;

    }
    ////*******************************Retrofit****************************////
    /**
     * Set the retrofit function
     */
    private void SetRetrofit()
    {

        Retrofit retrofit = com.example.spotify.Interfaces.Retrofit.getInstance().getRetrofit();
        backinterfaces apiService = retrofit.create(backinterfaces.class);
        Call<Category> call = apiService.getCategories("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ZTgwYzZhZjE0Yzg1NjZkNmNkOWI0MDAiLCJwcm9kdWN0IjoiZnJlZSIsInVzZXJUeXBlIjoiQXJ0aXN0IiwiaWF0IjoxNTg2MDI2NjAyLCJleHAiOjQ3MzI1MTMwMDJ9.ztEjNCgbkyJ2-9WB6ojwLgDfhWsZ-ZGJVFUB8dYMz8s");
        call.enqueue(new Callback<Category>()
        {

            /**
             *
             * @param call --> interface request
             * @param response --> interface response
             * called when every changed requests and set the data
             */
            @Override
            public void onResponse(Call<Category> call, Response<Category> response)
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
                CategoriesList = response.body();
                //error in GET request url
                if (response.body() == null)
                    textViewResult.setText("responce body = null");
                //error in binding interface
                else if (CategoriesList == null)
                    textViewResult.setText(response.body().toString() + " CategoriesList = null");
                //Successful
                else
                    {
                    Log.d("TAG", "Response = " + CategoriesList);
                    recyclerAdapter = new adapterCategories(getActivity(), CategoriesList.getCategories());
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
            public void onFailure(Call<Category> call, Throwable t)
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


}

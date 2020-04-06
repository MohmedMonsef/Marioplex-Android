package com.example.spotify.BackClasses.BackFragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotify.Activities.MainActivity;
import com.example.spotify.BackClasses.BackAdapter.adapterCategories;
import com.example.spotify.BackClasses.BackInterfaces.backinterfaces;
import com.example.spotify.BackClasses.Backclasses.backcategory.Category;
import com.example.spotify.Fragments.SEARCH_FRAGMENT.viewmodelSearch;
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
public class searchfragment extends Fragment implements LifecycleOwner {
    Category CategoriesList;
    RecyclerView recyclerView;
    adapterCategories recyclerAdapter;
    private TextView textViewResult;
    ImageView searchimg;
    private viewmodelSearch searchViewmodel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


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

        searchimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create new fragment and transaction
                Fragment newFragment = new searchListfragment();
                // consider using Java coding conventions (upper first char class names!!!)
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.frame_fragment, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }

        });


        return view;

    }


    ////*******************************Retrofit****************************////
    private void SetRetrofit() {
        Retrofit retrofit = com.example.spotify.Interfaces.Retrofit.getInstance().getRetrofit();
        backinterfaces apiService = retrofit.create(backinterfaces.class);
        Call<Category> call = apiService.getCategories("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ZTgwYzZhZjE0Yzg1NjZkNmNkOWI0MDAiLCJwcm9kdWN0IjoiZnJlZSIsInVzZXJUeXBlIjoiQXJ0aXN0IiwiaWF0IjoxNTg2MDI2NjAyLCJleHAiOjQ3MzI1MTMwMDJ9.ztEjNCgbkyJ2-9WB6ojwLgDfhWsZ-ZGJVFUB8dYMz8s");
        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.code() == 401)
                    textViewResult.setText(response.errorBody().toString() + "401");
                else if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }
                CategoriesList = response.body();
                if (response.body() == null)
                    textViewResult.setText("responce body = null");
                else if (CategoriesList == null)
                    textViewResult.setText(response.body().toString() + " track = null");
                else {
                    Log.d("TAG", "Response = " + CategoriesList);
                    recyclerAdapter = new adapterCategories(getActivity(), CategoriesList.getCategories());
                    recyclerView.setAdapter(recyclerAdapter);
                    recyclerView.setHasFixedSize(true);
                }

            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                textViewResult.setText(t.getMessage() + "failed");
            }
        });


    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return null;
    }


}

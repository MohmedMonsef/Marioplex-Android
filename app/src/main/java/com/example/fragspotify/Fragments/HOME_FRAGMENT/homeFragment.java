package com.example.fragspotify.Fragments.HOME_FRAGMENT;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import androidx.annotation.NonNull;

import android.app.Fragment;

//import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fragspotify.Activities.MainActivity;
import com.example.fragspotify.Adapters.adapterNewreleases;
import com.example.fragspotify.Fragments.SETTING_FRAGMENT.settingFragment;
import com.example.fragspotify.Interfaces.classinterface;
import com.example.fragspotify.R;
import com.example.fragspotify.SpotifyClasses.backnewrelease.Newreleases;

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
public class homeFragment extends Fragment implements LifecycleOwner {
    Newreleases NewReleaseList;
    RecyclerView recyclerView;
    adapterNewreleases recyclerAdapter;
    private TextView textViewResult;
    Toolbar toolbar;
    private viewmodelHome homeViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel =ViewModelProviders.of((MainActivity) getActivity()).get(viewmodelHome.class);
        final TextView textView = view.findViewById(R.id.text_home);
        ////*****************************toolbar************************************////
        toolbar = (Toolbar)view.findViewById(R.id.toolbar); // get the reference of Toolbar
        toolbar.setTitle(""); // set Title for Toolbar
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.action_settings)
                {
                    loadFragmentSetting(new settingFragment());
                }
                return true;

            }
        });


        ////*******************************RecyclerView***********************////
        NewReleaseList = new Newreleases();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle);
        LinearLayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        SetRetrofit();
        ////*******************************To check the state***********************////
        textViewResult = view.findViewById(R.id.Popular_new_releases_back);

        return view;

    }

    ////*******************************Retrofit****************************////
    private void SetRetrofit()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.7:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        classinterface apiService = retrofit.create(classinterface.class);
        Call<Newreleases> call = apiService.getNewRelease();
        call.enqueue(new Callback<Newreleases>() {
            @Override
            public void onResponse(Call<Newreleases> call, Response<Newreleases> response) {
                if (response.code() == 401)
                    textViewResult.setText(response.errorBody().toString() + "401");
                else if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }
                NewReleaseList = response.body();
                if (response.body() == null)
                    textViewResult.setText("responce body = null");
                else if (NewReleaseList == null)
                    textViewResult.setText(response.body().toString() + " track = null");
                else {
                    Log.d("TAG", "Response = " + NewReleaseList);
                    recyclerAdapter = new adapterNewreleases(getActivity(), NewReleaseList.getAlbums());
                    recyclerView.setAdapter(recyclerAdapter);
                    recyclerView.setHasFixedSize(true);
                }

            }

            @Override
            public void onFailure(Call<Newreleases> call, Throwable t) {
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
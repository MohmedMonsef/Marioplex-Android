package com.example.fragspotify.Fragments.SEARCH_FRAGMENT;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fragspotify.Activities.MainActivity;
import com.example.fragspotify.Adapters.Adapter;
import com.example.fragspotify.Interfaces.classinterface;
import com.example.fragspotify.R;
import com.example.fragspotify.SpotifyClasses.NewRelease;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;
import static android.widget.Toast.makeText;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class searchFragment extends Fragment implements LifecycleOwner {

    private viewmodelSearch searchViewmodel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchViewmodel =ViewModelProviders.of((MainActivity) getActivity()).get(viewmodelSearch.class);
        final TextView textView = view.findViewById(R.id.text_setting);
               return view;

    }




    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return null;
    }
}

package com.example.spotify.Fragments.SEARCH_FRAGMENT;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class viewmodelSearch extends ViewModel
{

    private MutableLiveData<String> mText;

    public viewmodelSearch()
    {
        mText = new MutableLiveData<>();
        mText.setValue("This is search fragment");
    }

    public LiveData<String> getText()
    {
        return mText;
    }
}

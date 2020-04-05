package com.example.spotify.Fragments.HOME_FRAGMENT;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class viewmodelHome extends ViewModel
{

    private MutableLiveData<String> mText;

    public viewmodelHome()
    {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText()
    {
        return mText;
    }
}
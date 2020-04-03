package com.example.spotify.Fragments.PREMIUM_FRAGMENT;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class viewmodelPremium extends ViewModel
{

    private MutableLiveData<String> mText;

    public viewmodelPremium()
    {
        mText = new MutableLiveData<>();
        mText.setValue("This is premium fragment");
    }

    public LiveData<String> getText()
    {
        return mText;
    }
}
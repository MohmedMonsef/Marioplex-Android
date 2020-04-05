package com.example.spotify.Fragments.LIBRARY_FRAGMENT;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class viewmodelLibrary extends ViewModel
{

    private MutableLiveData<String> mText;

    public viewmodelLibrary()
    {
        mText = new MutableLiveData<>();
        mText.setValue("This is library fragment");
    }

    public LiveData<String> getText()
    {
        return mText;
    }
}

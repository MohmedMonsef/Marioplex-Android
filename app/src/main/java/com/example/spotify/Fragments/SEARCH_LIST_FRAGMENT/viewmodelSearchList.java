package com.example.spotify.Fragments.SEARCH_LIST_FRAGMENT;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
public class viewmodelSearchList extends ViewModel
{

    private MutableLiveData<String> mText;

    public viewmodelSearchList()
    {
        mText = new MutableLiveData<>();
        mText.setValue("This is search fragment");
    }

    public LiveData<String> getText()
    {
        return mText;
    }
}
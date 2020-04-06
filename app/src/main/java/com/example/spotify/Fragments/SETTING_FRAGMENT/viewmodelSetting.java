package com.example.spotify.Fragments.SETTING_FRAGMENT;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class viewmodelSetting extends ViewModel
{

    private MutableLiveData<String> mText;

    public viewmodelSetting()
    {
        mText = new MutableLiveData<>();
        mText.setValue("This is setting fragment");
    }

    public LiveData<String> getText()
    {
        return mText;
    }
}


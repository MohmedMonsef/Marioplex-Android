package com.example.spotify.Fragments.NEW_RELEASE_FRAHMENT;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
public class viewmodelNewRelease extends ViewModel
{

    private MutableLiveData<String> mText;

    public viewmodelNewRelease()
    {
        mText = new MutableLiveData<>();
        mText.setValue("This is library fragment");
    }

    public LiveData<String> getText()
    {
        return mText;
    }
}
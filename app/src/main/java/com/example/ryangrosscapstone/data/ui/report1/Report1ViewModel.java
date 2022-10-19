package com.example.ryangrosscapstone.data.ui.report1;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Report1ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Report1ViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
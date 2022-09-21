package com.example.ryangrosscapstone.data.ui.enterData;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EnterDataViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public EnterDataViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
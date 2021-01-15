package com.tobin.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tobin.lib_resource.lifecycle.BaseViewModel;

public class HomeViewModel extends BaseViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
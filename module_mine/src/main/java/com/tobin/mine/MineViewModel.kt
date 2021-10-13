package com.tobin.mine;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tobin.lib_resource.lifecycle.BaseViewModel;

public class MineViewModel extends BaseViewModel {

    private MutableLiveData<String> mText;

    public MineViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is mine fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
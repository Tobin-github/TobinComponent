package com.tobin.mine

import androidx.databinding.ObservableField
import com.tobin.lib_resource.lifecycle.BaseViewModel
import androidx.lifecycle.MutableLiveData

class MineViewModel : BaseViewModel() {
    var isLogin = MutableLiveData<Boolean>()

    private val mText: ObservableField<String> = ObservableField()
    val text: ObservableField<String>
        get() = mText

    init {
        mText.set("This is mine fragment")
    }


}
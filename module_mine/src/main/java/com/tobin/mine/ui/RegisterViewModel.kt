package com.tobin.mine.ui

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

/**
 * Created by Tobin on 2021/10/11.
 * Email: 616041023@qq.com
 * Description: User.
 */
class RegisterViewModel : ViewModel() {
    var name = ObservableField<String>()

    var password = ObservableField<String>()
    var rePassword = ObservableField<String>()

    var loadingVisible = ObservableBoolean()
}
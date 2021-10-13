package com.tobin.mine.ui

import android.os.Bundle
import com.tobin.lib_resource.mvvm.base.BaseActivity
import com.tobin.lib_resource.mvvm.bingding.DataBindingConfig
import com.tobin.mine.BR
import com.tobin.mine.R

class UserActivity : BaseActivity() {
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, RegisterFragment.newInstance())
                .commitNow()
        }
    }

    override fun initViewModel() {
        viewModel = getActivityScopeViewModel(UserViewModel::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.mine_activity_user, BR.vm, viewModel)
    }
}
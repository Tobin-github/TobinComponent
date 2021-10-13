package com.tobin.mine

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.tobin.lib_core.session.MmkvSessionManager
import com.tobin.lib_resource.arouter.RouterHub
import com.tobin.lib_resource.mvvm.base.BaseFragment
import com.tobin.lib_resource.mvvm.bingding.DataBindingConfig
import com.tobin.mine.bean.WanUserBean
import com.tobin.mine.databinding.MineFragmentMineBinding
import com.tobin.mine.ui.UserActivity

@Route(path = RouterHub.APP_MINE_FRAGMENT)
class MineFragment : BaseFragment() {
    private lateinit var binding: MineFragmentMineBinding
    private lateinit var mineViewModel: MineViewModel

    companion object {
        fun newInstance() = MineFragment()
    }

    private fun initData() {
        val user = MmkvSessionManager.get().getUser<WanUserBean>(WanUserBean::class.java)
        mineViewModel.isLogin.postValue(user != null)
        mineViewModel.text.set(user?.toString())

    }

    override fun onResume() {
        super.onResume()
        initData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViewModel() {
        mineViewModel = getFragmentScopeViewModel(MineViewModel::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.mine_fragment_mine, BR.vm, mineViewModel)
            .addBindingParam(BR.click, ClickProxy())
    }

    inner class ClickProxy {
        fun login() {
            startActivity(Intent(context, UserActivity::class.java))
        }
    }
}
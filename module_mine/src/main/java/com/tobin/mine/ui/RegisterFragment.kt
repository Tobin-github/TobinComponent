package com.tobin.mine.ui

import android.text.TextUtils
import com.tobin.lib_core.base.Box
import com.tobin.lib_core.http.exception.ApiException
import com.tobin.lib_core.http.observer.CommonObserver
import com.tobin.lib_core.session.MmkvSessionManager
import com.tobin.lib_core.utils.RxUtils
import com.tobin.lib_resource.mvvm.base.BaseFragment
import com.tobin.lib_resource.mvvm.bingding.DataBindingConfig
import com.tobin.lib_resource.utils.ToastUtils
import com.tobin.mine.BR
import com.tobin.mine.R
import com.tobin.mine.api.WanUserApi
import com.tobin.mine.bean.WanUserBean
import timber.log.Timber

/**
 * Created by Tobin on 2021/10/11.
 * Email: 616041023@qq.com
 * Description:
 */
class RegisterFragment : BaseFragment() {

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private lateinit var viewModel: RegisterViewModel


    override fun initViewModel() {
        viewModel = getFragmentScopeViewModel(RegisterViewModel::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.mine_fragment_register, BR.vm, viewModel)
            .addBindingParam(BR.click, ClickProxy())
    }

    inner class ClickProxy {
        fun register() {
            val userName = viewModel.name.get()
            val password = viewModel.password.get()
            val rePassword = viewModel.rePassword.get()

            if (TextUtils.isEmpty(password)
                || TextUtils.isEmpty(userName)
                || TextUtils.isEmpty(rePassword)) {
                ToastUtils.makeText("用户名密码不能为空")
                return
            }

            if (!password.equals(rePassword)) {
                ToastUtils.makeText("两次输入的密码不一致")
                return
            }
            viewModel.loadingVisible.set(true)
            Box.getRetrofit(WanUserApi::class.java)
                .register(userName!!, password!!, rePassword!!)
                .compose(RxUtils.httpResponseTransformer())
                .subscribe(object : CommonObserver<WanUserBean>() {
                    override fun onNext(o: WanUserBean) {
                        Timber.tag("Tobin").d("result --> $o")
                        MmkvSessionManager.get().setUser(o)
                        activity?.finish()
                    }

                    override fun onError(ex: ApiException?) {
                        super.onError(ex)
                        ToastUtils.makeText(ex?.message)

                    }

                    override fun onNetError() {
                        super.onNetError()
                        ToastUtils.makeText("当前无网络，请检查网络情况!")
                    }

                    override fun onComplete() {
                        viewModel.loadingVisible.set(false)
                    }
                })
        }
    }

    fun login(userName: String, password: String) {
        Box.getRetrofit(WanUserApi::class.java)
            .login(userName, password)
            .compose(RxUtils.httpResponseTransformer())
            .subscribe(object : CommonObserver<Any>() {
                override fun onNext(o: Any) {
                    Timber.tag("Tobin").d("result --> $o")
                }
            })
    }

}

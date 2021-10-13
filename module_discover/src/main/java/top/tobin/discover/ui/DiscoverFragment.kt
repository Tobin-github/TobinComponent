package top.tobin.discover.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.tobin.lib_resource.arouter.RouterHub
import com.tobin.lib_resource.mvvm.base.BaseFragment
import com.tobin.lib_resource.mvvm.bingding.DataBindingConfig
import com.tobin.recipe.ui.main.RecipeActivity
import top.tobin.discover.R
import top.tobin.discover.BR
import top.tobin.discover.databinding.DiscoverFragmentBinding

/**
 * Created by Tobin on 2021/4/20
 * Email: 616041023@qq.com
 * Description:
 */

@Route(path = RouterHub.APP_DISCOVER_FRAGMENT)
class DiscoverFragment : BaseFragment() {

    private lateinit var mBinding: DiscoverFragmentBinding

    companion object {
        fun newInstance() = DiscoverFragment()
    }

    private lateinit var viewModel: DiscoverViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = getBinding() as DiscoverFragmentBinding
    }

    override fun initViewModel() {
        viewModel = getFragmentScopeViewModel(DiscoverViewModel::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.discover_fragment, BR.vm, viewModel)
            .addBindingParam(BR.click, ClickProxy())
    }

    inner class ClickProxy {
        fun goRecipe() {
            startActivity(Intent(context, RecipeActivity::class.java))
        }
    }
}
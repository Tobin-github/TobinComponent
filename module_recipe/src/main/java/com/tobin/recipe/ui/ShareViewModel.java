package com.tobin.recipe.ui;

import androidx.lifecycle.MutableLiveData;

import com.tobin.lib_resource.lifecycle.BaseViewModel;
import com.tobin.recipe.bean.RecipesBean;

/**
 * Created by Tobin on 2021/4/21
 * Email: lijunb@szlanyou.com
 * Description:
 */
public class ShareViewModel extends BaseViewModel {

    public final MutableLiveData<String> classid = new MutableLiveData<>();
    public final MutableLiveData<RecipesBean> recipesBeanMutableLiveData = new MutableLiveData<>();


}

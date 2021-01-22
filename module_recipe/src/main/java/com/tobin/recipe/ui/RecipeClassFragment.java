package com.tobin.recipe.ui;

import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.gyf.immersionbar.ImmersionBar;
import com.tobin.lib_resource.arouter.RouterHub;
import com.tobin.lib_resource.mvvm.base.BaseFragment;
import com.tobin.recipe.R;
import com.tobin.recipe.bean.RecipesClassBean;
import com.tobin.recipe.databinding.RecipeFragmentClassBinding;
import com.tobin.recipe.adapter.FirstNode;
import com.tobin.recipe.adapter.RecipeClassLeftAdapter;
import com.tobin.recipe.adapter.RecipeClassNodeAdapter;
import com.tobin.recipe.adapter.SecondNode;
import com.tobin.recipe.widgets.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

@Route(path = RouterHub.RECIPE_RECIPE_FRAGMENT)
public class RecipeClassFragment extends BaseFragment<RecipeClassViewModel, RecipeFragmentClassBinding> {
    private RecipesClassBean recipesClassBean;

    @Override
    protected int onCreate() {
        return R.layout.recipe_fragment_class;
    }

    @Override
    protected void initView(View view) {
        initLeftRecyclerView();
        initRightRecyclerView();
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarColor(R.color.colorPrimaryDark)
                .statusBarDarkFont(true)
                .init();
    }

    @Override
    protected void initData() {
        showLoading();
        viewModel.getRecipesClassLiveData().observe(this, recipesClassBean -> {
            Timber.tag("Tobin").i("RecipeFragment initData");
            if (recipesClassBean != null) {
                this.recipesClassBean = recipesClassBean;
                nodeAdapter.setList(getEntity(recipesClassBean));
                leftAdapter.setList(recipesClassBean.getResult());
            }
            showSuccess();
        });
    }

    @Override
    protected boolean isLazyLoad() {
        return true;
    }

    @Override
    protected RecipeClassViewModel initViewModel() {
        return new ViewModelProvider(this).get(RecipeClassViewModel.class);
    }

    @Override
    protected void showError(Object obj) {
        Toast.makeText(requireContext(), obj.toString(), Toast.LENGTH_SHORT).show();
    }

    RecipeClassLeftAdapter leftAdapter;

    private void initLeftRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        dataBinding.rvSortLeft.setLayoutManager(layoutManager);
        dataBinding.rvSortLeft.addItemDecoration(new RecycleViewDivider(
                activity, LinearLayoutManager.HORIZONTAL, 2, android.R.color.white));
        leftAdapter = new RecipeClassLeftAdapter();
        dataBinding.rvSortLeft.setAdapter(leftAdapter);

        leftAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                int count = 0;
                for (int i = 0; i < position; i++) {
                    count += recipesClassBean.getResult().get(i).getList().size();
                }
                Timber.tag("Tobin").d("leftAdapter onItemClick count: " + count + " position: " + position);
                // dataBinding.rvSortRight.scrollToPosition(count + position);
                leftAdapter.setSelected(position);
                scrollToPosition(dataBinding.rvSortRight, count + position);
            }
        });
    }

    RecipeClassNodeAdapter nodeAdapter;

    private void initRightRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 3);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        dataBinding.rvSortRight.setLayoutManager(gridLayoutManager);
//        dataBinding.rvSortRight.addItemDecoration(new RecycleViewDivider(
//                activity, GridLayoutManager.VERTICAL, 10, android.R.color.holo_red_dark));
        nodeAdapter = new RecipeClassNodeAdapter();
        dataBinding.rvSortRight.setAdapter(nodeAdapter);
        dataBinding.rvSortRight.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                onScrollRightListScrolled();
            }


        });
    }

    private List<BaseNode> getEntity(RecipesClassBean recipesClassBean) {
        List<BaseNode> list = new ArrayList<>();
        for (RecipesClassBean.ResultBean resultBean : recipesClassBean.getResult()) {
            List<BaseNode> secondNodeList = new ArrayList<>();
            for (RecipesClassBean.ResultBean.ListBean listBean : resultBean.getList()) {
                SecondNode seNode = new SecondNode(listBean);
                secondNodeList.add(seNode);
            }
            FirstNode entity = new FirstNode(secondNodeList, resultBean);
            entity.setExpanded(true);
            list.add(entity);
        }
        return list;
    }

    public void scrollToPosition(RecyclerView recyclerView, int n) {
        LinearLayoutManager manager = ((LinearLayoutManager) recyclerView.getLayoutManager());
        if (manager != null)
            manager.scrollToPositionWithOffset(n, 0);
    }

    public void onScrollRightListScrolled(){
        LinearLayoutManager manager = ((LinearLayoutManager) dataBinding.rvSortRight.getLayoutManager());
        //获取右侧列表的第一个可见Item的position
        int topPosition = 0;
        if (manager != null){
            topPosition = manager.findFirstVisibleItemPosition();
        }
        if(topPosition == 0){
            leftAdapter.setSelected(topPosition);
        }else {
//            int currentPosition = recipesClassBean.getResult().get(topPosition).getList().get(topPosition).getParentid();
//            Timber.tag("Tobin").d("RightListScrolled getItemViewType: " + nodeAdapter.getItemViewType(topPosition));
//            Timber.tag("Tobin").d("RightListScrolled getItem: " + nodeAdapter.getItem(topPosition));
        }

    }
}
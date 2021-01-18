package com.tobin.lib_resource.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tobin.lib_resource.R;
import com.tobin.lib_resource.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 底部导航Tab
 */
public class NavigationTabBar extends LinearLayout {

    List<NavigationItem> navigationItemList = new ArrayList<>();
    private FragmentManager fragmentManager;
    private int idContainer;
    private int lastPosition = -1;

    public NavigationTabBar(Context context) {
        this(context, null);
    }

    public NavigationTabBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigationTabBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NavigationTabBar addNavigationItem(int selectId, int noSelectId, Fragment baseFragment, int fragmentTab, String title) {
        navigationItemList.add(new NavigationItem(selectId, noSelectId, baseFragment, fragmentTab, title));
        return this;
    }

//    public NavigationTabBar addNavigationItem(List<Fragment> fragmentList){
//        for (Fragment fragment: fragmentList){
//            navigationItemList.addAll(new NavigationItem(selectId, noSelectId, fragment, fragmentTab, title));
//        }
//        return this;
//    }

    public NavigationTabBar setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        return this;
    }

    public NavigationTabBar setIdContainer(int idContainer) {
        this.idContainer = idContainer;
        return this;
    }

    public NavigationTabBar init() {
        setOrientation(VERTICAL);

        View view = new View(getContext());
        view.setBackgroundColor(Color.parseColor("#DCDADB"));
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, 1);
        view.setLayoutParams(layoutParams);
        addView(view);

        LinearLayout tabLayout = new LinearLayout(getContext());
        layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        tabLayout.setLayoutParams(layoutParams);
        addView(tabLayout);

        for (int i = 0; i < navigationItemList.size(); i++) {
            NavigationItem navigationItem = navigationItemList.get(i);
            FrameLayout frameLayout = new FrameLayout(getContext());
            layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            layoutParams.weight = 1;
            frameLayout.setLayoutParams(layoutParams);

            LinearLayout linearLayout = new LinearLayout(getContext());
            layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setGravity(Gravity.CENTER);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setBackgroundColor(Color.parseColor("#E6FAFAFA"));
            linearLayout.addView(navigationItem.selectImageView);
            linearLayout.addView(navigationItem.noSelectImageView);
            linearLayout.addView(navigationItem.textView);

            linearLayout.setTag(navigationItem);
            linearLayout.setOnClickListener(v -> {
                NavigationItem currentNavigationItem = (NavigationItem) v.getTag();
                if (currentNavigationItem.onClickListener == null || currentNavigationItem.onClickListener.onClickListener()) {
                    setPosition(currentNavigationItem.fragmentTab);
                }
            });
            frameLayout.addView(linearLayout);

            frameLayout.addView(navigationItem.redpotView);

            tabLayout.addView(frameLayout);
        }
        setPosition(navigationItemList.get(0).fragmentTab);
        return this;
    }

    public void setPosition(int fragmentTab) {

        int position = -1;

        if (lastPosition == -1) {
            position = 0;
            lastPosition = position;
        } else {
            for (int i = 0; i < navigationItemList.size(); i++) {
                if (navigationItemList.get(i).fragmentTab == fragmentTab) {
                    position = i;
                    break;
                }
            }
            if (position == -1) {
                return;
            }
            if (position == lastPosition) {
                NavigationItem navigationItem = navigationItemList.get(position);
                if (navigationItem.onClickWhenThatIsIt != null) {
                    navigationItem.onClickWhenThatIsIt.onClickWhenThatIsIt();
                }
                return;
            }
        }

        NavigationItem navigationItem = navigationItemList.get(lastPosition);
        navigationItem.selectImageView.setVisibility(View.GONE);
        navigationItem.textView.setTextColor(Color.parseColor("#888888"));
        navigationItem.noSelectImageView.setVisibility(View.VISIBLE);

        Fragment baseFragmentWillHide = navigationItem.baseFragment;
        if (navigationItem.baseFragment.isAdded()) {
            baseFragmentWillHide.getView().setVisibility(View.GONE);
        }

        navigationItem = navigationItemList.get(position);
        navigationItem.noSelectImageView.setVisibility(View.GONE);
        navigationItem.textView.setTextColor(Color.parseColor("#C71444"));
        navigationItem.selectImageView.setVisibility(View.VISIBLE);

        baseFragmentWillHide.onHiddenChanged(true);

        Fragment baseFragmentWillShow = navigationItem.baseFragment;
        if (navigationItem.baseFragment.isAdded()) {
            baseFragmentWillShow.onHiddenChanged(false);
            baseFragmentWillShow.getView().setVisibility(View.VISIBLE);
        } else {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(idContainer, navigationItem.baseFragment);
            //不能使用commit,不然在别的activity调用changePager，会崩
            fragmentTransaction.commitAllowingStateLoss();
        }

        lastPosition = position;

    }

    public NavigationItem getNavigationItem(int fragmentTab) {
        for (int i = 0; i < navigationItemList.size(); i++) {
            NavigationItem navigationItem = navigationItemList.get(i);
            if (navigationItem.fragmentTab == fragmentTab) {
                return navigationItem;
            }
        }
        return null;
    }

    public class NavigationItem {

        private Fragment baseFragment;
        private int fragmentTab;
        private String title;
        private ImageView selectImageView;
        private ImageView noSelectImageView;
        private TextView textView;
        private OnClickListener onClickListener;
        private OnClickWhenThatIsIt onClickWhenThatIsIt;
        private View redpotView;

        public NavigationItem(int selectId, int noSelectId, Fragment baseFragment, int fragmentTab, String title) {

            this.baseFragment = baseFragment;
            this.fragmentTab = fragmentTab;
            this.title = title;

            int imageViewPx = ScreenUtils.dip2px(25);

            ImageView imageView = new ImageView(getContext());
            LayoutParams layoutParams = new LayoutParams(imageViewPx, imageViewPx);
            imageView.setLayoutParams(layoutParams);
            imageView.setImageResource(selectId);
            imageView.setVisibility(View.GONE);
            this.selectImageView = imageView;

            imageView = new ImageView(getContext());
            layoutParams = new LayoutParams(imageViewPx, imageViewPx);
            imageView.setLayoutParams(layoutParams);
            imageView.setImageResource(noSelectId);
            this.noSelectImageView = imageView;

            TextView textView = new TextView(getContext());
            textView.setText(title);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
            textView.setTextColor(Color.parseColor("#888888"));
            textView.setGravity(Gravity.CENTER);
            this.textView = textView;

            RelativeLayout relativeLayout = new RelativeLayout(getContext());
            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            relativeLayout.setLayoutParams(layoutParams);
            View view = new View(getContext());
            RelativeLayout.LayoutParams layoutParamsRelative = new RelativeLayout.LayoutParams(ScreenUtils.dip2px(10), ScreenUtils.dip2px(10));
            layoutParamsRelative.addRule(RelativeLayout.CENTER_HORIZONTAL);
            layoutParamsRelative.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            layoutParams.topMargin = ScreenUtils.dip2px(5);
            layoutParams.leftMargin = ScreenUtils.dip2px(15);
            view.setLayoutParams(layoutParamsRelative);
            view.setBackgroundResource(R.drawable.bg_unread_msg_bubble);
            relativeLayout.addView(view);
            relativeLayout.setVisibility(View.GONE);
            this.redpotView = relativeLayout;
        }

        public NavigationItem setRedpotViewVisible(boolean isVisible) {
            this.redpotView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
            return this;
        }

        public NavigationItem setOnClickListener(OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
            return this;
        }

        public NavigationItem setOnClickWhenThatIsIt(OnClickWhenThatIsIt onClickWhenThatIsIt) {
            this.onClickWhenThatIsIt = onClickWhenThatIsIt;
            return this;
        }

    }

    public interface OnClickListener {
        boolean onClickListener();
    }

    public interface OnClickWhenThatIsIt {
        void onClickWhenThatIsIt();
    }

}

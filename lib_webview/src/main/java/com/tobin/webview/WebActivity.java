package com.tobin.webview;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.tobin.lib_resource.base.BaseActivity;
import com.tobin.webview.basefragment.BaseWebViewFragment;
import com.tobin.webview.command.base.Command;
import com.tobin.webview.command.base.ResultBack;
import com.tobin.webview.command.webviewprocess.WebViewProcessCommandsManager;
import com.tobin.webview.utils.WebConstants;

import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

/**
 * Created by Tobin on 2020/12/22
 * Email: 616041023@qq.com
 * Description:
 */
public class WebActivity extends BaseActivity {
    private String title;
    private String url;

    BaseWebViewFragment webViewFragment;
    Toolbar toolbar;

    public static void startCommonWeb(Context context, String title, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(WebConstants.INTENT_TAG_TITLE, title);
        intent.putExtra(WebConstants.INTENT_TAG_URL, url);
        intent.putExtra("level", WebConstants.LEVEL_BASE);
        if (context instanceof Service) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    public static void startAccountWeb(Context context, String title, String url, @NonNull HashMap<String, String> headers) {
        Intent intent = new Intent(context, WebActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(WebConstants.INTENT_TAG_TITLE, title);
        bundle.putString(WebConstants.INTENT_TAG_URL, url);
        bundle.putSerializable(WebConstants.INTENT_TAG_HEADERS, headers);
        bundle.putInt("level", WebConstants.LEVEL_ACCOUNT);
        if (context instanceof Service) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_web);
        toolbar = findViewById(R.id.toolbar_layout);

        // 设置 Toolbar 上的按钮点击事件
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_share) {
                    Timber.tag("WebActivity").w("onMenuItemClick action_share");

                    Intent textIntent = new Intent(Intent.ACTION_SEND);
                    textIntent.setType("text/plain");
                    textIntent.putExtra(Intent.EXTRA_TEXT, url);
                    startActivity(Intent.createChooser(textIntent, "分享"));
                }

                return true;
            }
        });
        toolbar.setNavigationOnClickListener(v -> {
            Timber.tag("WebActivity").w("setNavigationOnClickListener view "+ v.getId() + "home: " + android.R.id.home);
            if (v.getId() == R.id.toolbar_back){
                Timber.tag("WebActivity").w("onMenuItemClick toolbar_back");
            }
        });
        toolbar.setOnClickListener(v -> {
            Timber.tag("WebActivity").w("setNavigationOnClickListener view%d", v.getId());
        });

        title = getIntent().getStringExtra(WebConstants.INTENT_TAG_TITLE);
        url = getIntent().getStringExtra(WebConstants.INTENT_TAG_URL);
        toolbar.setTitle(title);

        int level = getIntent().getIntExtra("level", WebConstants.LEVEL_BASE);

        if (level == WebConstants.LEVEL_BASE) {
            webViewFragment = CommonWebFragment.newInstance(url);
        } else {
            if (getIntent() != null && getIntent().getExtras() != null) {
                Bundle bundle = getIntent().getExtras();
                HashMap<String, String> s = (HashMap<String, String>) bundle.getSerializable(WebConstants.INTENT_TAG_HEADERS);
                if (s != null) {
                    webViewFragment = AccountWebFragment.newInstance(url, s, true);
                }
            }
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.web_view_fragment, webViewFragment).commit();

//        WebViewProcessCommandsManager.getInstance().registerCommand(WebConstants.LEVEL_LOCAL, titleUpdateCommand);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (webViewFragment != null) {
            boolean flag = webViewFragment.onKeyDown(keyCode, event);
            if (flag) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Timber.tag("WebActivity").w("onBackPressed");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == android.R.id.home) { // Toolbar的事件---返回
//            moveTaskToBack(true);
            if (!webViewFragment.onBackPressed()) {
                finish();
            }

        } else if (itemId == R.id.action_share) {
            Timber.tag("WebActivity").e("onOptionsItemSelected action_share");
        }
        // case blocks for other MenuItems (if any)
        return true;
    }

    /**
     * 页面路由
     */
    private Command titleUpdateCommand = new Command() {
        @Override
        public String name() {
            return Command.COMMAND_UPDATE_TITLE;
        }

        @Override
        public void exec(Context context, Map<String, Object> params, ResultBack resultBack) {
            if (params.containsKey(Command.COMMAND_UPDATE_TITLE_PARAMS)) {
                toolbar.setTitle((String) params.get(Command.COMMAND_UPDATE_TITLE_PARAMS));
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webViewFragment.onDestroy();
        webViewFragment = null;
        titleUpdateCommand = null;
    }
}

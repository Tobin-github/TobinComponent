package com.tobin.webview.command.webviewprocess;

import android.content.Context;

import com.tobin.webview.command.base.Command;
import com.tobin.webview.command.base.ResultBack;
import com.tobin.webview.utils.WebConstants;

import java.util.Map;

import timber.log.Timber;

public class WebViewProcessCommandsManager {
    private LocalCommands localCommands;

    private static class Holder {
        private static final WebViewProcessCommandsManager instance = new WebViewProcessCommandsManager();
    }

    private WebViewProcessCommandsManager() {
        Timber.tag("lib_webview").d("WebViewPCM: %s", Holder.instance);
        localCommands = new LocalCommands();
    }

    public static WebViewProcessCommandsManager getInstance() {
        return Holder.instance;
    }

    /**
     * 动态注册command
     * 应用场景：其他模块在使用WebView的时候，需要增加特定的command，动态加进来
     */
    public void registerCommand(int commandLevel, Command command) {
        if (commandLevel == WebConstants.LEVEL_LOCAL) {
            localCommands.registerCommand(command);
        }
    }

    public void unRegisterCommand(int commandLevel, Command command) {
        if (commandLevel == WebConstants.LEVEL_LOCAL) {
            localCommands.unRegisterCommand(command);
        }
    }

    public void onDestroy(){
        localCommands.unRegisterCommands();
    }

    /**
     * Commands handled by WebView itself, these command does not require aidl.
     */
    public void findAndExecLocalCommand(Context context, int level, String action, Map params, ResultBack resultBack) {
        if (localCommands.getCommands().get(action) != null) {
            localCommands.getCommands().get(action).exec(context, params, resultBack);
        }
    }

    public boolean checkHitLocalCommand(int level, String action) {
        return localCommands.getCommands().get(action) != null;
    }

}


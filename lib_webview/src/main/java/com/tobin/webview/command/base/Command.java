package com.tobin.webview.command.base;

import android.content.Context;

import java.util.Map;

public interface Command {
    String COMMAND_UPDATE_TITLE = "tobin_webview_update_title";
    String COMMAND_UPDATE_TITLE_PARAMS = "tobin_webview_update_title_params";

    String name();

    void exec(Context context, Map params, ResultBack resultBack);
}

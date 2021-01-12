package com.tobin.webview.command.webviewprocess;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.List;
import java.util.Map;

import com.tobin.lib_core.utils.ActivityUtils;
import com.tobin.webview.command.base.Command;
import com.tobin.webview.command.base.Commands;
import com.tobin.webview.command.base.ResultBack;
import com.tobin.webview.utils.WebConstants;
import com.tobin.webview.utils.WebUtils;

import timber.log.Timber;

public class LocalCommands extends Commands {

    public LocalCommands() {
        super();
        registerCommands();
    }

    @Override
    protected int getCommandLevel() {
        return WebConstants.LEVEL_LOCAL;
    }

    void registerCommands() {
        registerCommand(showToastCommand);
        registerCommand(showDialogCommand);
    }

    void unRegisterCommands(){
        unRegisterCommand(showDialogCommand);
        unRegisterCommand(showToastCommand);
    }

    /**
     * 显示Toast信息
     */
    private final Command showToastCommand = new Command() {
        @Override
        public String name() {
            return "showToast";
        }

        @Override
        public void exec(Context context, Map params, ResultBack resultBack) {
            Timber.tag("Tobin").e("showToastCommand: %s", params.toString());
//            Toast.makeText(context.getApplicationContext(), String.valueOf(params.get("message")), Toast.LENGTH_SHORT).show();
//            params.put(WebConstants.NATIVE2WEB_CALLBACK, (String) params.get(WebConstants.WEB2NATIVE_CALLBACK));
//            resultBack.onResult(WebConstants.SUCCESS, name(), params);
        }
    };

    /**
     * 对话框显示
     */
    private final Command showDialogCommand = new Command() {
        @Override
        public String name() {
            return "showDialog";
        }

        @Override
        public void exec(Context context, Map params, final ResultBack resultBack) {
            Timber.tag("LocalCommands").e("params: %s", params.toString());
            if (WebUtils.isNotNull(params)) {
                String title = (String) params.get("title");
                String content = (String) params.get("content");
                boolean canceledOutside = true;
                if (params.get("canceledOutside") != null) {
                    canceledOutside = Boolean.parseBoolean(params.get("canceledOutside").toString());
                }
                List<Map<String, String>> buttons = (List<Map<String, String>>) params.get("buttons");
                final String callbackName = (String) params.get(WebConstants.WEB2NATIVE_CALLBACK);
                if (!TextUtils.isEmpty(content)) {
                    AlertDialog dialog = new AlertDialog.Builder(ActivityUtils.currentActivity())
                            .setTitle(title)
                            .setMessage(content)
                            .create();
                    dialog.setCanceledOnTouchOutside(canceledOutside);
                    if (WebUtils.isNotNull(buttons)) {
                        for (int i = 0; i < buttons.size(); i++) {
                            final Map<String, String> button = buttons.get(i);
                            int buttonWhich = getDialogButtonWhich(i);

                            if (buttonWhich == 0) return;

                            dialog.setButton(buttonWhich, button.get("title"), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    button.put(WebConstants.NATIVE2WEB_CALLBACK, callbackName);
                                    resultBack.onResult(WebConstants.SUCCESS, name(), button);
                                }
                            });
                        }
                    }
                    dialog.show();
                }
            }
        }

        private int getDialogButtonWhich(int index) {
            switch (index) {
                case 0:
                    return DialogInterface.BUTTON_POSITIVE;
                case 1:
                    return DialogInterface.BUTTON_NEGATIVE;
                case 2:
                    return DialogInterface.BUTTON_NEUTRAL;
            }
            return 0;
        }
    };
}

package com.tobin.webview.command.mainprocess;

import com.tobin.webview.command.base.Commands;
import com.tobin.webview.utils.WebConstants;

public class AccountLevelCommands extends Commands {

    public AccountLevelCommands() {
    }

    @Override
    protected int getCommandLevel() {
        return WebConstants.LEVEL_ACCOUNT;
    }

}

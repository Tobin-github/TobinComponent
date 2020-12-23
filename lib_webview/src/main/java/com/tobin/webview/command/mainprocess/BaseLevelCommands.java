package com.tobin.webview.command.mainprocess;

import com.tobin.webview.command.base.Commands;
import com.tobin.webview.utils.WebConstants;

public class BaseLevelCommands extends Commands {

    public BaseLevelCommands() {
    }

    @Override
    protected int getCommandLevel() {
        return WebConstants.LEVEL_BASE;
    }
}

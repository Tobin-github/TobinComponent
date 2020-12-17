package com.tobin.lib_core.base.config;

import android.content.Context;

import androidx.room.RoomDatabase;

/**
 * description: room数据库框架配置
 */
public interface RoomDatabaseConfig<DB extends RoomDatabase> {
    void room(Context context, RoomDatabase.Builder<DB> builder);
}

package com.tobin.lib_core.http.cache;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {RoomCacheEntity.class}, version = 1, exportSchema = false)
@TypeConverters(HeadsConverter.class)
public abstract class RoomCacheDB extends RoomDatabase {
    public abstract RoomCacheDao roomCacheDao();
}

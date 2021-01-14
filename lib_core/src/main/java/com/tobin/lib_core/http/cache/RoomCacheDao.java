package com.tobin.lib_core.http.cache;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RoomCacheDao {
    /**
     * 插入缓存，如果有冲突直接替换
     *
     * @param cacheEntity RoomCacheEntity
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCache(RoomCacheEntity cacheEntity);

    /**
     * 查询所有缓存
     *
     * @return List<RoomCacheEntity>
     */
    @Query("SELECT * FROM RoomCache")
    List<RoomCacheEntity> getAllCache();

    /**
     * 根据key查询
     *
     * @param key String
     * @return
     */
    @Query("SELECT * FROM RoomCache WHERE `key` = :key")
    RoomCacheEntity queryByKey(String key);

    /**
     * 根据key删除缓存
     *
     * @param key String
     */
    @Query("DELETE FROM RoomCache WHERE `key` = :key")
    void deleteByKey(String key);

}

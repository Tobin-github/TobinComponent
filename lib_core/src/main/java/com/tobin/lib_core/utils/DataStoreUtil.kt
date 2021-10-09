//package com.tobin.lib_core.utils
//
//import android.content.Context
//import androidx.datastore.core.DataStore
//import androidx.datastore.preferences.core.*
//import androidx.datastore.preferences.createDataStore
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.catch
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.flow.map
//import kotlinx.coroutines.runBlocking
//import java.io.IOException
//
///**
// * Created by Tobin on 2021/1/8
// * Email: 616041023@qq.com
// * Description:
// */
//object DataStoreUtils {
//
//    private lateinit var dataStore: DataStore<Preferences>
//    private const val preferenceName = "TobinDataStore"
//
//    /**
//     * init Context
//     * @param context Context
//     */
//    fun init(context: Context) {
//        dataStore = context.createDataStore(preferenceName)
//    }
//
//    fun <U> getSyncData(key: String, default: U): U {
//        val res = when (default) {
//            is Long -> readLongData(key, default)
//            is String -> readStringData(key, default)
//            is Int -> readIntData(key, default)
//            is Boolean -> readBooleanData(key, default)
//            is Float -> readFloatData(key, default)
//            else -> throw IllegalArgumentException("This type can be saved into DataStore")
//        }
//        return res as U
//    }
//
//    fun <U> getData(key: String, default: U): Flow<U> {
//        val data = when (default) {
//            is Long -> readLongFlow(key, default)
//            is String -> readStringFlow(key, default)
//            is Int -> readIntFlow(key, default)
//            is Boolean -> readBooleanFlow(key, default)
//            is Float -> readFloatFlow(key, default)
//            else -> throw IllegalArgumentException("Type not supported: ${default!!::class.java}")
//        }
//        return data as Flow<U>
//    }
//
//    suspend fun <U> putData(key: String, value: U) {
//        when (value) {
//            is Long -> saveLongData(key, value)
//            is String -> saveStringData(key, value)
//            is Int -> saveIntData(key, value)
//            is Boolean -> saveBooleanData(key, value)
//            is Float -> saveFloatData(key, value)
//            else -> throw IllegalArgumentException("Type not supported for DataStore")
//        }
//    }
//
//    fun <U> putSyncData(key: String, value: U) {
//        when (value) {
//            is Long -> saveSyncLongData(key, value)
//            is String -> saveSyncStringData(key, value)
//            is Int -> saveSyncIntData(key, value)
//            is Boolean -> saveSyncBooleanData(key, value)
//            is Float -> saveSyncFloatData(key, value)
//            else -> throw IllegalArgumentException("This type can be saved into DataStore")
//        }
//    }
//
//    private fun readBooleanFlow(key: String, default: Boolean = false): Flow<Boolean> =
//            dataStore.data
//                    .catch {
//                        //当读取数据遇到错误时，如果是 `IOException` 异常，发送一个 emptyPreferences 来重新使用
//                        //但是如果是其他的异常，最好将它抛出去，不要隐藏问题
//                        if (it is IOException) {
//                            it.printStackTrace()
//                            emit(emptyPreferences())
//                        } else {
//                            throw it
//                        }
//                    }.map {
//                        it[preferencesKey(key)] ?: default
//                    }
//
//    private fun readBooleanData(key: String, default: Boolean = false): Boolean {
//        var value = false
//        runBlocking {
//            dataStore.data.first {
//                value = it[preferencesKey(key)] ?: default
//                true
//            }
//        }
//        return value
//    }
//
//    private fun readIntFlow(key: String, default: Int = 0): Flow<Int> =
//            dataStore.data
//                    .catch {
//                        if (it is IOException) {
//                            it.printStackTrace()
//                            emit(emptyPreferences())
//                        } else {
//                            throw it
//                        }
//                    }.map {
//                        it[preferencesKey(key)] ?: default
//                    }
//
//    private fun readIntData(key: String, default: Int = 0): Int {
//        var value = 0
//        runBlocking {
//            dataStore.data.first {
//                value = it[preferencesKey(key)] ?: default
//                true
//            }
//        }
//        return value
//    }
//
//    private fun readStringFlow(key: String, default: String = ""): Flow<String> =
//            dataStore.data
//                    .catch {
//                        if (it is IOException) {
//                            it.printStackTrace()
//                            emit(emptyPreferences())
//                        } else {
//                            throw it
//                        }
//                    }.map {
//                        it[preferencesKey(key)] ?: default
//                    }
//
//    private fun readStringData(key: String, default: String = ""): String {
//        var value = ""
//        runBlocking {
//            dataStore.data.first {
//                value = it[preferencesKey(key)] ?: default
//                true
//            }
//        }
//        return value
//    }
//
//    private fun readFloatFlow(key: String, default: Float = 0f): Flow<Float> =
//            dataStore.data
//                    .catch {
//                        if (it is IOException) {
//                            it.printStackTrace()
//                            emit(emptyPreferences())
//                        } else {
//                            throw it
//                        }
//                    }.map {
//                        it[preferencesKey(key)] ?: default
//                    }
//
//    private fun readFloatData(key: String, default: Float = 0f): Float {
//        var value = 0f
//        runBlocking {
//            dataStore.data.first {
//                value = it[preferencesKey(key)] ?: default
//                true
//            }
//        }
//        return value
//    }
//
//    private fun readLongFlow(key: String, default: Long = 0L): Flow<Long> =
//            dataStore.data
//                    .catch {
//                        if (it is IOException) {
//                            it.printStackTrace()
//                            emit(emptyPreferences())
//                        } else {
//                            throw it
//                        }
//                    }.map {
//                        it[preferencesKey(key)] ?: default
//                    }
//
//    private fun readLongData(key: String, default: Long = 0L): Long {
//        var value = 0L
//        runBlocking {
//            dataStore.data.first {
//                value = it[preferencesKey(key)] ?: default
//                true
//            }
//        }
//        return value
//    }
//
//    private suspend fun saveBooleanData(key: String, value: Boolean) {
//        dataStore.edit { mutablePreferences ->
//            mutablePreferences[preferencesKey(key)] = value
//        }
//    }
//
//    private fun saveSyncBooleanData(key: String, value: Boolean) =
//            runBlocking { saveBooleanData(key, value) }
//
//    private suspend fun saveIntData(key: String, value: Int) {
//        dataStore.edit { mutablePreferences ->
//            mutablePreferences[preferencesKey(key)] = value
//        }
//    }
//
//    private fun saveSyncIntData(key: String, value: Int) = runBlocking { saveIntData(key, value) }
//
//    suspend fun saveStringData(key: String, value: String) {
//        dataStore.edit { mutablePreferences ->
//            mutablePreferences[preferencesKey(key)] = value
//        }
//    }
//
//    private fun saveSyncStringData(key: String, value: String) = runBlocking { saveStringData(key, value) }
//
//    suspend fun saveFloatData(key: String, value: Float) {
//        dataStore.edit { mutablePreferences ->
//            mutablePreferences[preferencesKey(key)] = value
//        }
//    }
//
//    private fun saveSyncFloatData(key: String, value: Float) = runBlocking { saveFloatData(key, value) }
//
//    suspend fun saveLongData(key: String, value: Long) {
//        dataStore.edit { mutablePreferences ->
//            mutablePreferences[preferencesKey(key)] = value
//        }
//    }
//
//    private fun saveSyncLongData(key: String, value: Long) = runBlocking { saveLongData(key, value) }
//
//    suspend fun clear() {
//        dataStore.edit {
//            it.clear()
//        }
//    }
//
//    fun clearSync() {
//        runBlocking {
//            dataStore.edit {
//                it.clear()
//            }
//        }
//    }
//
//}

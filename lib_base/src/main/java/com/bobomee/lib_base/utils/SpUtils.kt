package com.bobomee.lib_base.utils

import android.content.Context
import com.tencent.mmkv.MMKV

object SpUtils {

    fun initUtils(mContext: Context): String? = MMKV.initialize(mContext)

    fun put(key: String, value: Any) =
        when (value) {
            is Int -> putInt(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
            is Double -> putDouble(key, value)
            is String -> putString(key, value)
            is Boolean -> putBoolean(key, value)
            else -> false
        }

    fun putString(key: String, value: String): Boolean? = MMKV.defaultMMKV()?.encode(key, value)

    fun getString(key: String, defValue: String): String =
        MMKV.defaultMMKV()?.decodeString(key, defValue) ?: defValue

    fun putInt(key: String, value: Int): Boolean? = MMKV.defaultMMKV()?.encode(key, value)

    fun getInt(key: String, defValue: Int): Int = MMKV.defaultMMKV()?.decodeInt(key, defValue) ?: defValue

    fun putLong(key: String, value: Long): Boolean? = MMKV.defaultMMKV()?.encode(key, value)

    fun getLong(key: String, defValue: Long): Long =
        MMKV.defaultMMKV()?.decodeLong(key, defValue) ?: defValue

    fun putDouble(key: String, value: Double): Boolean? = MMKV.defaultMMKV()?.encode(key, value)

    fun getDouble(key: String, defValue: Double): Double =
        MMKV.defaultMMKV()?.decodeDouble(key, defValue) ?: defValue

    fun putFloat(key: String, value: Float): Boolean? = MMKV.defaultMMKV()?.encode(key, value)

    fun getFloat(key: String, defValue: Float): Float =
        MMKV.defaultMMKV()?.decodeFloat(key, defValue) ?: defValue

    fun putBoolean(key: String, value: Boolean): Boolean? = MMKV.defaultMMKV()?.encode(key, value)

    fun getBoolean(key: String, defValue: Boolean): Boolean =
        MMKV.defaultMMKV()?.decodeBool(key, defValue) ?: defValue

    fun contains(key: String): Boolean = MMKV.defaultMMKV()?.contains(key) ?: false
}
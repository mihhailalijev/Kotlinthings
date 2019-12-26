package com.example.kotlinthings

import android.graphics.Bitmap
import android.util.LruCache

class CacheManager {

    companion object {
        val MEMORY_LRU_CACHE by lazy { CacheManager() }
    }

    private var maxMemorySize = 100 * 1024 * 1024 // 100mb

    var memoryLruCache = object : LruCache<String?, Bitmap>(maxMemorySize) {
        override fun sizeOf(key: String?, value: Bitmap): Int {
            return value.byteCount
        }
    }

    fun getBitmapFromMemCache(key: String): Bitmap? {
        return memoryLruCache.get(key)
    }

    fun addBitmapToMemoryCache(key: String, bitmap: Bitmap) {
        memoryLruCache.put(key, bitmap)
    }

    fun getCacheSize(): Int {
        return memoryLruCache.size()
    }

    fun getCacheElementCount(): Int {
        return memoryLruCache.snapshot().size
    }

    fun contains(key: String): Boolean {
        return getBitmapFromMemCache(key) != null
    }

// -----------------------------------------------------------------------------------------------------------------------


}
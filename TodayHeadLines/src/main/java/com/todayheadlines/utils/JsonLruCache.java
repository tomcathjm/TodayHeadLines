package com.todayheadlines.utils;

import android.util.Log;
import android.util.LruCache;

/**
 * Created by HJM on 2016/8/22.
 */
public class JsonLruCache {
    private LruCache<String, String> mJsonLruCache;

    public JsonLruCache() {
        int maxSize = (int) Runtime.getRuntime().maxMemory() / 10;
        mJsonLruCache = new LruCache<String, String>(maxSize) {
            @Override
            protected int sizeOf(String key, String value) {
                return value.length();
            }
        };
    }

    public void addJsonCache(String key, String jsonString) {
        if (jsonString.isEmpty() || key.isEmpty()) {
            return;
        } else {
            if (getCacheJson(key) == null) {
                mJsonLruCache.put(key, jsonString);
            }
        }
    }

    public String getCacheJson(String key) {
        if (!key.isEmpty() && mJsonLruCache != null) {
            return mJsonLruCache.get(key);
        }
        return null;
    }

    public boolean isExist(String key) {
        if (getCacheJson(key) != null){
            return true;
        }else{
            return false;
        }
    }

}

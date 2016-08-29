package com.todayheadlines.utils;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

/**
 * Created by HJM on 2016/8/29.
 */
public class BitmapLruCache {
    private static LruCache<String, Bitmap> mLruCache;

    public static void getInstance() {
        if (mLruCache == null){
            synchronized (BitmapLruCache.class){
                if (mLruCache == null){
                    int maxCache = (int) (Runtime.getRuntime().maxMemory() / 8);
                    mLruCache = new LruCache<String, Bitmap>(maxCache) {
                        @Override
                        protected int sizeOf(String key, Bitmap value) {
                            return value.getByteCount();
                        }
                    };
                }
            }
        }
    }

    public void addBitmapToCache(String key, Bitmap bitmap) {
        if (key == null || "".equals(key) || bitmap == null)
            return;
        if (getBitmapFromCache(key) == null) {
            mLruCache.put(key, bitmap);
        } else {
            return;
        }
    }

    public Bitmap getBitmapFromCache(String key) {
        if (key != null && !"".equals(key) && mLruCache != null) {
            return mLruCache.get(key);
        } else {
            return null;
        }
    }

    public boolean isExist(String key) {
        if (getBitmapFromCache(key) != null) {
            return true;
        } else {
            return false;
        }
    }
}

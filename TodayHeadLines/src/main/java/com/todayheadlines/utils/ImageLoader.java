package com.todayheadlines.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by HJM on 2016/8/23.
 */
public class ImageLoader {

    private static ImageLoader imageLoader = null;
    private static BitmapLruCache mLruCache = new BitmapLruCache();

    public static ImageLoader getInstance() {
       BitmapLruCache.getInstance();
        if (imageLoader == null) {
            synchronized (ImageLoader.class) {
                imageLoader = new ImageLoader();
            }
        }
        return imageLoader;
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mImageView.getTag().equals(mUrl)) {
                mImageView.setImageBitmap((Bitmap) msg.obj);
                mLruCache.addBitmapToCache(mUrl,(Bitmap) msg.obj);
            }
        }
    };
    private ImageView mImageView;
    private String mUrl;

    public void showImageByThread(final ImageView imageView, final String url) {
        mImageView = imageView;
        mUrl = url;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = null;
                if (mLruCache.getBitmapFromCache(url) == null){
                    bitmap = getBitmapFromUrl(url);
                }else{
                    bitmap = mLruCache.getBitmapFromCache(url);
                }
                Message msg = Message.obtain();
                msg.obj = bitmap;
                mHandler.sendMessage(msg);
            }
        }).start();
    }

    public Bitmap getBitmapFromUrl(String urlString) {
        Bitmap bitmap;
        InputStream is = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            is = new BufferedInputStream(connection.getInputStream());
            bitmap = BitmapFactory.decodeStream(is);
            connection.disconnect();
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

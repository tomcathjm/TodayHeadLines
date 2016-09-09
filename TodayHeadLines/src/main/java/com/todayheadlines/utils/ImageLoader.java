package com.todayheadlines.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.todayheadlines.R;
import com.todayheadlines.adapter.NewsTuiJianAdapter;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by HJM on splash/8/23.
 */
public class ImageLoader {

    private LruCache<String, Bitmap> mCache;
    private MyAsyncTask task;
    private ListView mListView;
    private Set<MyAsyncTask> mTasks;

    public ImageLoader(ListView listView) {
        mListView = listView;
        mTasks = new HashSet<>();
        int maxCache = (int) (Runtime.getRuntime().maxMemory() / 8);
        mCache = new LruCache<String, Bitmap>(maxCache) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    private void addBitmapToCache(String key, Bitmap bitmap) {
        if (getBitmapFromCache(key) == null) {
            mCache.put(key, bitmap);
        }
    }

    private Bitmap getBitmapFromCache(String key) {
        if (key != null && !"".equals(key) && mCache != null) {
            return mCache.get(key);
        } else {
            return null;
        }
    }

    public void showImageByTask(String url, ImageView imageView) {
        if (getBitmapFromCache(url) == null) {
            imageView.setImageResource(R.drawable.icon);
        } else {
            imageView.setImageBitmap(getBitmapFromCache(url));
        }
    }

    public void loadImage(int start, int end) {
        for (int i = start; i < end; i++) {
            String url = NewsTuiJianAdapter.URLS[i];
            if (getBitmapFromCache(url) == null) {
                task = new MyAsyncTask(url);
                task.execute(url);
                mTasks.add(task);
            } else {
                ImageView imageView = (ImageView) mListView.findViewWithTag(url);
                imageView.setImageBitmap(getBitmapFromCache(url));
            }

        }
    }

    public void cancelTask() {
        if (mTasks != null) {
            for (MyAsyncTask task:mTasks) {
                task.cancel(false);
            }
        }
    }

    private class MyAsyncTask extends AsyncTask<String, Void, Bitmap> {
        private String url;

        public MyAsyncTask(String url) {
            this.url = url;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            Bitmap bitmap = getBitmapFromUrl(url);
            if (bitmap != null) {
                addBitmapToCache(url, bitmap);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ImageView imageView = (ImageView) mListView.findViewWithTag(url);
            if (imageView != null && bitmap != null && imageView.getTag().equals(url)) {
                imageView.setImageBitmap(bitmap);
            }
            mTasks.remove(this);
        }
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

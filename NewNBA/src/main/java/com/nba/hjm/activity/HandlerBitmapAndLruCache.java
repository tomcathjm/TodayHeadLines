package com.nba.hjm.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.LruCache;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.nba.hjm.myapplication.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by hjm on 2016/2/19.
 */
public class HandlerBitmapAndLruCache extends Activity {

    private boolean isLoad = false;

    private ListView mListView;
    //缓存类
    LruCache<String, Bitmap> mCacheMemory;

    /**
     * @param savedInstanceState
     */

    public String[] iamgeUrls = {
            "http://ac-owqomw6m.clouddn.com/dSjgB47TU6gV3MXy7E758k2RqF0zRv082QEpyBOB.png",
            "http://ac-owqomw6m.clouddn.com/6pmGwKRHWKmhlMeiKnNI4axGjc2CpK1zlfMr07CY.png",
            "http://ac-owqomw6m.clouddn.com/evNdnDQB5sAo7JCgiI27lxWSwTeUJiIfiHqJYTs8.png",
            "http://ac-owqomw6m.clouddn.com/5TFmU3TSU2CpmF6jDVnMuEQbrs7xSpB6cv0fIUpA.png",
            "http://ac-owqomw6m.clouddn.com/uTILnfPaA24TCGnKVhO9jnLlJDUVMJOvvasuzz6T.png",
            "http://ac-owqomw6m.clouddn.com/0EX9h11v358x5Wd0RingWlgKJ2qQ1EY40uCBMz9L.jpg",
            "http://ac-owqomw6m.clouddn.com/0CiJs5eiGla1srLDGPHOdu6CHWMTNf1Mup2qn3FH.png",
            "http://ac-owqomw6m.clouddn.com/0EX9h11v358x5Wd0RingWlgKJ2qQ1EY40uCBMz9L.jpg",
            "http://ac-owqomw6m.clouddn.com/0CiJs5eiGla1srLDGPHOdu6CHWMTNf1Mup2qn3FH.png",
            "http://ac-owqomw6m.clouddn.com/0EX9h11v358x5Wd0RingWlgKJ2qQ1EY40uCBMz9L.jpg",
            "http://ac-owqomw6m.clouddn.com/0CiJs5eiGla1srLDGPHOdu6CHWMTNf1Mup2qn3FH.png",
            "http://ac-owqomw6m.clouddn.com/0EX9h11v358x5Wd0RingWlgKJ2qQ1EY40uCBMz9L.jpg",
            "http://ac-owqomw6m.clouddn.com/0CiJs5eiGla1srLDGPHOdu6CHWMTNf1Mup2qn3FH.png",
            "http://ac-owqomw6m.clouddn.com/0EX9h11v358x5Wd0RingWlgKJ2qQ1EY40uCBMz9L.jpg",
            "http://ac-owqomw6m.clouddn.com/0CiJs5eiGla1srLDGPHOdu6CHWMTNf1Mup2qn3FH.png",
            "http://ac-owqomw6m.clouddn.com/0EX9h11v358x5Wd0RingWlgKJ2qQ1EY40uCBMz9L.jpg",
            "http://ac-owqomw6m.clouddn.com/0CiJs5eiGla1srLDGPHOdu6CHWMTNf1Mup2qn3FH.png",
            "http://ac-owqomw6m.clouddn.com/0EX9h11v358x5Wd0RingWlgKJ2qQ1EY40uCBMz9L.jpg",
            "http://ac-owqomw6m.clouddn.com/0CiJs5eiGla1srLDGPHOdu6CHWMTNf1Mup2qn3FH.png",
            "http://ac-owqomw6m.clouddn.com/0EX9h11v358x5Wd0RingWlgKJ2qQ1EY40uCBMz9L.jpg",
            "http://ac-owqomw6m.clouddn.com/0CiJs5eiGla1srLDGPHOdu6CHWMTNf1Mup2qn3FH.png",
            "http://ac-owqomw6m.clouddn.com/0EX9h11v358x5Wd0RingWlgKJ2qQ1EY40uCBMz9L.jpg",
            "http://ac-owqomw6m.clouddn.com/0CiJs5eiGla1srLDGPHOdu6CHWMTNf1Mup2qn3FH.png",
            "http://ac-owqomw6m.clouddn.com/0EX9h11v358x5Wd0RingWlgKJ2qQ1EY40uCBMz9L.jpg",
            "http://ac-owqomw6m.clouddn.com/0CiJs5eiGla1srLDGPHOdu6CHWMTNf1Mup2qn3FH.png",
            "http://ac-owqomw6m.clouddn.com/2wvE0g3JvVvapoIa2XiRC1hu8OHdN5jL33I5HMdf.png",
            "http://ac-owqomw6m.clouddn.com/QChc7C8QEQuPY4dVH6Ywk1G5g9r0a4ofsuiQi6Cl.png",
            "http://ac-owqomw6m.clouddn.com/vSoM7w1Gd7WuMtMqE3d6C4IaIXmmy6zjMtCct17Q.png",
            "http://ac-owqomw6m.clouddn.com/DkXjv24UQBNDYiGuAsRrk2obUP2ezvmhR6HnPQC9.png"
    };

    ImageViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.handler_lrucache_bitmap_layout);
        mListView = (ListView) findViewById(R.id.lv);

        adapter = new ImageViewAdapter(this);
        mListView.setAdapter(adapter);

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                isLoad = true;
                switch (i){
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        isLoad = false;
                        adapter.notifyDataSetChanged();
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        isLoad = true;
                        adapter.notifyDataSetChanged();
                        break;
                    /*case  AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        isLoad = true;
                        adapter.notifyDataSetChanged();
                        break;*/
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
    }

    //适配器
    class ImageViewAdapter extends BaseAdapter {

        LayoutInflater mLayoutInflater = null;
        Context context;

        public ImageViewAdapter(Context mContext) {
            this.context = mContext;
            mLayoutInflater = LayoutInflater.from(context);

            int maxMemory = (int) Runtime.getRuntime().maxMemory();
            int cacheSize = maxMemory / 8;
            mCacheMemory = new LruCache<String, Bitmap>(cacheSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {

                    return bitmap.getByteCount();
                }
            };
        }

        @Override
        public int getCount() {
            return iamgeUrls.length;
        }

        @Override
        public String getItem(int position) {
            return iamgeUrls[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String url = getItem(position);
            View view = mLayoutInflater.inflate(R.layout.bitmap_item_layout, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
            /**
             * 第一步设置一张空图片
             * 第二步给ImageView设置一个标记 Tag(url)
             * 然后首先从缓存中获取数据，如果没有就联网获取
             */
            imageView.setImageResource(R.mipmap.imageview_null);
            imageView.setTag(url);
            if (!isLoad) {
                Bitmap bitmap = getFromCacheBitmap(url);
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                } else {
                    BitmapTask task = new BitmapTask();
                    task.execute(url);
                }
            } else {
                imageView.setImageResource(R.mipmap.imageview_null);
            }
            return view;
        }
    }

    public synchronized Bitmap getFromCacheBitmap(String key) {
        return mCacheMemory.get(key);
    }

    public synchronized void addBitmapDrawableToCache(String key, Bitmap bitmap) {
        if (getFromCacheBitmap(key) == null) {
            mCacheMemory.put(key, bitmap);
        }
    }

    //异步任务处理网络获取图片类
    class BitmapTask extends AsyncTask<String, Void, Bitmap> {
        String url;

        @Override
        protected Bitmap doInBackground(String... params) {
            url = params[0];
            //根据url联网下载图片，并且缓存起来
            Bitmap bitmap = getBitmapFromNetWork(url);

            //压缩图片
            bitmap = compressImage(bitmap);

            addBitmapDrawableToCache(url, bitmap);
            return bitmap;
        }

        //UI线程执行
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ImageView imageView = (ImageView) mListView.findViewWithTag(url);
            if (imageView != null && bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    public Bitmap getBitmapFromNetWork(String iamge_Url) {
        Bitmap bitmap = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(iamge_Url);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5 * 1000);
            connection.setReadTimeout(10 * 1000);
            bitmap = BitmapFactory.decodeStream(connection.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return bitmap;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mCacheMemory.evictAll();//移除缓存
        }
        return super.onKeyDown(keyCode, event);
    }


    //图片按比例大小压缩方法（根据路径获取图片并压缩）
    private Bitmap getImage(String url) {
        BitmapFactory.Options option = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        option.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(url, option);//此时返回bm为空

        option.inJustDecodeBounds = false;
        int w = option.outWidth;
        int h = option.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (option.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (option.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        option.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(url, option);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }

    //质量压缩
    private Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 先进行大小（占用内存方面）压缩，在进行比例（显示方面）压缩
     *
     * @param image
     * @return
     */
    private Bitmap comp(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if (baos.toByteArray().length / 1024 > 1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }
}

package com.nba.hjm.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.nba.hjm.myapplication.R;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/2/19.
 */
public class TestPictrueActivity extends Activity {

    private Button show_pictrue;
    private ImageView show_iamge;
    String url_url = "http://ac-owqomw6m.clouddn.com/dSjgB47TU6gV3MXy7E758k2RqF0zRv082QEpyBOB.png";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_pictrue_layout);
        show_pictrue = (Button) findViewById(R.id.show_pictrue);
        show_iamge = (ImageView) findViewById(R.id.show_image);
        show_pictrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapTask task = new BitmapTask();
                task.execute(url_url);
            }
        });
    }

    //异步任务处理网络获取图片类
    class BitmapTask extends AsyncTask<String, Void, Bitmap> {
        String url;

        @Override
        protected Bitmap doInBackground(String... params) {
            url = params[0];
            //根据url联网下载图片，并且缓存起来
            Bitmap bitmap = getBitmapFromNetWork(url);
            return bitmap;
        }

        //UI线程执行
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            show_iamge.setImageBitmap(bitmap);
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
}

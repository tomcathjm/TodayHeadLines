package com.todayheadlines.fragment.video;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.todayheadlines.R;
import com.todayheadlines.adapter.VideoTuiJianAdapter;
import com.todayheadlines.base.BaseFragment;
import com.todayheadlines.utils.NetWorkUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by HJM on splash/8/16.
 */
public class KanTianXia extends BaseFragment {

    public static String URL = "http://www.imooc.com/api/teacher?type=4&num=30";

    private final static int SUCCESS = 0;

    @Bind(R.id.listview_video)
    ListView listview_video;

    private VideoTuiJianAdapter adapter;
    private List<NewsBean> list;

    private JsonDataLoader jsonDataLoader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.video_tuijian, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = new ArrayList<>();
        jsonDataLoader = new JsonDataLoader(getActivity());
        adapter = new VideoTuiJianAdapter(getActivity(), list, KanTianXia.this);
        listview_video.setAdapter(adapter);
        getData(getActivity());
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS:
                    adapter.setData(jsonDataLoader.resolveJson((String) msg.obj));
                    break;
            }
        }
    };

    public void getData(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (NetWorkUtils.checkNetWorkState(context)) {
                    if (jsonDataLoader != null) {
                        String jsonFile = jsonDataLoader.getJsonFromLruCache(URL);
                        if (jsonFile != null && !"".equals(jsonFile)) {
                            Message msg = mHandler.obtainMessage();
                            msg.what = SUCCESS;
                            msg.obj = jsonFile;
                            mHandler.sendMessage(msg);
                        } else if (jsonDataLoader.getJsonFromFile(URL) != null) {
                            Message msg = mHandler.obtainMessage();
                            msg.what = SUCCESS;
                            msg.obj = jsonDataLoader.getJsonFromFile(URL);
                            mHandler.sendMessage(msg);
                        } else {
                            new VideoAsyncTask().execute(URL);
                        }
                    }
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showMessage("请检查您的网络");
                        }
                    });
                }
            }
        }).start();
    }

    class VideoAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return readStream(strings[0]);
        }

        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);
            adapter.setData(jsonDataLoader.resolveJson(str));
        }
    }
    //    从网络 --- 读取数据---String-json
    private String readStream(final String url) {
        InputStream is;
        InputStreamReader isr;
        String result = "";
        try {
            String line = null;
            is = new URL(url).openStream();
            isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                result += line;
            }
            return result;
        } catch (UnsupportedEncodingException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }


}

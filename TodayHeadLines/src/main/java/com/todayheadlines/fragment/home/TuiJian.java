package com.todayheadlines.fragment.home;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.todayheadlines.MainTabActivity;
import com.todayheadlines.R;
import com.todayheadlines.adapter.TJAdapter;
import com.todayheadlines.base.BaseFragment;
import com.todayheadlines.model.NewsBean;
import com.todayheadlines.utils.FileCache;
import com.todayheadlines.utils.JsonLruCache;
import com.todayheadlines.utils.NetWorkUtils;
import com.todayheadlines.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
 * Created by HJM on 2016/8/16.
 */
public class TuiJian extends BaseFragment {

    public static String URL = "http://www.imooc.com/api/teacher?type=4&num=30";

    private final static int SUCCESS = 0;

    private JsonLruCache jsonLruCache;
    private FileCache fileCache;

    @Bind(R.id.recyclerview)
    RecyclerView recyclrView;

    private TJAdapter adapter;

    private List<NewsBean> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tuijian, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclrView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        adapter = new TJAdapter(getActivity(), list);
        recyclrView.setAdapter(adapter);
        getData(getActivity());
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS:
                    adapter = new TJAdapter(getActivity(), list);
                    recyclrView.setAdapter(adapter);
                    break;
            }
        }
    };

    public void getData(Context context) {
        if (NetWorkUtils.checkNetWorkState(context)) {
            jsonLruCache = new JsonLruCache();
            if (jsonLruCache.isExist(URL)) {
                String jsonFromCache = jsonLruCache.getCacheJson(URL);
                resolveJson(jsonFromCache);
                mHandler.sendEmptyMessage(SUCCESS);
            } else {
                fileCache = new FileCache(context);
                String jsonString = fileCache.getJsonCache(URL);
                if (jsonString != null) {
                    resolveJson(jsonString);
                    mHandler.sendEmptyMessage(SUCCESS);
                } else {
                    new TJTask().execute(URL);
                }
            }
        } else {
            jsonLruCache = new JsonLruCache();
            if (jsonLruCache.isExist(URL)) {
                String jsonFromCache = jsonLruCache.getCacheJson(URL);
                resolveJson(jsonFromCache);
                mHandler.sendEmptyMessage(SUCCESS);
            } else {
                fileCache = new FileCache(context);
                String jsonString = fileCache.getJsonCache(URL);
                if (jsonString != null) {
                    resolveJson(jsonString);
                    mHandler.sendEmptyMessage(SUCCESS);
                } else {
                    ToastUtils.showNoNet(getActivity(), "请检查您的网络");
                }
            }
        }
    }

    // 联网 --- 获取数据
    class TJTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return getDataFromInternent(strings[0]);
        }

        @Override
        protected void onPostExecute(String string) {
            super.onPostExecute(string);
            resolveJson(string);
            mHandler.sendEmptyMessage(SUCCESS);
        }
    }

    // 处理数据 --- 缓存到内存、缓存到文件中
    private String getDataFromInternent(String url) {
        try {
            String jsonString = readStream(new URL(url).openStream());
            jsonLruCache.addJsonCache(url, jsonString);
            fileCache.saveFile(url, jsonString);
            return jsonString;
        } catch (Exception e) {
        }
        return null;
    }

    //    从网络 --- 读取数据
    private String readStream(InputStream is) {
        InputStreamReader isr;
        String result = "";
        try {
            String line = null;
            isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                result += line;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    // 解析 ---- json数据
    private List<NewsBean> resolveJson(String jsonString) {
        if (jsonString == null)
            return null;
        JSONObject jsonObject;
        NewsBean newsBean;
        try {
            jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                newsBean = new NewsBean();
                newsBean.url = jsonObject.getString("picSmall");
                newsBean.title = jsonObject.getString("name");
                newsBean.content = jsonObject.getString("description");
                list.add(newsBean);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}

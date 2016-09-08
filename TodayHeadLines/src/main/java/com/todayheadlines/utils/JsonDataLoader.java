package com.todayheadlines.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.util.LruCache;

import com.todayheadlines.model.NewsBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HJM on 2016/9/1.
 */
public class JsonDataLoader {

    private final static int SUCCESS_DATA = 0;
    private final static int FAIL_DATA = 1;

    // sdCard  根路径
    private String sdPath = Environment.getExternalStorageDirectory().getPath();
    // 手机内存 根路径
    private String dataPath = null;
    // 缓存文件根目录
    private String FILE_NAME = "/androidJson";

    private LruCache<String, String> mCache;

    public JsonDataLoader(Context context) {

        dataPath = context.getCacheDir().getPath();

        int maxSize = (int) (Runtime.getRuntime().maxMemory() / 10);
        mCache = new LruCache<String, String>(maxSize) {
            @Override
            protected int sizeOf(String key, String json) {
                return json.length();
            }
        };
    }
    //获取缓存目录
    private String getStorageDirectory() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ? sdPath + FILE_NAME : dataPath + FILE_NAME;
    }

    //保存数据 有sd卡存sd卡，否则存在手机内存中
    public void saveJsonToFile(String key, String json) {
        if (key != null && json != null && getJsonFromFile(key) == null) {
            try {
                String path = getStorageDirectory() + File.separator;
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdir();
                }
                File mFile = new File(path + key.replaceAll("[^\\w]", ""));
                mFile.createNewFile();
                BufferedWriter bf = new BufferedWriter(new FileWriter(mFile));
                bf.write(json);
                bf.flush();
                bf.close();
                return;
            } catch (Exception e) {
            }
        }
    }

    public String getJsonFromFile(String key) {
        if (key != null) {
            try {
                String path = getStorageDirectory() + File.separator;
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdir();
                }
                File mFile = new File(path + key.replaceAll("[^\\w]", ""));
                BufferedReader bf = new BufferedReader(new FileReader(mFile));
                String line = null;
                String result = "";
                while ((line = bf.readLine()) != null) {
                    result += line;
                }
                bf.close();
                return result;
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }

    public void addJsonToLruCache(String key, String json) {
        if (mCache != null && getJsonFromLruCache(key) == null && json != null) {
            mCache.put(key, json);
        }
    }

    public String getJsonFromLruCache(String key) {
        if (mCache != null && key != null) {
            return mCache.get(key);
        } else {
            return null;
        }
    }
    // 解析 ---- json数据
    public List<NewsBean> resolveJson(String jsonString) {
        if (jsonString != null && !"".equals(jsonString)) {
            JSONObject jsonObject;
            NewsBean newsBean;
            try {
                List<NewsBean> list = new ArrayList<>();
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
                return null;
            }
        } else {
            return null;
        }
    }
}

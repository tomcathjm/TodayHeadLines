package com.todayheadlines.utils;

import android.content.Context;
import android.os.Environment;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by HJM on 2016/8/22.
 */
public class FileCache {
    //
    private String sdPath = Environment.getExternalStorageDirectory().getPath();
    //
    private String dataPath = null;
    // 缓存文件根目录
    private String FILE_NAME = "/androidJson";

    public FileCache(Context mContext) {
        dataPath = mContext.getCacheDir().getPath();
    }

    //获取缓存目录
    public String getStorageDirectory() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ? sdPath + FILE_NAME : dataPath + FILE_NAME;
    }

    //保存数据 有sd卡存sd卡，否则存在手机内存中
    public void saveFile(String fileName, String str) {
        if (fileName.isEmpty() || str.isEmpty()) {
            return ;
        } else {
            try {
                String path = getStorageDirectory() + File.separator;
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdir();
                }
                File mFile = new File(path + fileName.replaceAll("[^\\w]", ""));
                mFile.createNewFile();
                BufferedWriter bf = new BufferedWriter(new FileWriter(mFile));
                bf.write(str);
                bf.flush();
                bf.close();
                return ;
            } catch (Exception e) {
            }
        }
    }

    public String getJsonCache(String fileName) {
        if (fileName.isEmpty()) {
            return null;
        } else {
            try {
                String path = getStorageDirectory() + File.separator;
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdir();
                }
                File mFile = new File(path + fileName.replaceAll("[^\\w]", ""));
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
        }
    }


}

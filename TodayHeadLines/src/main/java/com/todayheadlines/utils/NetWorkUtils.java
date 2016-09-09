package com.todayheadlines.utils;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by HJM on splash/8/29.
 */
public class NetWorkUtils {
    public static boolean checkNetWorkState(Context mContext){
        ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (null != info){
            if (info.getType() == ConnectivityManager.TYPE_WIFI)
                return true;
            if (info.getType() == ConnectivityManager.TYPE_MOBILE)
                return true;
        }else{
            return false;
        }
        return false;
    }
}

package com.todayheadlines.listener;

/**
 * Created by HJM on 2016/9/1.
 */
public interface GetJsonDataListener {
    void success(String json);
    void fail(String message);
}

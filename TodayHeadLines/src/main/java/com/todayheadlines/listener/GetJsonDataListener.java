package com.todayheadlines.listener;

/**
 * Created by HJM on splash/9/1.
 */
public interface GetJsonDataListener {
    void success(String json);
    void error(String message);
}

package com.test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

public class MainActivity extends BaseActivity {

    private static final String BASE_URL = "http://www.tuweng.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button getJson = (Button) findViewById(R.id.getjson);
        getJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpUtils.get().url(BASE_URL).build().execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        showMessage(e.getMessage());
                    }

                    @Override
                    public void onResponse(final String response) {
                        getJson.setText(response);
                    }
                });
            }
        });
    }
}

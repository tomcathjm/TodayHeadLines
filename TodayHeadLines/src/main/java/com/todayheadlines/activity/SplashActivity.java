package com.todayheadlines.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.todayheadlines.MainTabActivity;
import com.todayheadlines.R;
import com.todayheadlines.base.BaseActivity;

/**
 * Created by HJM on splash/9/9.
 */
public class SplashActivity extends BaseActivity{

    private String url = "http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=gif&step_word=&hs=3&pn=7&spn=0&di=136741455072&pi=0&rn=1&tn=baiduimagedetail&is=&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=507021430%2C2264764364&os=3245663619%2C1765495831&simid=3360399444%2C343963200&adpicid=0&ln=1934&fr=&fmq=1473417569636_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fimages.17173.com%2F2015%2Fnews%2F2015%2F06%2F01%2F2015cpb0601gif04.gif&fromurl=ippr_z2C%24qAzdH3FAzdH3Fgjof_z%26e3B8080n_z%26e3Bv54AzdH3Fv5gpjgpAzdH3Fda8c-am-adAzdH3Fda8camadaaa98cc9c_wss_z%26e3Bfip4s&gsm=0&rpstart=0&rpnum=0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        ImageView imageView = (ImageView)findViewById(R.id.iamge);
//        Glide.with(this).load(R.drawable.splash).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainTabActivity.class));
                finish();
            }
        },100);
    }
}

package com.example.beggar.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.example.beggar.ViewPagerIndicator.ViewPagerIndicatorActivity;
import com.example.beggar.animation.TweenAnimationActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.test_animation)
    void testAnimation(){
        startActivity(new Intent(MainActivity.this, TweenAnimationActivity.class));
    }
    @OnClick(R.id.viewpagerindicator)
    void viewVP(){
        startActivity(new Intent(MainActivity.this, ViewPagerIndicatorActivity.class));
    }
}

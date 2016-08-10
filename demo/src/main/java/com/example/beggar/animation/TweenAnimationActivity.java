package com.example.beggar.animation;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.beggar.myapplication.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Beggar on 2016/7/17.
 */
public class TweenAnimationActivity extends Activity {

    @Bind(R.id.iamgeview)
    ImageView iamgeview;

    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tween_animation_layout);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.alpha_bt)
    void alphaBT() {
        animation = AnimationUtils.loadAnimation(TweenAnimationActivity.this, R.anim.alpha);
        iamgeview.startAnimation(animation);
    }

    @OnClick(R.id.scale_bt)
    void scaleBT() {
        animation = AnimationUtils.loadAnimation(TweenAnimationActivity.this, R.anim.scale);
        iamgeview.startAnimation(animation);
    }

    @OnClick(R.id.translate_bt)
    void translateBT() {
        animation = AnimationUtils.loadAnimation(TweenAnimationActivity.this, R.anim.translate);
        iamgeview.startAnimation(animation);
    }

    @OnClick(R.id.rotate_bt)
    void rotateBT() {
        animation = AnimationUtils.loadAnimation(TweenAnimationActivity.this, R.anim.rotate);
        iamgeview.startAnimation(animation);
    }
}

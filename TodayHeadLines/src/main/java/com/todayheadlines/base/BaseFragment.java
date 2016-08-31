package com.todayheadlines.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/5.
 */
public class BaseFragment extends Fragment{

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this,view);
        super.onViewCreated(view, savedInstanceState);

    }
}

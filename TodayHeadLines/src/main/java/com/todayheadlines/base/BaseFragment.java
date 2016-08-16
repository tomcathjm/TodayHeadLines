package com.todayheadlines.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

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

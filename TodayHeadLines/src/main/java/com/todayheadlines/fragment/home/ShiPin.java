package com.todayheadlines.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.todayheadlines.R;
import com.todayheadlines.base.BaseFragment;

/**
 * Created by Administrator on 2016/8/16.
 */
public class ShiPin extends BaseFragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.shipin,container,false);
    }
}

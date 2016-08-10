package com.todayheadlines.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.todayheadlines.R;
import com.todayheadlines.base.BaseFragment;

/**
 * Created by Administrator on 2016/8/5.
 */
public class HomeFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment_layout,null);
        return view;
    }
}

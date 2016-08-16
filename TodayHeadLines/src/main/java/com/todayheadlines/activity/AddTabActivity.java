package com.todayheadlines.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.todayheadlines.R;
import com.todayheadlines.base.BaseFragmentActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/8/16.
 */
public class AddTabActivity extends BaseFragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_tab);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.close)
    void close() {
        finish();
    }
}

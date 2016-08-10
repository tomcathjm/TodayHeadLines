package com.example.beggar.ViewPagerIndicator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.beggar.myapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Beggar on 2016/7/25.
 */
public class ViewPagerIndicatorActivity extends FragmentActivity {

    @Bind(R.id.vp_indicator)
    ViewPagerIndicator mIndicator;
    @Bind(R.id.viewpager)
    ViewPager viewpager;

    private List<String> mTitles = Arrays.asList("热点", "订阅", "游戏", "娱乐", "科技", "体育");
    private List<ViewPagerFragment> mContents = new ArrayList<ViewPagerFragment>();

    private FragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpagerindicato_layout);
        ButterKnife.bind(this);

        initData();

        mIndicator.setViewPager(viewpager,0,mTitles.size());
        mIndicator.setTabTitle(mTitles);
        viewpager.setAdapter(adapter);


        /**
         * 对外暴露的ViewPager 滑动接口 提交给用户做其他的操作
          */
        mIndicator.setOnPagerChangeListener(new ViewPagerIndicator.OnPagerChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                Toast.makeText(ViewPagerIndicatorActivity.this, "页面状态改变了", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initData() {
        for (String title : mTitles) {
            ViewPagerFragment fragment = ViewPagerFragment.newInstance(title);
            mContents.add(fragment);
        }
        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mContents.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mContents.get(position);
            }
        };
    }
}

package com.todayheadlines.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.todayheadlines.R;
import com.todayheadlines.base.BaseFragment;
import com.todayheadlines.fragment.video.DouBiJu;
import com.todayheadlines.fragment.video.KaiYan;
import com.todayheadlines.fragment.video.KanTianXia;
import com.todayheadlines.fragment.video.VideoTuiJian;
import com.todayheadlines.fragment.video.XiangSheng;
import com.todayheadlines.fragment.video.XiaoPin;
import com.todayheadlines.fragment.video.YinYueTai;
import com.todayheadlines.fragment.video.YouXi;
import com.todayheadlines.fragment.video.YuanChuan;
import com.todayheadlines.widget.ViewPagerIndicator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/8/5.
 */
public class VideoFragment extends BaseFragment {

    // 初始 Tab 内容
    private List<String> mTitles = Arrays.asList("推荐", "逗比剧", "音乐台", "看天下", "相声", "小品", "游戏", "原创", "开眼");
//    private List<String> mTitles = Arrays.asList("推荐", "逗比剧");
    private ArrayList<Fragment> mContents = new ArrayList<>();

    private ViewPager viewPager;
    private ViewPagerIndicator indicator;
    private FragmentPagerAdapter adapter;
    private LinearLayout search;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vidoe_fragment_layout, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = (ViewPager) view.findViewById(R.id.vp);
        indicator = (ViewPagerIndicator) view.findViewById(R.id.indicator);
        search = (LinearLayout) view.findViewById(R.id.search);

        addFragment();

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initDatas();

        indicator.setTabTitle(mTitles);
        indicator.setViewPager(viewPager, 0);
        viewPager.setAdapter(adapter);
    }

    private void addFragment() {
        mContents.add(new VideoTuiJian());
        mContents.add(new DouBiJu());
        mContents.add(new YinYueTai());
        mContents.add(new KanTianXia());
        mContents.add(new XiangSheng());
        mContents.add(new XiaoPin());
        mContents.add(new YouXi());
        mContents.add(new YuanChuan());
        mContents.add(new KaiYan());
    }

    private void initDatas() {
        adapter = new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mContents.get(position);
            }

            @Override
            public int getCount() {
                return mContents.size();
            }
        };
    }
}

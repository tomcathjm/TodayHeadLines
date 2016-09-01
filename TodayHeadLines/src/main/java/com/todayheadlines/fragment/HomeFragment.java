package com.todayheadlines.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.todayheadlines.R;
import com.todayheadlines.activity.AddTabActivity;
import com.todayheadlines.base.BaseFragment;
import com.todayheadlines.fragment.home.BeiJing;
import com.todayheadlines.fragment.home.KeJi;
import com.todayheadlines.fragment.home.QiChe;
import com.todayheadlines.fragment.home.ReDian;
import com.todayheadlines.fragment.home.SheHui;
import com.todayheadlines.fragment.home.ShiPin;
import com.todayheadlines.fragment.home.TouTiaoHao;
import com.todayheadlines.fragment.home.TuPian;
import com.todayheadlines.fragment.home.NewsTuiJian;
import com.todayheadlines.widget.ViewPagerIndicator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/8/5.
 */
public class HomeFragment extends BaseFragment {

    private final int ADD_TAB_REQUEST_CODE = 1;

    private ViewPager viewPager;
    private ViewPagerIndicator vp_indicator;
    private FragmentPagerAdapter adapter;

    // 初始 Tab 内容
    private List<String> mTitles = Arrays.asList("推荐", "热点", "北京", "视频", "头条号", "社会", "科技", "汽车", "图片");
//    private List<String> mTitles = Arrays.asList("推荐", "热点");
    private ArrayList<Fragment> mContents = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        vp_indicator = (ViewPagerIndicator) view.findViewById(R.id.vp_indicator);

    }

    @OnClick(R.id.add)
    void add() {
        startActivityForResult(new Intent(getActivity(), AddTabActivity.class),ADD_TAB_REQUEST_CODE);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        addFragment();

        initDatas();

        vp_indicator.setTabTitle(mTitles);
        vp_indicator.setViewPager(viewPager, 0);
        viewPager.setAdapter(adapter);

    }

    private void addFragment() {
        mContents.add(new NewsTuiJian());
        mContents.add(new ReDian());
        mContents.add(new BeiJing());
        mContents.add(new ShiPin());
        mContents.add(new TouTiaoHao());
        mContents.add(new SheHui());
        mContents.add(new KeJi());
        mContents.add(new QiChe());
        mContents.add(new TuPian());
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

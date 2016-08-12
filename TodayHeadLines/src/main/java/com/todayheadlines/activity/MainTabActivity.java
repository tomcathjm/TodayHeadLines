package com.todayheadlines.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.todayheadlines.R;
import com.todayheadlines.base.BaseFragmentActivity;
import com.todayheadlines.fragment.FollowFragment;
import com.todayheadlines.fragment.HomeFragment;
import com.todayheadlines.fragment.MineFragment;
import com.todayheadlines.fragment.VideoFragment;

import java.util.HashMap;
import java.util.Set;

import butterknife.ButterKnife;

/**
 * Created by hjm on 2016/8/5.
 */
public class MainTabActivity extends BaseFragmentActivity implements RadioGroup.OnCheckedChangeListener {

    RadioGroup radioGroup;
    RadioButton home;
    RadioButton video;
    RadioButton follow;
    RadioButton mine;

    HashMap<Integer, Fragment> map = new HashMap<Integer, Fragment>();
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintab_activiy_layout);

        radioGroup = (RadioGroup) findViewById(R.id.radio);
        home = (RadioButton) findViewById(R.id.home);
        video = (RadioButton) findViewById(R.id.video);
        follow = (RadioButton) findViewById(R.id.follow);
        mine = (RadioButton) findViewById(R.id.mine);

        ButterKnife.bind(this);
        if (savedInstanceState == null){
            //初始化数据
            map.put(R.id.home, new HomeFragment());
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.main_frame, map.get(R.id.home)).commit();
            this.showFragment(id);
        }

        radioGroup.setOnCheckedChangeListener(this);
    }

    private int id = R.id.home;

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int index) {
        clean();
        switch (index) {
            case R.id.home:
                home.setChecked(true);
                break;
            case R.id.video:
                video.setChecked(true);
                break;
            case R.id.follow:
                follow.setChecked(true);
                break;
            case R.id.mine:
                mine.setChecked(true);
                break;
            default:
                break;
        }
        showFragment(index);
    }

    private void clean() {
        home.setChecked(false);
        video.setChecked(false);
        follow.setChecked(false);
        mine.setChecked(false);
    }

    public void showFragment(int id){
        this.id = id;
        Fragment fragment = map.get(id);
        if (fragment == null){
            switch (id){
                case R.id.home:
                    fragment = new HomeFragment();
                    break;
                case R.id.video:
                    fragment = new VideoFragment();
                    break;
                case R.id.follow:
                    fragment = new FollowFragment();
                    break;
                case R.id.mine:
                    fragment = new MineFragment();
                    break;
            }
            map.put(id, fragment);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_frame, fragment).commitAllowingStateLoss();
        }
        Set<Integer> set = map.keySet();
        for (Integer integer : set) {
            if (id == integer) {
                getSupportFragmentManager().beginTransaction()
                        .show(map.get(integer))
                        .commitAllowingStateLoss();
            } else {
                getSupportFragmentManager().beginTransaction()
                        .hide(map.get(integer))
                        .commitAllowingStateLoss();
            }
        }
    }
}

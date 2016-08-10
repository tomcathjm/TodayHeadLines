package com.todayheadlines.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.todayheadlines.R;
import com.todayheadlines.base.BaseFragmentActivity;
import com.todayheadlines.fragment.FollowFragment;
import com.todayheadlines.fragment.HomeFragment;
import com.todayheadlines.fragment.MineFragment;
import com.todayheadlines.fragment.VideoFragment;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hjm on 2016/8/5.
 */
public class MainTabActivity extends BaseFragmentActivity implements RadioGroup.OnCheckedChangeListener {

    @Bind(R.id.radio)
    RadioGroup radioGroup;

    HashMap<Integer, Fragment> map = new HashMap<Integer, Fragment>();
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintab_activiy_layout);
        ButterKnife.bind(this);

        //初始化数据
        map.put(R.id.home, new HomeFragment());

        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_frame, map.get(R.id.home)).commit();
        this.showFragment(id);

        radioGroup.setOnCheckedChangeListener(this);

    }

    private int id = R.id.home;

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int index) {
        switch (index) {
            case R.id.home:
                if (index == R.id.home)
                return;
                break;
            case R.id.video:
                if (index == R.id.video)
                    return;
                break;
            case R.id.follow:
                if (index == R.id.follow)
                    return;
                break;
            case R.id.mine:
                if (index == R.id.mine)
                    return;
                break;
            default:
                break;
        }
        showFragment(index);
    }
    public void showFragment(int id){
        this.id = id;
        Fragment fragment = map.get(id);
        if (fragment != null){
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
            map.put(id,fragment);
            transaction.add(R.id.main_frame,fragment).commitAllowingStateLoss();
            for(Integer integer : map.keySet()){
                if (id == integer){
                    transaction.show(map.get(integer)).commitAllowingStateLoss();
                }else{
                    transaction.hide(map.get(integer)).commitAllowingStateLoss();
                }
            }
        }
    }
}

package com.todayheadlines.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.todayheadlines.R;
import com.todayheadlines.adapter.MoreChannelAdapter;
import com.todayheadlines.adapter.MyChannelAdapter;
import com.todayheadlines.base.BaseActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/8/16.
 */
public class AddTabActivity extends BaseActivity {
    @Bind(R.id.mychannel)
    RecyclerView my_recyclerview;
    @Bind(R.id.morechannel)
    RecyclerView more_recyclerview;

    ArrayList<String> moreList = new ArrayList<>();
    ArrayList<String> myList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_tab);
        ButterKnife.bind(this);
        initData();
        GridLayoutManager my_gm = new GridLayoutManager(this, 4);
        more_recyclerview.setLayoutManager(my_gm);
        GridLayoutManager more_gm = new GridLayoutManager(this, 4);
        my_recyclerview.setLayoutManager(more_gm);
        final MyChannelAdapter my_adapter = new MyChannelAdapter(this, myList);
        final MoreChannelAdapter more_adapter = new MoreChannelAdapter(this, moreList);

        my_recyclerview.setAdapter(my_adapter);
        more_recyclerview.setAdapter(more_adapter);

        // 频道的自主订阅
        my_adapter.setOnItemClickListener(new MyChannelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                more_adapter.addItem(moreList.size(), myList.get(position));
                my_adapter.removeItem(position);
            }
        });
        more_adapter.setOnItemClickListener(new MoreChannelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                my_adapter.addItem(myList.size(), moreList.get(position));
                more_adapter.removeItem(position);

            }
        });
    }

    // 默认频道数据
    private void initData() {

        myList.add("推荐");
        myList.add("热点");
        myList.add("北京");
        myList.add("视频");
        myList.add("头条号");
        myList.add("社会");
        myList.add("科技");
        myList.add("汽车");
        myList.add("图片");

        moreList.add("时尚");
        moreList.add("美容");
        moreList.add("车险");
        moreList.add("搞笑");
        moreList.add("幽默");
        moreList.add("美国");
        moreList.add("日本");
        moreList.add("韩国");
        moreList.add("英国");
        moreList.add("德国");
        moreList.add("历史");
        moreList.add("地理");
        moreList.add("生物");
        moreList.add("化学");
        moreList.add("物理");
        moreList.add("高数");
        moreList.add("机械");
        moreList.add("编程");
        moreList.add("科学");
    }

    @OnClick(R.id.close)
    void close() {
        finish();
    }

    /**
     * 保存频道选择结果
     * 1：通知主页刷新频道选择结果
     * 2：将选择结果上传到服务器保存
     */
    @OnClick(R.id.save_channel)
    void save() {
        showMessage("保存成功");
        finish();
    }
}

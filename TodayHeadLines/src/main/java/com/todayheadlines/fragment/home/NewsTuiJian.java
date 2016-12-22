package com.todayheadlines.fragment.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.view.CommonFooter;
import com.todayheadlines.R;
import com.todayheadlines.adapter.MyChannelAdapter;
import com.todayheadlines.adapter.NewsTuiJianAdapter;
import com.todayheadlines.base.BaseFragment;

import butterknife.Bind;

/**
 * Created by HJM on splash/8/16.
 */
public class NewsTuiJian extends BaseFragment {

    public static String URL = "http://www.imooc.com/api/teacher?type=4&num=30";

    private NewsTuiJianAdapter adapter;

    @Bind(R.id.listview)
    ListView listView;


    @Bind(R.id.lRecyclerView)
    LRecyclerView mLRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.news_tuijian, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        adapter = new NewsTuiJianAdapter(getActivity(), list, listView);

        final LRecyclerViewAdapter mAdapter = new LRecyclerViewAdapter(new MyChannelAdapter(getActivity(), null));
        mLRecyclerView.setAdapter(mAdapter);
        mLRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter.addFooterView(new CommonFooter(getActivity(), R.layout.add_tab));

        mLRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mLRecyclerView.refreshComplete();
                showMessage("下拉刷新操作");
            }
        });
        mLRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mAdapter.notifyDataSetChanged();
                showMessage("上啦加载操作");
            }
        });

    }
}

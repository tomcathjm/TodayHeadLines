package com.todayheadlines.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.todayheadlines.R;
import com.todayheadlines.model.NewsBean;
import com.todayheadlines.utils.ImageLoader;

import java.util.List;

/**
 * Created by HJM on 2016/9/1.
 */
public class NewsTuiJianAdapter extends BaseAdapter implements AbsListView.OnScrollListener {
    private LayoutInflater mInflater;
    private List<NewsBean> mList;
    private ImageLoader mImageLoader;
    private int mStart;
    private int mEnd;
    public static String[] URLS; // 保存所有图片的 url
    private boolean mFirstIn; // 判断是否是第一次进入列表项


    public NewsTuiJianAdapter(Context mContext, List<NewsBean> list, ListView listView) {
        this.mInflater = LayoutInflater.from(mContext);
        this.mList = list;
        mImageLoader = new ImageLoader(listView);

        listView.setOnScrollListener(this);
        mFirstIn = true;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    public void setData(List<NewsBean> list) {
        this.mList = list;
        URLS = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            URLS[i] = list.get(i).url;
        }
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.news_item_layout, viewGroup, false);
            holder = new ViewHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.content = (TextView) convertView.findViewById(R.id.content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        NewsBean newsBean = mList.get(position);
        holder.title.setText(newsBean.title);
        holder.content.setText(newsBean.content);
        holder.icon.setImageResource(R.drawable.icon);
        holder.icon.setTag(newsBean.url);
        mImageLoader.showImageByTask(newsBean.url, holder.icon);
        return convertView;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {
            //加载可见条目
            mImageLoader.loadImage(mStart, mEnd);
        } else {
            //停止任务
            mImageLoader.cancelTask();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mStart = firstVisibleItem;
        mEnd = firstVisibleItem + visibleItemCount;

        if (mFirstIn && visibleItemCount > 0) {
            mImageLoader.loadImage(mStart, mEnd);
        }
    }

    static class ViewHolder {
        ImageView icon;
        TextView title;
        TextView content;
    }
}

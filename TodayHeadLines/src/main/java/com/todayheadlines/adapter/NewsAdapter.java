package com.todayheadlines.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.todayheadlines.R;
import com.todayheadlines.model.NewsBean;
import com.todayheadlines.utils.ImageLoader;
import com.todayheadlines.utils.NetWorkUtils;

import java.util.List;

/**
 * Created by HJM on 2016/8/22.
 */
public class NewsAdapter extends BaseAdapter {

    private List<NewsBean> mList;
    private Context mContext;
    private LayoutInflater mInflater;

    public NewsAdapter(Context mContext, List<NewsBean> list) {
        this.mContext = mContext;
        this.mList = list;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.item_layout, viewGroup, false);
            holder.icon = (ImageView) view.findViewById(R.id.icon);
            holder.title = (TextView) view.findViewById(R.id.title);
            holder.content = (TextView) view.findViewById(R.id.content);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        NewsBean newsBean = mList.get(i);
        holder.title.setText(newsBean.title);
        holder.content.setText(newsBean.content);

        holder.icon.setTag(newsBean.url);
        if (NetWorkUtils.checkNetWorkState(mContext)){
            ImageLoader.getInstance().showImageByThread(holder.icon, newsBean.url);
        }else{
            holder.icon.setImageResource(R.drawable.android);
        }
        return view;
    }
    static class ViewHolder {
        TextView title;
        TextView content;
        ImageView icon;
    }

}

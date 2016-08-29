package com.todayheadlines.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.todayheadlines.R;
import com.todayheadlines.model.NewsBean;
import com.todayheadlines.utils.ImageLoader;
import com.todayheadlines.utils.NetWorkUtils;

import java.util.List;

/**
 * Created by HJM on 2016/8/29.
 */
public class TJAdapter extends RecyclerView.Adapter<TJAdapter.MyViewHolder> {
    private Context mContext;
    private List<NewsBean> mList;
    private LayoutInflater inflater;

    public TJAdapter(Context context, List<NewsBean> list) {
        this.mContext = context;
        this.mList = list;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NewsBean newsBean = mList.get(position);
        holder.icon.setImageResource(R.drawable.icon);
        holder.title.setText(newsBean.title);
        holder.content.setText(newsBean.content);
        holder.icon.setTag(newsBean.url);
        if (NetWorkUtils.checkNetWorkState(mContext)){
            ImageLoader.getInstance().showImageByThread(holder.icon, newsBean.url);
        }else{
            holder.icon.setImageResource(R.drawable.android);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView icon;
        private TextView title;
        private TextView content;

        public MyViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            title = (TextView) itemView.findViewById(R.id.title);
            content = (TextView) itemView.findViewById(R.id.content);
        }
    }
}

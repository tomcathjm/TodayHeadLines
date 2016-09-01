package com.todayheadlines.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.todayheadlines.R;
import com.todayheadlines.model.NewsBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by HJM on 2016/9/1.
 */
public class VideoTuiJianAdapter extends BaseAdapter {

    private List<NewsBean> mList;
    private LayoutInflater mInflater;
    private Fragment fragment;

    public VideoTuiJianAdapter(Context context, List<NewsBean> list, Fragment fragment) {
        this.mList = list;
        this.fragment = fragment;
        mInflater = LayoutInflater.from(context);
    }

    public void setData(List<NewsBean> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
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
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.video_item_layout, viewGroup, false);
            convertView.setTag(holder);

            holder.play_up = (RelativeLayout) convertView.findViewById(R.id.play_up);

            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.iv_bg = (ImageView) convertView.findViewById(R.id.iv_bg);
            holder.waittingdialog = (ProgressBar) convertView.findViewById(R.id.waittingdialog);
            holder.play = (CircleImageView) convertView.findViewById(R.id.play);
            holder.time = (TextView) convertView.findViewById(R.id.time);

            holder.play_down = (RelativeLayout) convertView.findViewById(R.id.play_down);

            holder.usericon = (CircleImageView) convertView.findViewById(R.id.usericon);
            holder.username = (TextView) convertView.findViewById(R.id.username);
            holder.play_time = (TextView) convertView.findViewById(R.id.play_time);
            holder.comment = (TextView) convertView.findViewById(R.id.comment);
            holder.share = (TextView) convertView.findViewById(R.id.share);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        NewsBean newsBean = mList.get(position);
        holder.title.setText(newsBean.title);

        Glide.with(fragment).load(newsBean.url).crossFade().placeholder(R.drawable.icon).into(holder.iv_bg);

        return convertView;
    }

    static class ViewHolder {
        TextView title;
        RelativeLayout play_up;
        RelativeLayout play_down;
        ImageView iv_bg;
        CircleImageView play;
        TextView time;
        CircleImageView usericon;
        TextView username;
        TextView play_time;
        TextView comment;
        TextView share;
        ProgressBar waittingdialog;
    }
}

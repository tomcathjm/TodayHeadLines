package com.todayheadlines.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.todayheadlines.R;
import com.todayheadlines.model.NewsBean;
import com.todayheadlines.utils.ImageLoader;
import com.todayheadlines.utils.NetWorkUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by HJM on 2016/8/29.
 */
public class VideoTJAdapter extends RecyclerView.Adapter<VideoTJAdapter.MyViewHolder> {
    private Context mContext;
    private List<NewsBean> mList;
    private LayoutInflater inflater;

    public VideoTJAdapter(Context context, List<NewsBean> list) {
        this.mContext = context;
        this.mList = list;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.video_item_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NewsBean newsBean = mList.get(position);
        holder.title.setText(newsBean.title);
        holder.iv_bg.setTag(newsBean.url);
        if (NetWorkUtils.checkNetWorkState(mContext)){
            ImageLoader.getInstance().showImageByThread(holder.iv_bg, newsBean.url);
        }else{
            holder.iv_bg.setImageResource(R.drawable.android);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout play_up;
        private RelativeLayout play_down;
        private ImageView iv_bg;
        private CircleImageView play_icon;
        private TextView title;
        private TextView time;
        private CircleImageView usericon;
        private TextView username;

        private TextView play_time;
        private TextView comment;
        private TextView share;



        public MyViewHolder(View itemView) {
            super(itemView);
            iv_bg = (ImageView) itemView.findViewById(R.id.iv_bg);
            title = (TextView) itemView.findViewById(R.id.title);
            play_icon = (CircleImageView)itemView.findViewById(R.id.play_icon);
            usericon = (CircleImageView)itemView.findViewById(R.id.usericon);
            time = (TextView) itemView.findViewById(R.id.time);
            username = (TextView) itemView.findViewById(R.id.username);
            play_time = (TextView) itemView.findViewById(R.id.play_time);
            comment = (TextView) itemView.findViewById(R.id.comment);
            share = (TextView) itemView.findViewById(R.id.share);
        }
    }
}

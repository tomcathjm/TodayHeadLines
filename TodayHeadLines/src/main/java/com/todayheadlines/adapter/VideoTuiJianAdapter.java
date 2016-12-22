package com.todayheadlines.adapter;

import android.content.Context;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.todayheadlines.R;

import java.io.File;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by HJM on splash/9/1.
 */
public class VideoTuiJianAdapter extends BaseAdapter {

    private List<NewsBean> mList;
    private LayoutInflater mInflater;
    private Fragment fragment;
    private Context mContext;

    public VideoTuiJianAdapter(Context context, List<NewsBean> list, Fragment fragment) {
        this.mContext = context;
        this.mList = list;
        this.fragment = fragment;
        mInflater = LayoutInflater.from(context);
        dataPath = mContext.getCacheDir().getPath();
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
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.video_item_layout, viewGroup, false);
            convertView.setTag(holder);

            holder.videoview = (JCVideoPlayerStandard) convertView.findViewById(R.id.viedoview);
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

        Glide.with(fragment).load(newsBean.url).crossFade().placeholder(R.drawable.icon).into(holder.usericon);

        String path = getStorageDirectory();
        final File file = new File(path);

        holder.videoview.setUp(file.exists() ? file.getPath():""
                , JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, newsBean.title);

        return convertView;
    }

    static class ViewHolder {
        JCVideoPlayerStandard videoview;
        RelativeLayout play_down;
        TextView time;
        CircleImageView usericon;
        TextView username;
        TextView play_time;
        TextView comment;
        TextView share;
    }


   //获取本地视频路径
    private String getStorageDirectory() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ? sdPath + FILE_NAME : dataPath + FILE_NAME;
    }
    // sdCard  路径
    private String sdPath = Environment.getExternalStorageDirectory().getPath();
    // 手机储存路径
    private String dataPath = null;
    // 文件名
    private String FILE_NAME = "/video/1.mp4";
}

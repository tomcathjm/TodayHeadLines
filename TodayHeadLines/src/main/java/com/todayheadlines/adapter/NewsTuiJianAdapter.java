package com.todayheadlines.adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
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
import com.todayheadlines.utils.DatabaseHelper;
import com.todayheadlines.utils.ImageLoader;

import java.util.List;

/**
 * Created by HJM on splash/9/1.
 */
public class NewsTuiJianAdapter extends BaseAdapter implements AbsListView.OnScrollListener {

    private Context mContext;
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
        this.mContext = mContext;

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

        getDBData(holder.title,newsBean.title);

        return convertView;
    }

    private DatabaseHelper dbh;
    private SQLiteDatabase db;
    private Cursor cs;
    public void getDBData(TextView textView,String title){
        //查询数据库中的点击记录
        if(dbh != null){
            db = dbh.getReadableDatabase();
        }else{
            dbh = new DatabaseHelper(mContext);
            db = dbh.getReadableDatabase();
        }
        String sql = "select * from mark ORDER BY item DESC";
        try{
            cs = db.rawQuery(sql,null);
            while (cs.moveToNext()){
                if (!"".equals(cs.getColumnIndex("item"))){
                    String mark_str = cs.getString(cs.getColumnIndex("item"));
                    if (mark_str != null && !"".equals(mark_str)){
                        if (mark_str.equals(title)){
                            textView.setTextColor(Color.parseColor("#BBBBBB"));
                        }
                    }
                }
            }
        }catch (Exception e){

        }finally {
            if (cs != null){
                cs.close();
                cs = null;
            }
            if (db != null){
                db.close();
            }
        }
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

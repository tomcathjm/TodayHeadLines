package com.todayheadlines.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.todayheadlines.R;

import java.util.ArrayList;

/**
 * Created by HJM on 2016/9/9.
 */
public class MoreChannelAdapter extends RecyclerView.Adapter<MoreChannelAdapter.ChannelViewHolder> {
    private ArrayList<String> mList;
    private Context mContext;
    private LayoutInflater mInflater;

    public MoreChannelAdapter(Context context, ArrayList<String> list) {
        this.mList = list;
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MoreChannelAdapter.ChannelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.channel_item_layout, parent, false);
        ChannelViewHolder holder = new ChannelViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ChannelViewHolder holder, final int position) {
        holder.text.setText(mList.get(position));
        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view, holder.getLayoutPosition());
            }
        });
    }

    public void addItem(int position,String str) {
        mList.add(position,str);
        notifyItemInserted(position);
    }
    public void removeItem(int position){
        mList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ChannelViewHolder extends RecyclerView.ViewHolder {
        private TextView text;

        public ChannelViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }
}

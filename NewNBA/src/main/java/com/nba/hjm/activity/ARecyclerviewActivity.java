package com.nba.hjm.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nba.hjm.myapplication.R;

import java.util.ArrayList;

/**
 * Created by hjm on 2016/7/11.
 */

public class ARecyclerviewActivity extends Activity {

    private ArrayList<Integer> testDataList = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_layout);

        for (int i = 0; i < 10; i++) {
            testDataList.add(R.mipmap.ic_launcher);
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(new TestAdapter(this, testDataList));
    }

    public class TestAdapter extends RecyclerView.Adapter<VH> {

        private ArrayList<Integer> list;
        private Context mContext;

        public TestAdapter(Context mContext, ArrayList<Integer> arrayList) {
            this.mContext = mContext;
            this.list = arrayList;
        }

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            return new VH(View.inflate(mContext, R.layout.recycle_item_layout, null));
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            holder.imageView.setImageResource(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    public static class VH extends RecyclerView.ViewHolder {
        ImageView imageView;

        public VH(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}

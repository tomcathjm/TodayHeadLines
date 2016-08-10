package com.example.beggar.ViewPagerIndicator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Beggar on 2016/7/26.
 */
public class ViewPagerFragment extends Fragment {

    private static final String BUNDLE_TITLE = "bundle";
    private String mTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle mBundle = getArguments();
        if (mBundle != null){
            mTitle = mBundle.getString(BUNDLE_TITLE);
        }
        TextView textView = new TextView(getActivity());
        textView.setText(mTitle);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    public static ViewPagerFragment newInstance(String title){
        Bundle mBundle = new Bundle();
        mBundle.putString(BUNDLE_TITLE,title);
        ViewPagerFragment fragment =new ViewPagerFragment();
        fragment.setArguments(mBundle);
        return fragment;
    }
}

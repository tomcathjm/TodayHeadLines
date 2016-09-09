package com.todayheadlines.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/5.
 */
public class BaseFragment extends Fragment{

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this,view);
        super.onViewCreated(view, savedInstanceState);
    }
    public void showMessage(String message){
        Toast toast = Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
}

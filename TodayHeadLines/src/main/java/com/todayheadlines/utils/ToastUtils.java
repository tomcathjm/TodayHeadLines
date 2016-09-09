package com.todayheadlines.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by HJM on splash/8/29.
 */
public class ToastUtils {

    public static void showNoNet(Context context,String message){
        Toast toast = Toast.makeText(context,message,Toast.LENGTH_SHORT);
        toast.setText(message);
        toast.setGravity(Gravity.BOTTOM,0,200);
        toast.show();
    }
}

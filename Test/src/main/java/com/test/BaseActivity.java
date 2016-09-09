package com.test;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by HJM on 2016/9/8.
 */
public class BaseActivity extends AppCompatActivity{
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }
    public void showMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}

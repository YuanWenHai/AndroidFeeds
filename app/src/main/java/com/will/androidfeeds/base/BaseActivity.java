package com.will.androidfeeds.base;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Will on 2016/7/6.
 */
public class BaseActivity extends AppCompatActivity {

    public int getContentViewHeight(){
        return findViewById(android.R.id.content).getHeight();
    }
}

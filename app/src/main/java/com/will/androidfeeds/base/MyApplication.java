package com.will.androidfeeds.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by Will on 2016/7/6.
 */
public class MyApplication extends Application {
    private static Context mContext;
    public void onCreate(){
        super.onCreate();
        mContext = getApplicationContext();
    }
    public static Context getGlobalContext(){
        return mContext;
    }

}

package com.will.androidfeeds.base;

import android.app.Fragment;

import com.will.androidfeeds.common.Tools;

/**
 * Created by Will on 2016/7/7.
 */
public class BaseFragment extends Fragment {
    public void showToast(String message){
        Tools.showToast(message);
    }
}

package com.will.androidfeeds.base;

import android.app.Fragment;
import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;

import com.will.androidfeeds.common.Tools;

/**
 * Created by Will on 2016/7/7.
 */
public class BaseFragment extends Fragment {
    public void showToast(String message){
        Tools.showToast(message);
    }
    public LayoutInflater getInflaterWithTheme(LayoutInflater inflater,int style){
        Context contextWithTheme = new ContextThemeWrapper(getActivity(), style);
        return inflater.cloneInContext(contextWithTheme);
    }
}

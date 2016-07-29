package com.will.androidfeeds.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Will on 2016/7/29.
 */
public abstract class BaseListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private View mStatusBar;
    private Toolbar mToolbar;
    private int scrolledHeight = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    public abstract RecyclerView getRecyclerView();
    public abstract View getStatusBar();
    public abstract Toolbar getToolbar();
    private void initView(){
        mRecyclerView = getRecyclerView();
        mStatusBar = getStatusBar();
        mToolbar = getToolbar();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                scrolledHeight += dy;
            }
        });
    }

}


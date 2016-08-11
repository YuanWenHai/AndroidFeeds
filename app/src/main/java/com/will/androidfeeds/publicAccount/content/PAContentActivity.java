package com.will.androidfeeds.publicAccount.content;

import com.will.androidfeeds.base.BaseWebContentActivity;

/**
 * Created by Will on 2016/7/19.
 */
public class PAContentActivity extends BaseWebContentActivity {
    private static final String HOST = "http:chuansong.me";

    @Override
    public String getContentUrl() {
        return HOST + getIntent().getStringExtra("url");
    }

    @Override
    public String getToolbarTitle() {
        return getIntent().getStringExtra("title");
    }
}

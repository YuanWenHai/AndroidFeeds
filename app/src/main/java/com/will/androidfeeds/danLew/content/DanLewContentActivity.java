package com.will.androidfeeds.danLew.content;

import com.will.androidfeeds.base.BaseWebContentActivity;

/**
 * Created by Will on 2016/7/28.
 */
public class DanLewContentActivity extends BaseWebContentActivity {
    @Override
    public String getContentUrl() {
        return getIntent().getStringExtra("url");
    }
    @Override
    public String getToolbarTitle(){
        return getIntent().getStringExtra("title");
    }
}

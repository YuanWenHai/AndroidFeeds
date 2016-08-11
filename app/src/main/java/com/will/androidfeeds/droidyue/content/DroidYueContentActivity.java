package com.will.androidfeeds.droidyue.content;

import com.will.androidfeeds.base.BaseWebContentActivity;

/**
 * Created by Will on 2016/7/21.
 */
public class DroidYueContentActivity extends BaseWebContentActivity {

    @Override
    protected boolean appendTitleToContent(){
        return true;
    }
    @Override
    public String getContentUrl() {
        return getIntent().getStringExtra("url");
    }

    @Override
    public String getToolbarTitle() {
        return getIntent().getStringExtra("title");
    }
}

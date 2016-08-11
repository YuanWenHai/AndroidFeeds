package com.will.androidfeeds.stylingAndroid.content;

import com.will.androidfeeds.base.BaseWebContentActivity;

/**
 * Created by Will on 2016/7/21.
 */
public class StylingAndroidContentActivity extends BaseWebContentActivity {

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

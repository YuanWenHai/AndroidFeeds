package com.will.androidfeeds.csdn.content;

import com.will.androidfeeds.base.BaseWebContentActivity;

/**
 * Created by Will on 2016/7/13.
 */
public class CsdnContentActivity extends BaseWebContentActivity  {
    private static final String BLOG_HOST = "http://m.blog.csdn.net/article/details?id=";

    @Override
    public String getToolbarTitle() {
        return getIntent().getStringExtra("title");
    }

    @Override
    public String getContentUrl() {
        String id = getIntent().getStringExtra("url");
        id = id.substring(id.lastIndexOf("/")+1);
        return BLOG_HOST + id;

    }

}

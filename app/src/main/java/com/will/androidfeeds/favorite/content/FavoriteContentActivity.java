package com.will.androidfeeds.favorite.content;

import com.will.androidfeeds.base.BaseWebContentActivity;

/**
 * Created by Will on 2016/8/11.
 */
public class FavoriteContentActivity extends BaseWebContentActivity {


    @Override
    public void onBackPressed() {
        if(isFavoriteStateHasChanged()){
            setResult(888);
        }
        super.onBackPressed();
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

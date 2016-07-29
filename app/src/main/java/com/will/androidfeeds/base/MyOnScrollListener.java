package com.will.androidfeeds.base;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.github.ksoichiro.android.observablescrollview.ScrollUtils;

/**
 * Created by Will on 2016/7/29.
 */
public class MyOnScrollListener extends RecyclerView.OnScrollListener {
    private int scrolledHeight;
    private int parallaxHeight;
    private int colorPrimaryDark;
    private View parallaxLayout;
    private Toolbar toolbar;
    private View statusBar;
    public MyOnScrollListener(int parallaxHeight, int colorPrimaryDark, View parallaxLayout, Toolbar toolbar, View statusBar){
        this.parallaxHeight = parallaxHeight;
        this.colorPrimaryDark = colorPrimaryDark;
        this.parallaxLayout = parallaxLayout;
        this.toolbar = toolbar;
        this.statusBar = statusBar;
    }
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        scrolledHeight += dy;
        Log.e("dy",scrolledHeight+"");

        if(scrolledHeight < parallaxHeight){
            float alpha = Math.min(1, (float) scrolledHeight / parallaxHeight);
            parallaxLayout.setTranslationY(scrolledHeight/2);
            toolbar.setAlpha(alpha);
            if(alpha == 0 ){
                toolbar.setVisibility(View.INVISIBLE);
            }else if (toolbar.getVisibility() == View.INVISIBLE){
                toolbar.setVisibility(View.VISIBLE);
            }
            statusBar.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha,colorPrimaryDark));
        }else if (scrolledHeight < parallaxHeight*2){
            toolbar.setAlpha(1);
            statusBar.setBackgroundColor(colorPrimaryDark);
        }
    }
}

package com.will.androidfeeds.base;

import android.animation.ValueAnimator;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.will.androidfeeds.R;
import com.will.androidfeeds.view.MWebView;

/**
 * Created by Will on 2016/7/18.
 */
public abstract class BaseWebContentActivity extends BaseActivity implements ObservableScrollViewCallbacks {
    private MWebView markedView;
    private Toolbar toolbar;

    protected Toolbar getToolbar(){
        return toolbar;
    }
    protected MWebView getWebView(){
        return markedView;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_csdn_content);
        markedView = (MWebView) findViewById(R.id.csdn_content_marked_view);
        markedView.setScrollViewCallbacks(this);
        toolbar = (Toolbar) findViewById(R.id.csdn_content_toolbar);
    }
    @Override
    public void onBackPressed(){
        if(markedView.canGoBack()){
            markedView.goBack();
        }else{
            super.onBackPressed();
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        markedView.onResume();
    }
    @Override
    public void onPause(){
        super.onPause();
        markedView.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.change_orientation){
            if(getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }else{
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.csdn_content_menu,menu);
        return true;
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        if(scrollState == ScrollState.UP){
            hideToolbar();
        }else if (scrollState == ScrollState.DOWN){
            showToolbar();
        }
    }
    private void hideToolbar(){
        moveToolbar(-toolbar.getHeight());
    }
    private void showToolbar(){
        moveToolbar(0);
    }
    private void moveToolbar(float toTranslationY){
        if(toolbar.getTranslationY() == toTranslationY){
            return;
        }
        ValueAnimator animator = ValueAnimator.ofFloat(toolbar.getTranslationY(),toTranslationY).setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float translationY = (float) animation.getAnimatedValue();
                toolbar.setTranslationY(translationY);
                markedView.setTranslationY(translationY);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) markedView.getLayoutParams();
                params.height = (int) - translationY + getContentViewHeight() - params.topMargin - toolbar.getHeight();
                markedView.requestLayout();
            }
        });
        animator.start();
    }
}

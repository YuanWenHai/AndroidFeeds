package com.will.androidfeeds.base;

import android.animation.ValueAnimator;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.will.androidfeeds.R;
import com.will.androidfeeds.common.ErrorCode;
import com.will.androidfeeds.util.JsoupHelper;
import com.will.androidfeeds.util.NetworkHelper;
import com.will.androidfeeds.util.Tools;
import com.will.androidfeeds.view.MWebView;

/**
 * Created by Will on 2016/7/18.
 */
public abstract class BaseWebContentActivity extends BaseActivity implements ObservableScrollViewCallbacks {
    private MWebView markedView;
    private Toolbar toolbar;
    private boolean favoriteStateHasChanged = false;

    protected Toolbar getToolbar(){
        return toolbar;
    }
    protected MWebView getWebView(){
        return markedView;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_content_webview);
        markedView = (MWebView) findViewById(R.id.content_webview);
        markedView.setScrollViewCallbacks(this);
        toolbar = (Toolbar) findViewById(R.id.content_toolbar);
        toolbar.setTitle(getToolbarTitle());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.loading_in,R.animator.loading_out)
                .add(R.id.content_webview,new LoadingFragment(),"loading").commit();
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                getContentData();
            }
        });
    }

    public boolean isFavoriteStateHasChanged(){
        return favoriteStateHasChanged;
    }
    public abstract String getToolbarTitle();
    public abstract String getContentUrl();
    protected boolean appendTitleToContent(){
        return false;
    }
    public void getContentData(){
        final String title;
        if (appendTitleToContent()){
            title = "<h3>"+getToolbarTitle()+"</h3>";
        }else{
            title = "";
        }
        NetworkHelper.getInstance().loadWebSource(getContentUrl(), true, true, new NetworkHelper.LoadWebSourceCallback() {
            @Override
            public void onSuccess(String source) {
                getWebView().setMDText(title + JsoupHelper.getAppropriateTypeContentFromSource(getContentUrl(),source));
                closeLoadingAnimation();
            }

            @Override
            public void onFailure(ErrorCode code) {

            }
        });
    }
    public void closeLoadingAnimation(){
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.loading_in,R.animator.loading_out)
                .remove(getFragmentManager().findFragmentByTag("loading")).commit();
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
        } else if(item.getItemId() == R.id.favorite){
            favoriteStateHasChanged = !favoriteStateHasChanged;

            if(Tools.getFavoriteSP().getString(getToolbarTitle(),"").isEmpty()){
                item.setIcon(R.drawable.ic_favorite_white_24dp);
                Tools.getFavoriteSP().edit().putString(getToolbarTitle(),getContentUrl()).commit();
            }else{
                item.setIcon(R.drawable.ic_favorite_border_white_24dp);
                Tools.getFavoriteSP().edit().remove(getToolbarTitle()).commit();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.csdn_content_menu,menu);
        if(!Tools.getFavoriteSP().getString(getToolbarTitle(),"").isEmpty()){
            menu.getItem(0).setIcon(R.drawable.ic_favorite_white_24dp);
        }
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
                params.height = (int) - translationY + getContentViewHeight() - params.topMargin - toolbar.getHeight()
                        - getResources().getDimensionPixelSize(R.dimen.status_bar_size);
                markedView.requestLayout();
            }
        });
        animator.start();
    }
}

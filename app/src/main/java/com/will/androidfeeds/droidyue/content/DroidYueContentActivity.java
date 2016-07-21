package com.will.androidfeeds.droidyue.content;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.will.androidfeeds.base.BaseWebContentActivity;
import com.will.androidfeeds.common.ErrorCode;
import com.will.androidfeeds.util.JsoupHelper;
import com.will.androidfeeds.util.NetworkHelper;
import com.will.androidfeeds.view.MWebView;

/**
 * Created by Will on 2016/7/21.
 */
public class DroidYueContentActivity extends BaseWebContentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = getToolbar();
        MWebView mWebView = getWebView();
        setSupportActionBar(toolbar);
        loadContent(mWebView,getIntent().getStringExtra("url"));
    }
    private void loadContent(final MWebView mWebView, String url){
        NetworkHelper.getInstance().loadWebSource(url, true, true, new NetworkHelper.LoadWebSourceCallback() {
            @Override
            public void onSuccess(String source) {
                mWebView.setMDText(JsoupHelper.getDroidYueContentFromSource(source));
            }

            @Override
            public void onFailure(ErrorCode code) {

            }
        });
    }
}

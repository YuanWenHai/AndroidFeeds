package com.will.androidfeeds.publicAccount.content;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.will.androidfeeds.base.BaseWebContentActivity;
import com.will.androidfeeds.common.ErrorCode;
import com.will.androidfeeds.util.JsoupHelper;
import com.will.androidfeeds.util.NetworkHelper;
import com.will.androidfeeds.view.MWebView;

/**
 * Created by Will on 2016/7/19.
 */
public class PAContentActivity extends BaseWebContentActivity {
    private MWebView webView;
    private static final String HOST = "http:chuansong.me";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = getWebView();
        Toolbar toolbar = getToolbar();
        setSupportActionBar(toolbar);
        getData(getIntent().getStringExtra("url"));
    }
    private void getData(String url){
        NetworkHelper.getInstance().loadWebSource(HOST+url, true, true, new NetworkHelper.LoadWebSourceCallback() {
            @Override
            public void onSuccess(String source) {
                webView.setMDText(JsoupHelper.getPAContentFromSource(source));
            }

            @Override
            public void onFailure(ErrorCode code) {

            }
        });
    }
}

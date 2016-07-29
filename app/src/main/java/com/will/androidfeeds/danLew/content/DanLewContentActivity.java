package com.will.androidfeeds.danLew.content;

import android.os.Bundle;

import com.will.androidfeeds.base.BaseWebContentActivity;
import com.will.androidfeeds.common.ErrorCode;
import com.will.androidfeeds.util.JsoupHelper;
import com.will.androidfeeds.util.NetworkHelper;
import com.will.androidfeeds.view.MWebView;

/**
 * Created by Will on 2016/7/28.
 */
public class DanLewContentActivity extends BaseWebContentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MWebView webView = getWebView();
        loadData(getIntent().getStringExtra("url"),webView);
    }
    private void loadData(String url, final MWebView webView){
        NetworkHelper.getInstance().loadWebSource(url, true, true, new NetworkHelper.LoadWebSourceCallback() {
            @Override
            public void onSuccess(String source) {
                webView.setMDText(JsoupHelper.getDanLewContentFromSource(source));
            }

            @Override
            public void onFailure(ErrorCode code) {

            }
        });
    }
}

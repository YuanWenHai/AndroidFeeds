package com.will.androidfeeds.hukai.content;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.will.androidfeeds.base.BaseWebContentActivity;
import com.will.androidfeeds.bean.HKItem;
import com.will.androidfeeds.common.ErrorCode;
import com.will.androidfeeds.hukai.HKDataHelper;
import com.will.androidfeeds.util.NetworkHelper;
import com.will.androidfeeds.view.MWebView;

/**
 * Created by Will on 2016/7/18.
 */
public class HKContentActivity extends BaseWebContentActivity {
    private static final String HUKAI_HOST = "http://hukai.me";
    MWebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = getToolbar();
        webView= getWebView();
        HKItem item = (HKItem) getIntent().getSerializableExtra("item");
        toolbar.setTitle("Content");
        getContentData(item);

    }
    private void getContentData(HKItem item){
        String url = HUKAI_HOST + item.getLink();
        final String title = "<h3>"+item.getTitle()+"</h3>";
        new HKDataHelper().getContentData(url, new NetworkHelper.LoadWebSourceCallback() {
            @Override
            public void onSuccess(String source) {
                webView.setMDText(title+source);
            }

            @Override
            public void onFailure(ErrorCode code) {

            }
        });
    }
}

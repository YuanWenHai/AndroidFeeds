package com.will.androidfeeds;

import android.os.Bundle;
import android.util.Log;

import com.will.androidfeeds.base.BaseActivity;
import com.will.androidfeeds.bean.CsdnItem;
import com.will.androidfeeds.common.ErrorCode;
import com.will.androidfeeds.util.JsoupHelper;
import com.will.androidfeeds.util.NetworkHelper;

import java.util.List;

public class MainActivity extends BaseActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NetworkHelper.getInstance().loadWebSource("http://blog.csdn.net/lmj623565791/article/list/1", new NetworkHelper.LoadWebSourceCallback() {
            @Override
            public void onSuccess(String source) {
                List<CsdnItem> list = JsoupHelper.getCsdnListItemFromSource(source);
                Log.e("link:",list.get(0).getLink());
                Log.e("title:",list.get(0).getTitle());
                Log.e("time:",list.get(0).getTime());
                Log.e("count:",JsoupHelper.getCsdnListItemCount(source));
            }

            @Override
            public void onFailure(ErrorCode code) {

            }
        });
    }
}

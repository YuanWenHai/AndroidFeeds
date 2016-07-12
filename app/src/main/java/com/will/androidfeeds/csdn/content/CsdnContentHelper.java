package com.will.androidfeeds.csdn.content;

import com.will.androidfeeds.common.ErrorCode;
import com.will.androidfeeds.util.JsoupHelper;
import com.will.androidfeeds.util.NetworkHelper;

/**
 * Created by Will on 2016/7/6.
 */
public class CsdnContentHelper {
    public void loadContent(String url, final Callback callback){
        NetworkHelper.getInstance().loadWebSource(url, new NetworkHelper.LoadWebSourceCallback() {
            @Override
            public void onSuccess(String source) {
                callback.onSuccess(JsoupHelper.getCsdnContentFromSource(source));
            }

            @Override
            public void onFailure(ErrorCode code) {

            }
        });
    }
    public interface Callback {
        void onSuccess(String source);
    }
}

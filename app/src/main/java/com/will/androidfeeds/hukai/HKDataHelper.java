package com.will.androidfeeds.hukai;

import com.will.androidfeeds.common.ErrorCode;
import com.will.androidfeeds.util.DiskCache;
import com.will.androidfeeds.util.JsoupHelper;
import com.will.androidfeeds.util.NetworkHelper;

/**
 * Created by Will on 2016/7/18.
 */
public class HKDataHelper  {
    private DiskCache mCache = DiskCache.getInstance();
    private NetworkHelper networkHelper = NetworkHelper.getInstance();

    public void getContentData(final String url, final NetworkHelper.LoadWebSourceCallback callback){
        final String content = mCache.getSourceFromCache(url);
        if(content != null){
            callback.onSuccess(content);
        }else{
            networkHelper.loadWebSource(url, false, false, new NetworkHelper.LoadWebSourceCallback() {
                @Override
                public void onSuccess(String source) {
                    String content = JsoupHelper.getHKContentFromSource(source);
                    mCache.write2DiskCache(url,content);
                    callback.onSuccess(content);
                }

                @Override
                public void onFailure(ErrorCode code) {
                    callback.onFailure(code);
                }
            });
        }
    }
}

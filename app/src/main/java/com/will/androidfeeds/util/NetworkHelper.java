package com.will.androidfeeds.util;

import android.content.Context;
import android.os.Handler;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.will.androidfeeds.base.MyApplication;
import com.will.androidfeeds.common.ErrorCode;

import java.io.IOException;

/**
 * Created by Will on 2016/7/6.
 */
public class NetworkHelper {
    private volatile static NetworkHelper mInstance;
    private static Context mContext = MyApplication.getGlobalContext();
    private OkHttpClient mClient;
    private Handler mHandler;
    private DiskCache mCache = DiskCache.getInstance();
    private NetworkHelper(){
        mClient = new OkHttpClient();
        mHandler = new Handler(mContext.getMainLooper());
    }
    public static  NetworkHelper getInstance(){
        if(mInstance == null){
            synchronized(NetworkHelper.class){
                if(mInstance == null){
                    mInstance = new NetworkHelper();
                }
            }
        }
        return mInstance;
    }

    /**
     * 加载指定网址源码
     * @param url url
     * @param fromCache 是否优先从缓存中加载，如果为否，则在获取网络信息后更新缓存。
     * @param write2Cache 是否写入disk缓存
     * @param callback callback
     */
    public void loadWebSource(final String url, boolean fromCache, final boolean write2Cache, final LoadWebSourceCallback callback){
        String source;
        if(fromCache && (source = mCache.getSourceFromCache(url)) != null){
            callback.onSuccess(source);
            return;
        }
        Request request = new Request.Builder().url(url).build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Request request, IOException e) {
             mHandler.post(new Runnable() {
                 @Override
                 public void run() {
                     callback.onFailure(ErrorCode.CONNECTION_FAILED);

                 }
             });
            }
            @Override
            public void onResponse(Response response) throws IOException {
                final String body = response.body().string();
                if(write2Cache){
                    mCache.write2DiskCache(url,body);
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(body);
                    }
                });
            }
        });
    }
    public interface LoadWebSourceCallback{
        void onSuccess(String source);
        void onFailure(ErrorCode code);
    }
}

package com.will.androidfeeds.util;

import com.jakewharton.disklrucache.DiskLruCache;
import com.will.androidfeeds.base.MyApplication;
import com.will.androidfeeds.common.Tools;

import java.io.File;
import java.io.IOException;

/**
 * Created by Will on 2016/7/7.
 */
public class DiskCache {
    private static final int DISK_CACHE_SIZE = 10 * 1024 *1024;//10Mib
    private  DiskLruCache mCache;
    private volatile static DiskCache mInstance;
    public static DiskCache getInstance(){
        if(mInstance == null){
            synchronized(DiskCache.class){
                if(mInstance == null){
                    mInstance = new DiskCache();
                }
            }
        }
        return mInstance;
    }
    private DiskCache(){
        try{
            mCache = DiskLruCache.open(new File(MyApplication.getGlobalContext().getCacheDir(),"disk_lru_cache"),1,1,DISK_CACHE_SIZE);
        }catch (IOException i ){
            i.printStackTrace();
        }
    }
    public void write2DiskCache(String name,String source){
        try{
            DiskLruCache.Editor editor = mCache.edit(Tools.string2Md5(name));
            editor.set(0,source);
            editor.commit();
            mCache.flush();
        }catch (IOException i){
            i.printStackTrace();
        }
    }
    public String getSourceFromCache(String name){
        String source = null;
        try{
            DiskLruCache.Snapshot snapshot = mCache.get(Tools.string2Md5(name));
            if(snapshot != null){
                source = snapshot.getString(0);
            }
        }catch (IOException i){
            i.printStackTrace();
        }
        return source;
    }
}

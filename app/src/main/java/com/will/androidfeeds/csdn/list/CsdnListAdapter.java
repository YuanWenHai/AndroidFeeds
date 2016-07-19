package com.will.androidfeeds.csdn.list;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.will.androidfeeds.R;
import com.will.androidfeeds.bean.CsdnItem;
import com.will.androidfeeds.common.ErrorCode;
import com.will.androidfeeds.csdn.content.CsdnContentActivity;
import com.will.androidfeeds.customAdapter.BaseViewHolder;
import com.will.androidfeeds.customAdapter.CustomAdapter;
import com.will.androidfeeds.util.JsoupHelper;
import com.will.androidfeeds.util.NetworkHelper;

import java.util.List;

/**
 * Created by Will on 2016/7/18.
 */
public class CsdnListAdapter extends CustomAdapter<CsdnItem> {
    private Context mContext;
    private String host;
    private int totalCount = -1;

    private NetworkHelper networkHelper = NetworkHelper.getInstance();

    public CsdnListAdapter(String url){
        super(R.layout.csdn_list_item,R.layout.list_loading_view,R.layout.list_loading_failed_view);
        host = url;
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClicked(Object item) {
                if(mContext == null){
                    mContext = getRecyclerView().getContext();
                }
                CsdnItem csdnItem = (CsdnItem) item;
                Intent intent = new Intent(mContext, CsdnContentActivity.class);
                intent.putExtra("url",csdnItem.getLink());
                intent.putExtra("title",csdnItem.getTitle());
                mContext.startActivity(intent);
            }
        });
    }
    public void onRefresh(final OnRefreshCallback callback){
        networkHelper.loadWebSource(host + 1, false, true, new NetworkHelper.LoadWebSourceCallback() {
            @Override
            public void onSuccess(String source) {
                refreshData(JsoupHelper.getCsdnListItemFromSource(source));
                callback.onSuccess();
                Log.e("onRefresh","success");
            }

            @Override
            public void onFailure(ErrorCode code) {
                callback.onFailure(code);
                Log.e("onRefresh","failed");
            }
        });
    }

    @Override
    public void loadData(int page) {
        String url = host;
        boolean useCache = true;
        if(page != 1){
            url = url + page;
            useCache = false;
        }
        networkHelper.loadWebSource(url, useCache, useCache, new NetworkHelper.LoadWebSourceCallback() {
            @Override
            public void onSuccess(String source) {
                if(totalCount == -1){
                    totalCount = JsoupHelper.getCsdnListItemCount(source);
                }
                List<CsdnItem> data = JsoupHelper.getCsdnListItemFromSource(source);
                update(true,data);
            }

            @Override
            public void onFailure(ErrorCode code) {
                getRecyclerView().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        update(false);
                    }
                },500);
            }
        });
    }

    @Override
    public boolean hasMoreData() {
        return getData().size() < totalCount || getData().size() == 0 ;
    }

    @Override
    public void convert(BaseViewHolder holder, CsdnItem item) {
        holder.setText(R.id.csdn_list_item_text,item.getTitle()).
                setText(R.id.csdn_list_item_time,item.getTime());
    }
}

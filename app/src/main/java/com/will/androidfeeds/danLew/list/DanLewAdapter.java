package com.will.androidfeeds.danLew.list;

import android.content.Context;
import android.content.Intent;

import com.will.androidfeeds.R;
import com.will.androidfeeds.bean.DanLewItem;
import com.will.androidfeeds.common.ErrorCode;
import com.will.androidfeeds.customAdapter.BaseRecyclerViewHolder;
import com.will.androidfeeds.customAdapter.CustomRecyclerAdapter;
import com.will.androidfeeds.danLew.content.DanLewContentActivity;
import com.will.androidfeeds.util.JsoupHelper;
import com.will.androidfeeds.util.NetworkHelper;

import java.util.List;

/**
 * Created by Will on 2016/7/24.
 */
public class DanLewAdapter extends CustomRecyclerAdapter<DanLewItem> {
    private static final String HOST = "http://blog.danlew.net/page/";
    private NetworkHelper networkHelper = NetworkHelper.getInstance();
    private boolean hasMoreItem = true;
    private Context mContext;

    public DanLewAdapter (){
        super(R.layout.hukai_list_item,R.layout.list_loading_view,R.layout.list_loading_failed_view);
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClicked(Object item) {
                DanLewItem danLewItem = (DanLewItem) item;
                if(mContext == null){
                    mContext = getRecyclerView().getContext();
                }
                Intent intent = new Intent(mContext, DanLewContentActivity.class);
                intent.putExtra("url",danLewItem.getLink());
                mContext.startActivity(intent);
            }
        });
    }


    public void onRefresh(final OnRefreshCallback callback){
        networkHelper.loadWebSource(HOST + 1, false, true, new NetworkHelper.LoadWebSourceCallback() {
            @Override
            public void onSuccess(String source) {
                List<DanLewItem> list =JsoupHelper.getDanLewItemFromSource(source);
                hasMoreItem = list.size() == 5;
                refreshData(list);
                callback.onSuccess();
            }

            @Override
            public void onFailure(ErrorCode code) {
                callback.onFailure(code);
            }
        });
    }
    @Override
    public void convert(BaseRecyclerViewHolder holder, DanLewItem item) {
        holder.setText(R.id.hukai_item_preview,item.getPreview())
                .setText(R.id.hukai_item_time,item.getTime())
                .setText(R.id.hukai_item_title,item.getTitle());
    }

    @Override
    public boolean hasMoreData() {
        return hasMoreItem;
    }

    @Override
    public void loadData(int page) {
        String url = HOST + page;
        boolean cachePolicy = false;
        if(page == 1){
            cachePolicy = true;
        }
        networkHelper.loadWebSource(url, cachePolicy, cachePolicy, new NetworkHelper.LoadWebSourceCallback() {
            @Override
            public void onSuccess(String source) {
                List<DanLewItem> list = JsoupHelper.getDanLewItemFromSource(source);
                hasMoreItem = list.size() == 5;
                update(true,list);
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
}

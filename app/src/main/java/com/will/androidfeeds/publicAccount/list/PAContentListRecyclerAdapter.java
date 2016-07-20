package com.will.androidfeeds.publicAccount.list;

import android.content.Context;
import android.content.Intent;

import com.will.androidfeeds.R;
import com.will.androidfeeds.bean.PAItem;
import com.will.androidfeeds.common.ErrorCode;
import com.will.androidfeeds.customAdapter.BaseRecyclerViewHolder;
import com.will.androidfeeds.customAdapter.CustomRecyclerAdapter;
import com.will.androidfeeds.publicAccount.content.PAContentActivity;
import com.will.androidfeeds.util.JsoupHelper;
import com.will.androidfeeds.util.NetworkHelper;

import java.util.List;

/**
 * Created by Will on 2016/7/19.
 */
public class PAContentListRecyclerAdapter extends CustomRecyclerAdapter<PAItem> {
    private String host;
    private NetworkHelper networkHelper = NetworkHelper.getInstance();
    private boolean hasMoreItem = true;
    public PAContentListRecyclerAdapter(String url){
        super(R.layout.csdn_list_item,R.layout.list_loading_view,R.layout.list_loading_failed_view);
        host = url;
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClicked(Object item) {
                Context mContext = getRecyclerView().getContext();
                Intent intent = new Intent(mContext, PAContentActivity.class);
                intent.putExtra("url",((PAItem) item).getLink());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public void loadData(final int page) {
        String url;
        boolean cachePolicy = true;
        if(page != 1){
            url = host+(page-1)*12;
            cachePolicy = false;
        }else{
            url = host + 0;
        }
        networkHelper.loadWebSource(url, cachePolicy, cachePolicy, new NetworkHelper.LoadWebSourceCallback() {
            @Override
            public void onSuccess(String source) {
                final List<PAItem> item =  JsoupHelper.getPAItemFromSource(source);
                hasMoreItem = item.size() == 12;
                if(page == 1){
                    update(true,item);
                }else{
                    getRecyclerView().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            update(true,item);
                        }
                    },300);
                }
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
        return hasMoreItem;
    }
    public void onRefresh(final OnRefreshCallback callback){
        networkHelper.loadWebSource(host + 0, false, true, new NetworkHelper.LoadWebSourceCallback() {
            @Override
            public void onSuccess(String source) {
                List<PAItem> item =  JsoupHelper.getPAItemFromSource(source);
                refreshData(item);
                hasMoreItem =  item.size() == 12;
                callback.onSuccess();
            }

            @Override
            public void onFailure(ErrorCode code) {
                callback.onFailure(code);
            }
        });

    }

    @Override
    public void convert(BaseRecyclerViewHolder holder, PAItem item) {
        holder.setText(R.id.csdn_list_item_text,item.getTitle())
                .setText(R.id.csdn_list_item_time,item.getTime());
    }
}

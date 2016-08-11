package com.will.androidfeeds.droidyue.list;

import android.content.Context;
import android.content.Intent;
import android.text.Html;

import com.will.androidfeeds.R;
import com.will.androidfeeds.bean.DroidYueItem;
import com.will.androidfeeds.common.ErrorCode;
import com.will.androidfeeds.customAdapter.BaseRecyclerViewHolder;
import com.will.androidfeeds.customAdapter.CustomRecyclerAdapter;
import com.will.androidfeeds.droidyue.content.DroidYueContentActivity;
import com.will.androidfeeds.util.JsoupHelper;
import com.will.androidfeeds.util.NetworkHelper;

import java.util.List;

/**
 * Created by Will on 2016/7/20.
 */
public class DroidYueAdapter extends CustomRecyclerAdapter<DroidYueItem> {
    private static final String HOST = "http://droidyue.com";
    private static final String SURFFIX = "/blog/page/";
    private boolean hasMoreItem = true;
    private NetworkHelper networkHelper = NetworkHelper.getInstance();

    public DroidYueAdapter(){
        super(R.layout.hukai_list_item,R.layout.list_loading_view,R.layout.list_loading_failed_view);
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClicked(Object item) {
                DroidYueItem droidYueItem = (DroidYueItem) item;
                Context mContext = getRecyclerView().getContext();
                Intent intent = new Intent(mContext, DroidYueContentActivity.class);
                intent.putExtra("url",droidYueItem.getLink());
                intent.putExtra("title",droidYueItem.getTitle());
                mContext.startActivity(intent);
            }
        });
    }
    public void onRefresh(final OnRefreshCallback callback){
        networkHelper.loadWebSource(HOST, false, true, new NetworkHelper.LoadWebSourceCallback() {
            @Override
            public void onSuccess(String source) {
                List<DroidYueItem> list = JsoupHelper.getDroidYueItemFromSource(source);
                hasMoreItem = list.size() == 9;
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
    public void convert(BaseRecyclerViewHolder holder, DroidYueItem item) {
        holder.setText(R.id.hukai_item_time,item.getTime())
                .setText(R.id.hukai_item_title,item.getTitle())
                .setText(R.id.hukai_item_preview, Html.fromHtml(item.getPreview()));
    }

    @Override
    public void loadData(int page) {
        String url = HOST;
        boolean cachePolicy = false;
        if(page == 1){
            cachePolicy = true;
        }else{
            url = HOST + SURFFIX + page;
        }
        networkHelper.loadWebSource(url, cachePolicy, cachePolicy, new NetworkHelper.LoadWebSourceCallback() {
            @Override
            public void onSuccess(String source) {
                List<DroidYueItem> list = JsoupHelper.getDroidYueItemFromSource(source);
                hasMoreItem = list.size() == 9;
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

    @Override
    public boolean hasMoreData() {
        return hasMoreItem;
    }

}

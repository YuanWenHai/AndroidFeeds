package com.will.androidfeeds.stylingAndroid.list;

import android.content.Context;
import android.content.Intent;
import android.text.Html;

import com.will.androidfeeds.R;
import com.will.androidfeeds.bean.StylingAndroidItem;
import com.will.androidfeeds.common.ErrorCode;
import com.will.androidfeeds.customAdapter.BaseRecyclerViewHolder;
import com.will.androidfeeds.customAdapter.CustomRecyclerAdapter;
import com.will.androidfeeds.stylingAndroid.content.StylingAndroidContentActivity;
import com.will.androidfeeds.util.JsoupHelper;
import com.will.androidfeeds.util.NetworkHelper;

import java.util.List;

/**
 * Created by Will on 2016/7/21.
 */
public class StylingAndroidAdapter extends CustomRecyclerAdapter<StylingAndroidItem> {
    private static final String HOST = "https://blog.stylingandroid.com/page/";
    private NetworkHelper networkHelper = NetworkHelper.getInstance();
    private boolean hasMoreItem = true;
    public StylingAndroidAdapter(){
        super(R.layout.hukai_list_item,R.layout.list_loading_view,R.layout.list_loading_failed_view);
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClicked(Object item) {
                StylingAndroidItem stylingAndroidItem = (StylingAndroidItem) item;
                Context mContext = getRecyclerView().getContext();
                Intent intent = new Intent(mContext, StylingAndroidContentActivity.class);
                intent.putExtra("url",stylingAndroidItem.getLink());
                intent.putExtra("title",stylingAndroidItem.getTitle());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public void convert(BaseRecyclerViewHolder holder, StylingAndroidItem item) {
        holder.setText(R.id.hukai_item_preview, Html.fromHtml(item.getPreview()))
                .setText(R.id.hukai_item_title,item.getTitle())
                .setText(R.id.hukai_item_time,item.getTime());
    }

    @Override
    public void loadData(int page) {
        boolean cachePolicy = page == 1;
        networkHelper.loadWebSource(HOST + page, cachePolicy, cachePolicy, new NetworkHelper.LoadWebSourceCallback() {
            @Override
            public void onSuccess(String source) {
                List<StylingAndroidItem> list = JsoupHelper.getStylingAndroidItemFromSource(source);
                hasMoreItem = list.size() == 10;
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
    public void onRefresh(final OnRefreshCallback callback){
        networkHelper.loadWebSource(HOST + 1, false, true, new NetworkHelper.LoadWebSourceCallback() {
            @Override
            public void onSuccess(String source) {
                List<StylingAndroidItem> list = JsoupHelper.getStylingAndroidItemFromSource(source);
                hasMoreItem = list.size() == 10;
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
    public boolean hasMoreData() {
        return hasMoreItem;
    }

}

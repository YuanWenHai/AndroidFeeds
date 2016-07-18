package com.will.androidfeeds.hukai.list;

import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.will.androidfeeds.R;
import com.will.androidfeeds.bean.HKItem;
import com.will.androidfeeds.common.ErrorCode;
import com.will.androidfeeds.customadapter.BaseViewHolder;
import com.will.androidfeeds.customadapter.CustomAdapter;
import com.will.androidfeeds.util.JsoupHelper;
import com.will.androidfeeds.util.NetworkHelper;

/**
 * Created by Will on 2016/7/17.
 */
public  class HKListAdapter extends CustomAdapter<HKItem> {
    private static final String HUKAI_HOST = "http://hukai.me";
    private static final String HUKAI_LINK = "/blog/page/";
    private NetworkHelper networkHelper = NetworkHelper.getInstance();
    private boolean hasMoreData = true;
    public HKListAdapter(int layoutRes,int loadingViewRes){
        super(layoutRes,loadingViewRes);

        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClicked(Object item) {
                HKItem hkItem = (HKItem) item;
                Log.e("Item title",hkItem.getTitle());
            }
        });

    }
    public void onRefresh(final OnRefreshCallback callback){
        networkHelper.loadWebSource(HUKAI_HOST, false, true, new NetworkHelper.LoadWebSourceCallback() {
            @Override
            public void onSuccess(String source) {
                hasMoreData = JsoupHelper.hasMoreHKItem(source);
                refreshData(JsoupHelper.getHKItemFromSource(source));
                callback.onSuccess();

            }
            @Override
            public void onFailure(ErrorCode code) {
                callback.onFailure(code);
            }
        });
    }



    @Override
    public void loadData(int page) {
        String url = HUKAI_HOST;
        boolean useCache = true;
        if(page != 1){
            url = HUKAI_HOST  + HUKAI_LINK + page;
            useCache = false;
        }
        networkHelper.loadWebSource(url, useCache, useCache, new NetworkHelper.LoadWebSourceCallback() {
            @Override
            public void onSuccess(String source) {
                hasMoreData = JsoupHelper.hasMoreHKItem(source);
                update(true,JsoupHelper.getHKItemFromSource(source));
            }

            @Override
            public void onFailure(ErrorCode code) {
                update(false);
            }
        });
    }
    @Override
    public boolean hasMoreData() {
        return hasMoreData;
    }

    @Override
    public void convert(BaseViewHolder holder, HKItem item) {
        holder.setText(R.id.hukai_item_title,item.getTitle())
                .setText(R.id.hukai_item_time,item.getTime());
        TextView textView = (TextView) holder.getView(R.id.hukai_item_preview);
        textView.setText(Html.fromHtml(item.getPreview()));
    }
}

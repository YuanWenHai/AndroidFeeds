package com.will.androidfeeds.hukai.list;

import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.will.androidfeeds.R;
import com.will.androidfeeds.bean.HKItem;
import com.will.androidfeeds.common.ErrorCode;
import com.will.androidfeeds.util.JsoupHelper;
import com.will.androidfeeds.util.NetworkHelper;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.List;

/**
 * Created by Will on 2016/7/16.
 */
public  class HKListAdapter extends BaseQuickAdapter<HKItem> {
    private static final String HUKAI_HOST = "http://hukai.me";
    private List<HKItem> data;
    private boolean hasMoreItem;
    private NetworkHelper networkHelper = NetworkHelper.getInstance();
    public HKListAdapter(List<HKItem> data){
        super(R.layout.hukai_list_item,data);
        this.data  = data;
        loadData();
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, HKItem hkItem) {
        baseViewHolder.setText(R.id.hukai_item_title,hkItem.getTitle())
                .setText(R.id.hukai_item_time,hkItem.getTime());
        //((HtmlTextView)baseViewHolder.getView(R.id.hukai_item_preview)).setHtmlFromString(hkItem.getPreview(), null);
        HtmlTextView textView = baseViewHolder.getView(R.id.hukai_item_preview);
        textView.setHtmlFromStringWithHtmlImageGetter(hkItem.getPreview(),new URLImageParser(textView));
    }

    private void loadData(){
        networkHelper.loadWebSource(HUKAI_HOST, true, true, new NetworkHelper.LoadWebSourceCallback() {
            @Override
            public void onSuccess(String source) {
                data.addAll(JsoupHelper.getHKItemFromSource(source));
                hasMoreItem = JsoupHelper.hasMoreHKItem(source);
                notifyDataSetChanged();
                Log.e("time",data.get(0).getTime());
                Log.e("title",data.get(0).getTitle());
                Log.e("link",data.get(0).getLink());
                Log.e("size :",data.size()+"");
            }

            @Override
            public void onFailure(ErrorCode code) {

            }
        });
    }
}

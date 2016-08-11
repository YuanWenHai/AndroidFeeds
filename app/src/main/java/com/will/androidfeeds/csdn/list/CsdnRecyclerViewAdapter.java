package com.will.androidfeeds.csdn.list;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.will.androidfeeds.R;
import com.will.androidfeeds.bean.CsdnItem;
import com.will.androidfeeds.common.ErrorCode;
import com.will.androidfeeds.util.Tools;
import com.will.androidfeeds.util.JsoupHelper;
import com.will.androidfeeds.util.NetworkHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Will on 2016/7/12.
 */
public class CsdnRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private ArrayList<CsdnItem> data;
    private String url;
    private boolean isEnd;
    private int totalCount;
    private int pageIndex = 1;
    private boolean isLoading;
    private boolean isNetworkAvailable = true;
    private RecyclerView mRecyclerView;
    private NetworkHelper networkHelper = NetworkHelper.getInstance();
    private OnItemClickListener listener;
    public CsdnRecyclerViewAdapter(String url){
        this.url = url;
        data = new ArrayList<>();
    }
    @Override
    public int getItemViewType(int position){
        if(!isEnd && position == data.size() ){
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }
    @Override
    public int getItemCount(){
        if(!isEnd && isNetworkAvailable){
            return data.size()+1;
        }
        return data.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position){
        if(holder instanceof CsdnViewHolder){
            CsdnViewHolder csdnViewHolder = (CsdnViewHolder) holder;
            csdnViewHolder.text.setText(data.get(position).getTitle());
            csdnViewHolder.time.setText(data.get(position).getTime());
        }else if (pageIndex == 1){
            loadData(new ResultCallback() {
                @Override
                public void onSuccess() {
                    mRecyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            notifyDataSetChanged();

                        }
                    });
                }
                @Override
                public void onFailure(ErrorCode code) {
                    if(code == ErrorCode.CONNECTION_FAILED){
                        Tools.showToast("网络连接失败");
                    }
                    mRecyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            isNetworkAvailable = false;
                            notifyDataSetChanged();
                        }
                    });
                }
            });
        }else{
            loadMoreData(new ResultCallback() {
                @Override
                public void onSuccess() {
                    notifyDataSetChanged();
                }
                @Override
                public void onFailure(ErrorCode code) {
                    if(code == ErrorCode.CONNECTION_FAILED){
                        Tools.showToast("网络连接失败");
                        isNetworkAvailable = false;
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type){
        View v;
        if(type == TYPE_ITEM){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.csdn_list_item,parent,false);
            return new CsdnViewHolder(v);
        }else{
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_loading_view,parent,false);
            return new FooterViewHolder(v);
        }

    }

    class CsdnViewHolder extends RecyclerView.ViewHolder{
        private TextView text,time;
        public CsdnViewHolder(View v){
            super(v);
            text = (TextView) v.findViewById(R.id.csdn_list_item_text);
            time = (TextView) v.findViewById(R.id.csdn_list_item_time);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.onClick(data.get(getAdapterPosition()));
                    }
                }
            });
        }
    }
    class FooterViewHolder extends RecyclerView.ViewHolder{
        public FooterViewHolder(View v){
            super(v);
        }
    }
    public void setOnClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    private void loadData(final ResultCallback callback){
        Log.e("loadData","executed");
        networkHelper.loadWebSource(url+pageIndex,true,true, new NetworkHelper.LoadWebSourceCallback() {
            @Override
            public void onSuccess(String source) {
                List<CsdnItem> list = JsoupHelper.getCsdnListItemFromSource(source);
                totalCount = JsoupHelper.getCsdnListItemCount(source);
                data.addAll(list);
                //Log.e(data.get(0).getTitle(),data.size()+"");
                pageIndex++;
                isEnd = data.size() == totalCount;
                callback.onSuccess();
            }
            @Override
            public void onFailure(ErrorCode code) {
                callback.onFailure(code);
            }
        });
    }
    private void loadMoreData(final ResultCallback callback){
       if(!isEnd && !isLoading){
           isLoading = true;
           Log.e("loadMoreData","executed");
           networkHelper.loadWebSource(url + pageIndex, false, false, new NetworkHelper.LoadWebSourceCallback() {
               @Override
               public void onSuccess(String source) {
                   List<CsdnItem>  list = JsoupHelper.getCsdnListItemFromSource(source);
                   data.addAll(list);
                   pageIndex++;
                   isEnd = data.size() >= totalCount;
                   callback.onSuccess();
                   isLoading = false;
               }
               @Override
               public void onFailure(ErrorCode code) {
                   callback.onFailure(code);
                   isLoading = false;
               }
           });
       }
    }
    public void refreshData(final ResultCallback callback){
        if(!isLoading ){
            pageIndex = 2;
            isEnd = false;
            isLoading = true;
            isNetworkAvailable = true;
            networkHelper.loadWebSource(url + 1, false, true, new NetworkHelper.LoadWebSourceCallback() {
                @Override
                public void onSuccess(String source) {
                    data.clear();
                    List<CsdnItem>  list = JsoupHelper.getCsdnListItemFromSource(source);
                    data.addAll(list);
                    isEnd = data.size() == totalCount;
                    callback.onSuccess();
                    isLoading = false;
                }

                @Override
                public void onFailure(ErrorCode code) {
                    callback.onFailure(code);
                    isLoading = false;
                }
            });
        }

    }
    interface ResultCallback {
        void onSuccess();
        void onFailure(ErrorCode code);
    }
    interface OnItemClickListener{
        void onClick(CsdnItem item);
    }
}

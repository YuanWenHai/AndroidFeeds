package com.will.androidfeeds.customAdapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.will.androidfeeds.common.ErrorCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Will on 2016/7/20.
 */
public abstract class CustomListAdapter<T> extends BaseAdapter {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_LOADING = 1;
    private static final int TYPE_LOADING_FAILED = 2;
    private static final int TAG = 8888;
    private boolean isLoading = false;
    private boolean loadingSuccessful = true;
    private int page = 1;
    private int layoutRes;
    private int loadingViewRes;
    private int loadingFailedViewRes;
    private ListView mListView;
    private List<T> data = new ArrayList<>();
    private OnReloadClickListener reloadClickListener;

    public CustomListAdapter(int layoutRes,int loadingViewRes,int loadingFailedViewRes,ListView listView){
        this.layoutRes = layoutRes;
        this.loadingViewRes = loadingViewRes;
        this.loadingFailedViewRes = loadingFailedViewRes;
        mListView = listView;
    }

    protected abstract boolean hasMoreData();
    protected abstract void convert(BaseListViewHolder holder,T item);
    protected abstract void loadData(int page);
    public boolean isLoading(){
        return isLoading;
    }
    public List<T> getData(){
        return data;
    }
    private void reLoadData(){
        loadingSuccessful = true;
        notifyDataSetChanged();

    }
    public ListView getListView(){
        return mListView;
    }


    /**
     *  异步任务完成后将数据加载进data，并刷新显示
     * @param which 成功/失败,若为失败，则不递增load次数
     * @param newData 新数据
     * @return 添加后data的size
     */
    public int update(boolean which,List<T> newData){
        data.addAll(newData);
        update(which);
        return data.size();
    }

    /**
     *异步任务完成后将数据加载进data，并刷新显示
     * @param which 成功/失败,若为失败，则不递增load次数
     */
    public void update(boolean which){
        isLoading = false;
        loadingSuccessful = which;
        if(which){
            page++;
        }
        mListView.post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
                if(!loadingSuccessful){
                    mListView.smoothScrollToPosition(getCount()-1);
                }
            }
        });
    }
    @Override
    public int getCount() {
        return hasMoreData() ? data.size()+1 : data.size();
    }


    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        switch (getItemViewType(position)){
            case TYPE_ITEM:
                BaseListViewHolder holder;
                if(convertView == null){
                    convertView = LayoutInflater.from(parent.getContext()).inflate(layoutRes,parent,false);
                    holder = new BaseListViewHolder(convertView);
                    convertView.setTag(TAG,holder);
                }else{
                    Log.e("convert view != null!",(convertView != null)+"");
                    holder = (BaseListViewHolder) convertView.getTag(TAG);
                }
                convert(holder,data.get(position));
                return convertView;
            case TYPE_LOADING:
                if(!isLoading){
                    isLoading = true;
                    loadData(page);
                }
                if(convertView == null){
                    convertView = LayoutInflater.from(parent.getContext()).inflate(loadingViewRes,parent,false);
                }
                return convertView;
            case TYPE_LOADING_FAILED:
                if(convertView == null){
                    convertView = LayoutInflater.from(parent.getContext()).inflate(loadingFailedViewRes,parent,false);
                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(reloadClickListener == null){
                                reLoadData();
                            }else{
                                reloadClickListener.onReload();
                            }
                        }
                    });
                }
                return convertView;
            default:
                return  null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        if(hasMoreData() && position == data.size() ){
            return loadingSuccessful ? TYPE_LOADING : TYPE_LOADING_FAILED;
        }
        return TYPE_ITEM;
    }

    public interface OnRefreshCallback{
        void onSuccess();
        void onFailure(ErrorCode code);
    }
    public interface OnReloadClickListener{
        void onReload();
    }

}

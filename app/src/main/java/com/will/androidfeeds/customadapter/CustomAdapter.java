package com.will.androidfeeds.customadapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.will.androidfeeds.common.ErrorCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Will on 2016/7/17.
 */
public abstract class CustomAdapter <T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private ArrayList<T> data;
    private int pageIndex = 1;
    private boolean isLoading;
    private int layoutRes;
    private OnItemClickListener listener;
    private int loadingViewRes;
    private RecyclerView recyclerView;
    public CustomAdapter(int layoutRes,int loadingViewRes){
        data = new ArrayList<>();
        this.layoutRes = layoutRes;
        this.loadingViewRes = loadingViewRes;
    }

    /**
     * 返回是否有更多数据，这个返回值将影响到本次加载完后下次到末尾时是否刷新
     * @return 返回值
     */
    public abstract boolean hasMoreData();

    /**
     * <p>加载内容，当下拉至末尾时会调用此方法
     * @param page 加载页数，初始值为1</p>
     * <p>因为是加载内容，故多为异步加载，在异步任务完成后，务必调用{@link #update(boolean)} 提交更新，无论成功与否.</p>
     *
     */
    public abstract void loadData(int page);


    public abstract void convert(BaseViewHolder holder,T item );





    @Override
    public int getItemViewType(int position){
        if(hasMoreData() && position == data.size() ){
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount(){
        if(hasMoreData()){
            return data.size()+1;
        }
        return data.size();
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position){
        if(holder instanceof BaseViewHolder){
            convert((BaseViewHolder) holder,data.get(position));
        }else{
            if(!isLoading){
                isLoading = true;
                loadData(pageIndex);
            }
        }
    }

    public void setLoadingViewRes(int loadingViewRes){
        this.loadingViewRes = loadingViewRes;
    }


    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type){
        if(recyclerView == null){
            recyclerView = (RecyclerView) parent;
        }
        View v;
        if(type == TYPE_ITEM){
            v = LayoutInflater.from(parent.getContext()).inflate(layoutRes,parent,false);
            final BaseViewHolder holder = new BaseViewHolder(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.onItemClicked(data.get(holder.getAdapterPosition()));
                    }
                }
            });
            return holder;
        }else{
            v = LayoutInflater.from(parent.getContext()).inflate(loadingViewRes,parent,false);
            return new FooterViewHolder(v);
        }

    }
    public class FooterViewHolder extends RecyclerView.ViewHolder{

        public FooterViewHolder(View v){
            super(v);
        }
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
        if(which){
            pageIndex++;
        }
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }
    public List<T> getData(){
        return data;
    }

    /**
     * 清除已有数据重新获得，刷新显示
     * @param data data
     */
    public void refreshData(List<T> data){
        data.clear();
        pageIndex = 1;
        update(true,data);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    public interface OnItemClickListener{
        void onItemClicked(Object item);
    }
    public interface OnRefreshCallback{
        void onSuccess();
        void onFailure(ErrorCode code);
    }

}

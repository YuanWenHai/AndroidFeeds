package com.will.androidfeeds.favorite.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.will.androidfeeds.R;
import com.will.androidfeeds.util.Tools;

import java.util.Map;

/**
 * Created by Will on 2016/8/11.
 */
public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    private Map dataMap = Tools.getFavoriteSP().getAll();
    private Object[]  keyArray = dataMap.keySet().toArray();
    private OnItemClickListener listener;
    public FavoriteAdapter(){

    }
    @Override
    public int getItemCount() {
        return dataMap.size();
    }
    public void refreshData(){
        dataMap = Tools.getFavoriteSP().getAll();
        keyArray = dataMap.keySet().toArray();
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(FavoriteViewHolder holder, int position) {
        holder.title.setText((String)keyArray[position]);
    }

    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.csdn_list_item,parent,false);
        return new FavoriteViewHolder(v);
    }
    class FavoriteViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public FavoriteViewHolder(View v){
            super(v);
            title = (TextView) v.findViewById(R.id.csdn_list_item_text);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.onItemClick((String)keyArray[getAdapterPosition()],(String)dataMap.get(keyArray[getAdapterPosition()]));
                    }
                }
            });
        }
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    interface OnItemClickListener{
        void onItemClick(String title,String url);
    }
}

package com.will.androidfeeds.publicAccount.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.will.androidfeeds.R;
import com.will.androidfeeds.base.MyApplication;

/**
 * Created by Will on 2016/7/19.
 */
public class PAListAdapter extends BaseAdapter {
    private Context mContext = MyApplication.getGlobalContext();
    private String[] titleList = mContext.getResources().getStringArray(R.array.public_account);
    private String[] linkList = mContext.getResources().getStringArray(R.array.public_account_links);
    private int[] imageList = {R.drawable.guolin_blog,R.drawable.hongyangandroid,R.drawable.apkbus,R.drawable.googdev,
    R.drawable.gh_1322d5f620b9,R.drawable.androidtrending,R.drawable.ardays,R.drawable.anzhuocoder};
    @Override
    public int getCount() {
        return imageList.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.public_account_item,parent,false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.public_account_item_image);
            holder.textView = (TextView) convertView.findViewById(R.id.public_account_item_title);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.imageView.setImageResource(imageList[position]);
        holder.textView.setText(titleList[position]);

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }
    public String getLink(int position){
        return linkList[position-2];
    }
    public String getTitle(int position){
        return titleList[position-2];
    }
    class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}

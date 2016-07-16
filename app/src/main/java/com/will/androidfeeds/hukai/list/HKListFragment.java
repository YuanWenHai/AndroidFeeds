package com.will.androidfeeds.hukai.list;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.will.androidfeeds.R;
import com.will.androidfeeds.base.BaseFragment;
import com.will.androidfeeds.bean.HKItem;

import java.util.ArrayList;

/**
 * Created by Will on 2016/7/16.
 */
public class HKListFragment extends BaseFragment{
    private SwipeRefreshLayout refreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_hukai_list,container,false);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.hukai_refresh_layout);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.hukai_recycler_view);
        Toolbar toolbar = (Toolbar)view.findViewById(R.id.hukai_list_toolbar);
        HKListAdapter adapter = new HKListAdapter(new ArrayList<HKItem>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return view;
    }
}

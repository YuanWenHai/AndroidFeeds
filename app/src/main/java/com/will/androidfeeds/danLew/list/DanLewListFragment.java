package com.will.androidfeeds.danLew.list;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.will.androidfeeds.R;
import com.will.androidfeeds.base.BaseFragment;
import com.will.androidfeeds.common.ErrorCode;
import com.will.androidfeeds.customAdapter.CustomRecyclerAdapter;

/**
 * Created by Will on 2016/7/24.
 */
public class DanLewListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    private DanLewAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getInflaterWithTheme(inflater, R.style.DanLewTheme).inflate(R.layout.fragment_dan_lew_list,container,false);
        //View view = inflater.inflate(R.layout.fragment_dan_lew_list,container,false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.dan_lew_list_recycler_view);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.dan_lew_list_refresh_layout);
        refreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new DanLewAdapter();
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onRefresh() {
        adapter.onRefresh(new CustomRecyclerAdapter.OnRefreshCallback() {
            @Override
            public void onSuccess() {
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(ErrorCode code) {
                refreshLayout.setRefreshing(false);
            }
        });
    }
}

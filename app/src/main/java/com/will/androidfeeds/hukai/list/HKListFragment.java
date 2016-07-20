package com.will.androidfeeds.hukai.list;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.will.androidfeeds.R;
import com.will.androidfeeds.base.BaseFragment;
import com.will.androidfeeds.common.ErrorCode;
import com.will.androidfeeds.customAdapter.CustomRecyclerAdapter;

/**
 * Created by Will on 2016/7/16.
 */
public class HKListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    private SwipeRefreshLayout refreshLayout;
    private HKListRecyclerAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_hukai_list,container,false);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.hukai_refresh_layout);
        refreshLayout.setOnRefreshListener(this);

        Toolbar toolbar = (Toolbar)view.findViewById(R.id.hukai_list_toolbar);
        toolbar.setTitle("胡凯");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.hukai_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new HKListRecyclerAdapter(R.layout.hukai_list_item,R.layout.list_loading_view,R.layout.list_loading_failed_view);
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onRefresh() {
        mAdapter.onRefresh(new CustomRecyclerAdapter.OnRefreshCallback() {
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

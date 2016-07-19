package com.will.androidfeeds.csdn.list;

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
import com.will.androidfeeds.customAdapter.CustomAdapter;

/**
 * Created by Will on 2016/7/12.
 */
public class PagerFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    private CsdnListAdapter mAdapter;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    public static PagerFragment getInstance(String url){
        PagerFragment instance = new PagerFragment();
        Bundle args = new Bundle();
        args.putString("url",url);
        instance.setArguments(args);
        return instance;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        String url = (String) getArguments().get("url");
        View view = inflater.inflate(R.layout.fragment_csdn_viewpager_content,container,false);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.csdn_list_refresh_layout);
        refreshLayout.setOnRefreshListener(this);
        recyclerView = (RecyclerView) view.findViewById(R.id.csdn_list_recycler_view);
        mAdapter = new CsdnListAdapter(url);

        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onRefresh(){
        if(mAdapter.isLoading()){
            refreshLayout.setRefreshing(false);
            return;
        }
        mAdapter.onRefresh(new CustomAdapter.OnRefreshCallback() {
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



    public void scrollToTop(){
        recyclerView.smoothScrollToPosition(0);
    }

}

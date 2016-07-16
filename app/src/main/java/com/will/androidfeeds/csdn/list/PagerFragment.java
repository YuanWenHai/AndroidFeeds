package com.will.androidfeeds.csdn.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.will.androidfeeds.R;
import com.will.androidfeeds.base.BaseFragment;
import com.will.androidfeeds.bean.CsdnItem;
import com.will.androidfeeds.common.ErrorCode;
import com.will.androidfeeds.common.Tools;
import com.will.androidfeeds.csdn.content.CsdnContentActivity;

/**
 * Created by Will on 2016/7/12.
 */
public class PagerFragment extends BaseFragment implements CsdnRecyclerViewAdapter.ResultCallback,SwipeRefreshLayout.OnRefreshListener{
    private CsdnRecyclerViewAdapter mAdapter;
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
        mAdapter = new CsdnRecyclerViewAdapter(url);
        mAdapter.setOnClickListener(new CsdnRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onClick(CsdnItem item) {
                Intent intent = new Intent(getActivity(), CsdnContentActivity.class);
                intent.putExtra("url",item.getLink());
                intent.putExtra("title",item.getTitle());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                autoRefresh();
            }
        });

        return view;
    }

    @Override
    public void onRefresh(){
        mAdapter.refreshData(this);
    }



    //加载结果回调
    @Override
    public void onSuccess(){
        refreshLayout.setRefreshing(false);
        mAdapter.notifyDataSetChanged();
    }
    @Override
    public void onFailure(ErrorCode code){
        refreshLayout.setRefreshing(false);
        mAdapter.notifyDataSetChanged();
        showToast("网络连接失败");
    }



    public void scrollToTop(){
        recyclerView.smoothScrollToPosition(0);
    }

    private void autoRefresh(){
        if(Tools.isWiFiAvailable()){
            refreshLayout.setRefreshing(true);
            //至少保证一秒的动画时间，否则效果太差
            refreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mAdapter.refreshData(PagerFragment.this);
                }
            },1000);

        }
    }
}

package com.will.androidfeeds.droidyue.list;

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
 * Created by Will on 2016/7/20.
 */
public class DroidYueListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    private DroidYueAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getInflaterWithTheme(inflater,R.style.DroidYueTheme).inflate(R.layout.fragment_droid_yue_list,container,false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.droid_yue_list_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.droid_yue_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new DroidYueAdapter();
        recyclerView.setAdapter(adapter);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.droid_yue_list_refresh_layout);
        refreshLayout.setOnRefreshListener(this);
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

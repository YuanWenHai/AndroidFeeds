package com.will.androidfeeds.hukai.list;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.orhanobut.logger.Logger;
import com.will.androidfeeds.R;
import com.will.androidfeeds.base.BaseFragment;
import com.will.androidfeeds.base.MyOnScrollListener;
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
        Logger.e("onCreateView beginning");
        View view = inflater.inflate(R.layout.fragment_hukai_list,container,false);
        int parallaxHeight = getResources().getDimensionPixelSize(R.dimen.hukai_parallax_height);
        int colorPrimaryDark = getResources().getColor(R.color.hukai_purple_dark);
        View parallaxLayout = view.findViewById(R.id.hukai_list_parallax);
        View statusBar = view.findViewById(R.id.list_status_bar);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.hukai_refresh_layout);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setProgressViewOffset(true,parallaxHeight-50,parallaxHeight + getRefreshLayoutDragOffset());
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.hukai_purple));

        Toolbar toolbar = (Toolbar)view.findViewById(R.id.list_toolbar);
        setupToolbar(toolbar);
        toolbar.setTitle(getResources().getString(R.string.hukai));

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.hukai_recycler_view);
        RecyclerViewHeader header = (RecyclerViewHeader) view.findViewById(R.id.hukai_list_header);
        TextView about = (TextView) header.findViewById(R.id.hukai_list_about);
        about.setText(getText(R.string.hukai_about));
        about.setMovementMethod(LinkMovementMethod.getInstance());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        header.attachTo(recyclerView);
        mAdapter = new HKListRecyclerAdapter(R.layout.hukai_list_item,R.layout.list_loading_view,R.layout.list_loading_failed_view);
        recyclerView.setAdapter(mAdapter);
        MyOnScrollListener onScrollListener = new MyOnScrollListener(parallaxHeight,colorPrimaryDark,parallaxLayout,toolbar,statusBar);
        recyclerView.addOnScrollListener(onScrollListener);
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                //((MainActivity)getActivity()).showContainer();
                //((MainActivity)getActivity()).hideLoading();
                //((MainActivity)getActivity()).closeDrawer();
            }
        });
        Logger.e("onCreateView end");
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

package com.will.androidfeeds.danLew.list;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.will.androidfeeds.R;
import com.will.androidfeeds.base.BaseFragment;
import com.will.androidfeeds.base.MyOnScrollListener;
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
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.dan_lew_list_recycler_view);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.dan_lew_list_refresh_layout);
        View statusBar = view.findViewById(R.id.list_status_bar);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.list_toolbar);
        View parallaxLayout = view.findViewById(R.id.dan_lew_list_parallax);
        int parallaxHeight = getResources().getDimensionPixelSize(R.dimen.dan_lew_parallax_height);
        int colorPrimaryDark = getResources().getColor(R.color.dan_lew_color_dark);
        RecyclerViewHeader header = (RecyclerViewHeader) view.findViewById(R.id.dan_lew_list_header);

        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setProgressViewOffset(true,parallaxHeight-50,parallaxHeight+getRefreshLayoutDragOffset());
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.dan_lew_color));

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        header.attachTo(recyclerView);
        adapter = new DanLewAdapter();
        recyclerView.setAdapter(adapter);
        MyOnScrollListener onScrollListener = new MyOnScrollListener(parallaxHeight,colorPrimaryDark,parallaxLayout,toolbar,statusBar);
        recyclerView.addOnScrollListener(onScrollListener);

        toolbar.setTitle(getResources().getString(R.string.dan_lew));
        setupToolbar(toolbar);

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

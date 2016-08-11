package com.will.androidfeeds.stylingAndroid.list;

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
 * Created by Will on 2016/7/21.
 */
public class StylingAndroidListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    private StylingAndroidAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getInflaterWithTheme(inflater, R.style.StylingAndroidTheme).
                inflate(R.layout.fragment_styling_android_list,container,false);
        int parallaxHeight = getResources().getDimensionPixelSize(R.dimen.styling_android_parallax_height);
        View parallaxLayout =  view.findViewById(R.id.list_parallax);
        View statusBar = view.findViewById(R.id.list_status_bar);
        int colorPrimaryDark = getResources().getColor(R.color.styling_android_color_dark);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.list_toolbar);


        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.styling_android_list_refresh_layout);
        refreshLayout.setProgressViewOffset(true,parallaxHeight-50,parallaxHeight+getRefreshLayoutDragOffset());
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.styling_android_color));


        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.styling_android_list_recycler_view);
        RecyclerViewHeader header = (RecyclerViewHeader) view.findViewById(R.id.styling_android_list_header);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        header.attachTo(recyclerView);
        adapter = new StylingAndroidAdapter();
        recyclerView.setAdapter(adapter);
        MyOnScrollListener onScrollListener = new MyOnScrollListener(parallaxHeight,colorPrimaryDark,parallaxLayout,toolbar,statusBar);
        recyclerView.addOnScrollListener(onScrollListener);
        /*
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                scrolledHeight += dy;
                Log.e("dy",scrolledHeight+"");

                if(scrolledHeight < parallaxHeight){
                    float alpha = Math.min(1, (float) scrolledHeight / parallaxHeight);
                    parallaxLayout.setTranslationY(scrolledHeight/2);
                    toolbar.setAlpha(alpha);
                    if(alpha == 0 ){
                        toolbar.setVisibility(View.INVISIBLE);
                    }else if (toolbar.getVisibility() == View.INVISIBLE){
                        toolbar.setVisibility(View.VISIBLE);
                    }
                    statusBar.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha,statusBarColor));
                }else if (scrolledHeight < parallaxHeight*2){
                    toolbar.setAlpha(1);
                    statusBar.setBackgroundColor(statusBarColor);
                    toolbar.setTitle(getResources().getString(R.string.styling_android));
                }
            }
        });*/


        setupToolbar(toolbar);
        toolbar.setTitle(getResources().getString(R.string.styling_android));



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

package com.will.androidfeeds;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.will.androidfeeds.base.BaseFragment;
import com.will.androidfeeds.droidyue.list.DroidYueAdapter;

/**
 * Created by Will on 2016/7/29.
 */
public class TestFragment extends BaseFragment {
    Toolbar toolbar;
    RecyclerViewHeader parallax;
    RelativeLayout imageView;
    View statusBar;
    int parallaxHeight = (200)*4;
    int scrolledHeight = 0;
    AppCompatActivity mActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.fragment_test,container,false);
        View view = getInflaterWithTheme(inflater,R.style.StylingAndroidTheme).inflate(R.layout.fragment_test,container,false);
        SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.test_refresh);
        refreshLayout.setProgressViewOffset(true,800,1000);
        toolbar = (Toolbar) view.findViewById(R.id.test_toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        parallax = (RecyclerViewHeader) view.findViewById(R.id.test_parallax);
        imageView = (RelativeLayout) view.findViewById(R.id.test_image);
        statusBar = view.findViewById(R.id.status_bar);
        toolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(0,getResources().getColor(R.color.colorPrimary)));
        statusBar.setBackgroundColor(ScrollUtils.getColorWithAlpha(0,getResources().getColor(R.color.colorPrimary)));
        mActivity = ((AppCompatActivity)getActivity());
        setupToolbar(toolbar);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.test_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        parallax.attachTo(recyclerView);
        recyclerView.setAdapter(new DroidYueAdapter());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                scrolledHeight += dy;
                Log.e("dy",scrolledHeight+"");
                int toolbarColor = getResources().getColor(R.color.colorPrimary);
                int statusBarColor= getResources().getColor(R.color.colorPrimaryDark);
                if(scrolledHeight < parallaxHeight){
                    float alpha = Math.min(1, (float) scrolledHeight / parallaxHeight);
                    imageView.setTranslationY(scrolledHeight/2);
                    toolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, toolbarColor));
                    statusBar.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha,statusBarColor));
                }else{
                    toolbar.setBackgroundColor(toolbarColor);
                    statusBar.setBackgroundColor(statusBarColor);
                }
            }
        });
        return view;
    }
}

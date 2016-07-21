package com.will.androidfeeds.stylingAndroid.list;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.will.androidfeeds.R;
import com.will.androidfeeds.base.BaseFragment;
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
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.styling_android_list_recycler_view);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.styling_android_list_refresh_layout);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeColors(R.color.styling_android_color);
        adapter = new StylingAndroidAdapter();
        final TextView textView = (TextView) view.findViewById(R.id.styling_android_list_text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if( textView.getMaxLines() == 4){
                  textView.setMaxLines(100);
                  Drawable arrowUp = getResources().getDrawable(R.drawable.ic_arrow_drop_up_white_24dp);
                  arrowUp.setBounds(0, 0, arrowUp.getMinimumWidth(), arrowUp.getMinimumHeight());
                  textView.setCompoundDrawables(null, null, null,arrowUp);
              }else{
                  textView.setMaxLines(4);
                  Drawable arrowUp = getResources().getDrawable(R.drawable.ic_arrow_drop_down_white_24dp);
                  arrowUp.setBounds(0, 0, arrowUp.getMinimumWidth(), arrowUp.getMinimumHeight());
                  textView.setCompoundDrawables(null, null, null,arrowUp);
              }

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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

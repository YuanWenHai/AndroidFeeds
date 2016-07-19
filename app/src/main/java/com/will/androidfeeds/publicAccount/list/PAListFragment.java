package com.will.androidfeeds.publicAccount.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.github.ksoichiro.android.observablescrollview.ObservableGridView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.will.androidfeeds.R;
import com.will.androidfeeds.base.BaseFragment;

/**
 * Created by Will on 2016/7/19.
 */
public class PAListFragment extends BaseFragment implements ObservableScrollViewCallbacks{
    Toolbar toolbar;
    ImageView imageView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_p_a_list,container,false);
        ObservableGridView gridView = (ObservableGridView) view.findViewById(R.id.list);
        imageView = (ImageView) view.findViewById(R.id.image);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.colorPrimary)));
        int parallaxHeight = 720;
        gridView.setScrollViewCallbacks(this);
        View paddingView = new View(getActivity());
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                parallaxHeight);
        paddingView.setLayoutParams(lp);
        paddingView.setClickable(true);
        gridView.addHeaderView(paddingView);
        final PAListAdapter adapter = new PAListAdapter();
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),PAContentList.class);
                intent.putExtra("url",adapter.getLink(position));
                intent.putExtra("title",adapter.getTitle(position));
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        int baseColor = getResources().getColor(R.color.android_green);
        float alpha = Math.min(1, (float) scrollY / 720);
        toolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));
        imageView.setTranslationY(-scrollY / 2);
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }
}

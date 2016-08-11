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
import com.will.androidfeeds.R;
import com.will.androidfeeds.base.BaseFragment;

/**
 * Created by Will on 2016/7/19.
 */
public class PAListFragment extends BaseFragment implements ObservableScrollViewCallbacks{
    Toolbar toolbar;
    ImageView imageView;
    View statusBar;
    int parallaxHeight;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View view = getInflaterWithTheme(inflater,R.style.PublicAccountTheme).inflate(R.layout.fragment_p_a_list,container,false);
        ObservableGridView gridView = (ObservableGridView) view.findViewById(R.id.list);
        imageView = (ImageView) view.findViewById(R.id.image);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.public_account));
        setupToolbar(toolbar);


        View paddingView = new View(getActivity());
        statusBar = view.findViewById(R.id.status_bar);
        parallaxHeight = getResources().getDimensionPixelSize(R.dimen.public_account_parallax_height);
        gridView.setScrollViewCallbacks(this);
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
        float alpha = Math.min(1, (float) scrollY / parallaxHeight);
        toolbar.setAlpha(alpha);
        imageView.setTranslationY(-scrollY / 2);
        statusBar.setAlpha(alpha);
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }
}

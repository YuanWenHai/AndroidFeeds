package com.will.androidfeeds.favorite.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.will.androidfeeds.R;
import com.will.androidfeeds.base.BaseFragment;
import com.will.androidfeeds.favorite.content.FavoriteContentActivity;

/**
 * Created by Will on 2016/8/11.
 */
public class FavoriteListFragment extends BaseFragment {
    FavoriteAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getInflaterWithTheme(inflater, R.style.FavoriteTheme).inflate(R.layout.fragment_favorite_list,container,false);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.favorite_recycler_view);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.favorite_toolbar);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.favorite_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.smoothScrollToPosition(0);
            }
        });

        setupToolbar(toolbar);
        toolbar.setAlpha(1);
        toolbar.setTitle(getResources().getString(R.string.favorite));

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new FavoriteAdapter();
        adapter.setOnItemClickListener(new FavoriteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String title, String url) {
                Intent intent = new Intent(getActivity(), FavoriteContentActivity.class);
                intent.putExtra("title",title);
                intent.putExtra("url",url);
                startActivityForResult(intent,1);
            }
        });
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if(!hidden){
            adapter.refreshData();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       if(requestCode == 1 && resultCode == 888){
           adapter.refreshData();
       }
    }
}

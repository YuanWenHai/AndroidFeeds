package com.will.androidfeeds.droidyue.list;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.will.androidfeeds.R;
import com.will.androidfeeds.base.BaseFragment;

/**
 * Created by Will on 2016/7/20.
 */
public class DroidYueListFragment extends BaseFragment {
    private Toolbar toolbar;
    private ImageView parallaxView;
    private DroidYueAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_droid_yue_list,container,false);
        toolbar = (Toolbar) view.findViewById(R.id.droid_yue_list_toolbar);
        parallaxView = (ImageView) view.findViewById(R.id.droid_yue_list_parallax_image);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.droid_yue_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new DroidYueAdapter();
        recyclerView.setAdapter(adapter);
        return view;
    }


}

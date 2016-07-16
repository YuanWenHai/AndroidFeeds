package com.will.androidfeeds.csdn.list;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.will.androidfeeds.R;
import com.will.androidfeeds.base.BaseFragment;

/**
 * Created by Will on 2016/7/7.
 */
public class CsdnListFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_csdn_list,container,false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.csdn_toolbar);
        toolbar.setTitle("CSDN Blog");

        final TabLayout tabLayout = (TabLayout) view.findViewById(R.id.csdn_tab_layout);

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.csdn_viewpager);
        viewPager.setOffscreenPageLimit(3);
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.csdn_list_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PagerFragment)adapter.getFragment(viewPager.getCurrentItem())).scrollToTop();
            }
        });
        return view;
    }

}

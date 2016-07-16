package com.will.androidfeeds.csdn.list;

import android.app.Fragment;
import android.app.FragmentManager;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.will.androidfeeds.R;
import com.will.androidfeeds.base.MyApplication;

/**
 * Created by Will on 2016/7/12.
 */
public class ViewPagerAdapter extends android.support.v13.app.FragmentPagerAdapter {
    private String[] urls = MyApplication.getGlobalContext().getResources().getStringArray(R.array.csdn_blog_url);
    private String[] authors = MyApplication.getGlobalContext().getResources().getStringArray(R.array.csdn_author_array);
    private SparseArray<Fragment> fragments = new SparseArray<>();
    @Override
    public Fragment getItem(int index){
        return  PagerFragment.getInstance(urls[index]);
    }
    @Override
    public int getCount(){
        return urls.length;
    }
    public ViewPagerAdapter(FragmentManager manager){
        super(manager);
    }
    @Override
    public String getPageTitle(int position){
        return authors[position];
    }
    @Override
    public Object instantiateItem(ViewGroup container,int position){
        Fragment fragment = (Fragment) super.instantiateItem(container,position);
        fragments.put(position,fragment);
        return fragment;
    }
    @Override
    public void destroyItem(ViewGroup container,int position,Object object){
        super.destroyItem(container,position,object);
        fragments.remove(position);
    }
    public Fragment getFragment(int position){
        return fragments.get(position);
    }
}

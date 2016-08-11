package com.will.androidfeeds.base;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;

import com.will.androidfeeds.MainActivity;
import com.will.androidfeeds.R;

/**
 * Created by Will on 2016/7/7.
 */
public class BaseFragment extends Fragment {

    public LayoutInflater getInflaterWithTheme(LayoutInflater inflater,int style){
        Context contextWithTheme = new ContextThemeWrapper(getActivity(), style);
        return inflater.cloneInContext(contextWithTheme);
    }
    public void setupToolbar(Toolbar toolbar){
        AppCompatActivity mActivity = (AppCompatActivity) getActivity();
        mActivity.setSupportActionBar(toolbar);
        toolbar.setAlpha(0);
        //mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).openDrawer();
            }
        });
    }
    public int getRefreshLayoutDragOffset(){
        return getResources().getDimensionPixelSize(R.dimen.refresh_layout_offset);
    }
    public int getHeaderMargin(){
        return getResources().getDimensionPixelSize(R.dimen.header_margin);
    }
}

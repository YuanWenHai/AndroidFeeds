package com.will.androidfeeds;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;

import com.will.androidfeeds.base.BaseActivity;
import com.will.androidfeeds.common.Const;
import com.will.androidfeeds.util.FragmentSwitcher;

public class MainActivity extends BaseActivity {
    private FragmentSwitcher switcher;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        switcher = new FragmentSwitcher(getFragmentManager());
        switcher.switchFragment(Const.FRAGMENT_HUKAI);
    }
    @SuppressWarnings("all")
    private void initViews(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_csdn:
                        switcher.switchFragment(Const.FRAGMENT_CSDN);
                        break;
                    case R.id.navigation_hukai:
                        switcher.switchFragment(Const.FRAGMENT_HUKAI);
                        break;
                }
                return true;
            }
        });
    }
}

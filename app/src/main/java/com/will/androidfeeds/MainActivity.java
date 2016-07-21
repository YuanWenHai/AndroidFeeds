package com.will.androidfeeds;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;

import com.will.androidfeeds.base.BaseActivity;
import com.will.androidfeeds.common.Const;
import com.will.androidfeeds.stylingAndroid.list.StylingAndroidListFragment;
import com.will.androidfeeds.util.FragmentSwitcher;

public class MainActivity extends BaseActivity {
    private FragmentSwitcher switcher;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        switcher = new FragmentSwitcher(getFragmentManager());
        //switcher.switchFragment(Const.FRAGMENT_PUBLIC_ACCOUNT);
        getFragmentManager().beginTransaction().add(R.id.fragment_container,new StylingAndroidListFragment()).commit();
    }
    @SuppressWarnings("all")
    private void initViews(){
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
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
                    case R.id.navigation_public_account:
                        switcher.switchFragment(Const.FRAGMENT_PUBLIC_ACCOUNT);
                        break;
                    case R.id.navigation_droid_yue:
                        switcher.switchFragment(Const.FRAGMENT_DROID_YUE);
                        break;
                    case R.id.navigation_styling_android:
                        switcher.switchFragment(Const.FRAGMENT_STYLING_ANDROID);
                        break;
                }
                if(drawerLayout.isDrawerOpen(Gravity.LEFT)){
                    drawerLayout.closeDrawers();
                }
                return true;
            }
        });
    }
}

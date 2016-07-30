package com.will.androidfeeds;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.will.androidfeeds.base.BaseActivity;
import com.will.androidfeeds.base.LoadingFragment;
import com.will.androidfeeds.common.Const;
import com.will.androidfeeds.util.FragmentSwitcher;

public class MainActivity extends BaseActivity {
    private FragmentSwitcher switcher;
    private View container;
    private DrawerLayout drawerLayout;
    private int selectedItem = Const.FRAGMENT_CSDN;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = findViewById(R.id.fragment_container);
        initViews();
        switcher = new FragmentSwitcher(getFragmentManager());
        //getFragmentManager().beginTransaction().add(R.id.fragment_container,new TestFragment(),"loading").commit();
        switcher.switchFragment(Const.FRAGMENT_CSDN);
        //getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentByTag("loading")).commit();


    }
    @SuppressWarnings("all")
    private void initViews(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if(drawerLayout.isDrawerOpen(Gravity.LEFT)){
                    drawerLayout.closeDrawers();
                }
                switch (item.getItemId()){
                    case R.id.navigation_csdn:
                        //switcher.switchFragment(Const.FRAGMENT_CSDN);
                        selectedItem = Const.FRAGMENT_CSDN;
                        break;
                    case R.id.navigation_hukai:
                        //switcher.switchFragment(Const.FRAGMENT_HUKAI);
                        selectedItem = Const.FRAGMENT_HUKAI;
                        break;
                    case R.id.navigation_public_account:
                        //switcher.switchFragment(Const.FRAGMENT_PUBLIC_ACCOUNT);
                        selectedItem = Const.FRAGMENT_PUBLIC_ACCOUNT;
                        break;
                    case R.id.navigation_droid_yue:
                        //switcher.switchFragment(Const.FRAGMENT_DROID_YUE);
                        selectedItem = Const.FRAGMENT_DROID_YUE;
                        break;
                    case R.id.navigation_styling_android:
                        //switcher.switchFragment(Const.FRAGMENT_STYLING_ANDROID);
                        selectedItem = Const.FRAGMENT_STYLING_ANDROID;
                        break;
                    case R.id.navigation_dan_lew:
                        //switcher.switchFragment(Const.FRAGMENT_DANLEW);
                        selectedItem = Const.FRAGMENT_DANLEW;
                }
                //hideContainer();
                //showLoading();
                return true;
            }
        });
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                switcher.switchFragment(selectedItem);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }
    public void showContainer(){
        container.setVisibility(View.VISIBLE);
    }
    public void hideContainer(){
        container.setVisibility(View.INVISIBLE);
    }
    public void showLoading(){
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.animator_in,R.animator.animator_out)
                .add(R.id.fragment_loading_container,new LoadingFragment(),"loading")
                .commit();
    }
    public void hideLoading(){
        getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentByTag("loading")).commit();
    }
    public void closeDrawer(){
        if(drawerLayout.isDrawerOpen(Gravity.LEFT)){
            drawerLayout.closeDrawers();
        }
    }
}

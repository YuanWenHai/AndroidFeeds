package com.will.androidfeeds.util;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Handler;
import android.os.Looper;

import com.orhanobut.logger.Logger;
import com.will.androidfeeds.R;
import com.will.androidfeeds.common.Const;
import com.will.androidfeeds.csdn.list.CsdnListFragment;
import com.will.androidfeeds.danLew.list.DanLewListFragment;
import com.will.androidfeeds.droidyue.list.DroidYueListFragment;
import com.will.androidfeeds.hukai.list.HKListFragment;
import com.will.androidfeeds.publicAccount.list.PAListFragment;
import com.will.androidfeeds.stylingAndroid.list.StylingAndroidListFragment;

/**
 * Created by Will on 2016/7/15.
 */
public class FragmentSwitcher {
    private FragmentManager mFragmentManager;
    private Handler mHandler;
    public FragmentSwitcher(FragmentManager manager){
        mFragmentManager = manager;
        mHandler = new Handler(Looper.getMainLooper());
    }
    private static final int[] fragments = {Const.FRAGMENT_CSDN,Const.FRAGMENT_HUKAI,
            Const.FRAGMENT_PUBLIC_ACCOUNT,Const.FRAGMENT_DROID_YUE,
            Const.FRAGMENT_STYLING_ANDROID,Const.FRAGMENT_DANLEW};
    public void switchFragment(int which){
        Fragment fragment;
        switch (which){
            case Const.FRAGMENT_CSDN:

                if((fragment = getFragment(which)) != null){
                    mFragmentManager.beginTransaction()
                            .setCustomAnimations(R.animator.animator_in,R.animator.animator_out)
                            .show(fragment).commit();
                }else{
                    /*mFragmentManager.beginTransaction()
                            .setCustomAnimations(R.animator.animator_in,R.animator.animator_out)
                            .add(R.id.fragment_loading_container,new LoadingFragment(),"loading")
                            .commit();*/
                    mFragmentManager.beginTransaction()
                            .setCustomAnimations(R.animator.animator_in,R.animator.animator_out)
                            .add(R.id.fragment_container,new CsdnListFragment(),"csdn")
                            .commit();
                }
                closeOtherFragment(which);
                break;

            case Const.FRAGMENT_HUKAI:

                if((fragment = getFragment(which)) != null){
                    mFragmentManager.beginTransaction()
                            .setCustomAnimations(R.animator.animator_in,R.animator.animator_out)
                            .show(fragment).commit();
                }else{
                    Logger.e("switcher before create");
                    mFragmentManager.beginTransaction()
                            .setCustomAnimations(R.animator.animator_in,R.animator.animator_out)
                            .add(R.id.fragment_container,new HKListFragment(),"hukai").commit();
                    Logger.e("switcher after create");
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Logger.e("post execute");
                        }
                    });
                }
                closeOtherFragment(which);
                break;
            case Const.FRAGMENT_PUBLIC_ACCOUNT:
                if((fragment = getFragment(which)) != null){
                    mFragmentManager.beginTransaction()
                            .setCustomAnimations(R.animator.animator_in,R.animator.animator_out)
                            .show(fragment).commit();
                }else{
                    mFragmentManager.beginTransaction()
                            .setCustomAnimations(R.animator.animator_in,R.animator.animator_out)
                            .add(R.id.fragment_container,new PAListFragment(),"public_account").commit();
                }
                closeOtherFragment(which);
                break;
            case Const.FRAGMENT_DROID_YUE:
                if((fragment = getFragment(which)) != null){
                    mFragmentManager.beginTransaction().show(fragment).commit();
                }else{
                    mFragmentManager.beginTransaction()
                            .setCustomAnimations(R.animator.animator_in,R.animator.animator_out)
                            .add(R.id.fragment_container,new DroidYueListFragment(),"droid_yue").commit();
                }
                closeOtherFragment(which);
                break;
            case Const.FRAGMENT_STYLING_ANDROID:
                if((fragment = getFragment(which)) != null){
                    mFragmentManager.beginTransaction()
                            .setCustomAnimations(R.animator.animator_in,R.animator.animator_out)
                            .show(fragment).commit();
                }else{
                    mFragmentManager.beginTransaction()
                            .setCustomAnimations(R.animator.animator_in,R.animator.animator_out)
                            .add(R.id.fragment_container,new StylingAndroidListFragment(),"styling_android").commit();
                }
                closeOtherFragment(which);
                break;
            case Const.FRAGMENT_DANLEW:
                if((fragment = getFragment(which)) != null){
                    mFragmentManager.beginTransaction()
                            .setCustomAnimations(R.animator.animator_in,R.animator.animator_out)
                            .show(fragment).commit();
                }else{
                    mFragmentManager.beginTransaction()
                            .setCustomAnimations(R.animator.animator_in,R.animator.animator_out)
                            .add(R.id.fragment_container,new DanLewListFragment(),"dan_lew").commit();
                }
                closeOtherFragment(which);
                break;
        }
    }

    public void closeOtherFragment(final int which){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Fragment fragment;
                for(int code : fragments){
                    if(code != which){
                        if((fragment = getFragment(code)) != null){
                            mFragmentManager.beginTransaction()
                                    .setCustomAnimations(R.animator.animator_in,R.animator.animator_out)
                                    .hide(fragment).commit();
                        }
                    }
                }
            }
        },0);

    }
    public Fragment getFragment(int which){
        Fragment fragment = null;
        switch (which){
            case Const.FRAGMENT_CSDN:
                 fragment = mFragmentManager.findFragmentByTag("csdn");
                break;
            case Const.FRAGMENT_HUKAI:
                fragment = mFragmentManager.findFragmentByTag("hukai");
                break;
            case Const.FRAGMENT_PUBLIC_ACCOUNT:
                fragment = mFragmentManager.findFragmentByTag("public_account");
                break;
            case Const.FRAGMENT_DROID_YUE:
                fragment = mFragmentManager.findFragmentByTag("droid_yue");
                break;
            case Const.FRAGMENT_STYLING_ANDROID:
                fragment = mFragmentManager.findFragmentByTag("styling_android");
                break;
            case Const.FRAGMENT_DANLEW:
                fragment = mFragmentManager.findFragmentByTag("dan_lew");
                break;
        }
        return fragment ;
    }
}

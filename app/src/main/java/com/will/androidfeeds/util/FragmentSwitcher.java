package com.will.androidfeeds.util;

import android.app.Fragment;
import android.app.FragmentManager;

import com.will.androidfeeds.R;
import com.will.androidfeeds.common.Const;
import com.will.androidfeeds.csdn.list.CsdnListFragment;
import com.will.androidfeeds.hukai.list.HKListFragment;
import com.will.androidfeeds.publicAccount.list.PAListFragment;

/**
 * Created by Will on 2016/7/15.
 */
public class FragmentSwitcher {
    private FragmentManager mFragmentManager;
    public FragmentSwitcher(FragmentManager manager){
        mFragmentManager = manager;
    }
    private static final int[] fragments = {Const.FRAGMENT_CSDN,Const.FRAGMENT_HUKAI,Const.FRAGMENT_PUBLIC_ACCOUNT};
    public void switchFragment(int which){
        Fragment fragment;
        switch (which){
            case Const.FRAGMENT_CSDN:

                if((fragment = getFragment(which)) != null){
                    mFragmentManager.beginTransaction().show(fragment).commit();
                }else{
                    mFragmentManager.beginTransaction().add(R.id.fragment_container,new CsdnListFragment(),"csdn").commit();
                }
                closeOtherFragment(which);
                break;

            case Const.FRAGMENT_HUKAI:

                if((fragment = getFragment(which)) != null){
                    mFragmentManager.beginTransaction().show(fragment).commit();
                }else{
                    mFragmentManager.beginTransaction().add(R.id.fragment_container,new HKListFragment(),"hukai").commit();
                }
                closeOtherFragment(which);
                break;
            case Const.FRAGMENT_PUBLIC_ACCOUNT:
                if((fragment = getFragment(which)) != null){
                    mFragmentManager.beginTransaction().show(fragment).commit();
                }else{
                    mFragmentManager.beginTransaction().add(R.id.fragment_container,new PAListFragment(),"public_account").commit();
                }
                closeOtherFragment(which);
                break;
        }
    }

    public void closeOtherFragment(int which){
        Fragment fragment;
        for(int code : fragments){
            if(code != which){
                if((fragment = getFragment(code)) != null){
                    mFragmentManager.beginTransaction().hide(fragment).
                            setCustomAnimations(R.animator.animator_in,R.animator.animator_out).commit();
                }
            }
        }

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
        }
        return fragment ;
    }
}

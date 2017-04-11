package com.domker.androidtoy.fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by wanlipeng on 2017/4/11.
 */

public final class FragmentDispatchManager {
    private int mContainerId;
    private FragmentManager mFragmentManager;

    public FragmentDispatchManager(int containerId, FragmentManager fragmentManager) {
        this.mContainerId = containerId;
        this.mFragmentManager = fragmentManager;
    }

    /**
     * 显示界面
     *
     * @param fragment
     */
    public void showFragment(BaseFragment fragment) {
        if (fragment == null) {
            return;
        }
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(mContainerId, fragment);
        transaction.commitAllowingStateLoss();
        fragment.onShow();
    }
}

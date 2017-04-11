package com.domker.androidtoy.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.domker.androidtoy.R;
import com.domker.androidtoy.fragment.BaseFragment;
import com.domker.androidtoy.fragment.ControlMenuFragment;
import com.domker.androidtoy.fragment.FragmentDispatchManager;

public class ControlActivity extends FragmentActivity {
    private FragmentDispatchManager fragmentManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.control_container_layout);
        fragmentManager = new FragmentDispatchManager(R.id.contain_id, getSupportFragmentManager());
        showMenu();
    }


    /**
     * 显示菜单
     */
    private void showMenu() {
        BaseFragment menuFragment = new ControlMenuFragment();
        fragmentManager.showFragment(menuFragment);
    }
}

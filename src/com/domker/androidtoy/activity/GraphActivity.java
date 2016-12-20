package com.domker.androidtoy.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.domker.androidtoy.R;
import com.domker.androidtoy.adapter.TabPagerAdapter;

/**
 * 绘制图形学
 * 
 * @ClassName: GraphActivity
 * @author wanlipeng
 * @date 2016年12月7日 下午5:29:55
 */
public class GraphActivity extends AppCompatActivity implements
		OnPageChangeListener, OnTabSelectedListener {
	
	private ViewPager mViewPager;
	private TabPagerAdapter mAdapter;
	private ArrayList<TabLayout.Tab> mTabs;
	private Toolbar toolbar;
	private TabLayout tabLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.graph_layout);
		init();
	}

	private void init() {
		initActionBar();
		initTab();
		initViewPager();
	}

	private void initActionBar() {
		toolbar = (Toolbar) this.findViewById(R.id.toolBar);
		tabLayout = (TabLayout) this.findViewById(R.id.tabLayout);
		toolbar.setNavigationIcon(R.drawable.app_icon);
		
		setSupportActionBar(toolbar);
	}
	
	private void initTab() {
		// 添加Tabs
		mTabs = new ArrayList<TabLayout.Tab>();
		for (String name : TabPagerAdapter.TAB_NAMES) {
			TabLayout.Tab tab = tabLayout.newTab();
			tab.setText(name);
			mTabs.add(tab);
			tabLayout.addTab(tab);
		}
		tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
		tabLayout.setOnTabSelectedListener(this);
	}
	
	@SuppressWarnings("deprecation")
	private void initViewPager() {
		// 获取ViewPager
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		// 初始化mAdapter
		mAdapter = new TabPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mAdapter);
		mViewPager.setOnPageChangeListener(this);
		// 默认显示第二项
		mViewPager.setCurrentItem(0);
	}
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int index) {
		// 设置当前要显示的View
		mViewPager.setCurrentItem(index);
		// 选中对应的Tab
		mTabs.get(index).select();
	}

	/* (non-Javadoc)
	 * @see android.support.design.widget.TabLayout.OnTabSelectedListener#onTabReselected(android.support.design.widget.TabLayout.Tab)
	 */
	@Override
	public void onTabReselected(TabLayout.Tab tab) {
		
	}

	/* (non-Javadoc)
	 * @see android.support.design.widget.TabLayout.OnTabSelectedListener#onTabSelected(android.support.design.widget.TabLayout.Tab)
	 */
	@Override
	public void onTabSelected(TabLayout.Tab tab) {
		if (mViewPager != null) {
			mViewPager.setCurrentItem(tab.getPosition());
		}
	}

	/* (non-Javadoc)
	 * @see android.support.design.widget.TabLayout.OnTabSelectedListener#onTabUnselected(android.support.design.widget.TabLayout.Tab)
	 */
	@Override
	public void onTabUnselected(TabLayout.Tab tab) {
		
	}

}

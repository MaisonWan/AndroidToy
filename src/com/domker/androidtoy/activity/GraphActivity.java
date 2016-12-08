package com.domker.androidtoy.activity;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.domker.androidtoy.R;
import com.domker.androidtoy.render.TabPagerAdapter;

/**
 * 绘制图形学
 * 
 * @ClassName: GraphActivity
 * @author wanlipeng
 * @date 2016年12月7日 下午5:29:55
 */
@SuppressWarnings("deprecation")
public class GraphActivity extends FragmentActivity implements OnPageChangeListener, TabListener {
	
	private ActionBar mActionBar;
	private ViewPager mViewPager;
	private TabPagerAdapter mAdapter;
	private ArrayList<ActionBar.Tab> mTabs;

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
		// 取得ActionBar
		mActionBar = getActionBar();
		// 以Tab方式导航
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// 禁用ActionBar标题
		mActionBar.setDisplayShowTitleEnabled(false);
		// 禁用ActionBar图标
		mActionBar.setDisplayUseLogoEnabled(false);
		// 禁用ActionBar返回键
		mActionBar.setDisplayShowHomeEnabled(false);
	}
	
	private void initTab() {
		// 添加Tabs
		mTabs = new ArrayList<ActionBar.Tab>();
		for (String name : TabPagerAdapter.TAB_NAMES) {
			ActionBar.Tab tab = mActionBar.newTab();
			tab.setText(name);
			tab.setTabListener(this);
			mTabs.add(tab);
			mActionBar.addTab(tab);
		}
	}
	
	private void initViewPager() {
		// 获取ViewPager
		mViewPager = (ViewPager) findViewById(R.id.viewPager1);
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
		mActionBar.selectTab(mTabs.get(index));
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		if (mViewPager != null) {
			mViewPager.setCurrentItem(tab.getPosition());
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {

	}

}

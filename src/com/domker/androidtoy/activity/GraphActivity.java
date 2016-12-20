package com.domker.androidtoy.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.domker.androidtoy.MainActivity;
import com.domker.androidtoy.R;
import com.domker.androidtoy.adapter.MenuActionProvider;
import com.domker.androidtoy.adapter.TabPagerAdapter;

/**
 * 绘制图形学
 * 
 * @ClassName: GraphActivity
 * @author wanlipeng
 * @date 2016年12月7日 下午5:29:55
 */
public class GraphActivity extends AppCompatActivity implements
		OnPageChangeListener, OnTabSelectedListener, OnMenuItemClickListener {
	
	private ViewPager mViewPager;
	private TabPagerAdapter mAdapter;
	private ArrayList<TabLayout.Tab> mTabs;
	private Toolbar mToolbar;
	private TabLayout mTabLayout;

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
		mToolbar = (Toolbar) this.findViewById(R.id.toolBar);
		mTabLayout = (TabLayout) this.findViewById(R.id.tabLayout);
		mToolbar.setNavigationIcon(R.drawable.f002);
		mToolbar.setOnMenuItemClickListener(this);
		setSupportActionBar(mToolbar);
	}
	
	private void initTab() {
		// 添加Tabs
		mTabs = new ArrayList<TabLayout.Tab>();
		for (String name : TabPagerAdapter.TAB_NAMES) {
			TabLayout.Tab tab = mTabLayout.newTab();
			tab.setText(name);
			mTabs.add(tab);
			mTabLayout.addTab(tab);
		}
		mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
		mTabLayout.setOnTabSelectedListener(this);
	}
	
	private void initViewPager() {
		// 获取ViewPager
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		// 初始化mAdapter
		mAdapter = new TabPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mAdapter);
		mViewPager.addOnPageChangeListener(this);
		// 默认显示第二项
		mViewPager.setCurrentItem(0);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.graph_menu, menu);
		return true;
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

	/* (non-Javadoc)
	 * @see android.support.v7.widget.Toolbar.OnMenuItemClickListener#onMenuItemClick(android.view.MenuItem)
	 */
	@Override
	public boolean onMenuItemClick(MenuItem menuItem) {
		int id = menuItem.getItemId();
		String msg = "";
		switch (id) {
		case R.id.action_search:
			msg = "action_search";
			break;
		case R.id.action_intent:
			msg = "action_intent";
			// 这个地方要注意使用这种方式增加actionprovider不然会报错
			MenuItemCompat.setActionProvider(menuItem, new MenuActionProvider(this));
			break;
		default:
			break;

		}
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		return false;
	}

}

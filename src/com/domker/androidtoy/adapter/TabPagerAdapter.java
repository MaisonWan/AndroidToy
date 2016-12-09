/**
 * @file TabPagerAdapter.java
 * @Package com.domker.androidtoy.render
 * @Project AndroidToy
 * Description: TODO 
 * 
 * @author wanlipeng
 * @since 1.0.0.0
 * @version 1.0.0.0
 * @date 2016 下午4:15:53
 * 
 *       \if TOSPLATFORM_CONFIDENTIAL_PROPRIETARY
 *       ================================
 *            \n Copyright (c) 2014
 *       wanlipeng. All Rights Reserved.\n \n
 *       ==================================
 *       ==========================================\n \n Update History\n \n
 *       Author (Name[WorkID]) | Modification | Tracked Id | Description\n
 *       --------------------- | ------------ | ---------- |
 *       ------------------------\n wanlipeng | 2016 下午4:15:53 | <xxxxxxxx>
 *       | Initial Created.\n \n \endif
 * 
 *       <tt>
 * \n
 * Release History:\n
 * \n
 * Author (Name[WorkID]) | ModifyDate | Version | Description \n
 * --------------------- | ---------- | ------- | -----------------------------\n
 * wanlipeng |2016 下午4:15:53 | 1.0.0.0 | Initial created. \n
 * \n
 * </tt>
 */
package com.domker.androidtoy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.domker.androidtoy.fragment.PeanoFragment;
import com.domker.androidtoy.fragment.WaterWaveFragment;

/**
 * 翻页数据的Adapter
 * 
 * @ClassName: TabPagerAdapter
 * @author wanlipeng
 * @date 2016年12月8日 下午4:15:53
 */
public class TabPagerAdapter extends FragmentPagerAdapter {
	/** 
	 * Tab的名字和顺序
	 */
	public static final String[] TAB_NAMES = {"皮亚诺", "水波纹"};
	
	// 定义三个Fragment的索引
	public static final int Fragment_Index_0 = 0;
	public static final int Fragment_Index_1 = 1;
	public static final int Fragment_Index_2 = 2;

	public TabPagerAdapter(FragmentManager fragmentManager) {
		super(fragmentManager);
	}

	@Override
	public Fragment getItem(int index) {
		Fragment mFragemnt = null;
		switch (index) {
		case Fragment_Index_0:
			mFragemnt = new PeanoFragment();
			break;
		case Fragment_Index_1:
			mFragemnt = new WaterWaveFragment();
			break;
		}
		return mFragemnt;
	}

	@Override
	public int getCount() {
		return TAB_NAMES.length;
	}
}

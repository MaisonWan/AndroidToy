/**
 * @file BaseFragment.java
 * @Package com.domker.androidtoy.fragment
 * @Project AndroidToy
 * Description: TODO 
 * 
 * @author wanlipeng
 * @since 1.0.0.0
 * @version 1.0.0.0
 * @date 2016 上午11:29:34
 * 
 *       \if TOSPLATFORM_CONFIDENTIAL_PROPRIETARY
 *       ================================
 *            \n Copyright (c) 2014
 *       wanlipeng. All Rights Reserved.\n \n
 *       ==================================
 *       ==========================================\n \n Update History\n \n
 *       Author (Name[WorkID]) | Modification | Tracked Id | Description\n
 *       --------------------- | ------------ | ---------- |
 *       ------------------------\n wanlipeng | 2016 上午11:29:34 | <xxxxxxxx>
 *       | Initial Created.\n \n \endif
 * 
 *       <tt>
 * \n
 * Release History:\n
 * \n
 * Author (Name[WorkID]) | ModifyDate | Version | Description \n
 * --------------------- | ---------- | ------- | -----------------------------\n
 * wanlipeng |2016 上午11:29:34 | 1.0.0.0 | Initial created. \n
 * \n
 * </tt>
 */
package com.domker.androidtoy.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 抽象Fragment的基类
 * 
 * @ClassName: BaseFragment
 * @author wanlipeng
 * @date 2016年12月8日 上午11:29:34
 */
public abstract class BaseFragment extends Fragment {
	protected View mView = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		int resId = initLayoutResId();
		mView = inflater.inflate(resId, container, false);
		initSubViews();
		return mView;
	}

	/**
	 * 查到view
	 * 
	 * @param id
	 * @return View
	 */
	protected View findViewById(int id) {
		if (mView != null) {
			return mView.findViewById(id);
		}
		return null;
	}

	/**
	 * 设置好初始化的资源ID
	 * 
	 * @return
	 */
	protected abstract int initLayoutResId();

	/**
	 * 初始化子view
	 */
	protected abstract void initSubViews();

	/**
	 * 在显示的时候回调
	 */
	protected abstract void onShow();
}

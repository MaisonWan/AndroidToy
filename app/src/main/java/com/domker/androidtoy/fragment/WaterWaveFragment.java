/**
 * @file WaterWaveFragment.java
 * @Package com.domker.androidtoy.fragment
 * @Project AndroidToy
 * Description: TODO 
 * 
 * @author wanlipeng
 * @since 1.0.0.0
 * @version 1.0.0.0
 * @date 2016 下午4:18:43
 * 
 *       \if TOSPLATFORM_CONFIDENTIAL_PROPRIETARY
 *       ================================
 *            \n Copyright (c) 2014
 *       wanlipeng. All Rights Reserved.\n \n
 *       ==================================
 *       ==========================================\n \n Update History\n \n
 *       Author (Name[WorkID]) | Modification | Tracked Id | Description\n
 *       --------------------- | ------------ | ---------- |
 *       ------------------------\n wanlipeng | 2016 下午4:18:43 | <xxxxxxxx>
 *       | Initial Created.\n \n \endif
 * 
 *       <tt>
 * \n
 * Release History:\n
 * \n
 * Author (Name[WorkID]) | ModifyDate | Version | Description \n
 * --------------------- | ---------- | ------- | -----------------------------\n
 * wanlipeng |2016 下午4:18:43 | 1.0.0.0 | Initial created. \n
 * \n
 * </tt>
 */
package com.domker.androidtoy.fragment;

import com.domker.androidtoy.R;
import com.domker.androidtoy.view.WaveView;

/**
 * 水纹
 * 
 * @ClassName: WaterWaveFragment
 * @author wanlipeng
 * @date 2016年12月8日 下午4:18:43
 */
public class WaterWaveFragment extends BaseFragment {
	private WaveView waveView = null;

	/*
	 * <p>Title: initLayoutResId</p> <p>Description: </p>
	 * 
	 * @return
	 * 
	 * @see com.domker.androidtoy.fragment.BaseFragment#initLayoutResId()
	 */
	@Override
	protected int initLayoutResId() {
		return R.layout.graph_water_wave_layout;
	}

	/*
	 * <p>Title: initSubViews</p> <p>Description: </p>
	 * 
	 * @see com.domker.androidtoy.fragment.BaseFragment#initSubViews()
	 */
	@Override
	protected void initSubViews() {
		waveView = (WaveView) findViewById(R.id.waveView1);
	}

	@Override
	public void onResume() {
		super.onResume();
		waveView.start();
	}

	@Override
	public void onPause() {
		super.onPause();
		waveView.stop();
	}

}

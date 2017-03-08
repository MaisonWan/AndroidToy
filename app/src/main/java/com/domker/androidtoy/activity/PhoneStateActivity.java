/**
 * @file PhoneStateActivity.java
 * @Package com.tencent.test
 * Description: TODO 
 * 
 * @author maisonwan
 * @since 1.0.0.0
 * @version 1.0.0.0
 * @date 2013-12-16
 * 
 *       \if TOSPLATFORM_CONFIDENTIAL_PROPRIETARY
 *       ================================
 *            \n Copyright (c) 2011
 *       maisonwan. All Rights Reserved.\n \n
 *       ==================================
 *       ==========================================\n \n Update History\n \n
 *       Author (Name[WorkID]) | Modification | Tracked Id | Description\n
 *       --------------------- | ------------ | ---------- |
 *       ------------------------\n maisonwan | 2013-12-16 | <xxxxxxxx>
 *       | Initial Created.\n \n \endif
 * 
 *       <tt>
 * \n
 * Release History:\n
 * \n
 * Author (Name[WorkID]) | ModifyDate | Version | Description \n
 * --------------------- | ---------- | ------- | -----------------------------\n
 * maisonwan |2013-12-16 | 1.0.0.0 | Initial created. \n
 * \n
 * </tt>
 */
package com.domker.androidtoy.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.domker.androidtoy.R;
import com.domker.androidtoy.util.PhoneUtil;

//=============================================================================
//CLASS DEFINITIONS
//=============================================================================
/**
 * TODO
 * @author maisonwan
 * @date 2013-12-16 上午11:08:07
 *
 */
public class PhoneStateActivity extends ListActivity {
//
//	private TextView mPhoneTypeName = null;
//	private TextView mMaxHeapSize = null;
//	private TextView mIMEI = null;
//	private TextView mIMSI = null;
	private ArrayAdapter<String> adapter = null;
	private List<String> data = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initData();
		initAdapter();
	}
	
	private void initAdapter() {
		adapter = new ArrayAdapter<String>(this, R.layout.phone_state_item, data);
		setListAdapter(adapter);
	}

	private void initData() {
		PhoneUtil util = PhoneUtil.getInstance(this);
		
		String totalMemory = util.getTotalMemory();
		String showTotalMemory = String.format("Total Memory : %s", totalMemory);
		data.add(showTotalMemory);
		
		String availMemory = util.getAvailMemory();
		String showAvailMemory = String.format("Avail Memory : %s", availMemory);
		data.add(showAvailMemory);
		
		int maxHeap = util.getAppMaxHeapSize();
		String appMaxHeap = String.format("App Max Heap Size : %dKB(%.2fMB)", maxHeap, maxHeap / 1024.0f);
		data.add(appMaxHeap);
		
		int totalHeap = util.getAppTotalHeapSize();
		String appTotalHeap = String.format("App Total Heap Size : %dKB(%.2fMB)", totalHeap, totalHeap / 1024.0f);
		data.add(appTotalHeap);
		
		int freeHeap = util.getAppFreeHeapSize();
		String appFreeHeap = String.format("App Free Heap Size : %dKB(%.2fMB)", freeHeap, freeHeap / 1024.0f);
		data.add(appFreeHeap);
		
		String sdkVersion = String.format("SDK Version : %s", util.getSdkVersion());
		data.add(sdkVersion);
		
		String imei = String.format("IMEI : %s", util.getIMEI());
		data.add(imei);
		
		String imsi = String.format("IMSI : %s", util.getIMSI());
		data.add(imsi);
		data.add("Screen Size : " + util.getScreenSize(this));
		data.add("Scaled Density : " + util.getDPIDensity());
		data.add("Density DPI : " + util.getDensityDpi());
		data.add("Density: " + util.getDensity());
		data.addAll(util.getOSBuildInfo());
		
	}
	
}

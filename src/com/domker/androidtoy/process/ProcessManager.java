/**
 * @file ProcessManager.java
 * @Package com.domker.androidtoy.process
 * @Project PhoneTest
 * Description: TODO 
 * 
 * @author wanlipeng
 * @since 1.0.0.0
 * @version 1.0.0.0
 * @date 2015 下午5:40:39
 * 
 *       \if TOSPLATFORM_CONFIDENTIAL_PROPRIETARY
 *       ================================
 *            \n Copyright (c) 2014
 *       wanlipeng. All Rights Reserved.\n \n
 *       ==================================
 *       ==========================================\n \n Update History\n \n
 *       Author (Name[WorkID]) | Modification | Tracked Id | Description\n
 *       --------------------- | ------------ | ---------- |
 *       ------------------------\n wanlipeng | 2015 下午5:40:39 | <xxxxxxxx>
 *       | Initial Created.\n \n \endif
 * 
 *       <tt>
 * \n
 * Release History:\n
 * \n
 * Author (Name[WorkID]) | ModifyDate | Version | Description \n
 * --------------------- | ---------- | ------- | -----------------------------\n
 * wanlipeng |2015 下午5:40:39 | 1.0.0.0 | Initial created. \n
 * \n
 * </tt>
 */
package com.domker.androidtoy.process;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ConfigurationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

/**
 * 
 * 
 * @ClassName: ProcessManager
 * @author wanlipeng
 * @date 2015年8月17日 下午5:40:39
 */
public class ProcessManager {
	private Context mContext = null;

	public ProcessManager(Context mContext) {
		this.mContext = mContext;
	}

	/**
	 * 返回所有进程
	 * 
	 * @return List<ProcessInfo>
	 */
	public List<ProcessInfo> getAllProcesses() {
		List<ProcessManager.ProcessInfo> list = new ArrayList<ProcessManager.ProcessInfo>();
		PackageManager packageManager = mContext.getPackageManager();
		List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
		for (PackageInfo info : installedPackages) {
			list.add(convert(info, packageManager));
		}
		return list;
	}

	/**
	 * 得到一个程序的信息
	 * @param name
	 * @return
	 */
	@SuppressLint("NewApi")
	public ProcessInfo getProcessInfo(String name) {
		ProcessInfo info = null;
		PackageManager packageManager = mContext.getPackageManager();
		try {
			PackageInfo packageInfo  = packageManager.getPackageInfo(name, 0);
			info = new ProcessInfo();
			info.appName = packageInfo.applicationInfo.loadLabel(packageManager).toString();
			info.packageName = packageInfo.packageName;
			info.versionName = packageInfo.versionName;
			info.versionCode = packageInfo.versionCode;
			info.appIcon = packageInfo.applicationInfo.loadIcon(packageManager);
			// mainfest
			if (info.sharedUserId == null) {
				info.sharedUserId = "None";
			} else {
				info.sharedUserId = packageInfo.sharedUserId;
			}
			info.firstInstallTime = packageInfo.firstInstallTime;
			info.lastUpdateTime = packageInfo.lastUpdateTime;

			int flag = PackageManager.GET_PERMISSIONS | PackageManager.GET_ACTIVITIES | PackageManager.GET_SIGNATURES | PackageManager.GET_CONFIGURATIONS;
			packageInfo = packageManager.getPackageInfo(name, flag);
			info.requestedPermissions = packageInfo.requestedPermissions;
			info.activities = packageInfo.activities;
			info.configPreferences = packageInfo.configPreferences;

		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return info;
	}

	/**
	 * 简单的转换几个信息
	 * @param info
	 * @param packageManager
	 * @return
	 */
	private ProcessInfo convert(PackageInfo info, PackageManager packageManager) {
		ProcessInfo p = new ProcessInfo();
		p.appName = info.applicationInfo.loadLabel(packageManager).toString();
		p.packageName = info.packageName;
		p.versionName = info.versionName;
		p.versionCode = info.versionCode;
		p.appIcon = info.applicationInfo.loadIcon(packageManager);
		return p;
	}

	public class ProcessInfo {
		public String appName;
		public String packageName;
		public String versionName;
		public int versionCode = 0;
		public Drawable appIcon = null;
		public String sharedUserId;
		public long firstInstallTime;
		public long lastUpdateTime;

		public String[] requestedPermissions;
		public ActivityInfo[] activities;
		public ConfigurationInfo[] configPreferences;
	}
}

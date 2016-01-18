/**
 * @file PhoneUtil.java
 * @Package com.tencent.test.util
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
package com.domker.androidtoy.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.DisplayMetrics;

//=============================================================================
//CLASS DEFINITIONS
//=============================================================================
/**
 * TODO
 * 
 * @author maisonwan
 * @date 2013-12-16 上午11:34:12
 * 
 */
public class PhoneUtil {
	private Context mContext = null;
	private TelephonyManager mTelephonyManager;
	private static PhoneUtil phoneUtil = null;

	private PhoneUtil(Context context) {
		this.mContext = context;
		this.mTelephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
	}

	public static PhoneUtil getInstance(Context context) {
		if (phoneUtil == null) {
			phoneUtil = new PhoneUtil(context);
		}
		return phoneUtil;
	}

	// -----------------------------------------------------------------------
	/**
	 * 得到应用的堆栈信息，返回单位是kb
	 * 
	 * @return int
	 */
	public int getAppMaxHeapSize() {
		return (int) (Runtime.getRuntime().maxMemory() / 1024);
	}
	
	public int getAppTotalHeapSize() {
		return (int) (Runtime.getRuntime().totalMemory() / 1024);
	}
	
	public int getAppFreeHeapSize() {
		return (int) (Runtime.getRuntime().freeMemory() / 1024);
	}

	public String getIMEI() {
		String myIMEI = mTelephonyManager.getDeviceId();
		if (myIMEI != null) {
			return isSameChar(myIMEI, '0') ? getAndroidID() : myIMEI;
		} else {
			WifiManager wifi = (WifiManager) mContext
					.getSystemService(Context.WIFI_SERVICE);
			WifiInfo info = wifi.getConnectionInfo();
			if (info != null && info.getMacAddress() != null)
				return info.getMacAddress();
			return getAndroidID();
		}
	}

	public String getAndroidID() {
		String androidID = android.provider.Settings.Secure.getString(
				mContext.getContentResolver(),
				android.provider.Settings.Secure.ANDROID_ID);
		return androidID;
	}

	public String getIMSI() {
		String myIMSI = mTelephonyManager.getSubscriberId();
		if (TextUtils.isEmpty(myIMSI)) {
			myIMSI = "310260000000000";
		}
		return myIMSI;
	}

	private boolean isSameChar(String str, char c) {
		if (str == null)
			return true;
		int length = str.length();
		for (int i = 0; i < length; i++) {
			if (str.charAt(i) != c)
				return false;
		}
		return true;
	}
	
	// -----------------------------------------------------------------------
	/**
	 * 系统sdk版本号
	 * @return String
	 */
	public String getSdkVersion() {
		return String.valueOf(Build.VERSION.SDK_INT);
	}
	
	public List<String> getOSBuildInfo() {
		List<String> data = new ArrayList<String>();
		data.add("Product: " + android.os.Build.PRODUCT); 
		data.add("CPU_ABI: " + android.os.Build.CPU_ABI); 
		data.add("TAGS: " + android.os.Build.TAGS); 
		data.add("VERSION_CODES.BASE: " + android.os.Build.VERSION_CODES.BASE); 
		data.add("MODEL: " + android.os.Build.MODEL); 
	    data.add("SDK: " + android.os.Build.VERSION.SDK); 
	    data.add("VERSION.RELEASE: " + android.os.Build.VERSION.RELEASE); 
	    data.add("DEVICE: " + android.os.Build.DEVICE); 
	    data.add("DISPLAY: " + android.os.Build.DISPLAY); 
	    data.add("BRAND: " + android.os.Build.BRAND); 
	    data.add("BOARD: " + android.os.Build.BOARD); 
	    data.add("FINGERPRINT: " + android.os.Build.FINGERPRINT); 
	    data.add("ID: " + android.os.Build.ID); 
	    data.add("MANUFACTURER: " + android.os.Build.MANUFACTURER); 
	    data.add("USER: " + android.os.Build.USER);
	    return data;
	}
	
	// -----------------------------------------------------------------------
	/**
	 * 得到屏幕分辨率
	 * 
	 * @param activity
	 * @return String
	 */
	public String getScreenSize(Activity activity) {
		int width = activity.getWindowManager().getDefaultDisplay().getWidth();
		int height = activity.getWindowManager().getDefaultDisplay().getHeight();
		return String.format("%d * %d", width, height);
	}
	
	public int getDensityDpi() {
		DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
		return dm.densityDpi;
	}
	
	public float getDensity() {
		DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
		return dm.density;
	}
	
	public String getDPIDensity() {
		DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
		return String.format("%f * %f", dm.xdpi, dm.ydpi);
	}
	
	public String getSDCardPath() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return Environment.getExternalStorageDirectory().toString();
		}
		return null;
	}
	
	public String getAvailMemory() {// 获取android当前可用内存大小  
        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);  
        MemoryInfo mi = new MemoryInfo();  
        am.getMemoryInfo(mi);  
        //mi.availMem; 当前系统的可用内存  
        return Formatter.formatFileSize(mContext, mi.availMem);// 将获取的内存大小规格化  
	}
	
	public String getTotalMemory() {
		String str1 = "/proc/meminfo";// 系统内存信息文件
		String str2;
		String arrayOfString = null;
		long initial_memory = 0;
		try {
			FileReader localFileReader = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(
					localFileReader, 8192);
			str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小
			Matcher matcher = Pattern.compile("\\d+").matcher(str2);
			if (matcher.find()) {
				arrayOfString = matcher.group();
			}
			initial_memory = Long.valueOf(arrayOfString).longValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
			localBufferedReader.close();

		} catch (IOException e) {
		}
		return Formatter.formatFileSize(mContext, initial_memory);// Byte转换为KB或者MB，内存大小规格化
	}
}

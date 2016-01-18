/**
 * @file SettingActivity.java
 * @Package com.tencent.test
 * Description: TODO 
 * 
 * @author maisonwan
 * @since 1.0.0.0
 * @version 1.0.0.0
 * @date 2014-3-27
 * 
 *       \if TOSPLATFORM_CONFIDENTIAL_PROPRIETARY
 *       ================================
 *            \n Copyright (c) 2011
 *       maisonwan. All Rights Reserved.\n \n
 *       ==================================
 *       ==========================================\n \n Update History\n \n
 *       Author (Name[WorkID]) | Modification | Tracked Id | Description\n
 *       --------------------- | ------------ | ---------- |
 *       ------------------------\n maisonwan | 2014-3-27 | <xxxxxxxx>
 *       | Initial Created.\n \n \endif
 * 
 *       <tt>
 * \n
 * Release History:\n
 * \n
 * Author (Name[WorkID]) | ModifyDate | Version | Description \n
 * --------------------- | ---------- | ------- | -----------------------------\n
 * maisonwan |2014-3-27 | 1.0.0.0 | Initial created. \n
 * \n
 * </tt>
 */
package com.domker.androidtoy.activity;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.domker.androidtoy.R;

//=============================================================================
//CLASS DEFINITIONS
//=============================================================================
/**
 * TODO
 * 
 * @author maisonwan
 * @date 2014-3-27 下午2:54:34
 * 
 */
public class SettingActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.addPreferencesFromResource(R.layout.setting);
	}

	public static boolean getPhontState(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(
						context.getString(R.string.preference_phone_state_key),
						context.getResources().getBoolean(
								R.bool.test_switch_phone_state));
	}

	public static boolean getInputText(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(
						context.getString(R.string.preference_input_text_key),
						context.getResources().getBoolean(
								R.bool.test_switch_inputtext));
	}

	public static boolean getColorOverlay(Context context) {
		return PreferenceManager
				.getDefaultSharedPreferences(context)
				.getBoolean(
						context.getString(R.string.preference_color_overlay_key),
						context.getResources().getBoolean(
								R.bool.test_color_overlay));
	}

	public static boolean getLayerBlend(Context context) {
		return PreferenceManager
				.getDefaultSharedPreferences(context)
				.getBoolean(
						context.getString(R.string.preference_layer_blending_key),
						context.getResources().getBoolean(
								R.bool.test_layer_blending));
	}
	
	public static boolean getSurfaceViewTest(Context context) {
		return PreferenceManager
				.getDefaultSharedPreferences(context)
				.getBoolean(
						context.getString(R.string.preference_surface_test_key),
						context.getResources().getBoolean(
								R.bool.test_switch_surface));
	}

	public static boolean getCapitalizeTest(Context context) {
		return PreferenceManager
				.getDefaultSharedPreferences(context)
				.getBoolean(
						context.getString(R.string.preference_capitalize_test_key),
						context.getResources().getBoolean(
								R.bool.test_capitalize));
	}
	
	public static boolean getImageCacheTest(Context context) {
		return PreferenceManager
				.getDefaultSharedPreferences(context)
				.getBoolean(
						context.getString(R.string.preference_image_cache_test_key),
						context.getResources().getBoolean(
								R.bool.test_image_cache));
	}
	
	public static boolean getSwitchIconTest(Context context) {
		return PreferenceManager
				.getDefaultSharedPreferences(context)
				.getBoolean(
						context.getString(R.string.preference_switch_icon_test_key),
						context.getResources().getBoolean(
								R.bool.test_switch_icon));
	}
	
	public static boolean getLockTest(Context context) {
		return PreferenceManager
				.getDefaultSharedPreferences(context)
				.getBoolean(
						context.getString(R.string.preference_lock_test_key),
						context.getResources().getBoolean(
								R.bool.test_lock));
	}
	
	public static boolean getSMSTest(Context context) {
		return PreferenceManager
				.getDefaultSharedPreferences(context)
				.getBoolean(
						context.getString(R.string.preference_sms_test_key),
						context.getResources().getBoolean(
								R.bool.test_sms));
	}
}

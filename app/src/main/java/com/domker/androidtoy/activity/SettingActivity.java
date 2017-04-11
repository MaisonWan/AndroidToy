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

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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
	private static Map<Class<? extends Activity>, ConfigValue> map = new HashMap<Class<? extends Activity>, ConfigValue>();
	
	static {
		map.put(PhoneStateActivity.class, new ConfigValue(R.string.preference_phone_state_key, R.bool.test_switch_phone_state));
		map.put(InputTextActivity.class, new ConfigValue(R.string.preference_input_text_key, R.bool.test_switch_inputtext));
		map.put(ColorOverLayActivity.class, new ConfigValue(R.string.preference_color_overlay_key, R.bool.test_color_overlay));
		map.put(LayerBlendingActivity.class, new ConfigValue(R.string.preference_layer_blending_key, R.bool.test_layer_blending));
		map.put(SurfaceActivity.class, new ConfigValue(R.string.preference_surface_test_key, R.bool.test_switch_surface));
		map.put(CapitalizeActivity.class, new ConfigValue(R.string.preference_capitalize_test_key, R.bool.test_capitalize));
		map.put(ImageCacheActivity.class, new ConfigValue(R.string.preference_image_cache_test_key, R.bool.test_image_cache));
		map.put(SwitchIconActivity.class, new ConfigValue(R.string.preference_switch_icon_test_key, R.bool.test_switch_icon));
		map.put(LockActivity.class, new ConfigValue(R.string.preference_lock_test_key, R.bool.test_lock));
		map.put(SMSActivity.class, new ConfigValue(R.string.preference_sms_test_key, R.bool.test_sms));
		map.put(SystemRootActivity.class, new ConfigValue(R.string.preference_system_root_key, R.bool.test_system_root));
		map.put(ProcessListActivity.class, new ConfigValue(R.string.preference_process_test_key, R.bool.test_process));
		map.put(PianoActivity.class, new ConfigValue(R.string.preference_piano_test_key, R.bool.test_piano));
		map.put(WebpActivity.class, new ConfigValue(R.string.preference_webp_test_key, R.bool.test_webp));
		map.put(GraphActivity.class, new ConfigValue(R.string.preference_graph_test_key, R.bool.test_graph));
        map.put(ControlActivity.class, new ConfigValue(R.string.preference_control_test_key, R.bool.test_control));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.addPreferencesFromResource(R.layout.setting);
	}
	
	/** 
	 * 返回当前Activity是否显示
	 * 
	 * @param context
	 * @param c
	 * @return boolean 
	 */
	public static boolean isShown(Context context, Class<? extends Activity> c) {
		SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
		ConfigValue values = map.get(c);
		String key = context.getString(values.keyName);
		boolean defaultValue = context.getResources().getBoolean(values.defaultValue);
		return preference.getBoolean(key, defaultValue);
	}
	
	/** 
	 * 配置的值
	 * 
	 * @ClassName: ConfigValue 
	 * @author wanlipeng
	 * @date 2016年1月19日 下午5:33:33  
	 */
	public static final class ConfigValue {

		public ConfigValue() {
		}

		public ConfigValue(int keyName, int defaultValue) {
			this.keyName = keyName;
			this.defaultValue = defaultValue;
		}

		public int keyName;
		public int defaultValue;
	}
}

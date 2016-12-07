/**
 * @file MainActivity.java
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
package com.domker.androidtoy;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TabHost;

import com.domker.androidtoy.activity.CapitalizeActivity;
import com.domker.androidtoy.activity.ColorOverLayActivity;
import com.domker.androidtoy.activity.DrawCustomFontActivity;
import com.domker.androidtoy.activity.GraphActivity;
import com.domker.androidtoy.activity.ImageCacheActivity;
import com.domker.androidtoy.activity.ImageToneActivity;
import com.domker.androidtoy.activity.InputTextActivity;
import com.domker.androidtoy.activity.JSActivity;
import com.domker.androidtoy.activity.LayerBlendingActivity;
import com.domker.androidtoy.activity.LockActivity;
import com.domker.androidtoy.activity.MenuAdapter;
import com.domker.androidtoy.activity.PhoneStateActivity;
import com.domker.androidtoy.activity.PianoActivity;
import com.domker.androidtoy.activity.ProcessListActivity;
import com.domker.androidtoy.activity.SMSActivity;
import com.domker.androidtoy.activity.SettingActivity;
import com.domker.androidtoy.activity.SurfaceActivity;
import com.domker.androidtoy.activity.SwitchIconActivity;
import com.domker.androidtoy.activity.SystemRootActivity;
import com.domker.androidtoy.activity.WebpActivity;

/**
 * 测试小工具的集合
 * 
 * @author maisonwan
 * @date 2013-12-16 上午11:20:38
 * 
 */
public class MainActivity extends Activity implements OnItemClickListener {
	private final int MENU_SETTING = 0x1;
	private final int MENU_ABOUT = 0x2;

	private List<CaseActivity> activityList = new ArrayList<MainActivity.CaseActivity>();
	
	private ListView listview = null;
	private GridView gridview = null;
	
	private List<Integer> shownButtonTextList = new ArrayList<Integer>();
	
	private MenuAdapter listviewAdapter = null;
	private MenuAdapter gridviewAdapter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_main);
		
		listview = (ListView) this.findViewById(R.id.listviewMenu);
		gridview = (GridView) this.findViewById(R.id.gridviewMenu);
		
		TabHost tabHost = (TabHost) findViewById(R.id.tabhost);  
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator(getString(R.string.tab1_name)).setContent(R.id.gridviewMenu));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator(getString(R.string.tab2_name)).setContent(R.id.listviewMenu));
        initData();
        
        listviewAdapter = new MenuAdapter(this, shownButtonTextList, R.layout.listview_menu_item);
        gridviewAdapter = new MenuAdapter(this, shownButtonTextList, R.layout.gridview_menu_item);
        
        listview.setOnItemClickListener(this);
        listview.setAdapter(listviewAdapter);
        
        gridview.setOnItemClickListener(this);
        gridview.setAdapter(gridviewAdapter);
	}
	
	private void initData() {
		activityList.clear();
		addActivityList(PhoneStateActivity.class, R.string.preference_phone_state_title);
		addActivityList(InputTextActivity.class, R.string.preference_input_text_title);
		addActivityList(ColorOverLayActivity.class, R.string.preference_color_overlay_title);
		addActivityList(LayerBlendingActivity.class, R.string.preference_layer_blending_title);
		addActivityList(SurfaceActivity.class, R.string.preference_surface_test_title);
		addActivityList(CapitalizeActivity.class, R.string.preference_capitalize_test_title);
		addActivityList(ImageCacheActivity.class, R.string.preference_image_cache_test_title);
		addActivityList(SwitchIconActivity.class, R.string.preference_switch_icon_test_title);
		addActivityList(LockActivity.class, R.string.preference_lock_test_title);
		addActivityList(SMSActivity.class, R.string.preference_sms_test_title);
		addActivityList(SystemRootActivity.class, R.string.preference_system_root_title);
		addActivityList(ProcessListActivity.class, R.string.preference_process_test_title);
		addActivityList(PianoActivity.class, R.string.preference_piano_test_title);
		addActivityList(WebpActivity.class, R.string.preference_webp_test_title);
		addActivityList(GraphActivity.class, R.string.preference_graph_test_title);

		addActivityList(JSActivity.class, R.string.preference_web_test_title, false);
		addActivityList(ImageToneActivity.class, R.string.preference_image_tone_test_title, true);
		addActivityList(DrawCustomFontActivity.class, R.string.preference_font_test_title, true);
		
		shownButtonTextList.clear();
		for (int i = 0; i < activityList.size(); i++) {
			CaseActivity caseActivity = activityList.get(i);
			if (caseActivity.isShown) {
				shownButtonTextList.add(caseActivity.nameResId);
			}
		}
	}
	
	private void addActivityList(Class<? extends Activity> c, int titleResId) {
		activityList.add(new CaseActivity(c, titleResId, SettingActivity.isShown(this, c)));
	}
	
	private void addActivityList(Class<? extends Activity> c, int titleResId, boolean show) {
		activityList.add(new CaseActivity(c, titleResId, show));
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		initData();
		listviewAdapter.notifyDataSetChanged();
		gridviewAdapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(1, MENU_SETTING, 1, R.string.setting);
		menu.add(1, MENU_ABOUT, 2, R.string.about);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_SETTING:
			startActivity(new Intent(this, SettingActivity.class));
			break;
		case MENU_ABOUT:
			showAboutDialog();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void showAboutDialog() {
		String content = String.format(getString(R.string.about_message), getString(R.string.app_name), getVersionName());
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.about);
		builder.setMessage(content);
		builder.setPositiveButton(R.string.ok, null);
		builder.create().show();
	}

	private String getVersionName() {
		// 获取packagemanager的实例
		PackageManager packageManager = getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo;
		try {
			packInfo = packageManager.getPackageInfo(getPackageName(), 0);
			String version = packInfo.versionName;
			return version;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		try {
			int nameId = shownButtonTextList.get(position);
			for (int i = 0; i < this.activityList.size(); i++) {
				if (nameId == activityList.get(i).nameResId) {
					startActivity(new Intent(this, activityList.get(i).activityClass));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 
	 * 每个测试Activity的状态
	 * 
	 * @ClassName: CaseActivity 
	 * @author wanlipeng
	 * @date 2015年6月16日 下午7:14:05  
	 */
	public static final class CaseActivity {
		public Class<?> activityClass;
		public int nameResId;
		public boolean isShown = false;

		public CaseActivity(Class<?> activityClass, int nameResId, boolean isShown) {
			this.activityClass = activityClass;
			this.nameResId = nameResId;
			this.isShown = isShown;
		}

		public CaseActivity() {
		}

	}
}

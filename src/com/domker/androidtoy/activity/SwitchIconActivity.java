/**
 * @file SwitchIconActivity.java
 * @Package com.domker.androidtoy
 * @Project PhoneTest
 * Description: TODO 
 * 
 * @author wanlipeng
 * @since 1.0.0.0
 * @version 1.0.0.0
 * @date 2015 下午6:20:20
 * 
 *       \if TOSPLATFORM_CONFIDENTIAL_PROPRIETARY
 *       ================================
 *            \n Copyright (c) 2014
 *       wanlipeng. All Rights Reserved.\n \n
 *       ==================================
 *       ==========================================\n \n Update History\n \n
 *       Author (Name[WorkID]) | Modification | Tracked Id | Description\n
 *       --------------------- | ------------ | ---------- |
 *       ------------------------\n wanlipeng | 2015 下午6:20:20 | <xxxxxxxx>
 *       | Initial Created.\n \n \endif
 * 
 *       <tt>
 * \n
 * Release History:\n
 * \n
 * Author (Name[WorkID]) | ModifyDate | Version | Description \n
 * --------------------- | ---------- | ------- | -----------------------------\n
 * wanlipeng |2015 下午6:20:20 | 1.0.0.0 | Initial created. \n
 * \n
 * </tt>
 */
package com.domker.androidtoy.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.domker.androidtoy.R;

/**
 * 
 * 
 * @ClassName: SwitchIconActivity
 * @author wanlipeng
 * @date 2015年6月1日 下午6:20:20
 */
public class SwitchIconActivity extends Activity {
	private Button mButtonSwitchIcon = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.switch_icon_layout);
		mButtonSwitchIcon = (Button) this.findViewById(R.id.buttonSwitchIcon);
		mButtonSwitchIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switchIcon();
			}
		});
	}

	private void switchIcon() {
		Bitmap bm2 = BitmapFactory.decodeResource(getResources(), R.drawable.f002);
		Intent shortcutIntent = new Intent();
        shortcutIntent.setClassName(this, getClass().getName());
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Shortcut Name 123");
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, bm2);
        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        sendBroadcast(addIntent); 
	}
}

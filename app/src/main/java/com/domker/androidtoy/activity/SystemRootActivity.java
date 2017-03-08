/**
 * @file SystemRootActivity.java
 * @Package com.tencent.test
 * Description: TODO 
 * 
 * @author maisonwan
 * @since 1.0.0.0
 * @version 1.0.0.0
 * @date 2014-2-24
 * 
 *       \if TOSPLATFORM_CONFIDENTIAL_PROPRIETARY
 *       ================================
 *            \n Copyright (c) 2011
 *       maisonwan. All Rights Reserved.\n \n
 *       ==================================
 *       ==========================================\n \n Update History\n \n
 *       Author (Name[WorkID]) | Modification | Tracked Id | Description\n
 *       --------------------- | ------------ | ---------- |
 *       ------------------------\n maisonwan | 2014-2-24 | <xxxxxxxx>
 *       | Initial Created.\n \n \endif
 * 
 *       <tt>
 * \n
 * Release History:\n
 * \n
 * Author (Name[WorkID]) | ModifyDate | Version | Description \n
 * --------------------- | ---------- | ------- | -----------------------------\n
 * maisonwan |2014-2-24 | 1.0.0.0 | Initial created. \n
 * \n
 * </tt>
 */
package com.domker.androidtoy.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.domker.androidtoy.R;
import com.domker.androidtoy.util.RootUtil;

//=============================================================================
//CLASS DEFINITIONS
//=============================================================================
/**
 * 测试系统是否root和相关权限
 * 
 * @author maisonwan
 * @date 2014-2-24 下午2:27:46
 * 
 */
public class SystemRootActivity extends Activity {

	private TextView tvRootInfo = null;
	private ImageView imageViewPNG = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.system_root);
		setTitle(R.string.system_root_test);
		findViewById(R.id.buttonRootTest).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						tvRootInfo.setText(RootUtil.getRootAuth() ? "已经Root" : "未Root");
					}
				});
		tvRootInfo = (TextView) findViewById(R.id.textViewRoot);
		imageViewPNG = (ImageView) findViewById(R.id.imageViewPng);
	}
}

/**
 * @file LockActivity.java
 * @Package com.domker.androidtoy.activity
 * @Project PhoneTest
 * Description: TODO 
 * 
 * @author wanlipeng
 * @since 1.0.0.0
 * @version 1.0.0.0
 * @date 2015 下午6:56:52
 * 
 *       \if TOSPLATFORM_CONFIDENTIAL_PROPRIETARY
 *       ================================
 *            \n Copyright (c) 2014
 *       wanlipeng. All Rights Reserved.\n \n
 *       ==================================
 *       ==========================================\n \n Update History\n \n
 *       Author (Name[WorkID]) | Modification | Tracked Id | Description\n
 *       --------------------- | ------------ | ---------- |
 *       ------------------------\n wanlipeng | 2015 下午6:56:52 | <xxxxxxxx>
 *       | Initial Created.\n \n \endif
 * 
 *       <tt>
 * \n
 * Release History:\n
 * \n
 * Author (Name[WorkID]) | ModifyDate | Version | Description \n
 * --------------------- | ---------- | ------- | -----------------------------\n
 * wanlipeng |2015 下午6:56:52 | 1.0.0.0 | Initial created. \n
 * \n
 * </tt>
 */
package com.domker.androidtoy.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.domker.androidtoy.R;
import com.domker.androidtoy.view.Lock9View;
import com.domker.androidtoy.view.Lock9View.CallBack;

/**
 * 九宫格锁屏
 * 
 * @ClassName: LockActivity
 * @author wanlipeng
 * @date 2015年6月16日 下午6:56:52
 */
public class LockActivity extends Activity {
	private Lock9View lockView = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lock_layout);
		this.lockView = (Lock9View) this.findViewById(R.id.lock_9_view);
		this.lockView.setCallBack(new CallBack() {
			
			@Override
			public void onFinish(String password) {
				Toast.makeText(LockActivity.this, password, Toast.LENGTH_SHORT).show();
			}
		});
	}

}

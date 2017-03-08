/**
 * @file PianoActivity.java
 * @Package com.domker.androidtoy.activity
 * @Project AndroidToy
 * Description: TODO 
 * 
 * @author wanlipeng
 * @since 1.0.0.0
 * @version 1.0.0.0
 * @date 2016 下午5:00:31
 * 
 *       \if TOSPLATFORM_CONFIDENTIAL_PROPRIETARY
 *       ================================
 *            \n Copyright (c) 2014
 *       wanlipeng. All Rights Reserved.\n \n
 *       ==================================
 *       ==========================================\n \n Update History\n \n
 *       Author (Name[WorkID]) | Modification | Tracked Id | Description\n
 *       --------------------- | ------------ | ---------- |
 *       ------------------------\n wanlipeng | 2016 下午5:00:31 | <xxxxxxxx>
 *       | Initial Created.\n \n \endif
 * 
 *       <tt>
 * \n
 * Release History:\n
 * \n
 * Author (Name[WorkID]) | ModifyDate | Version | Description \n
 * --------------------- | ---------- | ------- | -----------------------------\n
 * wanlipeng |2016 下午5:00:31 | 1.0.0.0 | Initial created. \n
 * \n
 * </tt>
 */
package com.domker.androidtoy.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.domker.androidtoy.R;
import com.domker.androidtoy.sound.SoundManager;

/**
 * 钢琴的一个测试，弹奏曲目
 * 
 * @ClassName: PianoActivity
 * @author wanlipeng
 * @date 2016年1月20日 下午5:00:31
 */
public class PianoActivity extends Activity {
	private SoundManager soundManager = null;
	
	private OnClickListener onPianoKeyClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.buttonPlay:
				// 播放按钮
				playKeys();
				break;
			case R.id.buttonClear:
				// 清空按钮
				clearKeyShow();
				break;
			case R.id.buttonSplite:
			default:
				if (v instanceof Button) {
					Button button = (Button) v;
					addKeyShow((String) button.getText());
				}
				break;
			}
		}
	};

	// 按钮的id
	private final int[] buttonIds = { R.id.button_1, R.id.button_2,
			R.id.button_3, R.id.button_4, R.id.button_5, R.id.button_6,
			R.id.button_7, R.id.button_21, R.id.button_22, R.id.button_23,
			R.id.button_24, R.id.button_25, R.id.button_26, R.id.button_27 };

	private TextView mTextShow = null;
	
	// 播放的按键
	private List<String> keys = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.piano_layout);
		
		initPianoButtons();
		soundManager = new SoundManager(this);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		soundManager.onDestroy();
	}


	private void initPianoButtons() {
		for (int id : buttonIds) {
			initButton(id);
		}
		
		mTextShow = (TextView) this.findViewById(R.id.textViewPiano);
		// init play key
		this.findViewById(R.id.buttonPlay).setOnClickListener(onPianoKeyClickListener);
		this.findViewById(R.id.buttonClear).setOnClickListener(onPianoKeyClickListener);
		this.findViewById(R.id.buttonSplite).setOnClickListener(onPianoKeyClickListener);
	}
	
	private void initButton(int buttonId) {
		this.findViewById(buttonId).setOnClickListener(onPianoKeyClickListener);
	}
	
	/** 
	 * 将按键显示出来，并且存放在播放数组中
	 * 
	 * @param key
	 */
	private void addKeyShow(String key) {
		String show = mTextShow.getText().toString();
		if (TextUtils.isEmpty(show)) {
			mTextShow.setText(key);
		} else {
			mTextShow.setText(show + " " + key);
		}
		keys.add(key);
	}
	
	private void playKeys() {
		for (String key : keys) {
			float count = 1;
			if ("|".equals(key)) {
				count = 0.25f;
			} else {
				soundManager.playSound(key);
			}
			try {
				Thread.sleep((int)(300 * count));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/** 
	 * 清空显示
	 */
	private void clearKeyShow() {
		mTextShow.setText("");
		keys.clear();
	}
}

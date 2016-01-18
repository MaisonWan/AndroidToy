package com.domker.androidtoy.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.domker.androidtoy.R;

public class InputTextActivity extends Activity {
	private EditText editText = null;
	private int[] editTextIds = { R.id.editTextGo, R.id.editTextSearch,
			R.id.editTextSend, R.id.editTextNext, R.id.editTextPrevious,
			R.id.editTextComplete };
	private int[] actions = { EditorInfo.IME_ACTION_GO,
			EditorInfo.IME_ACTION_SEARCH, EditorInfo.IME_ACTION_SEND,
			EditorInfo.IME_ACTION_NEXT, EditorInfo.IME_ACTION_PREVIOUS,
			EditorInfo.IME_ACTION_DONE };
	private String[] texts = { "前往", "搜索", "发送", "下一项", "上一项", "完成" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.input_layout);

		for (int i = 0; i < editTextIds.length; i++) {
			this.editText = (EditText) this.findViewById(editTextIds[i]);
			this.editText
					.setOnEditorActionListener(new OnInputEditorActionListener(
							actions[i], texts[i]));
		}
	}

	private class OnInputEditorActionListener implements OnEditorActionListener {
		private int action = 0;
		private String text = null;

		public OnInputEditorActionListener(int action, String text) {
			this.action = action;
			this.text = text;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.widget.TextView.OnEditorActionListener#onEditorAction(android
		 * .widget.TextView, int, android.view.KeyEvent)
		 */
		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			if (actionId == action) {
				Toast.makeText(InputTextActivity.this,
						text + "文本：" + v.getText(), Toast.LENGTH_SHORT).show();
				return true;
			}
			return false;
		}

	}
}
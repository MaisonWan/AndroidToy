/**
 * @file ColorOverLayActivity.java
 * @Package com.tencent.test
 * Description: TODO 
 * 
 * @author maisonwan
 * @since 1.0.0.0
 * @version 1.0.0.0
 * @date 2014-2-20
 * 
 *       \if TOSPLATFORM_CONFIDENTIAL_PROPRIETARY
 *       ================================
 *            \n Copyright (c) 2011
 *       maisonwan. All Rights Reserved.\n \n
 *       ==================================
 *       ==========================================\n \n Update History\n \n
 *       Author (Name[WorkID]) | Modification | Tracked Id | Description\n
 *       --------------------- | ------------ | ---------- |
 *       ------------------------\n maisonwan | 2014-2-20 | <xxxxxxxx>
 *       | Initial Created.\n \n \endif
 * 
 *       <tt>
 * \n
 * Release History:\n
 * \n
 * Author (Name[WorkID]) | ModifyDate | Version | Description \n
 * --------------------- | ---------- | ------- | -----------------------------\n
 * maisonwan |2014-2-20 | 1.0.0.0 | Initial created. \n
 * \n
 * </tt>
 */
package com.domker.androidtoy.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.domker.androidtoy.R;
import com.domker.androidtoy.color.ColorPickerDialog;
import com.domker.androidtoy.color.ColorPickerDialog.OnColorChangedListener;
import com.domker.androidtoy.view.TestColorView;

//=============================================================================
//CLASS DEFINITIONS
//=============================================================================
/**
 * TODO
 * 
 * @author maisonwan
 * @date 2014-2-20 上午11:17:00
 * 
 */
public class ColorOverLayActivity extends Activity {

	private View.OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.buttonColor1:
				showColorSelector(color1, 1);
				break;
			case R.id.buttonColor2:
				showColorSelector(color2, 2);
				break;
			case R.id.buttonColorOverlay:
				calcColorOverlay();
				showColorOverlayInfo();
				
				TestColorView view = (TestColorView) findViewById(R.id.testView);
				Bitmap bm = convertViewToBitmap(view, view.getWidth(), view.getHeight());
				int color = bm.getPixel(bm.getWidth() / 2, bm.getHeight() / 2);
				((TextView)findViewById(R.id.textViewColorTest)).setText("0x" + Integer.toHexString(color).toUpperCase());
				break;
			}
		}
	};
	public Bitmap convertViewToBitmap(View view, int bitmapWidth, int bitmapHeight){
        Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bitmap));
        return bitmap;
    }
	private TextView textViewColor1 = null;
	private TextView textViewColor2 = null;
	private TextView textViewColorOverlay = null;

	private ImageView imageViewColor1 = null;
	private ImageView imageViewColor2 = null;
	private ImageView imageViewColorOverlay = null;

	private int color1 = Color.argb(150, 0xc2, 0x6b, 0x46);
	private int color2 = Color.argb(100, 0x6d, 0x85, 0xb7);//Color.YELLOW;
	private int colorOverlay = Color.BLACK;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.color_overlay);
		setTitle(R.string.color_overlay_test);
		initViews();
	}

	private boolean showColorSelector(int initColor, final int flag) {
		ColorPickerDialog picker = new ColorPickerDialog(this, initColor);
		picker.setOnColorChangedListener(new OnColorChangedListener() {

			@Override
			public void onColorChanged(int color) {
				if (flag == 1) {
					color1 = color;
					showColor1Info();
				} else if (flag == 2) {
					color2 = color;
					showColor2Info();
				}
			}
		});
		picker.setAlphaSliderVisible(true);
		picker.show();
		return false;
	}

	private void initViews() {
		this.findViewById(R.id.buttonColor1).setOnClickListener(onClickListener);
		this.findViewById(R.id.buttonColor2).setOnClickListener(onClickListener);
		this.findViewById(R.id.buttonColorOverlay).setOnClickListener(onClickListener);

		this.textViewColor1 = (TextView) this.findViewById(R.id.textViewColor1);
		this.textViewColor2 = (TextView) this.findViewById(R.id.textViewColor2);
		this.textViewColorOverlay = (TextView) this.findViewById(R.id.textViewColorOverlay);

		this.imageViewColor1 = (ImageView) this.findViewById(R.id.imageViewColor1);
		this.imageViewColor2 = (ImageView) this.findViewById(R.id.imageViewColor2);
		this.imageViewColorOverlay = (ImageView) this .findViewById(R.id.imageViewColorOverlay);
		showColor1Info();
		showColor2Info();
//		((ImageView)this.findViewById(R.id.imageViewTest)).setImageBitmap(this.overlay(BitmapFactory.decodeResource(getResources(), R.drawable.mm)));
	}

	private void showColor1Info() {
		showColorInfo(color1, textViewColor1, imageViewColor1);
	}

	private void showColor2Info() {
		showColorInfo(color2, textViewColor2, imageViewColor2);
	}

	private void showColorOverlayInfo() {
		showColorInfo(colorOverlay, textViewColorOverlay, imageViewColorOverlay);
	}

	private void showColorInfo(int color, TextView tv, ImageView iv) {
		tv.setText("0x" + Integer.toHexString(color).toUpperCase());
		iv.setBackgroundColor(color);
	}

	private void calcColorOverlay() {

		int a1 = Color.alpha(color1);
		int r1 = Color.red(color1);
		int g1 = Color.green(color1);
		int b1 = Color.blue(color1);

		int a2 = Color.alpha(color2);
		int r2 = Color.red(color2);
		int g2 = Color.green(color2);
		int b2 = Color.blue(color2);

		float alpha_down = a1 / 255.0f;
		float alpha_up = a2 / 255.0f;
		float alpha_conbin = alpha_down + alpha_up - alpha_up * alpha_down;
		int a3 = (int) (alpha_conbin * 255.0f + 0.5f);
		int r3 = layerOverlay(r2, r1, alpha_up, alpha_down);
		int g3 = layerOverlay(g2, g1, alpha_up, alpha_down);
		int b3 = layerOverlay(b2, b1, alpha_up, alpha_down);
		colorOverlay = Color.argb(a3, r3, g3, b3);
	}

	public int layerOverlay(int up, int down, float alpha_up, float alpha_down) {
//		// 这是底色的纯色的情况
//		return (int) (up * alpha_up + down * (1 - alpha_up));
		return (int) ((down * alpha_down * (1 - alpha_up) + alpha_up * up) / (alpha_down + alpha_up - alpha_up * alpha_down) + 0.5f);
	}
}

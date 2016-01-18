/**
 * @file DrawCustomFontView.java
 * @Package com.domker.androidtoy.view
 * @Project PhoneTest
 * Description: TODO 
 * 
 * @author wanlipeng
 * @since 1.0.0.0
 * @version 1.0.0.0
 * @date 2014 ����5:03:27
 * 
 *       \if TOSPLATFORM_CONFIDENTIAL_PROPRIETARY
 *       ================================
 *            \n Copyright (c) 2014
 *       wanlipeng. All Rights Reserved.\n \n
 *       ==================================
 *       ==========================================\n \n Update History\n \n
 *       Author (Name[WorkID]) | Modification | Tracked Id | Description\n
 *       --------------------- | ------------ | ---------- |
 *       ------------------------\n wanlipeng | 2014 ����5:03:27 | <xxxxxxxx>
 *       | Initial Created.\n \n \endif
 * 
 *       <tt>
 * \n
 * Release History:\n
 * \n
 * Author (Name[WorkID]) | ModifyDate | Version | Description \n
 * --------------------- | ---------- | ------- | -----------------------------\n
 * wanlipeng |2014 ����5:03:27 | 1.0.0.0 | Initial created. \n
 * \n
 * </tt>
 */
package com.domker.androidtoy.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

/** 
 * 
 * @deprecated 暂未启用
 * @ClassName: DrawCustomFontView 
 * @author wanlipeng
 * @date 2014年9月23日 下午5:03:27  
 */
public class DrawCustomFontView extends View {
	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private int startCode = 0xEE01;
	private int endCode = 0xEEFF;
	private int line = 0;
	private int itemWidth;
	private int itemHeight;
	
	private int screenWidth = 0;
	private int screenHeight = 0;
	
	public DrawCustomFontView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public DrawCustomFontView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public DrawCustomFontView(Context context) {
		super(context);
		init();
	}

	private void init() {
		line = ((endCode - startCode - 1) / 16 + 1) * 4;
		screenWidth = getContext().getResources().getDisplayMetrics().widthPixels;
		screenHeight = getContext().getResources().getDisplayMetrics().heightPixels;
		mPaint.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/FTCustFont.ttf"));
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		itemWidth = screenWidth / 4;
		itemHeight = itemWidth * 2 / 3;
		setMeasuredDimension(widthMeasureSpec, itemHeight * line);
		mPaint.setTextSize(50);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// draw line
		mPaint.setColor(Color.RED);
		for (int j = 1; j < 4; j++) {
			canvas.drawLine(itemWidth * j, 0, itemWidth * j, getHeight(), mPaint);
		}
		for (int i = 1; i < line; i++) {
			canvas.drawLine(0, itemHeight * i, getWidth(), itemHeight * i, mPaint);
		}
		mPaint.setColor(Color.BLACK);
		for (int i = 0; i < line; i++) {
			for (int j = 0; j < 4; j++) {
				int textCode = startCode + i * 4 + j;
				
				String code = "0x" + Integer.toHexString(textCode).toUpperCase();
				int x = (int) (itemWidth * j + itemWidth / 2 - mPaint.measureText(code) / 2);
				int y = itemHeight * i + itemHeight / 3;
				canvas.drawText(code, x, y, mPaint);
				
				String text = (char)textCode + "";
				x = (int) (itemWidth * j + itemWidth / 2 - mPaint.measureText(text) / 2);
				y = itemHeight * i + itemHeight * 5 / 6;
				canvas.drawText(text, x, y, mPaint);
				
			}
		}
	}

}

/**
 * @file WebpActivity.java
 * @Package com.domker.androidtoy.activity
 * @Project AndroidToy
 * Description: TODO 
 * 
 * @author wanlipeng
 * @since 1.0.0.0
 * @version 1.0.0.0
 * @date 2016 下午3:08:51
 * 
 *       \if TOSPLATFORM_CONFIDENTIAL_PROPRIETARY
 *       ================================
 *            \n Copyright (c) 2014
 *       wanlipeng. All Rights Reserved.\n \n
 *       ==================================
 *       ==========================================\n \n Update History\n \n
 *       Author (Name[WorkID]) | Modification | Tracked Id | Description\n
 *       --------------------- | ------------ | ---------- |
 *       ------------------------\n wanlipeng | 2016 下午3:08:51 | <xxxxxxxx>
 *       | Initial Created.\n \n \endif
 * 
 *       <tt>
 * \n
 * Release History:\n
 * \n
 * Author (Name[WorkID]) | ModifyDate | Version | Description \n
 * --------------------- | ---------- | ------- | -----------------------------\n
 * wanlipeng |2016 下午3:08:51 | 1.0.0.0 | Initial created. \n
 * \n
 * </tt>
 */
package com.domker.androidtoy.activity;

import java.io.InputStream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.domker.androidtoy.R;
import com.domker.androidtoy.webp.WebPUtil;

/**
 * WebP绘制的测试
 * 
 * @ClassName: WebpActivity
 * @author wanlipeng
 * @date 2016年2月14日 下午3:08:51
 */
public class WebpActivity extends Activity {
	private ImageView mImageViewNormal = null;
	private ImageView mImageViewWebp = null;
	
	private TextView mTextViewNormal = null;
	private TextView mTextViewWebp = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webp_layout);

		mImageViewNormal = (ImageView) findViewById(R.id.imageViewNormal);
		mImageViewWebp = (ImageView) findViewById(R.id.imageViewWebp);
		mTextViewNormal = (TextView) findViewById(R.id.textViewNormal);
		mTextViewWebp = (TextView) findViewById(R.id.textViewWebp);

		final Button loadEmbeddedImageButton = (Button) findViewById(R.id.loadEmbeddedImage);
		loadEmbeddedImageButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				loadImage();
			}
		});
	}
	
	private void loadImage() {
		// WebP
		long begin = System.currentTimeMillis();
		if (Build.VERSION.SDK_INT >= 14) {
			mImageViewNormal.setImageResource(R.drawable.image_webp);
		} else {
			InputStream rawImageStream = getResources().openRawResource(R.drawable.image_webp);
			byte[] data = WebPUtil.streamToBytes(rawImageStream);
			final Bitmap webpBitmap = WebPUtil.webpToBitmap(data);
			mImageViewWebp.setImageBitmap(webpBitmap);
		}
		long end = System.currentTimeMillis();
		mTextViewWebp.setText(String.format("WebP: %dms", end - begin));
		
		// Normal
		begin = System.currentTimeMillis();
		mImageViewNormal.setImageResource(R.drawable.image_jpg);
		end = System.currentTimeMillis();
		mTextViewNormal.setText(String.format("WebP: %dms", end - begin));
	}
}

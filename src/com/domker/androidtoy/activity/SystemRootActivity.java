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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.domker.androidtoy.R;
import com.domker.androidtoy.skin.SogouSkinResource;
import com.domker.androidtoy.util.PhoneUtil;

//=============================================================================
//CLASS DEFINITIONS
//=============================================================================
/**
 * TODO
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
//						tvRootInfo.setText(RootUtil.getRootAuth() ? "已经Root" : "未Root");
//						initNinePatch();
						final Animation anim = AnimationUtils.loadAnimation(SystemRootActivity.this, R.anim.image_anim);
						imageViewPNG.startAnimation(anim);
					}
				});
		tvRootInfo = (TextView) findViewById(R.id.textViewRoot);
		imageViewPNG = (ImageView) findViewById(R.id.imageViewPng);
	}

	private void initNinePatch() {
		BitmapFactory.Options op = new Options();
		op.inScaled = false;
		Bitmap bmSrc = BitmapFactory.decodeResource(getResources(), R.drawable.candidate_bar, op);
		createCand(bmSrc);
		bmSrc.recycle();
		bmSrc = BitmapFactory.decodeResource(getResources(), R.drawable.keyboardback, op);
		createKeyboard(bmSrc);
		bmSrc.recycle();
	}

	private void createCand(Bitmap bmSrc) {
		SogouSkinResource res = new SogouSkinResource();
		res.setBitmap(bmSrc);
		Rect r = new Rect(50, 72, bmSrc.getWidth() - 166, bmSrc.getHeight() - 20);
//		Rect r = new Rect(20, 10, bmSrc.getWidth() - 20, bmSrc.getHeight() - 10);
		res.setScaleRect(r);
		Rect context = new Rect(0, 52, bmSrc.getWidth() - 120, bmSrc.getHeight() - 8);
		res.setContextRect(context);
		Bitmap b = res.createCandidateBackground(720, 96);
//		Bitmap b = res.createKeyboardBackground(720, 486, SogouSkinResource.LAYOUT_TILE_MODE, SogouSkinResource.LAYOUT_TILE_MODE);
		if (b != null) {
			imageViewPNG.setBackgroundColor(Color.BLACK);
			res.saveBitmapToFile(b, PhoneUtil.getInstance(this).getSDCardPath() + "/Tencent/QQInput/Skin/101/.nomedia/toolbar_portrait.jpg");
			
			b = res.createCandidateSrcBackground(720, 96);
			imageViewPNG.setImageBitmap(b);
			res.saveBitmapToFile(b, PhoneUtil.getInstance(this).getSDCardPath() + "/Tencent/QQInput/Skin/101/.nomedia/toolbar_portrait_src.png", true);
			
			b = res.createCandidateBackground(1280, 96);
			res.saveBitmapToFile(b, PhoneUtil.getInstance(this).getSDCardPath() + "/Tencent/QQInput/Skin/101/.nomedia/toolbar_landscape.jpg");
			
			b = res.createCandidateSrcBackground(1280, 96);
			res.saveBitmapToFile(b, PhoneUtil.getInstance(this).getSDCardPath() + "/Tencent/QQInput/Skin/101/.nomedia/toolbar_landscape_src.png", true);
		}
	}
	
	private void createKeyboard(Bitmap bmSrc) {
		SogouSkinResource res = new SogouSkinResource();
		res.setBitmap(bmSrc);
		Rect r = new Rect(20, 10, bmSrc.getWidth() - 20, bmSrc.getHeight() - 10);
		res.setScaleRect(r);
		Bitmap b = res.createKeyboardBackground(720, 486 + 96, SogouSkinResource.LAYOUT_TILE_MODE, SogouSkinResource.LAYOUT_TILE_MODE);
		if (b != null) {
			res.saveBitmapToFile(b, PhoneUtil.getInstance(this).getSDCardPath() + "/Tencent/QQInput/Skin/101/.nomedia/background_portrait.jpg");
			b = res.createKeyboardBackground(1280, 366 + 96, SogouSkinResource.LAYOUT_TILE_MODE, SogouSkinResource.LAYOUT_TILE_MODE);
			res.saveBitmapToFile(b, PhoneUtil.getInstance(this).getSDCardPath() + "/Tencent/QQInput/Skin/101/.nomedia/background_landscape.jpg");
		}
	}
}

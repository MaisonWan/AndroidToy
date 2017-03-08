/**
 * @file LayerBlendingActivity.java
 * @Package com.tencent.test
 * Description: TODO 
 * 
 * @author maisonwan
 * @since 1.0.0.0
 * @version 1.0.0.0
 * @date 2014-2-21
 * 
 *       \if TOSPLATFORM_CONFIDENTIAL_PROPRIETARY
 *       ================================
 *            \n Copyright (c) 2011
 *       maisonwan. All Rights Reserved.\n \n
 *       ==================================
 *       ==========================================\n \n Update History\n \n
 *       Author (Name[WorkID]) | Modification | Tracked Id | Description\n
 *       --------------------- | ------------ | ---------- |
 *       ------------------------\n maisonwan | 2014-2-21 | <xxxxxxxx>
 *       | Initial Created.\n \n \endif
 * 
 *       <tt>
 * \n
 * Release History:\n
 * \n
 * Author (Name[WorkID]) | ModifyDate | Version | Description \n
 * --------------------- | ---------- | ------- | -----------------------------\n
 * maisonwan |2014-2-21 | 1.0.0.0 | Initial created. \n
 * \n
 * </tt>
 */
package com.domker.androidtoy.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.domker.androidtoy.R;
import com.domker.androidtoy.image.BitmapLayerBlending;
import com.domker.androidtoy.image.BitmapLayerBlending.LayerBlendable;
import com.domker.androidtoy.image.BitmapLayerBlending.OnProgressListener;
import com.domker.androidtoy.image.BitmapUtil;
import com.domker.androidtoy.image.LayerAdd;
import com.domker.androidtoy.image.LayerColorBurn;
import com.domker.androidtoy.image.LayerColorDodge;
import com.domker.androidtoy.image.LayerDarken;
import com.domker.androidtoy.image.LayerDifference;
import com.domker.androidtoy.image.LayerExclusion;
import com.domker.androidtoy.image.LayerHardLight;
import com.domker.androidtoy.image.LayerHardMix;
import com.domker.androidtoy.image.LayerLighten;
import com.domker.androidtoy.image.LayerMultiply;
import com.domker.androidtoy.image.LayerOverlay;
import com.domker.androidtoy.image.LayerPinLight;
import com.domker.androidtoy.image.LayerScreen;
import com.domker.androidtoy.image.LayerSoftLight;
import com.domker.androidtoy.image.LayerSubtract;
import com.domker.androidtoy.image.LayerVividLight;

//=============================================================================
//CLASS DEFINITIONS
//=============================================================================
/**
 * TODO
 * @author maisonwan
 * @date 2014-2-21 下午2:50:16
 *
 */
public class LayerBlendingActivity extends Activity {

	private final String[] buttonText = { "原图", "变暗", "正片叠加", "颜色加深", "线性加深", "变亮",
			"滤色", "颜色减淡", "线性减淡", "叠加", "柔光", "强光", "亮光", "线性光", "点光", "实色混色",
			"色差", "排除" };

	private final LayerBlendable[] blends = { null,
			new LayerDarken(), new LayerMultiply(), new LayerColorBurn(),
			new LayerSubtract(), new LayerLighten(), new LayerScreen(),
			new LayerColorDodge(), new LayerAdd(), new LayerOverlay(),
			new LayerSoftLight(), new LayerHardLight(), new LayerVividLight(),
			new LayerLinearLight(), new LayerPinLight(), new LayerHardMix(),
			new LayerDifference(), new LayerExclusion() };

	private Bitmap downBitmap = null;
	private Bitmap upBitmap = null;

	private ImageView imageView = null;
	private ProgressBar progressBar = null;
	private BitmapLayerBlending layerBlending = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layer_blending);
		setTitle(R.string.layer_blend_test);
		this.initViews();
		this.initBitmap();
	}
	
	private void initViews() {
		LinearLayout layout = (LinearLayout) this.findViewById(R.id.buttonsLayout);
		for(int i = 0; i < buttonText.length; i++) {
			Button b = new Button(this);
			b.setText(buttonText[i]);
			b.setId(i);
			b.setOnClickListener(onClickListener);
			b.setBackgroundResource(R.drawable.button_bg);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			lp.setMargins(0, 0, 10, 0);
			b.setLayoutParams(lp);
			layout.addView(b);
		}
		imageView = (ImageView) this.findViewById(R.id.imageViewBlending);
		progressBar = (ProgressBar) this.findViewById(R.id.progressBarBlend);
	}
	
	private void initBitmap() {
		downBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.down);
		int screen_width = getWindowManager().getDefaultDisplay().getWidth() - 100;
		Bitmap b = BitmapUtil.scaleBitmap(downBitmap, screen_width * downBitmap.getHeight() / downBitmap.getWidth() , screen_width);
		downBitmap.recycle();
		downBitmap = b;
		
		upBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.up);
		layerBlending = new BitmapLayerBlending(downBitmap, upBitmap);
		imageView.setImageBitmap(downBitmap);
	}
	
	private View.OnClickListener onClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (v.getId() > 0 && v.getId() < blends.length) {
				layerBlending.setLayerBlendable(blends[v.getId()]);
			}
			if (v.getId() == 0) {
				imageView.setImageBitmap(downBitmap);
			} else {
				LayerBlendTask task = new LayerBlendTask();
				task.execute();
			}
		}
	};
	
	//=============================================================================
	//CLASS DEFINITIONS
	//=============================================================================
	/**
	* 异步处理图像
	* @author maisonwan
	* @date 2014-2-21 下午5:30:01
	*
	*/
	private class LayerBlendTask extends AsyncTask<Void, Integer, Bitmap> {

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected Bitmap doInBackground(Void... params) {
			layerBlending.setOnProgressChanged(new OnProgressListener() {
				
				@Override
				public void onProgressChanged(int totle, int progress) {
					publishProgress((int)(progress * 100.0f / totle));
				}
			});
			return layerBlending.layerBlending();
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			imageView.setImageBitmap(result);
			progressBar.setVisibility(View.INVISIBLE);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// 开始执行异步线程
			progressBar.setVisibility(View.VISIBLE);
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			progressBar.setProgress(values[0]);
		}
		
	}
}

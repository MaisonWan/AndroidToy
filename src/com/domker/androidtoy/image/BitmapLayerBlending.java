/**
 * @file BitmapLayerBlending.java
 * @Package com.tencent.test.image
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
package com.domker.androidtoy.image;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;

//=============================================================================
//CLASS DEFINITIONS
//=============================================================================
/**
 * TODO
 * @author maisonwan
 * @date 2014-2-21 下午4:06:47
 *
 */
public class BitmapLayerBlending {
	private LayerBlendable layerBlendable = null;
	private OnProgressListener onProgressChanged = null;
	
	private int down_width;
	private int down_height;
	
	private Bitmap downBitmap = null;
	private Bitmap upBitmap = null;

	public BitmapLayerBlending(Bitmap downBitmap, Bitmap upBitmap) {
		this.downBitmap = downBitmap;
		this.upBitmap = upBitmap;
		down_height = downBitmap.getHeight();
		down_width = downBitmap.getWidth();
		
		int up_height = upBitmap.getHeight();
		int up_width = upBitmap.getWidth();
		if (down_height != up_height || down_width != up_width) {
			Bitmap b = BitmapUtil.scaleBitmap(upBitmap, down_height, down_width);
			this.upBitmap.recycle();
			this.upBitmap = b;
		}
	}
	
	// -----------------------------------------------------------------------
	/**
	 * 开始合并
	 * @return Bitmap
	 */
	public Bitmap layerBlending() {
		Bitmap resultBitmap = Bitmap.createBitmap(down_width, down_height, Config.ARGB_8888);
		int count = 0;
		for (int j = 0; j < down_height; j++) {
			for (int i = 0; i < down_width; i++) {
				int d = downBitmap.getPixel(i, j);
				int dr = Color.red(d);
				int dg = Color.green(d);
				int db = Color.blue(d);
				
				int u = upBitmap.getPixel(i, j);
				int ur = Color.red(u);
				int ug = Color.green(u);
				int ub = Color.blue(u);
				
				if (layerBlendable != null) {
					int c = Color.rgb(layerBlendable.pixelCalc(dr, ur),
							layerBlendable.pixelCalc(dg, ug),
							layerBlendable.pixelCalc(db, ub));
					resultBitmap.setPixel(i, j, c);
					count++;
				}
				if (onProgressChanged != null && count >= 1000) {
					onProgressChanged.onProgressChanged(down_width * down_height, j * down_width + i + 1);
					count = 0;
				}
			}
		}
		return resultBitmap;
	}
	
	public void setLayerBlendable(LayerBlendable layerBlendable) {
		this.layerBlendable = layerBlendable;
	}

	public void setOnProgressChanged(OnProgressListener onProgressChanged) {
		this.onProgressChanged = onProgressChanged;
	}

	public interface LayerBlendable {
		public int pixelCalc(int down, int up);
	}
	
	public interface OnProgressListener {
		public void onProgressChanged(int totle, int progress);
	}
}

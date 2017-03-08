/**
 * @file BitmapUtil.java
 * @Package com.tencent.test.util
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

//=============================================================================
//CLASS DEFINITIONS
//=============================================================================
/**
 * TODO
 * @author maisonwan
 * @date 2014-2-21 下午4:00:11
 *
 */
public class BitmapUtil {
	// -----------------------------------------------------------------------
	/**
	 * 缩放图片
	 * @param src
	 * @param dstHeight
	 * @param dstWidth
	 * @return Bitmap
	 */
	public static Bitmap scaleBitmap(Bitmap src, int dstHeight, int dstWidth) {
		int h = src.getHeight();
		int w = src.getWidth();
		if (h == dstHeight && w == dstWidth) {
			return src;
		}
		Bitmap result = Bitmap.createScaledBitmap(src, dstWidth, dstHeight, true);
		return result;
	}
}

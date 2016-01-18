/**
 * @file ImageHandle.java
 * @Package com.tencent.test.skin
 * Description: TODO 
 * 
 * @author maisonwan
 * @since 1.0.0.0
 * @version 1.0.0.0
 * @date 2014-3-3
 * 
 *       \if TOSPLATFORM_CONFIDENTIAL_PROPRIETARY
 *       ================================
 *            \n Copyright (c) 2011
 *       maisonwan. All Rights Reserved.\n \n
 *       ==================================
 *       ==========================================\n \n Update History\n \n
 *       Author (Name[WorkID]) | Modification | Tracked Id | Description\n
 *       --------------------- | ------------ | ---------- |
 *       ------------------------\n maisonwan | 2014-3-3 | <xxxxxxxx>
 *       | Initial Created.\n \n \endif
 * 
 *       <tt>
 * \n
 * Release History:\n
 * \n
 * Author (Name[WorkID]) | ModifyDate | Version | Description \n
 * --------------------- | ---------- | ------- | -----------------------------\n
 * maisonwan |2014-3-3 | 1.0.0.0 | Initial created. \n
 * \n
 * </tt>
 */
package com.domker.androidtoy.skin;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;

//=============================================================================
//CLASS DEFINITIONS
//=============================================================================
/**
* 图像处理
* 
* @author maisonwan
* @date 2014-3-3 下午3:55:27
* 
*/
public class ImageHandle {
	/**
	 * 从上到下
	 */
	public static final int UP = 0x00000001;
	public static final int DOWN = 0x00000002;
	public static final int LEFT = 0x00000004;
	public static final int RIGHT = 0x00000008;

	// -----------------------------------------------------------------------
	/**
	 * 寻找可视化区域，包含内容区的部分
	 * 
	 * @param bm
	 * @param scaleRect
	 * @param direction 标识方向
	 * @return Rect
	 */
	public Rect searchVisibleRect(Bitmap bm, Rect scaleRect, Rect contextRect, int direction) {
		int w = bm.getWidth();
		int h = bm.getHeight();
		Rect result = new Rect(0, 0, w, h);
		int temp;
		if ((UP & direction) != 0) {
			int end = Math.max(contextRect.top, h / 2);
			int compare = end;
			for (int i = scaleRect.left; i <= scaleRect.right; i++) {
				temp = 0;
				for (int j = 1; j <= end; j++) {
					if (!isVisible(bm.getPixel(i, j)) && j > temp) {
						temp = j;
					} else {
						break;
					}
				}
				compare = compare > temp ? temp : compare;
			}
			result.top = compare;
		}
		if ((DOWN & direction) != 0) {
			if ((UP & direction) != 0) {
				int end = Math.min(h / 2, contextRect.bottom);
				int compare = end;
				for (int i = scaleRect.left; i <= scaleRect.right; i++) {
					temp = bm.getHeight() - 1;
					for (int j = bm.getHeight() - 1; j > end; j--) {
						if (!isVisible(bm.getPixel(i, j)) && j < temp) {
							temp = j;
						}
					}
					compare = compare < temp ? temp : compare;
				}
				result.bottom = compare;
			}
		}
		if ((LEFT & direction) != 0) {
	
		}
		if ((RIGHT & direction) != 0) {

		}
		return result;
	}
	
	// -----------------------------------------------------------------------
	/**
	 * 判断是否透明
	 * @param color
	 * @return boolean true为可看到
	 */
	private boolean isVisible(int color) {
		return Color.alpha(color) > 40;
	}
	
}

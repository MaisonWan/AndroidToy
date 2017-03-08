/**
 * @file RectUtil.java
 * @Package com.tencent.test.skin
 * Description: TODO 
 * 
 * @author maisonwan
 * @since 1.0.0.0
 * @version 1.0.0.0
 * @date 2014-3-4
 * 
 *       \if TOSPLATFORM_CONFIDENTIAL_PROPRIETARY
 *       ================================
 *            \n Copyright (c) 2011
 *       maisonwan. All Rights Reserved.\n \n
 *       ==================================
 *       ==========================================\n \n Update History\n \n
 *       Author (Name[WorkID]) | Modification | Tracked Id | Description\n
 *       --------------------- | ------------ | ---------- |
 *       ------------------------\n maisonwan | 2014-3-4 | <xxxxxxxx>
 *       | Initial Created.\n \n \endif
 * 
 *       <tt>
 * \n
 * Release History:\n
 * \n
 * Author (Name[WorkID]) | ModifyDate | Version | Description \n
 * --------------------- | ---------- | ------- | -----------------------------\n
 * maisonwan |2014-3-4 | 1.0.0.0 | Initial created. \n
 * \n
 * </tt>
 */
package com.domker.androidtoy.skin;

import android.graphics.Rect;

//=============================================================================
//CLASS DEFINITIONS
//=============================================================================
/**
 * 矩形区域的工具
 * 
 * @author maisonwan
 * @date 2014-3-4 下午3:42:59
 * 
 */
public class RectUtil {
	/**
	 * 左上区域
	 */
	public static final int REGION_LEFT_TOP = 1;
	/**
	 * 顶部区域
	 */
	public static final int REGION_TOP = 2;
	/**
	 * 右上
	 */
	public static final int REGION_RIGHT_TOP = 3;
	/**
	 * 右侧区域
	 */
	public static final int REGION_RIGHT = 4;
	/**
	 * 右下
	 */
	public static final int REGION_RIGHT_BOTTOM = 5;
	/**
	 * 底部区域
	 */
	public static final int REGION_BOTTOM = 6;
	/**
	 * 左下
	 */
	public static final int REGION_LEFT_BOTTOM = 7;
	/**
	 * 左侧区域
	 */
	public static final int REGION_LEFT = 8;
	/**
	 * 最中心区域
	 */
	public static final int REGION_CENTER = 9;

	/**
	 * 需要画图的面板区域
	 */
	private Rect boardBoundsRect = null;
	/**
	 * 图片资源的边界区域
	 */
	private Rect imgBoundsRect = null;
	/**
	 * 图片资源的缩放区域
	 */
	private Rect imgStretchRect = null;

	public Rect getBoardBoundsRect() {
		return boardBoundsRect;
	}

	public void setBoardBoundsRect(Rect boardBoundsRect) {
		this.boardBoundsRect = boardBoundsRect;
	}

	public Rect getImgBoundsRect() {
		return imgBoundsRect;
	}

	public void setImgBoundsRect(Rect imgBoundsRect) {
		this.imgBoundsRect = imgBoundsRect;
	}

	public Rect getImgStretchRect() {
		return imgStretchRect;
	}

	public void setImgStretchRect(Rect imgStretchRect) {
		this.imgStretchRect = imgStretchRect;
	}

	// -----------------------------------------------------------------------
	/**
	 * 根据图片的缩放区域，得到九个不同的区域
	 * 
	 * @param region
	 *            1,3,5,7分别代表左上，右上，左下，右下
	 * @return Rect
	 */
	public Rect getImageRegion(int region) {
		return getRegion(this.imgBoundsRect, this.imgStretchRect, region);
	}
	
	// -----------------------------------------------------------------------
	/**
	 * 得到面板的九段区域，但是需要依赖上面图片的拉伸和边界区域
	 * @param region
	 * @return Rect
	 */
	public Rect getBoardRegion(int region) {
		Rect boardNinePatchRect = this.getBoardNinePathchRect(boardBoundsRect, imgBoundsRect, imgStretchRect);
		return getRegion(this.boardBoundsRect, boardNinePatchRect, region);
	}
	
	// -----------------------------------------------------------------------
	/**
	 * 得到面板的九段区域
	 * @param boardBounds
	 * @param imgStretchRect
	 * @return Rect
	 */
	private Rect getBoardNinePathchRect(Rect boardBounds, Rect imgBoundsRect, Rect imgStretchRect) {
		Rect r = new Rect(boardBounds);
		r.left = imgStretchRect.left;
		r.right = boardBounds.right - imgBoundsRect.right + imgStretchRect.right;
		r.top = imgStretchRect.top;
		r.bottom = boardBounds.bottom - imgBoundsRect.bottom + imgStretchRect.bottom;
		return r;
	}
	
	// -----------------------------------------------------------------------
	/**
	 * 得到区域 
	 * @param boundsRect 总区域大小
	 * @param stretchRect 拉伸区域
	 * @param region
	 * @return Rect
	 */
	public static Rect getRegion(Rect boundsRect, Rect stretchRect, int region) {
		Rect r = new Rect();
		switch (region) {
		case REGION_LEFT_TOP:
			r.left = 0;
			r.top = 0;
			r.right = stretchRect.left;
			r.bottom = stretchRect.top;
			break;
		case REGION_TOP:
			r.left = stretchRect.left;
			r.top = 0;
			r.right = stretchRect.right;
			r.bottom = stretchRect.top;
			break;
		case REGION_RIGHT_TOP:
			r.left = stretchRect.right;
			r.top = 0;
			r.right = boundsRect.right;
			r.bottom = stretchRect.top;
			break;
		case REGION_RIGHT:
			r.left = stretchRect.right;
			r.top = stretchRect.top;
			r.right = boundsRect.right;
			r.bottom = stretchRect.bottom;
			break;
		case REGION_RIGHT_BOTTOM:
			r.left = stretchRect.right;
			r.top = stretchRect.bottom;
			r.right = boundsRect.right;
			r.bottom = boundsRect.bottom;
			break;
		case REGION_BOTTOM:
			r.left = stretchRect.left;
			r.top = stretchRect.bottom;
			r.right = stretchRect.right;
			r.bottom = boundsRect.bottom;
			break;
		case REGION_LEFT_BOTTOM:
			r.left = 0;
			r.top = stretchRect.bottom;
			r.right = stretchRect.left;
			r.bottom = boundsRect.bottom;
			break;
		case REGION_LEFT:
			r.left = 0;
			r.top = stretchRect.top;
			r.right = stretchRect.left;
			r.bottom = stretchRect.bottom;
			break;
		case REGION_CENTER:
			r.set(stretchRect);
			break;
		}
		return r;
	}
}

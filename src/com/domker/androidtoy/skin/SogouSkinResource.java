/**
 * @file SogouSkinResource.java
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.Rect;


//=============================================================================
//CLASS DEFINITIONS
//=============================================================================
/**
 * TODO
 * 
 * @author maisonwan
 * @date 2014-3-3 上午10:55:43
 * 
 */
public class SogouSkinResource {
	/**
	 * 拉伸或者平铺
	 */
	public static final int STYLE_STRETCH_OR_TILE = 0;
	/**
	 * 填充颜色
	 */
	public static final int STYLE_FILL_COLOR = 1;
	/**
	 * 自定裁剪
	 */
	public static final int STYLE_AUTO_CROP = 2;

	/**
	 * 拉伸方式
	 */
	public static final int LAYOUT_STRETCH_MODE = 0;
	/**
	 * 平铺方式
	 */
	public static final int LAYOUT_TILE_MODE = 1;
	
	private Bitmap bm = null;
	/**
	 * 原始的拉伸区域
	 */
	private Rect originalStretchRect;
	/**
	 * 原始的文本区域
	 */
	private Rect originalContextRect;
	/**
	 * 可视化区域
	 */
	private Rect bitmapVisibleRect;
	
	private RectUtil rectUtil = new RectUtil();
	private ImageHandle imgHandle = new ImageHandle();

	public Bitmap getBitmap() {
		return bm;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bm = bitmap;
		setAllRectToVisible();
	}

	// -----------------------------------------------------------------------
	/**
	 *  设置图片所有的区域为可视化区域
	 */
	private void setAllRectToVisible() {
		bitmapVisibleRect = new Rect(0, 0, bm.getWidth(), bm.getHeight());
	}

	// -----------------------------------------------------------------------
	/**
	 * 设置图片的缩放区域
	 * @param stretchRect void
	 */
	public void setScaleRect(Rect stretchRect) {
		this.originalStretchRect = stretchRect;
	}

	// -----------------------------------------------------------------------
	/**
	 * 设置图片的内容区域
	 * @param contextRect void
	 */
	public void setContextRect(Rect contextRect) {
		this.originalContextRect = contextRect;
	}

	// -----------------------------------------------------------------------
	/**
	 * 得到Bitmap的原始可视区域
	 * @return Rect
	 */
	public Rect getBitmapVisibleRect() {
		return bitmapVisibleRect;
	}

	// -----------------------------------------------------------------------
	/**
	 * 生成目标大小的候选区背景(去除不可视区域)
	 * 
	 * @param bm 未被缩放的，否则缩放区域有问题
	 * @param destHeight
	 * @param destWidth
	 *            void
	 */
	public Bitmap createCandidateBackground(int destWidth, int destHeight) {
		return createCandidateBackground(destWidth, destHeight, true);
	}
	
	// -----------------------------------------------------------------------
	/**
	 * 生成候选区的原图（包含非可视区域），按照比例进行缩放，只要求宽度，高度按比例缩放
	 * @param destWidth
	 * @return Bitmap
	 */
	public Bitmap createCandidateSrcBackground(int destWidth, int destVisibleHeight) {
		calcBitmapVisibleRect();
		final float scale = destVisibleHeight * 1.0f / (bitmapVisibleRect.bottom - bitmapVisibleRect.top);
		setAllRectToVisible();
		int destHeight = (int) ((bitmapVisibleRect.bottom - bitmapVisibleRect.top) * scale);
		return createCandidateBackground(destWidth, destHeight, false);
	}
	
	public Bitmap createCandidateBackground(int destWidth, int destHeight, boolean searchVisible) {
		if (bm == null) {
			return null;
		}
		int bmWidth = bm.getWidth();
		Bitmap scaledBitmap = null;
		try {
			if (searchVisible) {
				// 找到可视区域
				calcBitmapVisibleRect();
			}
			// 需要缩放的倍数
			final float scale = destHeight * 1.0f / (bitmapVisibleRect.bottom - bitmapVisibleRect.top);
			// 缩放之后的拉伸区域
			Rect scaledRect = resizeRect(this.originalStretchRect, scale);
			// 缩放成与目标高度一致
			scaledBitmap = scaleBitmap(bm, bitmapVisibleRect, Math.round(bmWidth * scale), destHeight);
			// 9png
			final byte[] chunks = NinePatchChunk.getNinePatchChunk(new int[] {
					scaledRect.left, scaledRect.right }, new int[] {
					scaledRect.top, scaledRect.bottom });
			if (NinePatch.isNinePatchChunk(chunks)) {
				Bitmap destBitmap = Bitmap.createBitmap(destWidth, destHeight, Bitmap.Config.ARGB_8888);
				Canvas c = new Canvas(destBitmap);
				NinePatch np = new NinePatch(scaledBitmap, chunks, null);
				final Rect location = new Rect(0, 0, destWidth, destHeight);
				np.draw(c, location);
				return destBitmap;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (scaledBitmap != null) {
				scaledBitmap.recycle();
			}
		}
		return null;
	}

	
	/**
	 *  计算Bitmap的可视化区域
	 */
	private void calcBitmapVisibleRect() {
		bitmapVisibleRect = imgHandle.searchVisibleRect(bm, this.originalStretchRect, originalContextRect, ImageHandle.UP | ImageHandle.DOWN);
	}
	
	
	/**
	 * 根据颜色创建背景
	 * @param destWidth
	 * @param destHeight
	 * @param color 
	 */
	public Bitmap createKeyboardBackground(int destWidth, int destHeight, int color) {
		Bitmap kb = Bitmap.createBitmap(destWidth, destHeight, Bitmap.Config.RGB_565);
		Canvas c = new Canvas(kb);
		c.drawColor(color);
		return kb;
	}
	
	// -----------------------------------------------------------------------
	/**
	 * 使用原始的拉伸区域，采用九段拉伸或者平铺的方式绘制出来键盘的背景
	 * 
	 * @param destWidth
	 * @param destHeight
	 * @param layoutHorizontal
	 * @param layoutVertical void
	 */
	public Bitmap createKeyboardBackground(int destWidth, int destHeight, int layoutHorizontal, int layoutVertical) {
		Bitmap kb = Bitmap.createBitmap(destWidth, destHeight, Bitmap.Config.ARGB_8888);
		Rect bkBoard = new Rect(0, 0, kb.getWidth(), kb.getHeight());
		rectUtil.setBoardBoundsRect(bkBoard);
		rectUtil.setImgBoundsRect(bitmapVisibleRect);
		rectUtil.setImgStretchRect(originalStretchRect);
		
		Canvas c = new Canvas(kb);
		// 四角
		drawStaticRegion(c, RectUtil.REGION_LEFT_TOP);
		drawStaticRegion(c, RectUtil.REGION_RIGHT_TOP);
		drawStaticRegion(c, RectUtil.REGION_LEFT_BOTTOM);
		drawStaticRegion(c, RectUtil.REGION_RIGHT_BOTTOM);
		
		if (layoutHorizontal == LAYOUT_STRETCH_MODE) {
			// 横着如果是拉伸
			drawStaticRegion(c, RectUtil.REGION_TOP);
			drawStaticRegion(c, RectUtil.REGION_BOTTOM);
		} else if (layoutHorizontal == LAYOUT_TILE_MODE) {
			// 横着是平铺方式
			drawDynamicRegion(c, RectUtil.REGION_TOP);
			drawDynamicRegion(c, RectUtil.REGION_BOTTOM);
		}
		
		if (layoutVertical == LAYOUT_STRETCH_MODE) {
			// 竖直方向拉伸
			drawStaticRegion(c, RectUtil.REGION_LEFT);
			drawStaticRegion(c, RectUtil.REGION_RIGHT);
		} else if (layoutVertical == LAYOUT_TILE_MODE) {
			// 竖直方向平铺
			drawDynamicRegion(c, RectUtil.REGION_LEFT);
			drawDynamicRegion(c, RectUtil.REGION_RIGHT);
		}
		drawDynamicRegion(c, RectUtil.REGION_CENTER, layoutHorizontal, layoutVertical);
		return kb;
	}

	// -----------------------------------------------------------------------
	/**
	 * 画静态区域
	 * @param canvas
	 * @param region void
	 */
	private void drawStaticRegion(Canvas canvas, int region) {
		Rect r = rectUtil.getImageRegion(region);
		Rect b = rectUtil.getBoardRegion(region);
		canvas.drawBitmap(this.bm, r, b, new Paint());
	}
	
	private void drawDynamicRegion(Canvas canvas, int region) {
		drawDynamicRegion(canvas, region, LAYOUT_TILE_MODE, LAYOUT_TILE_MODE);
	}
	// -----------------------------------------------------------------------
	/**
	 * 绘制动态区域，平铺
	 * @param canvas
	 * @param region
	 * @param layoutHorizontal
	 * @param layoutVertical void
	 */
	private void drawDynamicRegion(Canvas canvas, int region, int layoutHorizontal, int layoutVertical) {
		Rect iRect = rectUtil.getImageRegion(region);
		Rect bRect = rectUtil.getBoardRegion(region);
		int hStep = iRect.right - iRect.left; // 横着的步伐
		int vStep = iRect.bottom - iRect.top; //　竖着的步伐
		Rect destRect = new Rect(bRect); // 目标区域
		Rect srcRest = new Rect(iRect); // 源区域
		if (layoutHorizontal == LAYOUT_STRETCH_MODE) {
			hStep = bRect.right - bRect.left;
		}
		if (layoutVertical == LAYOUT_STRETCH_MODE) {
			vStep = bRect.bottom - bRect.top;
		}
		
		Paint paint = new Paint();
		for (int i = bRect.right; i > bRect.left; i -= hStep) {
			destRect.right = i;
			if (i - hStep < bRect.left) {
				// 如果水平超出边界
				destRect.left = bRect.left;
				srcRest.left = iRect.left + bRect.left + hStep - i;
			} else {
				destRect.left = i - hStep;
				srcRest.left = iRect.left;
			}
			for (int j = bRect.top; j < bRect.bottom; j += vStep) {
				destRect.top = j;
				if (j + vStep > bRect.bottom) {
					destRect.bottom = bRect.bottom;
					srcRest.bottom = iRect.bottom - j + bRect.bottom - vStep;
				} else {
					destRect.bottom = j + vStep;
					srcRest.bottom = iRect.bottom;
				}
				canvas.drawBitmap(this.bm, srcRest, destRect, paint);
			}
		}
	}
	
	// -----------------------------------------------------------------------
	/**
	 * 按照比例缩放区域
	 * @param r
	 * @param scale
	 * @return Rect
	 */
	private Rect resizeRect(Rect r, float scale) {
		Rect res = new Rect();
		res.left = (int) (r.left * scale + 0.5f);
		res.right = (int) (r.right * scale + 0.5f);
		res.top = (int) (r.top * scale + 0.5f);
		res.bottom = (int) (r.bottom * scale + 0.5f);
		return res;
	}
	
	// -----------------------------------------------------------------------
	/**
	 * 缩放图片，相比系统接口实现方式不一样
	 * @param src 原图片文件
	 * @param srcRect 原图片文件的显示区域
	 * @param destWidth
	 * @param destHeight
	 * @return Bitmap
	 */
	private Bitmap scaleBitmap(Bitmap src, Rect srcRect, int destWidth, int destHeight) {
		float x = destWidth * 1.0f / src.getWidth();
		float y = destHeight * 1.0f / src.getHeight();
		Matrix m = new Matrix();
		m.postScale(x, y);
		Bitmap b = Bitmap.createBitmap(destWidth, destHeight, Config.ARGB_8888);
		Canvas c = new Canvas(b);
		c.drawBitmap(src, srcRect, new Rect(0, 0, destWidth, destHeight), new Paint());
		return b;
	}
	
	public boolean saveBitmapToFile(Bitmap bm, String outPath) {
		return saveBitmapToFile(bm, outPath, false);
	}
	
	// -----------------------------------------------------------------------
	/**
	 * 保存文件
	 * @param bm
	 * @param outPath
	 * @return boolean
	 */
	public boolean saveBitmapToFile(Bitmap bm, String outPath, boolean isPNG) {
		FileOutputStream fos;
		File dir = new File(outPath.substring(0, outPath.lastIndexOf("/") + 1));
		if (!dir.exists()) {
			dir.mkdirs();
		}
		try {
			fos = new FileOutputStream(outPath);
			boolean res = bm.compress(isPNG ? CompressFormat.PNG : CompressFormat.JPEG, 100, fos);
			fos.close();
			return res;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
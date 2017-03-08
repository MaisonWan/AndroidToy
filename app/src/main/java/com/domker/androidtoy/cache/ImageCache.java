/**
 * @file ImageCache.java
 * @Package com.tencent.test.cache
 * Description: TODO 
 * 
 * @author maisonwan
 * @since 1.0.0.0
 * @version 1.0.0.0
 * @date 2014-5-9
 * 
 *       \if TOSPLATFORM_CONFIDENTIAL_PROPRIETARY
 *       ================================
 *            \n Copyright (c) 2011
 *       maisonwan. All Rights Reserved.\n \n
 *       ==================================
 *       ==========================================\n \n Update History\n \n
 *       Author (Name[WorkID]) | Modification | Tracked Id | Description\n
 *       --------------------- | ------------ | ---------- |
 *       ------------------------\n maisonwan | 2014-5-9 | <xxxxxxxx>
 *       | Initial Created.\n \n \endif
 * 
 *       <tt>
 * \n
 * Release History:\n
 * \n
 * Author (Name[WorkID]) | ModifyDate | Version | Description \n
 * --------------------- | ---------- | ------- | -----------------------------\n
 * maisonwan |2014-5-9 | 1.0.0.0 | Initial created. \n
 * \n
 * </tt>
 */
package com.domker.androidtoy.cache;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.util.LruCache;

//=============================================================================
//CLASS DEFINITIONS
//=============================================================================
/**
 * TODO
 * 
 * @author maisonwan
 * @date 2014-5-9 下午5:27:00
 * 
 */
@SuppressLint("NewApi")
public class ImageCache {
	private LruCache<String, Bitmap> mMemoryCache = null;
	private final int maxMemory;
	private final int cacheSize;

	public ImageCache() {
		maxMemory = (int) (Runtime.getRuntime().freeMemory() / 1024);
		cacheSize = maxMemory / 8;
		mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {

			@Override
			protected int sizeOf(String key, Bitmap value) {

				return value.getByteCount() / 1024;
			}

		};
	}
	
	public void addBitmapToMemory(String key, Bitmap value) {
		if (getBitmapFromImageCache(key) == null) {
			mMemoryCache.put(key, value);
		}
	}
	
	public Bitmap getBitmapFromImageCache(String key) {
		return mMemoryCache.get(key);
	}
}

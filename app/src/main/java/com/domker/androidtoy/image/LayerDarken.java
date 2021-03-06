/**
 * @file LayerDarken.java
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

import com.domker.androidtoy.image.BitmapLayerBlending.LayerBlendable;

//=============================================================================
//CLASS DEFINITIONS
//=============================================================================
/**
 * 变暗
 * @author maisonwan
 * @date 2014-2-21 下午4:18:16
 *
 */
public class LayerDarken implements LayerBlendable {

	/* (non-Javadoc)
	 * @see com.tencent.test.image.BitmapLayerBlending.LayerBlendable#pixelCalc(int, int)
	 */
	@Override
	public int pixelCalc(int down, int up) {
		return Math.min(down, up);
	}

}

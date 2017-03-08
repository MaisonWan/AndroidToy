/**
 * @file LayerHardMix.java
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
 * TODO
 * @author maisonwan
 * @date 2014-2-21 下午4:57:48
 *
 */
public class LayerHardMix implements LayerBlendable {

	/* (non-Javadoc)
	 * @see com.tencent.test.image.BitmapLayerBlending.LayerBlendable#pixelCalc(int, int)
	 */
	@Override
	public int pixelCalc(int down, int up) {
		if (down < 128) {
			return down == 0 ? 2 * down : Math.max(0, (255 - ((255 - up) << 8)
					/ (2 * down)));
		} else {
			return (2 * (down - 128)) == 255 ? (2 * (down - 128)) : Math.min(
					255, ((up << 8) / (255 - (2 * (down - 128))))) < 128 ? 0
					: 255;
		}
	}

}

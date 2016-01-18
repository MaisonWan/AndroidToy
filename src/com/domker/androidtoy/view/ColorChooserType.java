/**
 * @file ColorChooserType.java
 * @Package com.tencent.test.view
 * Description: TODO 
 * 
 * @author maisonwan
 * @since 1.0.0.0
 * @version 1.0.0.0
 * @date 2014-2-20
 * 
 *       \if TOSPLATFORM_CONFIDENTIAL_PROPRIETARY
 *       ================================
 *            \n Copyright (c) 2011
 *       maisonwan. All Rights Reserved.\n \n
 *       ==================================
 *       ==========================================\n \n Update History\n \n
 *       Author (Name[WorkID]) | Modification | Tracked Id | Description\n
 *       --------------------- | ------------ | ---------- |
 *       ------------------------\n maisonwan | 2014-2-20 | <xxxxxxxx>
 *       | Initial Created.\n \n \endif
 * 
 *       <tt>
 * \n
 * Release History:\n
 * \n
 * Author (Name[WorkID]) | ModifyDate | Version | Description \n
 * --------------------- | ---------- | ------- | -----------------------------\n
 * maisonwan |2014-2-20 | 1.0.0.0 | Initial created. \n
 * \n
 * </tt>
 */
package com.domker.androidtoy.view;

//=============================================================================
//CLASS DEFINITIONS
//=============================================================================
/**
 * TODO
 * @author maisonwan
 * @date 2014-2-20 上午11:33:14
 *
 */
public class ColorChooserType {
	private int type = 0;
	private static final int DEFINED_COLOR = 1;
	private static final int UNIVERSAL_COLOR = 2;
	public static final ColorChooserType DEFINED_COLOR_TYPE = new ColorChooserType(
			DEFINED_COLOR);
	public static final ColorChooserType UNIVERSAL_COLOR_TYPE = new ColorChooserType(
			UNIVERSAL_COLOR);

	private ColorChooserType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	@Override
	public boolean equals(Object type) {
		if (type instanceof ColorChooserType) {
			if (this.getType() == ((ColorChooserType) type).getType()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.getType();
	}
}

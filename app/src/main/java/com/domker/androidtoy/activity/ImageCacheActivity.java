/**
 * @file ImageCacheActivity.java
 * @Package com.tencent.test
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
package com.domker.androidtoy.activity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import com.domker.androidtoy.R;
import com.domker.androidtoy.cache.ImageGridViewAdapter;

//=============================================================================
//CLASS DEFINITIONS
//=============================================================================
/**
 * TODO
 * 
 * @author maisonwan
 * @date 2014-5-9 上午11:18:57
 * 
 */
public class ImageCacheActivity extends Activity {
	private GridView mGridView = null;
	private List<Integer> urlList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_cache_layout);
		this.mGridView = (GridView) this.findViewById(R.id.gridViewImage);
		initData();
		this.mGridView.setAdapter(new ImageGridViewAdapter(this, urlList));
	}

	private void initData() {
		urlList = new ArrayList<Integer>();
		try {
			Class c = R.drawable.class;
			Field[] fields = c.getDeclaredFields();
			for (Field f : fields) {
				if (f.getName().indexOf("f") == 0) {
					urlList.add(f.getInt(null));
				}
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

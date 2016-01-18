/**
 * @file MenuAdapter.java
 * @Package com.tencent.test
 * Description: TODO 
 * 
 * @author maisonwan
 * @since 1.0.0.0
 * @version 1.0.0.0
 * @date 2014年7月10日
 * 
 *       \if TOSPLATFORM_CONFIDENTIAL_PROPRIETARY
 *       ================================
 *            \n Copyright (c) 2011
 *       maisonwan. All Rights Reserved.\n \n
 *       ==================================
 *       ==========================================\n \n Update History\n \n
 *       Author (Name[WorkID]) | Modification | Tracked Id | Description\n
 *       --------------------- | ------------ | ---------- |
 *       ------------------------\n maisonwan | 2014年7月10日 | <xxxxxxxx>
 *       | Initial Created.\n \n \endif
 * 
 *       <tt>
 * \n
 * Release History:\n
 * \n
 * Author (Name[WorkID]) | ModifyDate | Version | Description \n
 * --------------------- | ---------- | ------- | -----------------------------\n
 * maisonwan |2014年7月10日 | 1.0.0.0 | Initial created. \n
 * \n
 * </tt>
 */
package com.domker.androidtoy.activity;

import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.domker.androidtoy.R;

//=============================================================================
//CLASS DEFINITIONS
//=============================================================================
/**
 * 菜单显示的
 * 
 * @author maisonwan
 * @date 2014年7月10日 下午3:22:21
 *
 */
public class MenuAdapter extends BaseAdapter {
	private Context mContext = null;
	private List<Integer> textResList = null;
	private LayoutInflater mInflater = null;
	private int layout = R.layout.gridview_menu_item;
	
	public MenuAdapter(Context context, List<Integer> textRes, int layout) {
		super();
		this.mContext = context;
		this.textResList = textRes;
		this.layout = layout;
		this.mInflater = LayoutInflater.from(mContext);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return textResList.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return textResList.get(position);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(this.layout, null);
			holder = new ViewHolder();
			holder.textview = (TextView) convertView.findViewById(R.id.textViewCenter);
			holder.bg = convertView.findViewById(R.id.textViewBg);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.bg.setBackgroundColor(getRandomColor());
		holder.textview.setText(textResList.get(position));
		return convertView;
	}

	private class ViewHolder {
		public TextView textview = null;
		public View bg = null;
	}

	private int getRandomColor() {
		Random r = new Random();
		return Color.argb(180, r.nextInt(255), r.nextInt(255), r.nextInt(255));
	}
}

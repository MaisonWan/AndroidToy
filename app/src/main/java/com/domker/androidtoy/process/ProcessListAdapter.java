/**
 * @file ProcessListAdapter.java
 * @Package com.domker.androidtoy.process
 * @Project PhoneTest
 * Description: TODO 
 * 
 * @author wanlipeng
 * @since 1.0.0.0
 * @version 1.0.0.0
 * @date 2015 下午5:49:44
 * 
 *       \if TOSPLATFORM_CONFIDENTIAL_PROPRIETARY
 *       ================================
 *            \n Copyright (c) 2014
 *       wanlipeng. All Rights Reserved.\n \n
 *       ==================================
 *       ==========================================\n \n Update History\n \n
 *       Author (Name[WorkID]) | Modification | Tracked Id | Description\n
 *       --------------------- | ------------ | ---------- |
 *       ------------------------\n wanlipeng | 2015 下午5:49:44 | <xxxxxxxx>
 *       | Initial Created.\n \n \endif
 * 
 *       <tt>
 * \n
 * Release History:\n
 * \n
 * Author (Name[WorkID]) | ModifyDate | Version | Description \n
 * --------------------- | ---------- | ------- | -----------------------------\n
 * wanlipeng |2015 下午5:49:44 | 1.0.0.0 | Initial created. \n
 * \n
 * </tt>
 */
package com.domker.androidtoy.process;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.domker.androidtoy.R;
import com.domker.androidtoy.process.ProcessManager.ProcessInfo;

/**
 * 
 * 
 * @ClassName: ProcessListAdapter
 * @author wanlipeng
 * @date 2015年8月17日 下午5:49:44
 */
public class ProcessListAdapter extends BaseAdapter {
	private Context mContext = null;
	private List<ProcessManager.ProcessInfo> list = null;
	private LayoutInflater mInflater = null;
	
	public ProcessListAdapter(Context context, List<ProcessInfo> list) {
		this.mContext = context;
		this.list = list;
		this.mInflater = LayoutInflater.from(mContext);
	}

	/**
	 * 设置显示的数据
	 * @param list
	 */
	public void setList(List<ProcessInfo> list) {
		this.list = list;
	}

	/*
         * <p>Title: getCount</p> <p>Description: </p>
         *
         * @return
         *
         * @see android.widget.Adapter#getCount()
         */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/*
	 * <p>Title: getItem</p> <p>Description: </p>
	 * 
	 * @param position
	 * 
	 * @return
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	/*
	 * <p>Title: getItemId</p> <p>Description: </p>
	 * 
	 * @param position
	 * 
	 * @return
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/*
	 * <p>Title: getView</p> <p>Description: </p>
	 * 
	 * @param position
	 * 
	 * @param convertView
	 * 
	 * @param parent
	 * 
	 * @return
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.layout_process_item, null);
			holder = new ViewHolder();
			holder.iv = (ImageView) convertView.findViewById(R.id.imageViewIcon);
			holder.appName = (TextView) convertView.findViewById(R.id.textViewAppName);
			holder.packageName = (TextView) convertView.findViewById(R.id.textViewPackageName);
			holder.versionName = (TextView) convertView.findViewById(R.id.textViewVersionName);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ProcessInfo info = list.get(position);
		holder.packageName.setText(info.packageName);
		holder.versionName.setText("V" + info.versionName);
		holder.appName.setText(info.appName);
		holder.iv.setImageDrawable(info.appIcon);
		return convertView;
	}

	/*
	 * <p>Title: isEmpty</p> <p>Description: </p>
	 * 
	 * @return
	 * 
	 * @see android.widget.Adapter#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return list.isEmpty();
	}

	public class ViewHolder {
		public ImageView iv = null;
		public TextView appName = null;
		public TextView packageName = null;
		public TextView versionName = null;
	}
}

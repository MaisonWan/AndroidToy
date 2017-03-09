/**
 * 
 */
package com.domker.androidtoy.adapter;

import android.content.Context;
import android.support.v4.view.ActionProvider;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Toast;

import com.domker.androidtoy.R;

/**
 * 
 * @author wanlipeng 2016年12月20日
 */
public class MenuActionProvider extends ActionProvider {
	private Context mContext = null;

	@Override
	public boolean hasSubMenu() {
		return true;
	}

	@Override
	public void onPrepareSubMenu(SubMenu subMenu) {
		subMenu.clear();
		subMenu.add("sub item1")
				.setIcon(R.drawable.f009)
				.setOnMenuItemClickListener(
						new MenuItem.OnMenuItemClickListener() {
							@Override
							public boolean onMenuItemClick(MenuItem item) {
								Toast.makeText(mContext, "item1",
										Toast.LENGTH_SHORT).show();

								return false;
							}
						});

		subMenu.add("sub item2")
				.setIcon(R.drawable.f010)
				.setOnMenuItemClickListener(
						new MenuItem.OnMenuItemClickListener() {
							@Override
							public boolean onMenuItemClick(MenuItem item) {
								Toast.makeText(mContext, "item2",
										Toast.LENGTH_SHORT).show();

								return false;
							}
						});
		super.onPrepareSubMenu(subMenu);
	}

	/**
	 * @param context
	 */
	public MenuActionProvider(Context context) {
		super(context);
		this.mContext = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.view.ActionProvider#onCreateActionView()
	 */
	@Override
	public View onCreateActionView() {
		return null;
	}

}

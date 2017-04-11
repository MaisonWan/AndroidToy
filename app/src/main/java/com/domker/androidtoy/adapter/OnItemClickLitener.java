package com.domker.androidtoy.adapter;

import android.view.View;

/**
 * Item点击或者长按的监听器
 * <p>
 * Created by wanlipeng on 2017/4/11.
 */

public interface OnItemClickLitener {
    /**
     * 点击item
     *
     * @param view
     * @param position
     */
    void onItemClick(View view, int position);

    /**
     * 长按item
     *
     * @param view
     * @param position
     */
    boolean onItemLongClick(View view, int position);
}

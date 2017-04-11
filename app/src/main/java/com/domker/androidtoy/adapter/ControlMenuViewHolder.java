package com.domker.androidtoy.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.domker.androidtoy.R;

/**
 * 菜单选项ViewHolder
 * Created by wanlipeng on 2017/4/11.
 */

public class ControlMenuViewHolder extends RecyclerView.ViewHolder {
    public TextView textViewTitle;

    public ControlMenuViewHolder(View itemView) {
        super(itemView);
        textViewTitle = (TextView) itemView.findViewById(R.id.menu_title);
    }

}

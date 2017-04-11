package com.domker.androidtoy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.domker.androidtoy.R;

import java.util.List;

/**
 * 菜单显示的适配器
 *
 * Created by wanlipeng on 2017/4/11.
 */

public class ControlMenuAdapter extends RecyclerView.Adapter<ControlMenuViewHolder> {
    private Context mContext = null;
    private List<String> mMenuTitleList = null;
    private OnItemClickLitener onItemClickLitener;

    public ControlMenuAdapter(Context context, List<String> titleList) {
        this.mContext = context;
        this.mMenuTitleList = titleList;
    }

    @Override
    public ControlMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.control_menu_item, parent, false);
        ControlMenuViewHolder viewHolder = new ControlMenuViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ControlMenuViewHolder holder, final int position) {
        holder.textViewTitle.setText(mMenuTitleList.get(position));
        if (onItemClickLitener != null) {
            holder.textViewTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    onItemClickLitener.onItemClick(v, pos);
                }
            });
            holder.textViewTitle.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    return onItemClickLitener.onItemLongClick(v, pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mMenuTitleList.size();
    }

    public void setOnItemClickLitener(OnItemClickLitener onItemClickLitener) {
        this.onItemClickLitener = onItemClickLitener;
    }
}

package com.domker.androidtoy.process;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanlipeng on 2015/8/19.
 */
public class ExpandProcessInfoAdapter extends BaseExpandableListAdapter {
    private Context mContext = null;
    private List<String> listGroup = new ArrayList<String>();
    private List<List<String>> listChild = new ArrayList<List<String>>();

    public ExpandProcessInfoAdapter(Context context, List<String> group, List<List<String>> child) {
        this.mContext = context;
        this.listGroup = group;
        this.listChild = child;
    }

    @Override
    public int getGroupCount() {
        return listGroup.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return listChild.get(i).size();
    }

    @Override
    public Object getGroup(int i) {
        return listGroup.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return listChild.get(i).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean isExpanded, View view, ViewGroup viewGroup) {
        TextView title = new TextView(mContext);
        title.setTextSize(20);
        // Center the text vertically
        title.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        // Set the text starting position
        title.setPadding(80, 0, 0, 0);
        title.setText(listGroup.get(i));
        return title;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        TextView child = new TextView(mContext);
        child.setTextSize(12);
        child.setPadding(10, 0, 0, 0);
        child.setText(listChild.get(i).get(i1));
        return child;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}

package com.domker.androidtoy.fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.domker.androidtoy.R;
import com.domker.androidtoy.adapter.ControlMenuAdapter;
import com.domker.androidtoy.adapter.OnItemClickLitener;

import java.util.ArrayList;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class ControlMenuFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private ControlMenuAdapter menuAdapter;
    private List<String> titleList = new ArrayList<>();

    @Override
    protected int initLayoutResId() {
        return R.layout.fragment_control_menu;
    }

    @Override
    protected void initSubViews() {
        titleList.add("正常");
        titleList.add("表格");
        titleList.add("瀑布流");
        titleList.add("添加项");
        mRecyclerView = (RecyclerView) findViewById(R.id.controlMenu);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(menuAdapter = new ControlMenuAdapter(getContext(), titleList));

        menuAdapter.setOnItemClickLitener(new OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == titleList.size() - 1) {
                    // 最后一项，添加项
                    titleList.add(position, "测试项[" + position + "]");
                    menuAdapter.notifyItemInserted(position);
                } else {
                    Toast.makeText(getContext(), position + " click", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public boolean onItemLongClick(View view, int position) {
                titleList.remove(position);
                menuAdapter.notifyItemRangeRemoved(position, 1);
                return true;
            }
        });
    }

    @Override
    protected void onShow() {

    }

}

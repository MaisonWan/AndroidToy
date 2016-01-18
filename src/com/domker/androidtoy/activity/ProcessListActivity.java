/**
 * @file ProcessListActivity.java
 * @Package com.domker.androidtoy.activity
 * @Project PhoneTest
 * Description: TODO
 * @author wanlipeng
 * @version 1.0.0.0
 * @date 2015 ����5:34:41
 * <p/>
 * \if TOSPLATFORM_CONFIDENTIAL_PROPRIETARY
 * ================================
 * \n Copyright (c) 2014
 * wanlipeng. All Rights Reserved.\n \n
 * ==================================
 * ==========================================\n \n Update History\n \n
 * Author (Name[WorkID]) | Modification | Tracked Id | Description\n
 * --------------------- | ------------ | ---------- |
 * ------------------------\n wanlipeng | 2015 ����5:34:41 | <xxxxxxxx>
 * | Initial Created.\n \n \endif
 * <p/>
 * <tt>
 * \n
 * Release History:\n
 * \n
 * Author (Name[WorkID]) | ModifyDate | Version | Description \n
 * --------------------- | ---------- | ------- | -----------------------------\n
 * wanlipeng |2015 ����5:34:41 | 1.0.0.0 | Initial created. \n
 * \n
 * </tt>
 * @since 1.0.0.0
 */
package com.domker.androidtoy.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.domker.androidtoy.R;
import com.domker.androidtoy.process.ProcessListAdapter;
import com.domker.androidtoy.process.ProcessManager;

/**
 * 线程进程列表
 *
 * @ClassName: ProcessListActivity
 * @author wanlipeng
 * @date 2015年8月17日 下午5:34:41
 */
public class ProcessListActivity extends Activity {
    private ListView listView = null;
    private EditText editTextPackageName = null;
    private Button buttonSearch = null;

    private ProcessManager pm = null;
    private ProcessListAdapter adapter = null;
    private List<ProcessManager.ProcessInfo> processInfoList = null;

    private View.OnClickListener buttonSearchListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            String name = editTextPackageName.getText().toString();
            List<ProcessManager.ProcessInfo> list = new ArrayList<ProcessManager.ProcessInfo>();
            for (ProcessManager.ProcessInfo info : processInfoList) {
                if (info.appName.contains(name)) {
                    // 包括的时候为搜索
                    list.add(info);
                }
            }
            if (list.isEmpty()) {
                adapter.setList(processInfoList);
            } else {
                adapter.setList(list);
            }
            adapter.notifyDataSetChanged();
        }
    };

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            ProcessManager.ProcessInfo info = (ProcessManager.ProcessInfo) adapter.getItem(i);
            Intent intent =  new Intent(ProcessListActivity.this, ProcessDetailActivity.class);
            intent.putExtra(ProcessDetailActivity.PACKAGE_NAME, info.packageName);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_process_main);
        editTextPackageName = (EditText) this.findViewById(R.id.editTextPackageName);
        listView = (ListView) this.findViewById(R.id.listViewProcessList);
        listView.setOnItemClickListener(onItemClickListener);
        buttonSearch = (Button) this.findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(buttonSearchListener);

        pm = new ProcessManager(this);
        processInfoList = pm.getAllProcesses();
        adapter = new ProcessListAdapter(this, processInfoList);
        listView.setAdapter(adapter);
    }

}

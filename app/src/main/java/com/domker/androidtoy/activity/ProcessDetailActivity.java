package com.domker.androidtoy.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.ConfigurationInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.domker.androidtoy.R;
import com.domker.androidtoy.process.ExpandProcessInfoAdapter;
import com.domker.androidtoy.process.ProcessManager;

/**
 * 进程详细信息
 */
public class ProcessDetailActivity extends Activity {
    /**
     * 传递显示的包名
     */
    public static final String PACKAGE_NAME = "package_name";

    private ProcessManager pm = null;
    private String packageName = null;
    private ProcessManager.ProcessInfo processInfo = null;

    private ImageView imageViewIcon = null;
    private TextView textViewPackageName = null;
    private TextView textViewVersionName = null;
    private TextView textViewVersionCode = null;
    private TextView textViewAppName = null;

    private ExpandableListView listInfo = null;
    private List<String> listGroup = new ArrayList<String>();
    private List<List<String>> listChild = new ArrayList<List<String>>();
    private ExpandProcessInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_process_detail);
        packageName = getIntent().getStringExtra(PACKAGE_NAME);
        if (TextUtils.isEmpty(packageName)) {
            finish();
        }
        initViews();
        pm = new ProcessManager(this);
        processInfo = pm.getProcessInfo(packageName);
        updateDataIntroduce();
        updateDataManifest();
        updateDataPermissions();
//        updateDataFeatures();
        updateDataActivities();
        adapter = new ExpandProcessInfoAdapter(this, listGroup, listChild);
        listInfo.setAdapter(adapter);
        defaultExpandList();
    }

    private void initViews() {
        imageViewIcon = (ImageView) findViewById(R.id.imageViewIcon);
        textViewAppName = (TextView) findViewById(R.id.textViewAppName);
        textViewPackageName = (TextView) findViewById(R.id.textViewPackageName);
        textViewVersionName = (TextView) findViewById(R.id.textViewVersionName);
        textViewVersionCode = (TextView) findViewById(R.id.textViewVersionCode);
        listInfo = (ExpandableListView) findViewById(R.id.linearLayoutListInfo);
    }

    private void defaultExpandList() {
        for(int i = 0; i < adapter.getGroupCount(); i++){
            listInfo.expandGroup(i);
        }
    }

    /**
     * 显示介绍信息
     */
    private  void updateDataIntroduce() {
        imageViewIcon.setImageDrawable(processInfo.appIcon);
        textViewAppName.setText(processInfo.appName);
        textViewPackageName.setText(processInfo.packageName);
        textViewVersionName.setText(processInfo.versionName);
        textViewVersionCode.setText("VersionCode : " + processInfo.versionCode);
    }

    /**
     * 显示配置文件中的一些信息
     */
    private void updateDataManifest() {
        listGroup.add("Manifests");
        List<String> items = new ArrayList<String>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        items.add("SharedUserId : " + processInfo.sharedUserId);
        items.add("FirstInstallTime : " + simpleDateFormat.format(processInfo.firstInstallTime));
        items.add("LastUpdateTime : " + simpleDateFormat.format(processInfo.lastUpdateTime));
        listChild.add(items);
    }

    /**
     * 显示权限
     */
    private void updateDataPermissions() {
        listGroup.add("Permissions");
        List<String> items = new ArrayList<String>();
        for (String p : processInfo.requestedPermissions) {
            items.add(p);
        }
        listChild.add(items);
    }

    private void updateDataFeatures() {
        listGroup.add("Features");
        List<String> items = new ArrayList<String>();
        for (ConfigurationInfo info : processInfo.configPreferences) {
            items.add(info.toString());
        }
        listChild.add(items);
    }

    private void updateDataActivities() {
        listGroup.add("Activities");
        List<String> items = new ArrayList<String>();
        for (ActivityInfo info : processInfo.activities) {
            items.add(info.name);
        }
        listChild.add(items);
    }
}

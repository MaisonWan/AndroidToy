/**
 * @file JSActivity.java
 * @Package com.tencent.test
 * Description: TODO 
 * 
 * @author maisonwan
 * @since 1.0.0.0
 * @version 1.0.0.0
 * @date 2014-5-27
 * 
 *       \if TOSPLATFORM_CONFIDENTIAL_PROPRIETARY
 *       ================================
 *            \n Copyright (c) 2011
 *       maisonwan. All Rights Reserved.\n \n
 *       ==================================
 *       ==========================================\n \n Update History\n \n
 *       Author (Name[WorkID]) | Modification | Tracked Id | Description\n
 *       --------------------- | ------------ | ---------- |
 *       ------------------------\n maisonwan | 2014-5-27 | <xxxxxxxx>
 *       | Initial Created.\n \n \endif
 * 
 *       <tt>
 * \n
 * Release History:\n
 * \n
 * Author (Name[WorkID]) | ModifyDate | Version | Description \n
 * --------------------- | ---------- | ------- | -----------------------------\n
 * maisonwan |2014-5-27 | 1.0.0.0 | Initial created. \n
 * \n
 * </tt>
 */
package com.domker.androidtoy.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.domker.androidtoy.R;

//=============================================================================
//CLASS DEFINITIONS
//=============================================================================
/**
 * js方式网页显示
 * 
 * @author maisonwan
 * @date 2014-5-27 下午4:47:24
 * 
 */
@SuppressLint("SetJavaScriptEnabled")
public class JSActivity extends Activity {

	private WebView wv = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.js_layout);
		wv = (WebView) this.findViewById(R.id.webview);
		String url = "file:///android_asset/web/index.html";
		readHtmlFormAssets(url);
	}

	private void readHtmlFormAssets(String url){
        WebSettings webSettings = wv.getSettings();
        
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        
        wv.setBackgroundColor(Color.TRANSPARENT);  //  WebView 背景透明效果 
        wv.loadUrl(url);
    }
}

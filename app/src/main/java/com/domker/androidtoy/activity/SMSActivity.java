/**
 * @file SMSActivity.java
 * @Package com.tencent.test
 * Description: TODO 
 * 
 * @author maisonwan
 * @since 1.0.0.0
 * @version 1.0.0.0
 * @date 2014年7月25日
 * 
 *       \if TOSPLATFORM_CONFIDENTIAL_PROPRIETARY
 *       ================================
 *            \n Copyright (c) 2011
 *       maisonwan. All Rights Reserved.\n \n
 *       ==================================
 *       ==========================================\n \n Update History\n \n
 *       Author (Name[WorkID]) | Modification | Tracked Id | Description\n
 *       --------------------- | ------------ | ---------- |
 *       ------------------------\n maisonwan | 2014年7月25日 | <xxxxxxxx>
 *       | Initial Created.\n \n \endif
 * 
 *       <tt>
 * \n
 * Release History:\n
 * \n
 * Author (Name[WorkID]) | ModifyDate | Version | Description \n
 * --------------------- | ---------- | ------- | -----------------------------\n
 * maisonwan |2014年7月25日 | 1.0.0.0 | Initial created. \n
 * \n
 * </tt>
 */
package com.domker.androidtoy.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.telephony.SmsMessage.SubmitPdu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.domker.androidtoy.R;
import com.domker.androidtoy.contact.ContactUtil;
import com.domker.androidtoy.sms.SmsManager;
import com.domker.androidtoy.sms.SmsManager.SMS;

//=============================================================================
//CLASS DEFINITIONS
//=============================================================================
/**
 * 
 * @author maisonwan
 * @date 2014年7月25日 下午2:18:11
 *
 */
public class SMSActivity extends Activity {

	private TextView textViewContent;
	private SmsManager smsManager = null;

	private BroadcastReceiver receiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent == null || intent.getExtras() == null) {
				return;
			}
			Object[] pduses= (Object[])intent.getExtras().get("pdus");  
	        for(Object pdus: pduses){  
	            byte[] pdusmessage = (byte[])pdus;
	            SmsMessage sms = SmsMessage.createFromPdu(pdusmessage);  
	            String mobile = sms.getOriginatingAddress();//发送短信的手机号码  
	            String content = sms.getMessageBody(); //短信内容  
	            Date date = new Date(sms.getTimestampMillis());  
	            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	            String time = format.format(date);  //得到发送时间 
	            textViewContent.setText("收到信息：" + mobile + ", " + content + ", " + time);
	        }
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sms_test_layout);
		
		smsManager = new SmsManager(getApplicationContext());
		textViewContent = (TextView) this.findViewById(R.id.textViewContent);
		Button b = (Button) this.findViewById(R.id.buttonReadSms);
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				List<SMS> list = smsManager.getSmsInPhone();
				textViewContent.setText(list.toString());
			}
		});
		
		Button write = (Button) this.findViewById(R.id.buttonWriteSms);
		write.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SMS sms = new SMS();
				sms.name = "爸爸";
				sms.phoneNumber = "+8615912345678";
				sms.smsbody = "[测试虚假短信]中大奖五百万了~";
				sms.date = new Date();
				sms.type = SmsManager.SMS_TYPE_RECEIVE;
				smsManager.addSms(sms);
				
				Intent intent = new Intent();
				intent.setAction("android.provider.Telephony.SMS_RECEIVED");
				SubmitPdu pdu = SmsMessage.getSubmitPdu(sms.phoneNumber, "+8618610955951", sms.smsbody, false);
				sendBroadcast(intent);
			}
		});
		
		Button readContact = (Button) this.findViewById(R.id.buttonReadContact);
		readContact.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Map<String, List<String>> contactMap = ContactUtil.getContactsNamePhone(getApplicationContext());
				textViewContent.setText(contactMap.toString());
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.provider.Telephony.SMS_RECEIVED");
		registerReceiver(receiver, filter);
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(receiver);
	}
	
}

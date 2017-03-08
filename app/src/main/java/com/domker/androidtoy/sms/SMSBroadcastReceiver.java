package com.domker.androidtoy.sms;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

public class SMSBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Object[] pduses = (Object[]) intent.getExtras().get("pdus");
		for (Object pdus : pduses) {
			byte[] pdusmessage = (byte[]) pdus;
			SmsMessage sms = SmsMessage.createFromPdu(pdusmessage);
			String mobile = sms.getOriginatingAddress();// 发送短信的手机号码
			String content = sms.getMessageBody(); // 短信内容
			Date date = new Date(sms.getTimestampMillis());
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String time = format.format(date); // 得到发送时间

		}
	}

}
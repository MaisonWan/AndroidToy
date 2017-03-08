/**
 * @file SmsManager.java
 * @Package com.tencent.test.sms
 * Description: TODO 
 * 
 * @author maisonwan
 * @since 1.0.0.0
 * @version 1.0.0.0
 * @date 2014��7��25��
 * 
 *       \if TOSPLATFORM_CONFIDENTIAL_PROPRIETARY
 *       ================================
 *            \n Copyright (c) 2011
 *       maisonwan. All Rights Reserved.\n \n
 *       ==================================
 *       ==========================================\n \n Update History\n \n
 *       Author (Name[WorkID]) | Modification | Tracked Id | Description\n
 *       --------------------- | ------------ | ---------- |
 *       ------------------------\n maisonwan | 2014��7��25�� | <xxxxxxxx>
 *       | Initial Created.\n \n \endif
 * 
 *       <tt>
 * \n
 * Release History:\n
 * \n
 * Author (Name[WorkID]) | ModifyDate | Version | Description \n
 * --------------------- | ---------- | ------- | -----------------------------\n
 * maisonwan |2014��7��25�� | 1.0.0.0 | Initial created. \n
 * \n
 * </tt>
 */
package com.domker.androidtoy.sms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.util.Log;

//=============================================================================
//CLASS DEFINITIONS
//=============================================================================
/**
 * sms主要结构：
　　_id：短信序号，如100
　　thread_id：对话的序号，如100，与同一个手机号互发的短信，其序号是相同的
　　address：发件人地址，即手机号，如+8613811810000
　　person：发件人，如果发件人在通讯录中则为具体姓名，陌生人为null
　　date：日期，long型，如1256539465022，可以对日期显示格式进行设置
　　protocol：协议0SMS_RPOTO短信，1MMS_PROTO彩信
　　read：是否阅读0未读，1已读
　　status：短信状态-1接收，0complete,64pending,128failed
　　type：短信类型1是接收到的，2是已发出
　　body：短信具体内容
　　service_center：短信服务中心号码编号，如+8613800755500
 * 
 * @author maisonwan
 * @date 2014年7月25日 下午2:26:57
 *
 */
public class SmsManager {
	public static final String SMS_URI_ALL = "content://sms/";
	public static final String SMS_URI_INBOX = "content://sms/inbox";
	public static final String SMS_URI_SEND = "content://sms/sent";
	public static final String SMS_URI_DRAFT = "content://sms/draft";
	
	public static final int SMS_TYPE_RECEIVE = 1;
	public static final int SMS_TYPE_SEND = 2;
	
	private Context context;
	
	public SmsManager(Context context) {
		this.context = context;
	}

	public List<SMS> getSmsInPhone() {
		List<SMS> smsList = new ArrayList<SMS>();
		try {
			ContentResolver cr = context.getContentResolver();
			String[] projection = new String[] { "_id", "address", "person", "body", "date", "type" };
			Uri uri = Uri.parse(SMS_URI_ALL);
			Cursor cur = cr.query(uri, projection, null, null, "date desc");
			if (cur.moveToFirst()) {
				int idColum = cur.getColumnIndex("_id");
				int phoneNumberColumn = cur.getColumnIndex("address");
				int nameColumn = cur.getColumnIndex("person");
				int smsbodyColumn = cur.getColumnIndex("body");
				int dateColumn = cur.getColumnIndex("date");
				int typeColumn = cur.getColumnIndex("type");
				do {
					SMS sms = new SMS();
					sms.id = cur.getInt(idColum);
					sms.name = cur.getString(nameColumn);
					sms.phoneNumber = cur.getString(phoneNumberColumn);
					sms.smsbody = cur.getString(smsbodyColumn);
					// 转换时间
					Date d = new Date(Long.parseLong(cur.getString(dateColumn)));
					sms.date = d;
					// 消息类型 type：短信类型1是接收到的，2是已发出
					int typeId = cur.getInt(typeColumn);
					sms.type = typeId;
					smsList.add(sms);
				} while (cur.moveToNext());
			}
		} catch (SQLiteException ex) {
			Log.d("SQLiteException in getSmsInPhone", ex.getMessage());
		}
		return smsList;
	}
	
	public void addSms(SMS sms) {
		ContentResolver cr = context.getContentResolver();
		String[] projection = new String[] { "_id", "address", "person", "body", "date", "type" };
		Uri uri = Uri.parse(SMS_URI_ALL);
		ContentValues values = new ContentValues();
		values.put(projection[1], sms.phoneNumber);
		values.put(projection[2], sms.name);
		values.put(projection[3], sms.smsbody);
		values.put(projection[4], sms.date.getTime());
		values.put(projection[5], sms.type);
		cr.insert(uri, values);
	}
	
	public static class SMS {
		public int id;
		public String name;
		public String phoneNumber;
		public String smsbody;
		public Date date;
		public int type;
		
		@Override
		public String toString() {
			return "SMS [id=" + id + ", name=" + name + ", phoneNumber="
					+ phoneNumber + ", smsbody=" + smsbody + ", date=" + date
					+ ", type=" + type + "]";
		}
	}
}

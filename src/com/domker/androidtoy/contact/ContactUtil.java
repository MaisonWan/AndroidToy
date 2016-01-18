package com.domker.androidtoy.contact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.PhoneLookup;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * 通讯录
 */
public class ContactUtil {
	
	/**
	 * 电话的标签
	 */
	public final static String CONTENT_PHONE_ITEM_TYPE = "qpy_phone"; 
	/**
	 * 电子邮件的标签
	 */
	public final static String CONTENT_EMAIL_ITEM_TYPE = "qpy_email"; 
	/**
	 * 地址的标签
	 */
	public final static String CONTENT_ADDRESS_ITEM_TYPE = "qpy_address"; 
	/**
	 * IM号码
	 */
	public final static String CONTENT_IM_ITEM_TYPE = "qpy_im"; 
	
	private static Map<String, ContactDataKinds> hash = new HashMap<String, ContactUtil.ContactDataKinds>(4);
	static {
		// 电话
		ContactDataKinds kind = new ContactDataKinds();
		kind.uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		kind.valueName = ContactsContract.CommonDataKinds.Phone.NUMBER;
		kind.type = ContactsContract.CommonDataKinds.Phone.TYPE;
		kind.customType = ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM;
		kind.label = ContactsContract.CommonDataKinds.Phone.LABEL;
		kind.defaultKeyName = "电话号码";
		hash.put(CONTENT_PHONE_ITEM_TYPE, kind);
		
		// 电子邮件
		kind = new ContactDataKinds();
		kind.uri = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
		kind.valueName = ContactsContract.CommonDataKinds.Email.DATA;
		kind.type = ContactsContract.CommonDataKinds.Email.TYPE;
		kind.customType = ContactsContract.CommonDataKinds.Email.TYPE_CUSTOM;
		kind.label = ContactsContract.CommonDataKinds.Email.LABEL;
		kind.defaultKeyName = "电子邮箱";
		hash.put(CONTENT_EMAIL_ITEM_TYPE, kind);
		
		// 地址
		kind = new ContactDataKinds();
		kind.uri = ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI;
		kind.valueName = ContactsContract.CommonDataKinds.StructuredPostal.DATA;
		kind.type = ContactsContract.CommonDataKinds.StructuredPostal.TYPE;
		kind.customType = ContactsContract.CommonDataKinds.StructuredPostal.TYPE_CUSTOM;
		kind.label = ContactsContract.CommonDataKinds.StructuredPostal.LABEL;
		kind.defaultKeyName = "地址";
		hash.put(CONTENT_ADDRESS_ITEM_TYPE, kind);
		
		// IM
		kind = new ContactDataKinds();
		kind.uri = Data.CONTENT_URI;
		kind.valueName = ContactsContract.CommonDataKinds.StructuredPostal.DATA;
		kind.type = ContactsContract.CommonDataKinds.Im.PROTOCOL;
		kind.customType = ContactsContract.CommonDataKinds.Im.PROTOCOL_CUSTOM;
		kind.label = ContactsContract.CommonDataKinds.Im.LABEL;
		kind.defaultKeyName = "IM";
		hash.put(CONTENT_IM_ITEM_TYPE, kind);
	}
	
	/**
	 * 读取通讯录，把通讯录中人名存到list中
	 * 
	 * @param context
	 * @param msgHandler
	 * @return 通讯录记录数
	 */
	public static Map<String, List<String>> getContactsNamePhone(Context context) {

		//取得电话本中开始一项的光标   
		ContentResolver cr = context.getContentResolver();
		Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);   
		
		HashMap<String, List<String>> hs=new HashMap<String, List<String>>();
		while (cursor.moveToNext()) {
			// 取得联系人名字
			int nameFieldColumnIndex = cursor
					.getColumnIndex(PhoneLookup.DISPLAY_NAME);
			String name = cursor.getString(nameFieldColumnIndex);
			// 取得联系人ID
			String contactId = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts._ID));
			Cursor phoneCursor = cr.query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);

			List<String> ad = hs.get(name);
			if (ad == null) {
				ad = new ArrayList<String>();
				hs.put(name, ad);
			}

			// 取得电话号码(可能存在多个号码)
			while (phoneCursor.moveToNext()) {
				String strPhoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				ad.add(strPhoneNumber);
			}

			phoneCursor.close();
		}
		cursor.close();
		return hs;
	}
	
	/**
	 * 通过用户的ID来获取联系人的信息
	 * 
	 * @param context
	 * @param ids
	 * @return
	 */
	public static List<ContactInfo> getContactsInfo(Context context,
			String[] ids) {
		return getContactsInfo(context, ids, new String[] {
				CONTENT_PHONE_ITEM_TYPE, CONTENT_EMAIL_ITEM_TYPE,
				CONTENT_ADDRESS_ITEM_TYPE, CONTENT_IM_ITEM_TYPE });
	}
	
	/**
	 * 用户ID来获取信息
	 * @param context
	 * @param ids
	 * @param contactDataKinds
	 * @return
	 */
	public static List<ContactInfo> getContactsInfo(Context context,
			String[] ids, String[] contactDataKinds) {
		List<ContactInfo> list = new ArrayList<ContactInfo>();
		if (contactDataKinds == null) {
			return list;
		}
		/****************************** 本地数据库，查询符合条件的所有的联系人 ********************/
		try {
			for (String contactId : ids) {
				Cursor cursor = context.getContentResolver().query(
						ContactsContract.Contacts.CONTENT_URI, null,
						ContactsContract.Contacts._ID + "=?",
						new String[] { contactId }, null);
				if (cursor != null && cursor.getCount() > 0 && cursor.moveToNext()) {
					ContactInfo info = new ContactInfo();
					// 得到用户名
					int nameIndex = cursor
							.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
					info.setContactName(cursor.getString(nameIndex));
					
					ContactDataKinds kind = null;
					// 遍历
					for (String item : contactDataKinds) {
						kind = hash.get(item);
						Cursor itemCursor = null;
						if (CONTENT_IM_ITEM_TYPE.equals(item)) {
							itemCursor = context
									.getContentResolver()
									.query(Data.CONTENT_URI,
											new String[] { Data._ID, kind.type,
													kind.valueName },
											Data.CONTACT_ID
													+ "=? AND "
													+ Data.MIMETYPE
													+ "='"
													+ ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE
													+ "'",
											new String[] { contactId }, null);
						} else {
							itemCursor = context
									.getContentResolver()
									.query(kind.uri,
											null,
											ContactsContract.CommonDataKinds.Phone.CONTACT_ID
													+ " = ?",
											new String[] { contactId }, null);
						}

						if (itemCursor != null && itemCursor.moveToFirst()) {
							do {
								String phoneNumber = itemCursor
										.getString(itemCursor
												.getColumnIndex(kind.valueName));
								int itemType = itemCursor.getInt(itemCursor
										.getColumnIndex(kind.type));

								String key = null;
								if (itemType == kind.customType) {
									key = itemCursor.getString(itemCursor
											.getColumnIndex(kind.label));
								} else {
									key = context
											.getString(getTypeLabelResource(
													item, itemType));
								}

								if (TextUtils.isEmpty(key)) {
									key = kind.defaultKeyName;
								}
								// 去信息中的空格
								phoneNumber = phoneNumber
										.replaceAll("\\s*", "");
								info.add(new ContactInfoItem(key, phoneNumber));
							} while (itemCursor.moveToNext());
						}
						if (itemCursor != null) {
							itemCursor.close();
						}
					}
					list.add(info);
				}
				if (cursor != null) {
					cursor.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 通过名字读取联系人姓名
	 * 
	 * @param context
	 * @param name 
	 * @return 通讯录内容 contactArray
	 * contactArray -->[{contactObject}] <br/>
	 * contactObject -->{@link qpy_phone}:{phoneobj},
	 * 					qpy_email:{emailobj},
	 * 					qpy_address:{addressobj}}<br/>
	 * phoneobj,emailobj,addressobj-->{"key":"value"}或者{"key",["value1","value2"]}<br/>
	 * 
	 * 
	 * 例如： [{"qpy_address":{"Home":"hai\nbeijing"},<br/>
	 * "qpy_phone":{"Mobile":"222-2222","测试":"666-666-66","Home":["1111111","333-333-333333"]},<br/>
	 * "qpy_email":{"Work":"bbbbbb","Home":"aaaaaaa"}}]
	 */
	public static JSONArray getContactsInfo(Context context, String name) {
		return getContactsInfo(context, name, new String[] {
				CONTENT_PHONE_ITEM_TYPE, CONTENT_EMAIL_ITEM_TYPE,
				CONTENT_ADDRESS_ITEM_TYPE, CONTENT_IM_ITEM_TYPE });
	}
	/**
	 * 通过名字读取联系人姓名
	 * 
	 * @param context
	 * @param name 
	 * @return 通讯录内容 contactArray
	 * contactArray -->[{contactObject}] <br/>
	 * contactObject -->{@link qpy_phone}:{phoneobj},
	 * 					qpy_email:{emailobj},
	 * 					qpy_address:{addressobj}}<br/>
	 * phoneobj,emailobj,addressobj-->{"key":"value"}或者{"key",["value1","value2"]}<br/>
	 * 
	 * 
	 * 例如： [{"qpy_address":{"Home":"hai\nbeijing"},<br/>
	 * "qpy_phone":{"Mobile":"222-2222","测试":"666-666-66","Home":["1111111","333-333-333333"]},<br/>
	 * "qpy_email":{"Work":"bbbbbb","Home":"aaaaaaa"}}]
	 */
	public static JSONArray getContactsInfo(Context context, String name, String[] contactDataKinds) {

        JSONArray jsonArray = new JSONArray();
        if (contactDataKinds == null) {
        	return jsonArray;
        }
        List<String> cacheList = new ArrayList<String>();
        
		/****************************** 本地数据库，查询符合条件的所有的联系人  ********************/
		Cursor cursor = context.getContentResolver().query(
				ContactsContract.Contacts.CONTENT_URI, null,
				ContactsContract.Contacts.DISPLAY_NAME + "=?",
				new String[] { name }, null);
        
		try {
			if (cursor != null && cursor.getCount() > 0) {
				while (cursor.moveToNext()) {
					JSONObject contactJson = new JSONObject();
					jsonArray.put(contactJson);
					int idColumn = cursor
							.getColumnIndex(ContactsContract.Contacts._ID);
					// 得到联系人ID
					String contactId = cursor.getString(idColumn);
					
					ContactDataKinds kind = null;
					// 遍历
					for (String item : contactDataKinds) {
						kind = hash.get(item);
						Cursor itemCursor = null;
						if (CONTENT_IM_ITEM_TYPE.equals(item)) {
							itemCursor = context
									.getContentResolver()
									.query(Data.CONTENT_URI,
											new String[] { Data._ID, kind.type,
													kind.valueName },
											Data.CONTACT_ID
													+ "=? AND "
													+ Data.MIMETYPE
													+ "='"
													+ ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE
													+ "'",
											new String[] { contactId }, null);
						} else {
							itemCursor = context
									.getContentResolver()
									.query(kind.uri,
											null,
											ContactsContract.CommonDataKinds.Phone.CONTACT_ID
													+ " = ?",
											new String[] { contactId }, null);
						}
						 
						
						if (itemCursor != null && itemCursor.moveToFirst()) {
							JSONObject itemJson = new JSONObject();

							contactJson.put(item, itemJson);
							do{
								String phoneNumber = itemCursor.getString(itemCursor.getColumnIndex(kind.valueName));  
								int itemType = itemCursor.getInt(itemCursor.getColumnIndex(kind.type));   
								
								String key = null;
								if (itemType == kind.customType) {
									key = itemCursor.getString(itemCursor
											.getColumnIndex(kind.label));
								} else {
									key = context.getString(getTypeLabelResource(item, itemType));
								}
								
								if (TextUtils.isEmpty(key)) {
									key = kind.defaultKeyName;
								}
								// 去信息中的空格
								phoneNumber = phoneNumber.replaceAll("\\s*", "");
								//有重复key存在时，添加到array中
								if (itemJson.has(key)) {
									JSONArray array = new JSONArray();
									array.put(itemJson.get(key));
									array.put(phoneNumber);

									itemJson.put(key, array);
								} else {
									itemJson.put(key, phoneNumber);
								}
								cacheList.add(phoneNumber);
								
							}while (itemCursor.moveToNext());
						}
						if (itemCursor != null) {
							itemCursor.close();
						}
					}
				}
			}
		} catch (Exception e) {
		}
		return jsonArray;		
        /***************************************** sim卡联系人***************************/
        
//        try
//        {
//			if ( getSimState(context) == TelephonyManager.SIM_STATE_READY )
//			{
//		        Cursor simCursor = context.getContentResolver().query(Uri.parse("content://icc/adn"), 
//		        		null, 
//		        		ContactsContract.Contacts.DISPLAY_NAME + "=?",   
//		                new String[] { name }, 
//		                null);
//		        
//		        if(simCursor != null && simCursor.moveToFirst())
//		        {
//		        	do{
//		        		JSONObject contactJson = new JSONObject();
//		        		JSONObject phoneJson = new JSONObject();
//		        		
//		        		int numberFieldColumnIndex = simCursor.getColumnIndex(People.NUMBER);
//		        		String number = simCursor.getString(numberFieldColumnIndex);
//		        		if ( !cacheList.contains(number) )
//		        		{
//		        			int type = ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE;
//		        			String key = context.getString(ContactsContract.CommonDataKinds.Phone.getTypeLabelResource(type));
//	        				
//		        			phoneJson.put(key, number);
//		        			
//		        		}
//		        		if ( phoneJson.length() > 0 )
//		        		{
//		        			contactJson.put(CONTENT_PHONE_ITEM_TYPE, phoneJson);
//		        		}
//		        		if( contactJson.length() > 0 )
//			        	{
//			        		jsonArray.put(contactJson);
//			        	}
//		        	}while (simCursor.moveToNext());
//		        	
//		        	if ( simCursor != null )
//		        	{
//		        		simCursor.close();
//		        	}
//		        }
//			}
//			//清除缓存
//			cacheList.clear();
//        }
//        catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}  
	private static int getTypeLabelResource(String itemType, int type) {
		if (CONTENT_PHONE_ITEM_TYPE.equals(itemType)) {
			return ContactsContract.CommonDataKinds.Phone.getTypeLabelResource(type);
		} else if (CONTENT_EMAIL_ITEM_TYPE.equals(itemType)) {
			return ContactsContract.CommonDataKinds.Email.getTypeLabelResource(type);
		} else if (CONTENT_ADDRESS_ITEM_TYPE.equals(itemType)) {
			return ContactsContract.CommonDataKinds.StructuredPostal.getTypeLabelResource(type);
		} else if (CONTENT_IM_ITEM_TYPE.equals(itemType)) {
			return ContactsContract.CommonDataKinds.Im.getProtocolLabelResource(type);
		}
		return ContactsContract.CommonDataKinds.Phone.getTypeLabelResource(type);
	}
	private static int getSimState(Context context){
	    return ((TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE)).getSimState();
	}
	
	/**
	 * 读取联系人和ID的映射关系
	 * @param context
	 * @return
	 */
	public static List<ContactInfoItem> getContactsNameMap(Context context) {
		// 取得电话本中开始一项的光标
		ContentResolver cr = context.getContentResolver();
		Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
				null, null, null);

		List<ContactInfoItem> nameList = new ArrayList<ContactInfoItem>();

		if (cursor != null) {
			while (cursor.moveToNext()) {
				// 取得联系人名字
				int nameFieldColumnIndex = cursor
						.getColumnIndex(PhoneLookup.DISPLAY_NAME);
				String name = cursor.getString(nameFieldColumnIndex);
				// 取得联系人ID
				int idFieldIndex = cursor
						.getColumnIndex(ContactsContract.Contacts._ID);
				String contactId = cursor.getString(idFieldIndex);

				if (name != null && name.trim().length() > 0
						&& !nameList.contains(name)) {
					nameList.add(new ContactInfoItem(name, contactId));
				}
			}
			cursor.close();
		}
		return nameList;
	}
	
	public static List<String> getContactsName(Context context) {
		//取得电话本中开始一项的光标   
		ContentResolver cr = context.getContentResolver();
		Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);   
		
		List<String> nameList=new ArrayList<String>();
		
		if (cursor != null) {
			while (cursor.moveToNext()) {
				// 取得联系人名字
				int nameFieldColumnIndex = cursor.getColumnIndex(PhoneLookup.DISPLAY_NAME);
				String name = cursor.getString(nameFieldColumnIndex);
				// 取得联系人ID
				if (name != null && name.trim().length() > 0
						&& !nameList.contains(name)) {
					nameList.add(name);
				}
			}
			cursor.close();
		}
		
		// sim卡联系人
		if (getSimState(context) == TelephonyManager.SIM_STATE_READY) {
	        Cursor mCursor = context.getContentResolver().query(Uri.parse("content://icc/adn"), null, null, null, null);
			if (mCursor != null) {
				while (mCursor.moveToNext()) {
					int nameFieldColumnIndex = mCursor.getColumnIndex("name");
					String name = mCursor.getString(nameFieldColumnIndex);
					if (name != null && name.trim().length() > 0
							&& !nameList.contains(name)) {
						nameList.add(name);
					}
				}
				mCursor.close();
			}
		}
		return nameList;
	}
	
	/**
	 * 一种类型的数据，对应的
	 * @author maisonwan
	 *
	 */
	public static class ContactDataKinds {
		public Uri uri = null;
		public String valueName = null;
		public String type = null;
		public int customType;
		public String label;
		public String defaultKeyName = null;
	}
}
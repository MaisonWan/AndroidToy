/**
 * 
 */
package com.domker.androidtoy.contact;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个联系人的信息的实体类
 * 
 * @author maisonwan
 * 
 */
public class ContactInfo {
	/**
	 * 联系人名称
	 */
	private String contactName = null;
	/**
	 * 联系人基本信息的key-value对应值
	 */
	private List<ContactInfoItem> contactList = null;

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/**
	 * 通过下标得到键值对
	 * 
	 * @param index
	 * @return
	 */
	public ContactInfoItem get(int index) {
		return contactList.get(index);
	}

	/**
	 * 添加一个属性对
	 * 
	 * @param contactInfoItem
	 */
	public void add(ContactInfoItem contactInfoItem) {
		this.contactList.add(contactInfoItem);
	}

	/**
	 * 得到尺寸
	 * 
	 * @return
	 */
	public int size() {
		return contactList.size();
	}

	public ContactInfo() {
		super();
		contactList = new ArrayList<ContactInfoItem>();
	}
}

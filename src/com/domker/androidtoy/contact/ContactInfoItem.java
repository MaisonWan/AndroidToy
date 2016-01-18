/**
 * 
 */
package com.domker.androidtoy.contact;

/**
 * 联系人一项信息，基本就是一个key和value的键值对
 * 
 * @author maisonwan
 * 
 */
public class ContactInfoItem {
	private String infoKey = null;
	private String infoValue = null;

	public String getInfoKey() {
		return infoKey;
	}

	public void setInfoKey(String infoKey) {
		this.infoKey = infoKey;
	}

	public String getInfoValue() {
		return infoValue;
	}

	public void setInfoValue(String infoValue) {
		this.infoValue = infoValue;
	}

	public ContactInfoItem(String infoKey, String infoValue) {
		this.infoKey = infoKey;
		this.infoValue = infoValue;
	}

	public ContactInfoItem() {
	}

	@Override
	public String toString() {
		return "[infoKey=" + infoKey + ", infoValue="
				+ infoValue + "]";
	}
}

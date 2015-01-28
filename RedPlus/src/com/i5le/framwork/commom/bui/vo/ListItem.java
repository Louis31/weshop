package com.i5le.framwork.commom.bui.vo;

/**
 * 列表项。
 * 
 * 比如：Select、List
 * @author chenxi
 *
 */
public class ListItem {

	private String text;
	private String value;
	
	
	public ListItem(String text, String value) {
		super();
		this.text = text;
		this.value = value;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}

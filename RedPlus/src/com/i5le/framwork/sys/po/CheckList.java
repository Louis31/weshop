package com.i5le.framwork.sys.po;

/**
 * 选择框列表
 * @author chenxi
 *
 */
public class CheckList {

	private String text;
	private String value;
	
	
	public CheckList(String text, String value) {
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

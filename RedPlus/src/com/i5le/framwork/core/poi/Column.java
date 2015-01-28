package com.i5le.framwork.core.poi;

import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;


public class Column {
	private String title;
	private String field;  	//数据库字段名
	private int type; 		// 单元格类型
	private int width;		//宽度为字符个数
	
	private Map<Object,Object> renderer;
	
	private CellStyle style;
	
	public Column(String title, String field, int type, int width) {
		this.title = title;
		this.field = field;
		this.type = type;
		this.width = width;
	}
	
	public Column(String title, String field, int type, int width,CellStyle style) {
		this.title = title;
		this.field = field;
		this.type = type;
		this.width = width;
		this.style = style;
	}
	
	public Column() {
		super();
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public CellStyle getStyle() {
		return style;
	}
	public void setStyle(CellStyle style) {
		this.style = style;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}

	public <T> T renderer(Object key){
		if(renderer==null||!renderer.containsKey(key)) return (T)key;
		return (T)renderer.get(key);
	}

	public void setRenderer(Map<Object, Object> renderer) {
		this.renderer = renderer;
	}
}

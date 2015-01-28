package com.i5le.framwork.sys.po;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.i5le.framwork.sys.entity.Resource;


public class Menu {
	private String id;//一二三级菜单都要
	private String icon;//一级菜单
	private String homePage;//一级菜单
	private List<Menu> menu;//一级菜单
	private List<Menu> items;//二级菜单
	private String text;//都要
	private Boolean collapsed;//二级菜单
	private String href;//三级菜单
	@JsonIgnore
	private int order;//排序
	@JsonIgnore
	private Long parentId;

	public static Menu build(int level, Resource resource, String ctx) {
		Menu menu = new Menu();
		menu.setId(resource.getCode());
		menu.setText(resource.getName());
		if(resource.getOrderId()!=null) menu.setOrder(resource.getOrderId());
		
		Resource parent = resource.getParent();
		//menu.setHomePage(ctx+"/"+resource.getUrl());
		if(parent!=null){
			menu.setParentId(parent.getId());
		}
		if (level == 1) {
			menu.setIcon(resource.getIco());
			menu.setMenu(new ArrayList<Menu>());
			menu.setHomePage(resource.getUrl());
		} else if (level == 2) {
			menu.setCollapsed(false);
			menu.setHomePage(resource.getUrl());
			menu.setItems(new ArrayList<Menu>());
		} else if(level ==3){
			menu.setHref(ctx+resource.getUrl());
		}
		return menu;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<Menu> getItems() {
		return items;
	}

	public void setItems(List<Menu> items) {
		this.items = items;
	}

	public String getHomePage() {
		return homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	public List<Menu> getMenu() {
		return menu;
	}

	public void setMenu(List<Menu> menu) {
		this.menu = menu;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Boolean getCollapsed() {
		return collapsed;
	}

	public void setCollapsed(Boolean collapsed) {
		this.collapsed = collapsed;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((href == null) ? 0 : href.hashCode());
		result = prime * result + ((icon == null) ? 0 : icon.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((menu == null) ? 0 : menu.hashCode());
		result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Menu other = (Menu) obj;
		if (href == null) {
			if (other.href != null)
				return false;
		} else if (!href.equals(other.href))
			return false;
		if (icon == null) {
			if (other.icon != null)
				return false;
		} else if (!icon.equals(other.icon))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (menu == null) {
			if (other.menu != null)
				return false;
		} else if (!menu.equals(other.menu))
			return false;
		if (parentId == null) {
			if (other.parentId != null)
				return false;
		} else if (!parentId.equals(other.parentId))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

}

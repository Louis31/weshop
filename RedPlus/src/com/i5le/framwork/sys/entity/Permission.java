package com.i5le.framwork.sys.entity;

// Generated 2014-6-19 17:10:12 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.i5le.framwork.core.entity.IdEntity;

/**
 * Permission generated by hbm2java
 */
@Entity
@Table(name = "sys_permission")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Permission  extends IdEntity {


	private String name;
	private String permission;
	private String memo;

	public Permission() {
	}

	public Permission(Long id) {
		this.id = id;
	}

	public Permission(Long id, String name, String permission, String memo) {
		this.id = id;
		this.name = name;
		this.permission = permission;
		this.memo = memo;
	}



	@Column(name = "name", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "permission", length = 200)
	public String getPermission() {
		return this.permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	@Column(name = "memo", length = 200)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}

package com.i5le.framwork.sys.entity;

// Generated 2014-6-19 17:10:12 by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.i5le.framwork.core.entity.IdEntity;

/**
 * Role generated by hbm2java
 */
@Entity
@Table(name = "sys_role")
public class Role extends IdEntity {


	private String name;
	private String code;
	private String memo;
	private Date createTime;
	@JsonIgnore
	private List<User> users = new ArrayList<User>(0);
	
	private List<RoleAuth> roleAuths = new ArrayList<RoleAuth>(0);

	public Role() {
	}

	public Role(Long id) {
		this.id = id;
	}

	public Role(Long id, String name, String code, String memo, Date createTime, List<User> users, List<RoleAuth> roleAuths) {
		this.id = id;
		this.name = name;
		this.code = code;
		this.memo = memo;
		this.createTime = createTime;
		this.users = users;
		this.roleAuths = roleAuths;
	}



	@Column(name = "name", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "code", length = 100)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "memo", length = 200)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade=CascadeType.REMOVE)
	@JoinTable(name = "sys_user_role", joinColumns = { @JoinColumn(name = "role_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "user_id", nullable = false, updatable = false) })
	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "role", cascade= {CascadeType.ALL})
	public List<RoleAuth> getRoleAuths() {
		return this.roleAuths;
	}

	public void setRoleAuths(List<RoleAuth> roleAuths) {
		this.roleAuths = roleAuths;
	}

/*	@Transient
	@JsonIgnore
	public List<String> getPermissionList() {
		if (this.roleAuths == null)
			return null;
		Iterator<RoleAuth> it = this.roleAuths.iterator();
		List<String> permissions = Lists.newArrayList(); // 有序的关联对象集合
		while (it.hasNext()) {
			RoleAuth ra = it.next();
			if(ra.getPermissions()==null) continue;
			String strs[] = StringUtils.split(ra.getPermissions(), ",");
			for (String str : strs) {
				permissions.add(ra.getResource().getCode() + ":" + str);
			}
		}
		return permissions;
	}*/
}

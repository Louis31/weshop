package com.i5le.redplus.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.i5le.framwork.core.entity.IdEntity;

@Entity
@Table(name = "collect")
public class Collect extends IdEntity implements Serializable {

	private static final long serialVersionUID = 6922545069352168814L;
	
	@Column(name = "host_id")
	private Long host_id;
	
	@Column(name = "user_id")
	private Long user_id;

	public Long getHost_id() {
		return host_id;
	}

	public void setHost_id(Long host_id) {
		this.host_id = host_id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

}

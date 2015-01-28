package com.i5le.redplus.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.i5le.framwork.core.entity.IdEntity;


@Entity
@Table(name="call_phone")

public class CallPhone extends IdEntity {

	private UserInfo userInfo;

	private String call;

	private String remark;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	@Column(name = "call")
	public String getCall() {
		return call;
	}

	public void setCall(String call) {
		this.call = call;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}

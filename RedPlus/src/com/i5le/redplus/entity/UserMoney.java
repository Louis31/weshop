package com.i5le.redplus.entity;

// Generated 2014-10-15 17:16:53 by Hibernate Tools 3.2.2.GA

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.i5le.framwork.core.entity.IdEntity;

/**
 * UserMoney generated by hbm2java
 */
@Entity
@Table(name = "user_money")
public class UserMoney implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1741826121796110034L;


	private Long userId;
	private Long money;
	private String remark;
	private Long gold;
	
	@Column(name = "gold")
	public Long getGold() {
		return gold;
	}

	public void setGold(Long gold) {
		this.gold = gold;
	}

	private String resv2;
	private String resv3;
	private String resv1;

	@Column(name = "resv1")
	public String getResv1() {
		return resv1;
	}

	public void setResv1(String resv1) {
		this.resv1 = resv1;
	}

	public UserMoney() {
	}

	@Id
	@Column(name = "user_id", nullable = false)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "money", nullable = false)
	public Long getMoney() {
		return this.money;
	}

	public void setMoney(Long money) {
		this.money = money;
	}

	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	

	@Column(name = "resv2")
	public String getResv2() {
		return this.resv2;
	}

	public void setResv2(String resv2) {
		this.resv2 = resv2;
	}

	@Column(name = "resv3")
	public String getResv3() {
		return this.resv3;
	}

	public void setResv3(String resv3) {
		this.resv3 = resv3;
	}

}
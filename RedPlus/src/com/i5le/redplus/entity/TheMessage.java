package com.i5le.redplus.entity;

// Generated 2014-10-15 17:16:53 by Hibernate Tools 3.2.2.GA

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.core.annotation.Order;

import com.i5le.framwork.core.entity.IdEntity;

/**
 * TheMessage generated by hbm2java
 */
@Entity
@Table(name = "the_message")

public class TheMessage extends IdEntity implements java.io.Serializable {

	public transient static final int TRANS_INFO_TYPE = 1;

	public transient static final int USER_WORD_TYPE = 0;
	public transient static final int SYSTEM_WORD_TYPE = 2;
	
	public transient static final String YES_RED = "1";
	public transient static final String NO_RED = "0";
	/**
	 * 
	 */
	private static final long serialVersionUID = 7272038900712018446L;
	private UserInfo fromUser;
	private UserInfo toUser;
	private Date createtime;
	private String info;
	private int infoType;
	private String resv1;
	private String resv2;
	private String resv3;

	public TheMessage() {

	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "to_user_id")
	public UserInfo getToUser() {

		return toUser;
	}

	public void setToUser(UserInfo toUser) {
		this.toUser = toUser;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "form_user_id")
	public UserInfo getFromUser() {
		return fromUser;
	}

	public void setFromUser(UserInfo fromUser) {
		this.fromUser = fromUser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createtime")
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Column(name = "info", length = 200)
	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Column(name = "info_type")
	public int getInfoType() {
		return this.infoType;
	}

	public void setInfoType(int infoType) {
		this.infoType = infoType;
	}

	@Column(name = "resv1")
	public String getResv1() {
		return this.resv1;
	}

	public void setResv1(String resv1) {
		this.resv1 = resv1;
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

	private Long transId;

	@Column(name = "trans_id")
	public Long getTransId() {
		return transId;
	}

	public void setTransId(Long transId) {
		this.transId = transId;
	}

}

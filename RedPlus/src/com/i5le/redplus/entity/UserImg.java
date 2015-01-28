package com.i5le.redplus.entity;

import java.io.Serializable;
import java.util.Properties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.i5le.framwork.core.entity.IdEntity;

@Entity
@Table(name = "user_img")
public class UserImg extends IdEntity implements Serializable {

	/**
	 * 
	 */
	

	private static final long serialVersionUID = 5070741047815896764L;

	private Long userId;

	private String imgSrc;

	private String remark;

	private Integer type;

	@Column(name = "user_id")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "img_src")
	public String getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}

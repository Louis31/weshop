package com.i5le.redplus.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.i5le.framwork.core.entity.IdEntity;

@Entity
@Table(name = "areacode")
public class AreaCode extends IdEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5283906348174331056L;

	private String areacode;

	private String areaname;

	private AreaCode parentArea;

	

	private String version;

	@Column(name = "version")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	private String remark;

	@Column(name = "areacode")
	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	@Column(name = "areaname")
	public String getAreaname() {
		return areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentarea")
	public AreaCode getParentArea() {
		return parentArea;
	}

	public void setParentArea(AreaCode parentArea) {
		this.parentArea = parentArea;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	

}

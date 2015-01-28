package com.i5le.redplus.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.i5le.framwork.core.entity.IdEntity;

@Entity
@Table(name = "type_map")
public class Type_map extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = -3583165240941655074L;
	
	private Integer type;
	
	private String typeName;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	
	public Type_map(){
		
	}

}

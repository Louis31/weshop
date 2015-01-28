/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.i5le.framwork.core.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@MappedSuperclass
@JsonIgnoreProperties( value={"hibernateLazyInitializer","handler"})//序列化成json是不序列化这两个属性否则会报错
public abstract class IdEntity {

	protected Long id;
	
	protected Long uid;
	
	

	@Transient
	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "table")
	@GenericGenerator(name = "table", strategy = "org.hibernate.id.MultipleHiLoPerTableGenerator", parameters = {
			//@Parameter(name = "table", value = "hibernate_sequences"),生成序列号用的表
			//@Parameter(name = "primary_key_column", value = "sequence_name"),sequence名字保存的列名，一般保存的为 表名
			//@Parameter(name = "value_column", value = "sequence_next_hi_value"),下一个增值
			@Parameter(name = "max_lo", value = "5") })//增长级别为5，可根据并发级别适当调整
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}

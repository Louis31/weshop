package com.i5le.framwork.sys.repository;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.QueryHints;

import com.i5le.framwork.core.repository.BaseJapRepository;
import com.i5le.framwork.sys.entity.Permission;

public interface PermissionRepository extends BaseJapRepository<Permission, Long>{

	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") }) 
	public List<Permission> findAll(Iterable<Long> ids);
	
}

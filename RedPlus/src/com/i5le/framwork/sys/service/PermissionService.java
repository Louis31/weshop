package com.i5le.framwork.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.i5le.framwork.core.repository.BaseJapRepository;
import com.i5le.framwork.core.service.BaseService;
import com.i5le.framwork.sys.entity.Permission;
import com.i5le.framwork.sys.repository.PermissionRepository;


@Component
@Transactional
public class PermissionService extends BaseService<Permission, Long>{
	
	@Autowired
	private PermissionRepository permissionRepository;

	@Override
	public BaseJapRepository<Permission, Long> getRepository() {
		return this.permissionRepository;
	}

}

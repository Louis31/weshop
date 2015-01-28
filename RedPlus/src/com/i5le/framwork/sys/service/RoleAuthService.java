package com.i5le.framwork.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.i5le.framwork.core.repository.BaseJapRepository;
import com.i5le.framwork.core.service.BaseService;
import com.i5le.framwork.sys.entity.RoleAuth;
import com.i5le.framwork.sys.repository.RoleAuthRepository;


@Component
@Transactional
public class RoleAuthService extends BaseService<RoleAuth, Long>{
	
	@Autowired
	private RoleAuthRepository roleAuthRepository;

	@Override
	public BaseJapRepository<RoleAuth, Long> getRepository() {
		return this.roleAuthRepository;
	}

}

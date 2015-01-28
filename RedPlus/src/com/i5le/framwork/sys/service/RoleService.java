package com.i5le.framwork.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.i5le.framwork.core.repository.BaseJapRepository;
import com.i5le.framwork.core.service.BaseService;
import com.i5le.framwork.sys.entity.Role;
import com.i5le.framwork.sys.entity.RoleAuth;
import com.i5le.framwork.sys.repository.RoleAuthRepository;
import com.i5le.framwork.sys.repository.RoleRepository;


@Component
@Transactional
public class RoleService extends BaseService<Role, Long>{
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private RoleAuthRepository roleAuthRepository;
	
	/**
	 * 给角色授权
	 * @param role 从前台传进来的只有Role的ID和roleAuth，没有Role基本信息
	 * @return
	 */
	public boolean authRole(Role role){
		Role dbRole = this.roleRepository.findOne(role.getId());
		List<RoleAuth> ras = dbRole.getRoleAuths();
		this.roleAuthRepository.deleteInBatch(ras);
		dbRole.setRoleAuths(role.getRoleAuths());
		this.roleRepository.save(dbRole);
		return true;
	}

	@Override
	public BaseJapRepository<Role, Long> getRepository() {
		return this.roleRepository;
	}

}

package com.i5le.redplus.repository;


import com.i5le.framwork.core.repository.BaseJapRepository;

import com.i5le.redplus.entity.UserInfo;

public interface UserInfoRepository extends
		BaseJapRepository<UserInfo, Long> {

	
	public UserInfo findByLoginname(String loginname);

}

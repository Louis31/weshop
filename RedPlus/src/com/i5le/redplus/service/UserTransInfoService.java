package com.i5le.redplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.i5le.framwork.core.repository.BaseJapRepository;
import com.i5le.framwork.core.service.BaseService;
import com.i5le.redplus.entity.UserInfo;
import com.i5le.redplus.entity.UserMoney;
import com.i5le.redplus.entity.UserTrans;
import com.i5le.redplus.entity.UserTransInfo;
import com.i5le.redplus.repository.UserInfoRepository;
import com.i5le.redplus.repository.UserMoneyRepository;
import com.i5le.redplus.repository.UserTransInfoRepository;
import com.i5le.redplus.repository.UserTransRepository;

@Component
@Transactional
public class UserTransInfoService extends BaseService<UserTransInfo, Long> {

	@Autowired
	private UserTransInfoRepository userTransInfoRepository;


	@Override
	public BaseJapRepository<UserTransInfo, Long> getRepository() {
		return this.userTransInfoRepository;
	}

}

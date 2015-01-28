package com.i5le.redplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.i5le.framwork.core.repository.BaseJapRepository;
import com.i5le.framwork.core.service.BaseService;
import com.i5le.redplus.entity.UserMoney;
import com.i5le.redplus.repository.UserMoneyRepository;

@Component
@Transactional
public class UserMoneyService extends BaseService<UserMoney, Long> {

	@Autowired
	private UserMoneyRepository userMoneyRepository;


	@Override
	public BaseJapRepository<UserMoney, Long> getRepository() {
		return this.userMoneyRepository;
	}


	public UserMoney findByUserId(Long fromUserId) {
		// TODO Auto-generated method stub
		return this.userMoneyRepository.findByUserId(fromUserId);
	}

}

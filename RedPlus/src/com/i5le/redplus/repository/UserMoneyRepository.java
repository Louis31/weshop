package com.i5le.redplus.repository;


import com.i5le.framwork.core.repository.BaseJapRepository;
import com.i5le.redplus.entity.UserMoney;

public interface UserMoneyRepository extends
		BaseJapRepository<UserMoney, Long> {

	UserMoney findByUserId(Long fromUserId);


}

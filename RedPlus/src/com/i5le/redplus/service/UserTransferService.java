package com.i5le.redplus.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.i5le.framwork.core.repository.BaseJapRepository;
import com.i5le.framwork.core.service.BaseService;
import com.i5le.redplus.entity.TheMessage;
import com.i5le.redplus.entity.UserInfo;
import com.i5le.redplus.entity.UserMoney;
import com.i5le.redplus.entity.UserTrans;
import com.i5le.redplus.entity.UserTransInfo;
import com.i5le.redplus.entity.UserTransfer;
import com.i5le.redplus.repository.TheMessageRepository;
import com.i5le.redplus.repository.UserInfoRepository;
import com.i5le.redplus.repository.UserMoneyRepository;
import com.i5le.redplus.repository.UserTransInfoRepository;
import com.i5le.redplus.repository.UserTransRepository;
import com.i5le.redplus.repository.UserTransferRepository;

@Component
@Transactional
public class UserTransferService extends BaseService<UserTransfer, Long> {

	@Autowired
	private TheMessageRepository theMessageRepository;
	@Autowired
	private UserTransferRepository userTransferRepository;

	@Override
	public BaseJapRepository<UserTransfer, Long> getRepository() {
		return this.userTransferRepository;
	}

}

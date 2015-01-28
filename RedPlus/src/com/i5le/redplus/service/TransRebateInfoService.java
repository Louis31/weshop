package com.i5le.redplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.i5le.framwork.core.repository.BaseJapRepository;
import com.i5le.framwork.core.service.BaseService;
import com.i5le.redplus.entity.TheMessage;
import com.i5le.redplus.entity.TransRebateInfo;
import com.i5le.redplus.repository.TheMessageRepository;
import com.i5le.redplus.repository.TransRebateInfoRepository;

@Component
@Transactional
public class TransRebateInfoService extends BaseService<TransRebateInfo, Long> {

	@Autowired
	private TransRebateInfoRepository transRebateInfoRepository;

	@Override
	public BaseJapRepository<TransRebateInfo, Long> getRepository() {
		return this.transRebateInfoRepository;
	}

}

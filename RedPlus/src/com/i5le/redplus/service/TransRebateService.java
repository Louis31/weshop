package com.i5le.redplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.i5le.framwork.core.repository.BaseJapRepository;
import com.i5le.framwork.core.service.BaseService;
import com.i5le.redplus.entity.TheMessage;
import com.i5le.redplus.entity.TransRebate;
import com.i5le.redplus.entity.TransRebateInfo;
import com.i5le.redplus.repository.TheMessageRepository;
import com.i5le.redplus.repository.TransRebateInfoRepository;
import com.i5le.redplus.repository.TransRebateRepository;

@Component
@Transactional
public class TransRebateService extends BaseService<TransRebate, Long> {

	@Autowired
	private TransRebateRepository transRebateRepository;

	@Override
	public BaseJapRepository<TransRebate, Long> getRepository() {
		return this.transRebateRepository;
	}

}

package com.i5le.redplus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.i5le.framwork.core.repository.BaseJapRepository;
import com.i5le.framwork.core.service.BaseService;
import com.i5le.redplus.entity.AreaCode;
import com.i5le.redplus.entity.CallPhone;
import com.i5le.redplus.repository.AreaCodeRepository;
import com.i5le.redplus.repository.CallPhoneRepository;

@Component
@Transactional
public class CallPhoneService extends BaseService<CallPhone, Long> {

	@Autowired
	private CallPhoneRepository callPhoneRepository;

	@Override
	public BaseJapRepository<CallPhone, Long> getRepository() {
		return this.callPhoneRepository;
	}

}

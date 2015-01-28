package com.i5le.redplus.repository;


import java.util.List;

import com.i5le.framwork.core.repository.BaseJapRepository;
import com.i5le.redplus.entity.CallPhone;
import com.i5le.redplus.entity.UserInfo;

public interface CallPhoneRepository extends
		BaseJapRepository<CallPhone, Long> {

	
	public List<CallPhone> findByUserInfoId(Long id);

}

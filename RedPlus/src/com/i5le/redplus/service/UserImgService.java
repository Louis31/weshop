package com.i5le.redplus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.i5le.framwork.core.repository.BaseJapRepository;
import com.i5le.framwork.core.service.BaseService;
import com.i5le.redplus.entity.UserImg;
import com.i5le.redplus.repository.UserImgRepository;

@Component
@Transactional
public class UserImgService extends BaseService<UserImg, Long> {

	@Autowired
	private UserImgRepository userImgRepository;

	@Override
	public BaseJapRepository<UserImg, Long> getRepository() {
		return this.userImgRepository;
	}

	public List<UserImg> findByUserId(Long uid) {
		
		// TODO Auto-generated method stub
		return this.userImgRepository.findByUserId(uid);
	}

}

package com.i5le.redplus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.i5le.framwork.core.repository.BaseJapRepository;
import com.i5le.framwork.core.service.BaseService;
import com.i5le.redplus.entity.AreaCode;
import com.i5le.redplus.repository.AreaCodeRepository;

@Component
@Transactional
public class RedplusMoneyService extends BaseService<AreaCode, Long> {

	@Autowired
	private AreaCodeRepository areaCodeRepository;

	@Override
	public BaseJapRepository<AreaCode, Long> getRepository() {
		return this.areaCodeRepository;
	}

	@SuppressWarnings("unchecked")
	public List<AreaCode> findRoot() {

		return this.entityManager.createNativeQuery(
				" select * from  areacode a  where a.parentarea is null",
				AreaCode.class).getResultList();

	}
	public List<AreaCode> findAll() {

		return this.entityManager.createNativeQuery(
				" select * from  areacode ",
				AreaCode.class).getResultList();

	}

}

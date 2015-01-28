package com.i5le.redplus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.i5le.framwork.core.repository.BaseJapRepository;
import com.i5le.framwork.core.service.BaseService;
import com.i5le.redplus.entity.Type_map;
import com.i5le.redplus.repository.TypeMapRepository;

@Component
@Transactional
public class TypeMapService extends BaseService<Type_map, Long> {

	@Autowired
	private TypeMapRepository typeMapRepository;
	
	@Override
	public BaseJapRepository<Type_map, Long> getRepository() {
		// TODO Auto-generated method stub
		return this.typeMapRepository;
	}

	public List<Type_map> findAll() {

		return this.entityManager.createNativeQuery(
				" select * from  type_map ",
				Type_map.class).getResultList();

	}

}

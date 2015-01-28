package com.i5le.redplus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.i5le.framwork.core.repository.BaseJapRepository;
import com.i5le.framwork.core.service.BaseService;
import com.i5le.redplus.entity.Collect;
import com.i5le.redplus.repository.CollectRepository;
import com.i5le.redplus.repository.UserInfoRepository;

@Component
@Transactional
public class CollectService extends BaseService<Collect, Long> {

	
	@Autowired
	private CollectRepository collectRepository;
	

	@Override
	public BaseJapRepository<Collect, Long> getRepository() {
		return this.collectRepository;
	}
	
	
	public List<Collect> findAll(Long host) {

		return this.entityManager.createNativeQuery(
				" select * from  Collect as co where co.host_id ="+host,
				Collect.class).getResultList();

	}
	
	public Collect findOneCollect(Long host,Long user) {

		List<Collect> list = this.entityManager.createNativeQuery(
				" select * from collect as co where co.host_id ="+host+" and co.user_id ="+user,
				Collect.class).getResultList();
		
		if(list!=null && list.size()>=1){
			return list.get(0);
		}else {
			return null;
		}

	}
}

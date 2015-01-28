package com.i5le.redplus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.i5le.framwork.core.repository.BaseJapRepository;
import com.i5le.framwork.core.service.BaseService;
import com.i5le.redplus.entity.AreaCode;
import com.i5le.redplus.entity.DealerInfo;
import com.i5le.redplus.entity.UserInfo;
import com.i5le.redplus.repository.AreaCodeRepository;
import com.i5le.redplus.repository.DealerInfoRepository;

@Component
@Transactional
public class DealerInfoService extends BaseService<DealerInfo, String> {

	
	
	@Autowired
	private DealerInfoRepository dealerInfoRepository;
	@Override
	public BaseJapRepository<DealerInfo, String> getRepository() {
		// TODO Auto-generated method stub
		return dealerInfoRepository;
	}


	public List<DealerInfo> findTopDealerInfos() {

//		return this.entityManager.createNativeQuery(
//				" select * from  dealer_info as ui where ui.id in ( select co.user_id from collect as co where co.host_id ="+host+" )",
//				UserInfo.class).getResultList();
      return null;
	}
	

}

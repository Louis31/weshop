package com.i5le.redplus.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.i5le.framwork.core.repository.BaseJapRepository;
import com.i5le.framwork.core.service.BaseService;
import com.i5le.redplus.entity.UserGift;
import com.i5le.redplus.repository.UserGiftRepository;

@Component
@Transactional
public class UserGiftService extends BaseService<UserGift, Long> {

	@Autowired
	private UserGiftRepository userGiftRepository;

	
	public  List<UserGift> findByUserId(int id){
		return userGiftRepository.findByUserId(id);
		
	}

	@Override
	public BaseJapRepository<UserGift, Long> getRepository() {
		return this.userGiftRepository;
	}

	public void deletByUserId(int userid) {
		userGiftRepository.deletByUserId(userid);
		// TODO Auto-generated method stub
		
	}

	public UserGift findByUserIdAndGiftId(long userid, Long id) {
		// TODO Auto-generated method stub
		return userGiftRepository.findByUserIdAndGiftId((int)userid, id);
	}

}

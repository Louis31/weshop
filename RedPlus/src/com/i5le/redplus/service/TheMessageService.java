package com.i5le.redplus.service;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.i5le.framwork.core.repository.BaseJapRepository;
import com.i5le.framwork.core.service.BaseService;
import com.i5le.redplus.entity.TheMessage;
import com.i5le.redplus.repository.TheMessageRepository;

@Component
@Transactional
public class TheMessageService extends BaseService<TheMessage, Long> {

	@Autowired
	private TheMessageRepository theMessageRepository;

	@Override
	public BaseJapRepository<TheMessage, Long> getRepository() {
		return this.theMessageRepository;
	}

	public List<TheMessage> getMessages(Long uid) {
		//
		javax.persistence.Query query = entityManager
				.createNativeQuery(
						"  SELECT  * FROM the_message WHERE the_message.to_user_id = "+uid+" AND the_message.resv1 = 0 ",
						TheMessage.class);
	
		return query.getResultList();
	}

	public List<TheMessage> findByToUserIdOrderCreatetimeAsc(Long uid) {
		// TODO Auto-generated method stub
		return this.theMessageRepository.findByToUserIdOrderByCreatetimeAsc(uid);
		
	}

	
	

}

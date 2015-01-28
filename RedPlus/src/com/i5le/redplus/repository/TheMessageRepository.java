package com.i5le.redplus.repository;


import java.util.List;

import com.i5le.framwork.core.repository.BaseJapRepository;
import com.i5le.redplus.entity.GiftInfo;
import com.i5le.redplus.entity.TheMessage;
import com.i5le.redplus.entity.UserGift;
import com.i5le.redplus.entity.UserInfo;

public interface TheMessageRepository extends
		BaseJapRepository<TheMessage, Long> {

	List<TheMessage> findByToUserIdOrderByCreatetimeAsc(Long uid);


}

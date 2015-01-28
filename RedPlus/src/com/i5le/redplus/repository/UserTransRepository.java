package com.i5le.redplus.repository;


import java.util.List;

import com.i5le.framwork.core.repository.BaseJapRepository;
import com.i5le.redplus.entity.UserTrans;

public interface UserTransRepository extends
		BaseJapRepository<UserTrans, Long> {



	List<UserTrans> findByFromUserIdOrderByCreatetimeDesc(Long fid);

	List<UserTrans> findByToUserIdOrderByCreatetimeDesc(Long tid);


}

package com.i5le.redplus.repository;


import java.util.List;

import com.i5le.framwork.core.repository.BaseJapRepository;
import com.i5le.redplus.entity.AreaCode;
import com.i5le.redplus.entity.UserImg;

public interface UserImgRepository extends
		BaseJapRepository<UserImg, Long> {

	List<UserImg> findByUserId(Long uid);


}

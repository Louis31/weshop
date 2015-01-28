package com.i5le.redplus.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.i5le.framwork.core.repository.BaseJapRepository;
import com.i5le.redplus.entity.UserGift;
import com.i5le.redplus.entity.UserInfo;

public interface UserGiftRepository extends
		BaseJapRepository<UserGift, Long> {

	List<UserGift> findByUserId(int id);

	@Modifying
	@Query(value="  delete UserGift   where  userId = ?1")
	void deletByUserId(int userid);

	UserGift findByUserIdAndGiftId(int userid, Long id);
	



}

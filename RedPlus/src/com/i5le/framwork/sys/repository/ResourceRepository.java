package com.i5le.framwork.sys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.i5le.framwork.core.repository.BaseJapRepository;
import com.i5le.framwork.sys.entity.Resource;

public interface ResourceRepository extends BaseJapRepository<Resource, Long>{

	//用findByLevelOrderByOrderId 里面有Order关键字。。。
	@Query("from Resource r where r.level=?1 order by r.orderId")
	public List<Resource> findByLevel(Integer level);
}

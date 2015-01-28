package com.i5le.framwork.sys.repository;

import com.i5le.framwork.core.repository.BaseJapRepository;
import com.i5le.framwork.sys.entity.User;

public interface UserRepository extends BaseJapRepository<User, Long>{

	User findByName(String name);

	User findByLoginName(String loginName);
}

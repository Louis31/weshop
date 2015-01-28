package com.i5le.framwork.sys.repository;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.QueryHints;

import com.i5le.framwork.core.repository.BaseJapRepository;
import com.i5le.framwork.sys.entity.Dictionary;

public interface DictionaryRepository extends
		BaseJapRepository<Dictionary, Long> {

	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
	@Override
	public List<Dictionary> findAll(Specification<Dictionary> spec, Sort sort);

}

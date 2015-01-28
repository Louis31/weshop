/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.i5le.framwork.core.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.i5le.framwork.core.persistence.DynamicSpecifications;
import com.i5le.framwork.core.persistence.SearchFilter;
import com.i5le.framwork.core.repository.BaseJapRepository;
import com.i5le.framwork.core.utils.Reflections;

/**
 * <p>
 * 抽象service层基类 提供一些简便方法
 * <p/>
 * <p>
 * 泛型 ： T 表示实体类型；ID表示主键类型
 * <p/>
 * <p>
 * User: 
 * <p>
 * Date: 13-1-12 下午4:43
 * <p>
 * Version: 1.0
 */
public abstract class BaseService<T, ID extends Serializable> {

	@PersistenceContext
	protected EntityManager entityManager;

	protected Class<T> entityClass = Reflections.getClassGenricType(getClass());

	public abstract BaseJapRepository<T, ID> getRepository();

	/**
	 * 保存单个实体
	 * 
	 * @param m
	 *            实体
	 * @return 返回保存的实体
	 */
	public T save(T entity) {
		
		return getRepository().save(entity);
	}

	public List<T> save(List<T> entity) {
		return getRepository().save(entity);
	}

	public T saveAndFlush(T entity) {
		return getRepository().saveAndFlush(entity);
	}

	@Transactional
	public void batchInsert(List list) {

		for (int i = 0; i < list.size(); i++) {

			entityManager.persist(list.get(i));

			if (i % 30 == 0) {

				entityManager.flush();

				entityManager.clear();

			}

		}

	}

	/**
	 * 根据主键删除相应实体
	 * 
	 * @param id
	 *            主键
	 */
	public void delete(ID id) {
		getRepository().delete(id);
	}

	/**
	 * 根据ID删除
	 * 
	 * @param m
	 *            实体
	 */
	public void delete(ID[] ids) {
		for (ID id : ids) {
			getRepository().delete(id);
		}
	}

	/**
	 * 根据ID删除
	 * 
	 * @param m
	 *            实体
	 */
	public void delete(Iterable<ID> ids) {
		for (ID id : ids) {
			getRepository().delete(id);
		}
	}

	/**
	 * 删除实体
	 * 
	 * @param m
	 *            实体
	 */
	public void delete(T entity) {
		getRepository().delete(entity);
	}

	/**
	 * 根据主键删除相应实体
	 * 
	 * @param ids
	 *            实体
	 */
	public void deleteInBatch(Iterable<T> entities) {
		getRepository().deleteInBatch(entities);
	}

	/**
	 * 按照主键查询
	 * 
	 * @param id
	 *            主键
	 * @return 返回id对应的实体
	 */
	public T findOne(ID id) {
		return getRepository().findOne(id);
	}

	/**
	 * 实体是否存在
	 * 
	 * @param id
	 *            主键
	 * @return 存在 返回true，否则false
	 */
	public boolean exists(ID id) {
		return getRepository().exists(id);
	}

	/**
	 * 统计实体总数
	 * 
	 * @return 实体总数
	 */
	public long count() {
		return getRepository().count();
	}

	/**
	 * 查询所有实体
	 * 
	 * @return
	 */
	public List<T> findAll() {
		return getRepository().findAll();
	}

	/**
	 * 按照顺序查询所有实体
	 * 
	 * @param sort
	 * @return
	 */
	public List<T> findAll(Sort sort) {
		return getRepository().findAll(sort);
	}

	/**
	 * 分页及排序查询实体
	 * 
	 * @param pageable
	 *            分页及排序数据
	 * @return
	 */
	public Page<T> findAll(Pageable pageable) {
		return getRepository().findAll(pageable);
	}

	/**
	 * 按页面传来的查询条件查询.
	 */
	public Page<T> searchByPage(Map<String, Object> searchParams,
			Pageable pageable) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<T> spec = DynamicSpecifications.bySearchFilter(
				filters.values(), this.entityClass);
		Page<T> page = this.getRepository().findAll(spec, pageable);
		return page;
	}

	/**
	 * 按页面传来的查询条件查询.
	 */
	public List<T> search(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<T> spec = DynamicSpecifications.bySearchFilter(
				filters.values(), this.entityClass);
		List<T> list = this.getRepository().findAll(spec);
		return list;
	}

	/**
	 * 按页面传来的查询条件查询.
	 */
	public List<T> search(Map<String, Object> searchParams, Sort sort) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<T> spec = DynamicSpecifications.bySearchFilter(
				filters.values(), this.entityClass);
		List<T> list = this.getRepository().findAll(spec, sort);
		return list;
	}

	public Long count(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<T> spec = DynamicSpecifications.bySearchFilter(
				filters.values(), this.entityClass);
		long list = this.getRepository().count(spec);
		return list;
	}

	/**
	 * Returns a single entity matching the given {@link Specification}.
	 * 
	 * @param spec
	 * @return
	 */
	public T findOne(Specification<T> spec) {
		return this.getRepository().findOne(spec);
	}

	/**
	 * Returns all entities matching the given {@link Specification}.
	 * 
	 * @param spec
	 * @return
	 */
	public List<T> findAll(Specification<T> spec) {
		return this.getRepository().findAll(spec);
	}

	/**
	 * Returns a {@link Page} of entities matching the given
	 * {@link Specification}.
	 * 
	 * @param spec
	 * @param pageable
	 * @return
	 */
	public Page<T> findAll(Specification<T> spec, Pageable pageable) {
		return this.getRepository().findAll(spec, pageable);
	}

	/**
	 * Returns all entities matching the given {@link Specification} and
	 * {@link Sort}.
	 * 
	 * @param spec
	 * @param sort
	 * @return
	 */
	public List<T> findAll(Specification<T> spec, Sort sort) {
		return this.getRepository().findAll(spec, sort);
	}

	/**
	 * Returns the number of instances that the given {@link Specification} will
	 * return.
	 * 
	 * @param spec
	 *            the {@link Specification} to count instances for
	 * @return the number of instances
	 */
	public long count(Specification<T> spec) {

		return this.getRepository().count();
	}
	
	public List<T> findAllCustomers()
	{

	  String jpql="select c from CustomerEO c";

	 Query query=entityManager.createQuery(jpql);

	query.setFirstResult(5);//设置查询结果的开始记录数

	query.setMaxResults(10);//设查询结果的结束记录数   查询记录数5~~10的数据

	List<T> result=query.getResultList();

	return result;
	}

}

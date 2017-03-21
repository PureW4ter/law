package com.jfzy.service.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.jfzy.service.po.TagPO;
import com.jfzy.service.repository.TagRepository;

@Repository
public class TagRepositoryImpl implements TagRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<TagPO> findAll() {
		return this.entityManager.createQuery("SELECT t from Tag t", TagPO.class).getResultList();
	}

}

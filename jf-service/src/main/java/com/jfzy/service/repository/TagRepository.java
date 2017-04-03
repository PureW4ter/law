package com.jfzy.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jfzy.service.po.TagPO;

public interface TagRepository extends JpaRepository<TagPO, Integer> {

	List<TagPO> findAll();
}

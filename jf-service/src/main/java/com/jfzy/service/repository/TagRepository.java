package com.jfzy.service.repository;

import java.util.List;

import com.jfzy.service.po.TagPO;

public interface TagRepository {

	List<TagPO> findAll();
}

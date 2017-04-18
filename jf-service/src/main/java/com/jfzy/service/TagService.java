package com.jfzy.service;

import java.util.List;

import com.jfzy.service.bo.TagBo;

public interface TagService {

	List<TagBo> getAllTags();

	void addTag(TagBo bo);

	void updateTag(TagBo bo);

	void deleteTag(int id);

}

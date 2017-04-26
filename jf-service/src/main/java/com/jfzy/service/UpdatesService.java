package com.jfzy.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.jfzy.service.bo.UpdateBo;

public interface UpdatesService {

	List<UpdateBo> getUpdates(int type, Pageable page);

}

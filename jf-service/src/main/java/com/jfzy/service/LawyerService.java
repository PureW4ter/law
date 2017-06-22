package com.jfzy.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.jfzy.service.bo.LawyerBo;

public interface LawyerService {

	public LawyerBo loginWithPassword(String loginName, String password);
	
	List<LawyerBo> getActiveLawyerByCity(int cityId);

	List<LawyerBo> getLawyerByCity(int cityId);

	void create(LawyerBo bo);

	void updateLawyerStatus(int lawyerId, int status);

	LawyerBo getLawyerById(int id);
	
	public List<LawyerBo> getLawyers(Pageable page);

}

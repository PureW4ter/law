package com.jfzy.service;

import java.util.List;

import com.jfzy.service.bo.LawyerBo;

public interface LawyerService {

	List<LawyerBo> getActiveLawyerByCity(int cityId);

	List<LawyerBo> getLawyerByCity(int cityId);

	void createLawyer(LawyerBo bo);

	void updateLawyer(LawyerBo bo);

	void updateLawyerStatus(int lawyerId, int status);

	LawyerBo getLawyerById(int id);

}

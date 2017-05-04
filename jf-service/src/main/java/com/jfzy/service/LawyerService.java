package com.jfzy.service;

import java.util.List;

import com.jfzy.service.bo.LawyerBo;

public interface LawyerService {

	List<LawyerBo> getActiveLawyerByCity(int cityId);

}

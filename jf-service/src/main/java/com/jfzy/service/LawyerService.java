package com.jfzy.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import com.jfzy.service.bo.LawyerBo;

public interface LawyerService {

	public LawyerBo loginWithPassword(String loginName, String password);

	public void addLaywerTask(double money, int id);

	List<LawyerBo> getActiveLawyerByCity(int cityId);

	List<LawyerBo> getLawyerByCity(int cityId);

	void create(LawyerBo bo);

	void updateLawyerStatus(int lawyerId, int status);

	LawyerBo getLawyerById(int id);

	public List<LawyerBo> getLawyers(Pageable page);

	void updateFinishedTask(int count, int id);

	void updateOnProcessTask(int count, int id);

	void updateTotalMoney(double money, int id);

	void updateFinishedMoney(double money, int id);

	void updateLawyerScore(int lawyerId, int score);
}

package com.jfzy.service.repository;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.jfzy.service.po.LawyerPo;

public interface LawyerRepository extends JpaRepository<LawyerPo, Integer> {

	List<LawyerPo> findByCityIdAndStatus(int cityId, int status);

	List<LawyerPo> findByCityId(int cityId);
	
	@Transactional
	@Modifying
	@Query("UPDATE LawyerPo SET status=?1, updateTime=?2 WHERE id=?3")
	void updateStatus(int status, Timestamp updateTime, int id);
	
	List<LawyerPo> findByPhoneNum(String phoneNum);
	
	List<LawyerPo> findByLoginNameAndPassword(String loginName, String password);
	
	@Transactional
	@Modifying
	@Query("UPDATE LawyerPo SET finishedTask=finishedTask+?1 WHERE id=?2")
	void updateFinishedTask(int count, int id);
	
	@Transactional
	@Modifying
	@Query("UPDATE LawyerPo SET onProcessTask=onProcessTask+?1 WHERE id=?2")
	void updateOnProcessTask(int count, int id);
	
	@Transactional
	@Modifying
	@Query("UPDATE LawyerPo SET totalMoney=totalMoney+?1 WHERE id=?2")
	void updateTotalMoney(double money, int id);
	
	@Transactional
	@Modifying
	@Query("UPDATE LawyerPo SET finishedMoney=finishedMoney+?1 WHERE id=?2")
	void updateFinishedMoney(double money, int id);

}

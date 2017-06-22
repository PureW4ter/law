package com.jfzy.service.repository;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.jfzy.service.po.OssUserPo;

public interface OssUserRepository extends JpaRepository<OssUserPo, Integer> {

	@Transactional
	@Modifying
	@Query("UPDATE OssUserPo SET status=?1, updateTime=?2 WHERE id=?3")
	void updateStatus(int status, Timestamp updateTime, int id);

	@Transactional
	@Modifying
	@Query("UPDATE OssUserPo SET role=?1, updateTime=?2 WHERE id=?3")
	void updateAuth(String role, Timestamp updateTime, int id);
	
	List<OssUserPo> findByPhoneNum(String phoneNum);
	
	List<OssUserPo> findByLoginNameAndPassword(String loginName, String password);
	
	List<OssUserPo> findByLoginName(String loginName);

}

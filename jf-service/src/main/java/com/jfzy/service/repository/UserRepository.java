package com.jfzy.service.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jfzy.service.po.UserPo;

public interface UserRepository extends JpaRepository<UserPo, Integer> {

	@Transactional
	@Modifying
	@Query("UPDATE UserPo SET deleted=1 WHERE id=:id")
	void updateDeleted(@Param("id") int id);
	
	
	@Transactional
	@Modifying
	@Query("UPDATE UserPo SET memo=:memo WHERE id=:id")
	void updateMemo(@Param("memo") String memo, @Param("id") int id);
	
	
	@Transactional
	@Modifying
	@Query("UPDATE UserPo SET level=:level WHERE id=:id")
	void updateLevel(@Param("level") int level, @Param("id") int id);
}

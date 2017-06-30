package com.jfzy.service.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jfzy.service.po.OrderPo;
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
	
	@Query(value = "SELECT t FROM UserPo t WHERE t.level=?1")
	Page<UserPo> getUsersByLevel(int level, Pageable page);
}

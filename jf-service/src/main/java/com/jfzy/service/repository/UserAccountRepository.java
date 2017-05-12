package com.jfzy.service.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jfzy.service.po.UserAccountPo;

public interface UserAccountRepository extends JpaRepository<UserAccountPo, Integer> {
	@Transactional
	@Modifying
	@Query("UPDATE UserAccountPo SET deleted=1 WHERE id=:id")
	void updateDeleted(@Param("id") int id);
	
	@Query(value = "SELECT t FROM UserAccountPo t WHERE t.value=?1 and t.status=0")
	UserAccountPo getByOpenid(String id);
	
	
	@Query(value = "SELECT t FROM UserAccountPo t WHERE t.userId=?1 and t.type=?2 and t.status=0")
	UserAccountPo getByUserid(int userId, int type);
}

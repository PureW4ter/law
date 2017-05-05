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
}

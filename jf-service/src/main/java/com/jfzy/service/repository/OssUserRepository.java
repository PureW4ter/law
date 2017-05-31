package com.jfzy.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jfzy.service.po.OssUserPo;

public interface OssUserRepository extends JpaRepository<OssUserPo, Integer> {

	List<OssUserPo> findByLoginNameAndPassword(String loginName, String password);

	List<OssUserPo> findByLoginName(String loginName);

}

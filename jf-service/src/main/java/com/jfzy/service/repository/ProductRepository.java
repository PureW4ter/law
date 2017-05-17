package com.jfzy.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jfzy.service.po.ProductPo;

public interface ProductRepository extends JpaRepository<ProductPo, Integer> {
	@Query("SELECT t FROM ProductPo t WHERE t.deleted=0")
	List<ProductPo> findNotDeleted();
	
	@Query(value = "SELECT t FROM ProductPo t WHERE t.id=?1 and t.deleted=0")
	ProductPo getById(int id);
}

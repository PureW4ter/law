package com.jfzy.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jfzy.service.po.PropertyPo;

public interface PropertyRepository extends JpaRepository<PropertyPo, Integer> {

}

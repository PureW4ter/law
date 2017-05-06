package com.jfzy.service.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jfzy.service.po.ArticlePo;
import com.jfzy.service.po.OrderPo;

public interface OrderRepository extends JpaRepository<OrderPo, Integer> {



}

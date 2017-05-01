package com.jfzy.service.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jf_order")
public class OrderPo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
}

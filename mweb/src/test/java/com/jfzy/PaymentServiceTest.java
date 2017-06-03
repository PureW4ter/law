package com.jfzy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jfzy.service.bo.OrderBo;
import com.jfzy.service.impl.PaymentService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentServiceTest {

	@Autowired
	private PaymentService service;

	@Test
	public void m1() {
		OrderBo order = new OrderBo();
		order.setSn("12345454958495");
		order.setRealPrice(0.99);
		order.setProductName("测试用");

		service.unifiedOrder(order, "192.168.0.1", "o7oTkv-65f4uQUX3DY_X7GmwM90k");
	}
}

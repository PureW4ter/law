package com.jfzy.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jfzy.service.repository.OrderRepository;

@RunWith(SpringRunner.class)
@SpringBootApplication
@SpringBootTest
public class OrderServiceTest {

	@Autowired
	private OrderRepository orderRepo;

	@Test
	public void test1() {
		Assert.assertNotNull(orderRepo);
		Assert.assertEquals(1, orderRepo.countByCityIdAndStatus(0, 1));
	}
}

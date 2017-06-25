package com.jfzy.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootApplication
@SpringBootTest
public class IdServiceTest {

	@Autowired
	private IdService idService;

	@Test
	public void test1() {
		System.out.println(idService.generateId(1, 20170624));
	}
}

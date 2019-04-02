package com.vish.example.order.main;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit test class to test spring boot context load
 * Created by Vishwanath on 19/03/2019.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderManagementApplication.class)
public class OrderManagementApplicationTests {

	private static final Logger log = LoggerFactory.getLogger(OrderManagementApplicationTests.class);

	@Test
	public void contextLoads() {
		log.info("Context Loaded Successfully!");
	}
}

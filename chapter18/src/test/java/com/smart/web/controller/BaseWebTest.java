package com.smart.web.controller;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.testng.annotations.BeforeClass;
import org.unitils.UnitilsTestNG;
import org.unitils.spring.annotation.SpringApplicationContext;

@SpringApplicationContext( { "classpath:/applicationContext.xml",
"classpath:/xiaochun-servlet.xml" })
public class BaseWebTest extends UnitilsTestNG  {

	//声明Request与Response模拟对象
	public MockHttpServletRequest request;
	public MockHttpServletResponse response;
	public MockHttpSession session;


	//执行测试前先初始模拟对象
	@BeforeClass
	public void before() {
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		session = new MockHttpSession();
		request.setCharacterEncoding("UTF-8");
	}

}

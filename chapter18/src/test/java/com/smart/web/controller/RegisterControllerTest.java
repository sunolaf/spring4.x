package com.smart.web.controller;

import com.smart.domain.User;
import com.smart.web.RegisterController;
import org.springframework.web.servlet.ModelAndView;
import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringBeanByType;

import static org.testng.Assert.assertNotNull;

public class RegisterControllerTest extends BaseWebTest {
	@SpringBeanByType
	private RegisterController controller;

	@Test
	public void register() throws Exception {
		request.setRequestURI("/register.html");
		request.setMethod("POST");
		User user = new User();
		user.setUserName("test");
		user.setPassword("1234");
		user.setLocked(0);

		// 向控制发起请求 
		ModelAndView mav = controller.register(request,user);
		assertNotNull(mav);
	}
	

}

package com.smart.web.controller;

import com.smart.cons.CommonConstant;
import com.smart.domain.User;
import com.smart.web.LoginController;
import org.springframework.web.servlet.ModelAndView;
import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringBeanByType;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;


public class LoginControllerTest extends BaseWebTest {
	@SpringBeanByType
	private LoginController controller;

	@Test
	public void loginCheckByMock() throws Exception {
		request.setRequestURI("/login/doLogin.html");
		User user = new User();
		user.setUserName("test");
		user.setPassword("1234");

		// 向控制发起请求 ” /loginCheck.html”
		ModelAndView mav = controller.login(request, user);
		User userBack = (User) request.getSession().getAttribute(CommonConstant.USER_CONTEXT);

		assertNotNull(mav);
		assertNotNull(userBack);
		assertEquals(userBack.getUserName(), "test");// ⑧ 验证返回结果
		assertEquals(userBack.getCredit()> 5, true);
	}
	

}

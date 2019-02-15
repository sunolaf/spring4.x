package com.smart.dao;

import com.smart.domain.User;
import com.smart.test.dataset.util.XlsDataSetBeanFactory;
import org.testng.annotations.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.spring.annotation.SpringBean;

import java.util.List;

import static org.junit.Assert.*;


public class UserDaoTest extends BaseDaoTest {

	@SpringBean("userDao")
	private UserDao userDao;


	@Test
	@DataSet("XiaoChun.Users.xls")//准备数据
	public void findUserByUserName() {
		User user = userDao.getUserByUserName("tony");
		assertNull("不存在用户名为tony的用户!", user);
		user = userDao.getUserByUserName("jan");
		assertNotNull("Jan用户存在！", user);
		assertEquals("jan", user.getUserName());
		assertEquals("123456",user.getPassword());
		assertEquals(10,user.getCredit());
	}

	// 验证数据库保存的正确性
	@Test
	@DataSet("XiaoChun.SaveUser.xls")
	@ExpectedDataSet("XiaoChun.ExpectedSaveUser.xls")// 准备验证数据
	public void saveUser()throws Exception  {
		User u  = XlsDataSetBeanFactory.createBean(UserDaoTest.class,"XiaoChun.SaveUser.xls", "t_user", User.class);
		userDao.update(u);  //执行用户信息更新操作
	}
	
	//验证数据库保存的正确性
	//@Test
	//@ExpectedDataSet("XiaoChun.ExpectedSaveUsers.xls")// 准备验证数据
	public void saveUsers()throws Exception  {
		List<User> users  = XlsDataSetBeanFactory.createBeans(UserDaoTest.class,"XiaoChun.SaveUsers.xls", "t_user", User.class);
		for(User u:users){
		     userDao.save(u);
		}
	}

}

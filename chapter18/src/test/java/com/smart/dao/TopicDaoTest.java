package com.smart.dao;

import com.smart.domain.Topic;
import com.smart.domain.User;
import com.smart.test.dataset.util.XlsDataSetBeanFactory;
import org.testng.annotations.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.spring.annotation.SpringBean;

import java.util.List;

/**
 * topic 的DAO类
 *
 */
public class TopicDaoTest extends BaseDaoTest {

	@SpringBean("topicDao")
	private TopicDao topicDao;
	
	@Test
	@DataSet("XiaoChun.SaveTopics.xls")
	@ExpectedDataSet("XiaoChun.ExpectedTopics.xls")
	public void addTopic()throws Exception {
	    List<Topic> topics = XlsDataSetBeanFactory.createBeans(TopicDaoTest.class,"XiaoChun.SaveTopics.xls", "t_topic", Topic.class);
	    for(Topic topic:topics){
			User user = new User();
			user.setUserId(1);
			topic.setUser(user);
	    	topicDao.save(topic);
	    }
	}
}

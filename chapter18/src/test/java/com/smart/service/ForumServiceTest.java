package com.smart.service;

import com.smart.domain.*;
import com.smart.test.dataset.util.XlsDataSetBeanFactory;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;

import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * BoardManager的测试类
 */
public class ForumServiceTest extends BaseServiceTest {

    @SpringBean("forumService")
	private ForumService forumService;

	@SpringBean("userService")
	private UserService userService;

	@BeforeMethod
	public void init(){
		SessionFactory sf  = hibernateTemplate.getSessionFactory();
		Map<String, CollectionMetadata> roleMap = sf.getAllCollectionMetadata();
		for (String roleName : roleMap.keySet()) {
			sf.evictCollection(roleName);
		}
		Map<String, ClassMetadata> entityMap = sf.getAllClassMetadata();
		for (String entityName : entityMap.keySet()) {
			sf.evictEntity(entityName);
		}
		sf.evictQueries();
	}

	/**
	 * 测试新增一个版块
	 */
	@Test
	@DataSet("XiaoChun.DataSet.xls")
	public void addBoard() throws Exception {
		Board board = XlsDataSetBeanFactory.createBean(ForumServiceTest.class,
				"XiaoChun.DataSet.xls", "t_board", Board.class);

		forumService.addBoard(board);
		Board boardDb = forumService.getBoardById(board.getBoardId());
		assertEquals(boardDb.getBoardName(), "育儿");
	}

	/**
	 * 测试新增一个主题帖子
	 */
	@Test
	@DataSet("XiaoChun.DataSet.xls")
	public void addTopic() throws Exception {
		Topic topic = XlsDataSetBeanFactory.createBean(ForumServiceTest.class,
				"XiaoChun.DataSet.xls", "t_topic", Topic.class);
		User user = XlsDataSetBeanFactory.createBean(ForumServiceTest.class,
				"XiaoChun.DataSet.xls", "t_user", User.class);
		MainPost post = XlsDataSetBeanFactory.createBean(ForumServiceTest.class,
				"XiaoChun.DataSet.xls", "t_post", MainPost.class);
		topic.setUser(user);
		topic.setMainPost(post);
		forumService.addTopic(topic);

		Board boardDb = forumService.getBoardById(1);
		User userDb = userService.getUserByUserName("tom");
		assertEquals(boardDb.getTopicNum(), 1);
		assertEquals(userDb.getCredit(), 110);
		assertEquals(topic.getTopicId()>0, true);
	}

	/**
	 * 测试删除帖子
	 */
	@Test
	@DataSet("XiaoChun.DataSet.xls")
	// 准备验证数据
	public void removeTopic() {
		forumService.removeTopic(1);
		Topic topicDb = forumService.getTopicByTopicId(1);
		assertNull(topicDb);
	}

	/**
	 * 测试添加回复帖子
	 * 
	 */
	@Test
	@DataSet("XiaoChun.DataSet.xls")
	public void addPost() throws Exception {
		Post post = XlsDataSetBeanFactory.createBean(ForumServiceTest.class,
				"XiaoChun.DataSet.xls", "t_post", Post.class);
		User user = XlsDataSetBeanFactory.createBean(ForumServiceTest.class,
				"XiaoChun.DataSet.xls", "t_user", User.class);
		Topic topic = new Topic();
		topic.setTopicId(1);
		post.setUser(user);
		post.setTopic(topic);
		forumService.addPost(post);
		
		User userDb = userService.getUserByUserName("tom");

		Topic topicDb = forumService.getTopicByTopicId(1);
		assertEquals(post.getPostId()>1, true);
		assertEquals(userDb.getCredit(), 105);
		assertEquals(topicDb.getReplies(), 2);
	}
	
    /**
     * 测试删除回复帖子的方法
     */
	@Test
	@DataSet("XiaoChun.DataSet.xls")
    public void removePost()
    {
		forumService.removePost(1);
		
		Post postDb = forumService.getPostByPostId(1);
		User userDb = userService.getUserByUserName("tom");
		Topic topicDb = forumService.getTopicByTopicId(1);
		
		assertNull(postDb);
		assertEquals(userDb.getCredit(), 80);
		assertEquals(topicDb.getReplies(), 0);
    }

    /**
     * 测试置精华主题帖的服务方法
     */
	@Test
	@DataSet("XiaoChun.DataSet.xls")
    public void makeDigestTopic()throws Exception
    {
		forumService.makeDigestTopic(1);
		User userDb = userService.getUserByUserName("tom");
		Topic topicDb = forumService.getTopicByTopicId(1);
		assertEquals(userDb.getCredit(), 200);
		assertEquals(topicDb.getDigest(), Topic.DIGEST_TOPIC);
    }
	
	
	
	@Test
	@DataSet("XiaoChun.DataSet.xls")
	public void addBoardManager(){
	    forumService.addBoardManager(1,"tom");
		User userDb = userService.getUserByUserName("tom");
		assertEquals(userDb.getManBoards().size()>0, true);
	}

}

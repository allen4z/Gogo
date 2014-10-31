package com.gogo.dao.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gogo.dao.UserDao;
import com.gogo.domain.Activity;
import com.gogo.domain.User;
import com.gogo.test.AbstractContextTests;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserDaoTest extends AbstractContextTests  {

	@Resource
	private UserDao userDao;
	
	//@Test
	public void loadUserByIdTest() throws Exception{
		User user = userDao.loadUserById(1);
		Assert.assertEquals(user.getName(), "1");
	}
	
	//@Test
	public void loadOwnActTest()throws Exception{
		/*List<Activity> list = userDao.loadOwnActivitesByUser(1);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size()>0);*/
	}
	
	//@Test
	public void updateUserTest() throws Exception{
		User user = userDao.loadUserById(4);
		user.setName("Allen4z");
		user.setAliasName("Allen4z");
		userDao.update(user);
	}
	@Test
	public void delUserTest()  throws Exception {
		User user = userDao.loadUserById(2);
		userDao.remove(user);
	}
}

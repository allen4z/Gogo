package com.gogo.dao.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gogo.dao.RoleDao;
import com.gogo.domain.Activity;
import com.gogo.domain.User;
import com.gogo.test.AbstractContextTests;


@RunWith(SpringJUnit4ClassRunner.class)
public class RoleDaoTest  extends AbstractContextTests {
	
	@Autowired
	RoleDao roleDao;
	
	@Test
	public void loadCurUserRole4ActTest(){
		
		User user=new User();
		user.setUserId(1);
		Activity act = new Activity();
		act.setActId(6);
		
		
		List roles = roleDao.loadCurUserRole4Act(user, act);
		
		Assert.assertNotNull(roles);
	}

}

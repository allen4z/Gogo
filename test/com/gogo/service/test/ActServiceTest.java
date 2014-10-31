package com.gogo.service.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gogo.domain.Activity;
import com.gogo.domain.helper.DomainStateHelper;
import com.gogo.service.ActivityService;
import com.gogo.test.AbstractContextTests;

@RunWith(SpringJUnit4ClassRunner.class)
public class ActServiceTest extends AbstractContextTests  {

	@Resource
	private ActivityService service;
	
	@Test
	public void updateUserTest() throws Exception{
		Activity act = new Activity();
		act.setId("2");
		act.setName("更新测试");
		act.setState(DomainStateHelper.ACT_STOP);
		service.deleteActivity("1", "1");
	}
	
}

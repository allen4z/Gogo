package com.gogo.ctrl.test;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.gogo.domain.User;
import com.gogo.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")

@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)  
@Transactional

public class UserTest extends AbstractTransactionalJUnit4SpringContextTests  {

//	private MockMvc mockMvc;
	

	@Resource
	UserService userservice;

	public void setUserservice(UserService userservice) {
		this.userservice = userservice;
	}

	/*@Before
	public void before() {
		this.mockMvc = webAppContextSetup(this.wac).build();
	}*/
	
	@Test
	public void loadUserTest() throws Exception{
		User user = userservice.loadUserById(1);
		assertNotNull(user);
		
	}
	
	/*//@Test
	public void login() throws Exception{
		mockMvc.perform((post("/login/doLogin")).param("userName", "1").param("password", "1"))
		.andExpect(status().isOk()).andDo(print());
	}

	//@Test
	public void getAccount() throws Exception {
		mockMvc.perform(post("/user/load/1"))
				.andExpect(status().isOk()).andDo(print());
	}*/

}

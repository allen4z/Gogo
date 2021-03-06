package com.gogo.ctrl.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.gogo.dao.UserDao;
import com.gogo.service.UserService;
import com.gogo.test.AbstractContextTests;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserCtrlTest extends AbstractContextTests  {

	
	private MockMvc mockMvc;
	@Resource
	private UserService userservice;
	
	@Resource
	private UserDao userDao;
	

	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	/*@Test
	public void loadUserTest() throws Exception{
		List list = userDao.loadUserByName("1", 1,2);
		Assert.assertNotNull(list);
	}*/
	
	@Test
	public void login()throws Exception{
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/loadPageByName/1")
				.param("curPage", "1")
				.param("pagesize", "2"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				//.andExpect(MockMvcResultMatchers.status().isFound())
				.andDo(MockMvcResultHandlers.print())
				.andReturn();
		Assert.assertNotNull(result.getResponse().toString());
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

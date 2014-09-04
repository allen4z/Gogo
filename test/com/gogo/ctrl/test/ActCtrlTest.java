package com.gogo.ctrl.test;

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

import com.gogo.test.AbstractContextTests;

@RunWith(SpringJUnit4ClassRunner.class)
public class ActCtrlTest extends AbstractContextTests  {

	
	private MockMvc mockMvc;


	@Before
	public void before() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void saveActTest()throws Exception{
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/activity/saveAct"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print())
				.andReturn();
		Assert.assertNotNull(result.getModelAndView().getModel().get("errorMsg"));
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

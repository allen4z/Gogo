package com.gogo.ctrl.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;


@WebAppConfiguration  
@ContextConfiguration(locations = { 
		"classpath:applicationContext.xml",
		"file:WebRoot/WEB-INF/viewspace-servlet.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)  
@Transactional
public class AbstractContextControllerTests {   
  
    @Autowired  
    protected WebApplicationContext wac;   
  
}  
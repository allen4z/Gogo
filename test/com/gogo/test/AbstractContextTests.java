package com.gogo.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;


@WebAppConfiguration  
@ContextConfiguration(locations = {"classpath:applicationContext.xml","classpath:viewspace-servlet.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)  
public class AbstractContextTests  extends AbstractTransactionalJUnit4SpringContextTests {   
  
    @Autowired  
    protected WebApplicationContext wac;   
  
}  
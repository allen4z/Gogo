package com.gogo.domain.helper;

import java.util.HashMap;
import java.util.Map;

public class RoleHelper {

	//发起人
	public static final String MANAGER_CODE= "01";
	public static final String MANAGER_NAME= "MANGER";
	
	//投资人
	public static final String INVEST_CODE= "02";
	public static final String INVEST_NAME= "INVESTOR";
	
	//参与者
	public static final String JOIN_CODE= "03";
	public static final String JOIN_NAME= "JOIN";
	
	//观众
	public static final String SIGNUP_CODE= "04";
	public static final String SIGNUP_NAME= "SIGNUP";
	
	//访客
	public static final String VISITOR_CODE= "05";
	public static final String VISITOR_NAME= "VISITOR";
	
	
	public static final int UAR_NONE_ACTIVITY=0;  //  000
	
	public static final int UAR_JOIN_ACTIVITY=1;  //  001
	
	public static final int UAR_SINGUP_ACTIVITY=2; // 010
	
	public static final int UAR_INVEST_ACTIVITY=4; // 100
	
	
	public static Map<String,String> getRoleInfo(){
		Map<String,String> map = new HashMap<String,String>();
		
		map.put(MANAGER_CODE, MANAGER_NAME);
		map.put(INVEST_CODE, INVEST_NAME);
		map.put(JOIN_CODE, JOIN_NAME);
		map.put(SIGNUP_CODE, SIGNUP_NAME);
		map.put(VISITOR_CODE, VISITOR_NAME);
		return map;
	}
}

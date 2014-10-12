package com.gogo.domain.helper;

import java.util.HashMap;
import java.util.Map;

/**
 * 角色权限
 * @author Allen
 *
 */
public class RoleHelper {

	//角色相关信息
	//管理员
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
	//成员
	public static final String VISITOR_CODE= "05";
	public static final String VISITOR_NAME= "VISITOR";
	
	//权限相关信息--使用二进制进行判断
	//用户在当前活动没有权限
	public static final int UAR_NONE_ACTIVITY=0;  //  000
	//用户在当前活动为参与者权限
	public static final int UAR_JOIN_ACTIVITY=1;  //  001
	//用户在当前活动为观众权限
	public static final int UAR_SINGUP_ACTIVITY=2; // 010
	//用户在当前活动为投资人权限
	public static final int UAR_INVEST_ACTIVITY=4; // 100
	
	/**
	 * 判断是否已经包含了此权限
	 * @param curState 当前权限
	 * @param changeState 比较的权限
	 * @return 是否包含
	 */
	public static boolean judgeState(int curState,int changeState){
		// 4 -- 100  
		// 5 -- 101
		//按位或    &  5&4 = 100 = 4
		//按位于    |  5|4 = 101 = 5
		//按位异或  ^ 5^4 = 001 = 1
		//System.out.println(curState&changeState);
		//System.out.println(curState|changeState);
		//System.out.println(curState^changeState);
		
		if((curState&changeState) == changeState){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 增加权限
	 * @param curState 当前的权限
	 * @param changeState 增加的权限
	 * @return 合并后的权限
	 */
	public static int mergeState(int curState,int changeState){
		return (curState|changeState);
	}
	/**
	 * 减少权限
	 * @param curState 当前权限
	 * @param changeState 取消的权限
	 * @return 减少后的权限标识
	 */
	public static int reduceState(int curState,int changeState){
		return (curState^changeState);
	}
	
	public static Map<String,String> getRoleInfo(){
		Map<String,String> map = new HashMap<String,String>();
		
		map.put(MANAGER_CODE, MANAGER_NAME);
		map.put(INVEST_CODE, INVEST_NAME);
		map.put(JOIN_CODE, JOIN_NAME);
		map.put(SIGNUP_CODE, SIGNUP_NAME);
		map.put(VISITOR_CODE, VISITOR_NAME);
		return map;
	}
	
	public static void main(String[] args) {
		System.out.println(RoleHelper.judgeState(7, 2));
	}
}

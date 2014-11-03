package com.gogo.domain.helper;

import javax.management.relation.Role;


/**
 * 角色权限
 * @author Allen
 *
 */
public class RoleHelper {

	//-----------------------------------------------------
	//用户权限
	//没有任何权限
	public static final int ONE_AUTHORITY_NONE=0; //000
	//是否允许发言
	public static final int TOW_AUTHORITY_TEXT=1;  //  001
	//是否允许邀请其他人
	public static final int THREE_AUTHORITY_INVITE=2;  //  010
	//是否允许踢人
	public static final int FOUR_AUTHORITY_EXPEL=4; //100
	
	public static final int MAX_AUTHORITY=FOUR_AUTHORITY_EXPEL;
	
	//--------------------------------------------------------------
	
	
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
	
	
	/**
	 * 获得传入参数以下所有值的总和(传入权限一下的所有权限)
	 * @param state
	 * @return
	 */
	public static int mergeParamState(int state){
		int result = 0;
		 while(0 !=state){
			 result = mergeState(result, state);
			 state = state>>1;
		}
		return result;
	}
	
	public static Integer[] getAuthInfo(int start,int end){
		int size = end - start + 1;
		Integer[] result = new Integer[size];
		
		for (int i = 0; i <result.length; i++) {
			result[i] = start+i;
		}
		
		return result;
	}
	
public static void main(String[] args) {
	System.out.println(RoleHelper.mergeParamState(4));
}
	
}

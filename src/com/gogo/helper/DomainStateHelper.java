package com.gogo.helper;

public class DomainStateHelper {
	
	//用户状态信息：
	//用户正常状态
	public static final int USER_NORMAL_STATE = 0;
	//用户删除状态
	public static final int USER_DEL_STATE=1;
	
	
	//活动状态信息：
	//活动删除
	public static final int ACT_DEL = -1;
	//活动新增状态
	public static final int ACT_NEW = 0;
	//活动发布状态
	public static final int ACT_RELEASE = 1;
	//活动暂停状态
	public static final int ACT_SUSPEND =2; 
	//活动停止状态
	public static final int ACT_STOP =3; 
	//活动停止结束
	public static final int ACT_FINASH =4; 
	
}

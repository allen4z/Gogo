package com.gogo.domain.helper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class DomainStateHelper {

	// 用户状态信息：
	// 用户正常状态
	public static final int USER_NORMAL_STATE = 0;
	// 用户删除状态
	public static final int USER_DEL_STATE = 1;

	// 活动状态信息：
	// 活动删除
	public static final int ACT_DEL = -1;
	// 活动新增状态
	public static final int ACT_NEW = 0;
	// 活动发布状态
	public static final int ACT_RELEASE = 1;
	// 活动暂停状态
	public static final int ACT_SUSPEND = 2;
	// 活动停止状态
	public static final int ACT_STOP = 3;
	// 活动停止结束
	public static final int ACT_FINASH = 4;
	
	//好友请求
	//好友请求通过
	public static final boolean FIREND_PASSED=true;
	//好友请求未通过
	public static final boolean FIREND_UNPASSED=false;
	
	//加入
	public static final int USER_AND_ACT_JOIN=0;
	//排队
	public static final int USER_AND_ACT_QUEUE=1;
	//取消
	public static final int USER_AND_ACT_CANCEL=2;
	
	
	public static final int GROUP_DEFAULT_USER_SIZE=50;

	/**
	 * 属性复制方法 -- 主要用于更新方法
	 * @param source
	 * @param target
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void copyPriperties(Object source, Object target)
			throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {

		String fileName, str, getName, setName;
		List fields = new ArrayList();
		Method getMethod = null;
		Method setMethod = null;

		Class c1 = source.getClass();
		Class c2 = target.getClass();

		//Field[] fs1 = c1.getDeclaredFields();
		//Field[] fs2 = c2.getDeclaredFields();
		// 两个类属性比较剔除不相同的属性，只留下相同的属性
		/*for (int i = 0; i < fs2.length; i++) {
			for (int j = 0; j < fs1.length; j++) {
				if (fs1[j].getName().equals(fs2[i].getName())) {
					fields.add(fs1[j]);
					break;
				}
			}
		}*/
		if (null != fields && fields.size() > 0) {
			for (int i = 0; i < fields.size(); i++) {
				// 获取属性名称
				Field f = (Field) fields.get(i);
				fileName = f.getName();
				// 属性名第一个字母大写
				str = fileName.substring(0, 1).toUpperCase();
				// 拼凑getXXX和setXXX方法名
				getName = "get" + str + fileName.substring(1);
				setName = "set" + str + fileName.substring(1);
				// 获取get、set方法
				getMethod = c1.getMethod(getName, new Class[] {});
				setMethod = c2.getMethod(setName, new Class[] { f.getType() });
				// 获取属性值
				Object o = getMethod.invoke(source, new Object[] {});
				// 将属性值放入另一个对象中对应的属性
				if (null != o) {
					setMethod.invoke(target, new Object[] { o });
				}
			}
		}

	}

}

package com.gogo.ctrl;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import com.gogo.domain.Activity;
import com.gogo.domain.User;

public interface ActivityController {
	
	/**
	 * 创建活动
	 * @param act
	 * @return
	 */
	public int saveActivity( Activity act, int userId);
	
	/**
	 * 根据ID删除活动
	 * @param actId
	 * @return
	 */
	public boolean delActivity(String actId,String userId) throws Exception;
	
	/**
	 * 根据ID跟新活动
	 * @param actId
	 * @return
	 */
	public boolean updateActivity(String actId,String userId)throws Exception;
	
	/**
	 * 根据活动ID获得活动信息
	 * @param actId
	 * @return
	 */
	
	public Activity loadActByActId( int actId)throws Exception;
	
	/**
	 * 根据活动ID获得所有参与活动的用户信息
	 * @param actId
	 * @return
	 */
	public List<User> loadJoinUserByActId(String actId) throws Exception;

	public String addPage() throws Exception;

	public ModelAndView showPage( int actId) throws Exception;
}

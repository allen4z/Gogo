package com.gogo.ctrl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.gogo.domain.Activity;

public interface ActivityController {
	
	/**
	 * 创建活动
	 * @param act
	 * @return
	 */
	public int saveActivity(HttpServletRequest req,Activity act);
	
	/**
	 * 根据ID删除活动
	 * @param actId
	 * @return
	 */
	public void delActivity(HttpServletRequest req ,int actId)  throws Exception;
	
	/**
	 * 根据ID跟新活动
	 * @param actId
	 * @return
	 */
	public void updateActivity(HttpServletRequest req ,Activity act)throws Exception;
	
	/**
	 * 根据活动ID获得活动信息
	 * @param actId
	 * @return
	 */
	
	public Activity loadActByActId( int actId)throws Exception;
	
	public String addPage() throws Exception;

	public ModelAndView showPage( int actId) throws Exception;
}

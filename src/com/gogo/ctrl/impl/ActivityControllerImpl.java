package com.gogo.ctrl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gogo.annotation.GoJsonFilter;
import com.gogo.ctrl.ActivityController;
import com.gogo.domain.Activity;
import com.gogo.domain.User;
import com.gogo.domain.filter.UserFilter;
import com.gogo.service.ActivityService;


@Controller
@RequestMapping("/activity")
public class ActivityControllerImpl implements ActivityController {

	@Autowired
	private ActivityService actService;
	
	/**
	 * 创建活动
	 * @param act
	 * @return
	 */
	@RequestMapping("saveAct/{userId}")
	@ResponseBody
	public int saveActivity(@RequestBody Activity act,@PathVariable int userId){
		int id = actService.saveActivity(act,userId);
		return id;
	}
	
	/**
	 * 根据ID删除活动
	 * @param actId
	 * @return
	 */
	public boolean delActivity(String actId,String userId) throws Exception{
		return actService.delActivity(actId);
	}
	
	/**
	 * 根据ID跟新活动
	 * @param actId
	 * @return
	 */
	public boolean updateActivity(String actId,String userId)throws Exception{
		return false;
	}
	
	/**
	 * 根据活动ID获得活动信息
	 * @param actId
	 * @return
	 */
	
	
	@RequestMapping(value = "loadAct/{actId}",produces = "text/html;charset=UTF-8")
	@ResponseBody
	@GoJsonFilter(mixin=UserFilter.class,target=User.class)
	public Activity loadActByActId(@PathVariable int actId)throws Exception{
		Activity act = actService.loadActbyActId(actId);
		return act;
	}
	
	/**
	 * 根据活动ID获得所有参与活动的用户信息
	 * @param actId
	 * @return
	 */
	public List<User> loadJoinUserByActId(String actId) throws Exception{
		return actService.loadJoinUserByActId(actId);
	}
	
	@RequestMapping("addPage")
	public String addPage() throws Exception{
		return "act/addActPage";
	}
	
	@RequestMapping("showPage/{actId}")
	public ModelAndView showPage(@PathVariable int actId) throws Exception{
		ModelAndView mav = new ModelAndView();
		mav.addObject(actId);
		mav.setViewName("act/showActPage");
		return mav;
	}
}

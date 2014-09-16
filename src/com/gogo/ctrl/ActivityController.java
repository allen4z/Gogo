package com.gogo.ctrl;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.gogo.annotation.GoJsonFilter;
import com.gogo.dao.UserAndRoleDao;
import com.gogo.domain.Activity;
import com.gogo.domain.Place;
import com.gogo.domain.Role;
import com.gogo.domain.User;
import com.gogo.domain.filter.UserFilter;
import com.gogo.helper.CommonConstant;
import com.gogo.page.Page;
import com.gogo.service.ActivityService;


@Controller
@RequestMapping("/activity")
@SessionAttributes(CommonConstant.USER_CONTEXT)
public class ActivityController extends BaseController {

	@Autowired
	private ActivityService actService;
	
	@Autowired
	private UserAndRoleDao uarDao;
	/**
	 * 创建活动
	 * @param act
	 * @return
	 */
	@RequestMapping("saveAct")
	@ResponseBody
	public void saveActivity(@ModelAttribute(CommonConstant.USER_CONTEXT) User user ,@RequestBody Activity act){
		actService.saveActivity(act,user);
	}
	
	/**
	 * 根据ID删除活动
	 * @param actId
	 * @return
	 */
	@RequestMapping("deleteAct/{actId}")
	public void delActivity(@ModelAttribute(CommonConstant.USER_CONTEXT) User user , String actId) throws Exception{
		actService.deleteActivity(actId,user.getUserId());
	}
	
	/**
	 * 根据ID跟新活动
	 * @param actId
	 * @return
	 */
	public void updateActivity(@ModelAttribute(CommonConstant.USER_CONTEXT) User user,@RequestBody Activity act)throws Exception{
		actService.updateActivity(act, user.getUserId());
	}
	
	/**
	 * 根据活动ID获得活动信息
	 * @param actId
	 * @return
	 */
	
	
	@RequestMapping(value = "loadAct/{actId}",produces = "text/html;charset=UTF-8")
	@ResponseBody
	@GoJsonFilter(mixin=UserFilter.class,target=User.class)
	public Activity loadActByActId(@PathVariable String actId)throws Exception{
		Activity act = actService.loadActbyActId(actId);
		return act;
	}
	
	/**
	 * 查找附近所有的活动信息
	 * @param request
	 * @param place
	 * @return
	 */
	@RequestMapping(value = "loadActByPlace")
	@ResponseBody
//	@GoJsonFilter(mixin=UserFilter.class,target=User.class)
	public Page<Activity> loadActByPlace(HttpServletRequest request, 
			@RequestParam(required=false) Place place,
			@RequestParam(defaultValue="0",required=false) int pn){
		String remoteAddr =request.getRemoteAddr();
		Page<Activity> queryList =  actService.loadActByPlace(place,remoteAddr,pn,CommonConstant.PAGE_SIZE);
		
		return queryList;
	}
	
	@RequestMapping(value="upload",method=RequestMethod.POST)
	@ResponseBody
	public String uploadImage(@RequestParam MultipartFile file) throws Exception {
		if(!file.isEmpty()){
			file.transferTo(new File("d:/tmp/"+file.getOriginalFilename()));
			return "success";
		}
		return "faild";
	}
	
	@RequestMapping("addPage")
	public String addPage() throws Exception{
		return "act/addActPage";
	}
	
	@RequestMapping("allAct")
	public String allActPage() throws Exception{
		return "act/showAllPage";
	}
	
	@RequestMapping("showPage/{actId}")
	public ModelAndView showPage(@ModelAttribute(CommonConstant.USER_CONTEXT) User user,@PathVariable String actId) throws Exception{
		ModelAndView mav = new ModelAndView();
		Activity act = actService.loadActbyActId(actId);
		
		//根据User查询当前活动的角色
		List<Role> roles = uarDao.loadCurUserRole4Act(user, act);
		
		if(roles!= null && roles.size()>0){
			mav.addObject("role", roles.get(0));
		}
		
		mav.addObject("act", act);
		mav.setViewName("act/showActPage");
		return mav;
	}

}

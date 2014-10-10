package com.gogo.ctrl;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
import com.gogo.annotation.GoJsonFilters;
import com.gogo.dao.UserAndRoleDao;
import com.gogo.domain.Activity;
import com.gogo.domain.Place;
import com.gogo.domain.Role;
import com.gogo.domain.User;
import com.gogo.domain.filter.RoleFilter;
import com.gogo.domain.helper.RoleHelper;
import com.gogo.exception.Business4JsonException;
import com.gogo.helper.CommonConstant;
import com.gogo.page.Page;
import com.gogo.service.ActivityService;

/**
 * 活动控制类
 * @author allen
 *
 */
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
	public boolean saveActivity(@ModelAttribute(CommonConstant.USER_CONTEXT) User user ,@Valid @RequestBody Activity act,BindingResult result){
		
		//验证用户信息
		if(result.hasErrors()){
			List<ObjectError> errorList = result.getAllErrors();
			StringBuffer errMsg = new StringBuffer();
			for (ObjectError oe : errorList) {
				errMsg.append(oe.getDefaultMessage()+"\n");
			}
			throw new Business4JsonException(errMsg.toString());
		}
		
		actService.saveActivity(act,user);
		return true;
	}
	
	
	/**
	 * 加入活动小组
	 * @param user
	 * @return
	 */
	@RequestMapping("visitor/{actId}")
	@ResponseBody
	public boolean visitorActivity(@ModelAttribute(CommonConstant.USER_CONTEXT) User user,@PathVariable String actId){
		actService.saveActivity4RoleState(actId,user,RoleHelper.VISITOR_CODE);
		return true;
	}
	
	/**
	 * 参与活动
	 * @param user
	 * @return
	 */
	@RequestMapping("join/{actId}")
	@ResponseBody
	public boolean joinActivity(@ModelAttribute(CommonConstant.USER_CONTEXT) User user,@PathVariable String actId){
		actService.saveActivity4RoleState(actId,user,RoleHelper.JOIN_CODE);
		return true;
	}
	
	/**
	 * 观看活动
	 * @param user
	 * @return
	 */
	@RequestMapping("signup/{actId}")
	@ResponseBody
	public boolean signupActivity(@ModelAttribute(CommonConstant.USER_CONTEXT) User user,@PathVariable String actId){
		actService.saveActivity4RoleState(actId,user,RoleHelper.SIGNUP_CODE);
		return true;
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
	@RequestMapping(value = "loadActByActId/{actId}",produces = "text/html;charset=UTF-8")
	@ResponseBody
	@GoJsonFilters(values={	
			@GoJsonFilter(mixin=RoleFilter.class,target=Role.class)})
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
	@GoJsonFilters(values={	
			@GoJsonFilter(mixin=RoleFilter.class,target=Role.class)})
	public Page<Activity> loadActByPlace(
			HttpServletRequest request, 
			@RequestParam(required=false) Place place,
			@RequestParam(value="pn",required=false) Integer pn){
		
		String remoteAddr =request.getRemoteAddr();
		
		User user = getSessionUser(request.getSession());
		//如果用户不为空，需要去掉用户创建的活动
		Page<Activity> queryList =  actService.loadActByPlace(user,place,remoteAddr,pn,CommonConstant.PAGE_SIZE);
		
		return queryList;
		
	}
	
	/**
	 * 活动LOGO图片上传
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="upload",method=RequestMethod.POST)
	@ResponseBody
	public String uploadImage(@RequestParam MultipartFile file) throws Exception {
		if(!file.isEmpty()){
			file.transferTo(new File("d:/tmp/"+file.getOriginalFilename()));
			return "success";
		}
		return "faild";
	}
	

	
	/**
	 * 进入活动信息页
	 * @param user
	 * @param actId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("toShowActPage/{state}/{actId}")
	public ModelAndView toShowActPage(@PathVariable String state,@PathVariable String actId) throws Exception{
		ModelAndView mav = new ModelAndView();
		//Activity act = actService.loadActbyActId(actId);
		
		//根据User查询当前活动的角色
		//List<Role> roles = uarDao.loadCurUserRole4Act(user, act);
		
		/*if(roles!= null && roles.size()>0){
			mav.addObject("role", roles.get(0));
		}*/
		mav.addObject("state", state);
		mav.addObject("actId", actId);
		mav.setViewName("act/showActPage");
		return mav;
	}
	
	/**
	 * 进入新增活动页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("toAddActPage")
	public String toAddActPage() throws Exception{
		return "act/addActPage";
	}
	
	/**
	 * 进入附近活动页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("toShowAllPage")
	public String toShowAllPage() throws Exception{
		return "act/showAllActPage";
	}
}

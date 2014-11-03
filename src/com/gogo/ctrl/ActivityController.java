package com.gogo.ctrl;

import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.gogo.annotation.Token;
import com.gogo.dao.UserAndGroupDao;
import com.gogo.domain.Activity;
import com.gogo.domain.Place;
import com.gogo.domain.User;
import com.gogo.domain.enums.UserAndActState;
import com.gogo.exception.Business4JsonException;
import com.gogo.helper.CommonConstant;
import com.gogo.page.Page;
import com.gogo.service.ActivityService;
import com.gogo.service.InviteService;

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
	private UserAndGroupDao uarDao;
	@Autowired
	private InviteService inviteService;
	/**
	 * 创建活动
	 * @param act
	 * @return
	 * @throws Exception 
	 */
	@Token(remove=true)
	@RequestMapping("saveAct")
	@ResponseBody
	public boolean saveActivity(@ModelAttribute(CommonConstant.USER_CONTEXT) User user ,@Valid @RequestBody Activity act,BindingResult result) throws Exception{
		
		//验证用户信息
		if(result.hasErrors()){
			List<ObjectError> errorList = result.getAllErrors();
			StringBuffer errMsg = new StringBuffer();
			for (ObjectError oe : errorList) {
				errMsg.append(oe.getDefaultMessage()+"\n");
			}
			throw new Business4JsonException(errMsg.toString());
		}
		
		if(checkActInfo(act)){
			actService.saveActivity(act,user);
		}
		
		return true;
	}
	
	/**
	 * 验证活动信息是否正确
	 * @param act
	 * @return
	 */
	private boolean checkActInfo(Activity act) throws Exception {
		
		
		if(act.getMinJoin()>act.getMaxJoin()){
			throw new Business4JsonException("act_savecheck_minjoin_morethen_maxjoin","min join people more then max join people!");
		}
		Date signDate =act.getSignTime();
		Date startDate = act.getStartTime();
		Date endDate =act.getEndTime();
		
		if(startDate.compareTo(signDate)<=0){
			throw new Business4JsonException("act_savecheck_startdate_earlythen_signupDate","start date early then signup date");
		}
		
		if(endDate.compareTo(signDate) <=0){
			throw new Business4JsonException("act_savecheck_enddate_earlythen_signupDate","end date early then signup date");
		}
		
		if(endDate.compareTo(startDate) <=0){
			throw new Business4JsonException("act_savecheck_enddate_earlythen_startDate","end date early then start date");
		}
		
		return true;
	}


	
	

	/**
	 * 参与活动  如果人满，则自动排队
	 * @param user
	 * @return
	 */
	@RequestMapping("join/{actId}")
	@ResponseBody
	public boolean joinActivity(@ModelAttribute(CommonConstant.USER_CONTEXT) User user,@PathVariable String actId){
		UserAndActState result = actService.saveUserJoinActivity(user.getId(),actId);
		if(result ==  UserAndActState.QUEUE){
			throw new Business4JsonException("act_join_full","Participate in the activity of the enrollment is full");
		}
		return true;
	}
	
	/**
	 * 取消排队或报名
	 * @param user
	 * @param actId
	 * @return
	 */
	@RequestMapping("cancelJoin/{actId}")
	@ResponseBody
	public boolean cancelJoinActivity(@ModelAttribute(CommonConstant.USER_CONTEXT) User user,@PathVariable String actId){
		actService.updateUserJoinActivity(actId,user);
		return true;
	}
	
	/**
	 * 根据ID跟新活动
	 * @param actId
	 * @return
	 */
	public void updateActivity(@ModelAttribute(CommonConstant.USER_CONTEXT) User user,@RequestBody Activity act)throws Exception{
		actService.updateActivity(act, user.getId());
	}
	
	/**
	 * 根据活动ID获得活动信息
	 * @param actId
	 * @return
	 */
	@RequestMapping(value = "loadActByActId/{actId}")
	@ResponseBody
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
	 * 查询活动相关的所有人员
	 * @param actId
	 * @return
	 */
	@RequestMapping(value = "loadAllUserFromAct/{actId}")
	@ResponseBody
	public Page<User> loadAllUserFromAct(@PathVariable String actId,@RequestParam int pn){
		 Page<User> page = actService.loadAllUserFromAct(actId,pn,CommonConstant.PAGE_SIZE);
		 return page;
	}
	/**
	 * 查询参加活动的用户
	 * @param actId
	 * @param curPage
	 * @return
	 */
	@RequestMapping(value = "loadJoinUserFromAct/{actId}")
	@ResponseBody
	public Page<User> loadJoinUserFromAct(@PathVariable String actId,@RequestParam int pn){
		return actService.loadSpecialUserFromAct(actId,pn,CommonConstant.PAGE_SIZE, UserAndActState.JOIN);
	}
	
	@RequestMapping(value = "loadQueueUserFromAct/{actId}")
	@ResponseBody
	public Page<User> loadQueueUserFromAct(@PathVariable String actId,@RequestParam int pn){
		return actService.loadSpecialUserFromAct(actId,pn,CommonConstant.PAGE_SIZE, UserAndActState.QUEUE);
	}
	
	
	
	@RequestMapping(value = "inviteJoinAct/{friendId}/{actId}")
	@ResponseBody
	public boolean InviteJoinGroup(@ModelAttribute(CommonConstant.USER_CONTEXT)User user,
			@PathVariable String friendId,
			@PathVariable String actId){
		inviteService.saveInviteJoinAct(user, friendId, actId);
		return true;
	}
	
	
	/**
	 * 进入活动信息页
	 * @param user
	 * @param actId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("toShowActPage/{actId}")
	public ModelAndView toShowActPage(HttpServletRequest request,@PathVariable String actId) throws Exception{
		ModelAndView mav = new ModelAndView();
		
		User user = getSessionUser(request.getSession());
		
		UserAndActState uarState =UserAndActState.CANCEL;
		
		if(user != null){
			uarState = actService.loadCurUserStateInAct(user.getId(),actId);
		}
		
		mav.addObject("uarState", uarState);
		mav.addObject("actId", actId);
		mav.setViewName("act/showActPage");
		return mav;
	}
	
	/**
	 * 进入新增活动页面
	 * @return
	 * @throws Exception
	 */
	@Token(save=true)
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
	
	/**
	 * 跳转到活动所有用户展示页
	 * @param actId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("showActAllUserPage/{actId}")
	public ModelAndView toShowActAllUserPage(@PathVariable String actId) throws Exception{
		ModelAndView mav = new ModelAndView();
		mav.addObject("actId", actId);
		mav.setViewName("act/user/showActAllUserPage");
		return mav;
	}
	
	/**
	 * 跳转到特殊用户展示页（参加活动的用户，排队的用户）
	 * @param state
	 * @param actId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("showSpecialActUserPage/{state}/{actId}")
	public ModelAndView showSpecialActUserPage(@PathVariable UserAndActState state,@PathVariable String actId) throws Exception{
		ModelAndView mav = new ModelAndView();
		mav.addObject("actId", actId);
		mav.addObject("state",state);
		mav.setViewName("act/user/showSpecialActUserPage");
		return mav;
	}
}

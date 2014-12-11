package com.gogo.ctrl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
	@RequestMapping("saveAct")
	@ResponseBody
	public Activity saveActivity(HttpServletRequest request,
			@Valid @RequestBody Activity act,
			BindingResult result) throws Exception{
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
			act.setOpen(true);
			String tokenId = getUserToken(request);
			actService.saveActivity(act,tokenId);
		}
		
		return act;
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

		if(startDate.compareTo(signDate)<=0){
			throw new Business4JsonException("act_savecheck_startdate_earlythen_signupDate","start date early then signup date");
		}
		act.setSignTime(signDate);
		act.setStartTime(startDate);
		return true;
	}


	
	

	/**
	 * 参与活动  如果人满，则自动排队
	 * @param user
	 * @return
	 */
	@RequestMapping("join/{actId}")
	@ResponseBody
	public boolean joinActivity(HttpServletRequest request,@PathVariable String actId){
		String tokenId = getUserToken(request);
		UserAndActState result = actService.saveUserJoinActivity(tokenId,actId);
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
	public boolean cancelJoinActivity(HttpServletRequest request,@PathVariable String actId){
		String tokenId = getUserToken(request);
		actService.updateUserJoinActivity(actId,tokenId);
		return true;
	}
	
	/**
	 * 根据ID跟新活动
	 * @param actId
	 * @return
	 */
	public void updateActivity(HttpServletRequest request,@RequestBody Activity act)throws Exception{
		String tokenId = getUserToken(request);
		actService.updateActivity(act, tokenId);
	}
	
	/**
	 * 根据活动ID获得活动信息
	 * @param actId
	 * @return
	 */
	@RequestMapping(value = "loadActByActId/{actId}",method=RequestMethod.GET)
	@ResponseBody
	public Activity loadActByActId(
			@PathVariable String actId,
			@RequestParam(value="access_token") String tokenId)throws Exception{
		Activity act = actService.loadActbyActId(actId);
		return act;
	}
	
	
	
	/**
	 * 根据地点信息查询附近活动
	 * @param request
	 * @param longitude 经度
	 * @param latitude 纬度
	 * @param pn 页数
	 * @return
	 */
	@RequestMapping(value = "loadActByPlace",method=RequestMethod.GET)
	@ResponseBody
//	@GoJsonFilter(mixin=UserFilter.class,target=User.class)
	public Page<Activity> loadActByPlace(
			HttpServletRequest request, 
			@RequestParam(required=false) Double longitude,
			@RequestParam(required=false) Double latitude,
			@RequestParam(value="pn",required=false) Integer pn,
			@RequestParam(value="access_token") String tokenId){
		
		String remoteAddr =request.getRemoteAddr();
		Place place = null;
		//根据经纬度组装数据模型
		if(longitude != null && latitude !=null){
			place = new Place();
			place.setLongitude(longitude);
			place.setLongitude(longitude);
		}
	
		//如果用户不为空，需要去掉用户创建的活动
		Page<Activity> queryList =  actService.loadActByPlace(place,remoteAddr,pn,CommonConstant.PAGE_SIZE);
		
		return queryList;
		
	}
	
	/**
	 * 查询活动相关的所有人员
	 * @param actId
	 * @return
	 */
	@RequestMapping(value = "loadAllUserFromAct/{actId}",method=RequestMethod.GET)
	@ResponseBody
	public Page<User> loadAllUserFromAct(
			@PathVariable String actId,
			@RequestParam int pn,
			@RequestParam(value="access_token") String tokenId){
		 Page<User> page = actService.loadAllUserFromAct(actId,pn,CommonConstant.PAGE_SIZE);
		 return page;
	}
	/**
	 * 查询参加活动的用户
	 * @param actId
	 * @param curPage
	 * @return
	 */
	@RequestMapping(value = "loadJoinUserFromAct/{actId}",method=RequestMethod.GET)
	@ResponseBody
	public Page<User> loadJoinUserFromAct(
			@PathVariable String actId,
			@RequestParam int pn,
			@RequestParam(value="access_token") String tokenId){
		return actService.loadSpecialUserFromAct(actId,pn,CommonConstant.PAGE_SIZE, UserAndActState.JOIN);
	}
	
	@RequestMapping(value = "loadQueueUserFromAct/{actId}",method=RequestMethod.GET)
	@ResponseBody
	public Page<User> loadQueueUserFromAct(
			@PathVariable String actId,
			@RequestParam int pn,
			@RequestParam(value="access_token") String tokenId){
		return actService.loadSpecialUserFromAct(actId,pn,CommonConstant.PAGE_SIZE, UserAndActState.QUEUE);
	}
	
	
	
	@RequestMapping(value = "inviteJoinAct/{friendId}/{actId}")
	@ResponseBody
	public boolean InviteJoinGroup(HttpServletRequest request,
			@PathVariable String friendId,
			@PathVariable String actId){
		String tokenId = getUserToken(request);
		inviteService.saveInviteJoinAct(tokenId, friendId, actId);
		return true;
	}
	
	
	/**
	 * 进入活动信息页
	 * @param user
	 * @param actId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("toShowActPage")
	public ModelAndView toShowActPage(HttpServletRequest request,@RequestParam String actId) throws Exception{
		ModelAndView mav = new ModelAndView();
		String token = getUserToken(request);
		UserAndActState uarState =UserAndActState.CANCEL;
		if(token != null){
			uarState = actService.loadCurUserStateInAct(token,actId);
		}
		mav.addObject("tokenId",token);
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
	public ModelAndView toAddActPage(@RequestParam String access_token) throws Exception{
		ModelAndView mav = new ModelAndView();
		mav.addObject("tokenId", access_token);
		mav.setViewName("act/addActPage");
		return mav;
	}
	
	/**
	 * 进入附近活动页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("toShowAllPage")
	public ModelAndView toShowAllPage(@RequestParam String access_token) throws Exception{
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("tokenId", access_token);
		mav.setViewName("act/showAllActPage");
		
		return mav;
	}
	
	/**
	 * 跳转到活动所有用户展示页
	 * @param actId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("toShowActAllUserPage")
	public ModelAndView toShowActAllUserPage(@RequestParam String actId) throws Exception{
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
	@RequestMapping("toShowSpecialActUserPage")
	public ModelAndView showSpecialActUserPage(@RequestParam UserAndActState state,@RequestParam String actId) throws Exception{
		ModelAndView mav = new ModelAndView();
		mav.addObject("actId", actId);
		mav.addObject("state",state);
		mav.setViewName("act/user/showSpecialActUserPage");
		return mav;
	}
}

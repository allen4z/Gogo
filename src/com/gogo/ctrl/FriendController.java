package com.gogo.ctrl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.gogo.domain.Place;
import com.gogo.domain.User;
import com.gogo.helper.CommonConstant;
import com.gogo.page.Page;
import com.gogo.service.FriendService;


@Controller
@RequestMapping("/friend")
@SessionAttributes(CommonConstant.USER_CONTEXT)
public class FriendController extends BaseController {

	@Autowired
	private FriendService friendService;
	
	
	/**
	 * 好友申请
	 * @param user
	 * @param friendId
	 * @return
	 */
	@RequestMapping(value="friendRequest/{friendId}")
	@ResponseBody
	public boolean friendRequest(@ModelAttribute(CommonConstant.USER_CONTEXT) User user,@PathVariable String friendId){
		friendService.saveFriendRequest(user, friendId);
		return true;
	}
	
	@RequestMapping(value="agreeApply/{friendId}")
	@ResponseBody
	public boolean agreeApply(@ModelAttribute(CommonConstant.USER_CONTEXT) User user,@PathVariable String friendId){
		friendService.saveAgreeApply(user, friendId);
		return true;
	}
	
	/**
	 * 查询附近的人
	 * @param request
	 * @param place
	 * @param pn
	 * @return
	 */
	@RequestMapping(value = "loadFriendByPlace")
	@ResponseBody
	public Page<User> loadPersonByPlace(
			HttpServletRequest request, 
			@RequestParam(required=false) Place place,
			@RequestParam(value="pn",required=false) Integer pn){
		
		String remoteAddr =request.getRemoteAddr();
		
		User user = getSessionUser(request.getSession());
		//如果用户不为空，需要去掉用户创建的活动
		Page<User> queryList =  friendService.loadPersonByPlace(user,place,remoteAddr,pn,CommonConstant.PAGE_SIZE);
		
		return queryList;
		
	}
	
	
	@RequestMapping("toFriendPage")
	public String toFriendPage() throws Exception{
		return "friend/showAllPersonPage";
	}
	
}
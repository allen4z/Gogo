package com.gogo.ctrl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gogo.annotation.GoJsonFilter;
import com.gogo.domain.Place;
import com.gogo.domain.User;
import com.gogo.domain.filter.UserFilter;
import com.gogo.helper.CommonConstant;
import com.gogo.page.Page;
import com.gogo.service.FriendService;


@Controller
@RequestMapping("/friend")
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
	public boolean friendRequest(HttpServletRequest request,@PathVariable String friendId){
		
		String tokenId = getUserToken(request);
		
		friendService.saveFriendRequest(tokenId, friendId);
		return true;
	}
	
	@RequestMapping(value="agreeApply/{friendId}")
	@ResponseBody
	public boolean agreeApply(HttpServletRequest request,@PathVariable String friendId){
		String tokenId = getUserToken(request);
		friendService.saveAgreeApply(tokenId, friendId);
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
	@GoJsonFilter(mixin=UserFilter.class,target=User.class)
	public Page<User> loadPersonByPlace(
			HttpServletRequest request, 
			@RequestParam(required=false) Place place,
			@RequestParam(value="pn",required=false) Integer pn){
		
		String remoteAddr =request.getRemoteAddr();
		String token = getUserToken(request);
		Page<User> queryList =  friendService.loadPersonByPlace(token,place,remoteAddr,pn,CommonConstant.PAGE_SIZE);
		
		return queryList;
		
	}
	
	@RequestMapping("toShowFriendPage")
	public String toFriendPage() throws Exception{
		return "friend/showAllPersonPage";
	}
	
}

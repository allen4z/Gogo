package com.gogo.ctrl;

import java.util.List;

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
import org.springframework.web.servlet.ModelAndView;

import com.gogo.domain.Activity;
import com.gogo.domain.Group;
import com.gogo.domain.GroupApplyInfo;
import com.gogo.domain.User;
import com.gogo.exception.Business4JsonException;
import com.gogo.helper.CommonConstant;
import com.gogo.page.Page;
import com.gogo.service.FriendService;
import com.gogo.service.GroupService;
import com.gogo.service.UserService;

/**
 * 用户管理控制器
 * @author allen
 *
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
	

	@Autowired
	private UserService userService;
	
	@Autowired
	private FriendService friendService;
	
	@Autowired
	private GroupService groupService;
	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	@RequestMapping("doRegister")
	@ResponseBody
	public boolean registerUser(@Valid @RequestBody User user,BindingResult result){
		
		//验证用户信息
		if(result.hasErrors()){
			List<ObjectError> errorList = result.getAllErrors();
			StringBuffer errMsg = new StringBuffer();
			for (ObjectError oe : errorList) {
				errMsg.append(oe.getDefaultMessage()+"\n");
			}
			throw new Business4JsonException(errMsg.toString());
		}
		userService.saveUser(user);
		return true;
	}
	
	/**
	 * 根据用户ID更新用户
	 * @param UserId
	 * @return
	 */
	@RequestMapping("update/{userId}")
	public boolean UpdateUser(@RequestBody User user,@PathVariable String UserId){
		return false;
	}
	
	/**
	 * 根据用户ID获得用户信息
	 * @param userId
	 * @return
	 */
	@RequestMapping("load/{userId}")
	@ResponseBody
	public User loadUserByUserId(@PathVariable int userId){
		return userService.loadUserById(userId);
	}
	
	
	/**
	 * 根据用户Name获得用户信息
	 * @param userName
	 * @param curPage
	 * @return
	 */
	@RequestMapping(value="loadByName/{userName}",method=RequestMethod.GET)
	@ResponseBody
	public List<User> loadUserByUserName(@PathVariable String userName,
			@RequestParam(required=false) Integer curPage,
			@RequestParam(required=false) Integer pagesize){
		
		List<User> users = null;
		if(curPage == null || curPage == 0){
			users =  userService.loadUserByName(userName);
		}else{
			users =  userService.loadUserByName(userName,curPage,getPageSize(pagesize));
		}
		return users;
	}
	
	/**
	 * 根据用户ID获得用户参加的所有活动信息
	 * @param userId
	 * @return
	 */
	/*@RequestMapping("loadJoinAct/{userId}")
	@ResponseBody
	public List<Activity> loadJoinActivitesByUser(int userId){
		return userService.loadJoinActivitesByUser(userId);
	}*/
	
	/**
	 * 根据用户ID获得用户拥有的所有活动信息
	 * @param userId
	 * @return
	 */
	@RequestMapping("loadOwnAct/{userId}")
	@ResponseBody
	public Page<Activity> loadOwnActivitesByUser(String userId,@RequestParam(defaultValue="0",required=false) int pn){
		return userService.loadOwnActivitesByUser(userId,pn,CommonConstant.PAGE_SIZE);
	}
	
	
	/**
	 * 根据用户ID删除用户
	 * @param UserId
	 * @return
	 */
	@RequestMapping("del/{userId}")
	public boolean delUser(String UserId){
		return false;
	}
	
	/**
	 * 返回用户主页
	 * @return
	 */
	@RequestMapping(value="main")
	public ModelAndView backUserMain(
			@ModelAttribute(CommonConstant.USER_CONTEXT) User user,
			@RequestParam(value="pn",defaultValue="0",required=false) int currPage) throws Exception{
		ModelAndView mav = new ModelAndView();
		String userId = user.getId();
		//查询拥有活动
		Page<Activity> ownAct = userService.loadOwnActivitesByUser(userId,currPage,CommonConstant.PAGE_SIZE);
		//查询相关活动
		Page<Activity> joinAct = userService.loadJoinActivitesByUser(userId,currPage,CommonConstant.PAGE_SIZE);
		//查询需要支付信息
		List<String> payInfo = userService.loadPayInfo(userId);	
		//查询请求列表
		List<User> requestFriend = friendService.loadFriendRequestList(userId);
		//查询好友列表
		List<User> friends = friendService.loadFriends(userId);
		
		//小组信息
		Page<Group> myGroup = groupService.loadGroup4User(user, currPage, CommonConstant.PAGE_SIZE);
		
		//申请加群信息
		List<GroupApplyInfo> groupApplys = groupService.loadAllApplyInfo(user);
		
		mav.addObject("page",ownAct );
		mav.addObject("joinpage",joinAct );
		mav.addObject("payinfo",payInfo );
		mav.addObject("friends", friends);
		mav.addObject("requestFriend", requestFriend);
		mav.addObject("myGroup", myGroup);
		mav.addObject("groupApplys", groupApplys);
		
		mav.setViewName("main");
		
		return mav;
	}
	
}

package com.gogo.ctrl;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gogo.domain.Group;
import com.gogo.domain.GroupApplyInfo;
import com.gogo.domain.Invite;
import com.gogo.domain.Place;
import com.gogo.domain.enums.InviteType;
import com.gogo.exception.Business4JsonException;
import com.gogo.helper.CommonConstant;
import com.gogo.page.Page;
import com.gogo.service.GroupService;
import com.gogo.service.InviteService;

@Controller
@RequestMapping("/group")
public class GroupController extends BaseController {

	@Autowired
	private GroupService groupService;
	@Autowired
	private InviteService inviteService;
	
	
	/**
	 * 保存活动小组信息
	 * @param user
	 * @param group
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("saveGroup")
	@ResponseBody		
	public boolean saveGroup(HttpServletRequest request, @Valid @RequestBody Group group,BindingResult result) throws Exception{
		//验证用户信息
		if(result.hasErrors()){
			List<ObjectError> errorList = result.getAllErrors();
			StringBuffer errMsg = new StringBuffer();
			for (ObjectError oe : errorList) {
				errMsg.append(oe.getDefaultMessage()+"\n");
			}
			throw new Business4JsonException(errMsg.toString());
		}
		
		groupService.saveGroup(group,getUserToken(request));
			
		return true;
	}
	
	/**
	 * 用户申请加入活动小组
	 * @param user
	 * @param groupId
	 * @return
	 */
	@RequestMapping("applyJoin/{groupId}")
	@ResponseBody
	public boolean applyJoinGroup(HttpServletRequest request,@PathVariable String groupId){
		groupService.saveApplyJoinGroup(getUserToken(request),groupId);
		return true;
	}
	
	/**
	 * 查看所有申请信息
	 * @param user
	 * @return
	 */
	public List<GroupApplyInfo> loadAllApplyInfo(HttpServletRequest request,String groupId){
		return groupService.loadAllApplyInfo(getUserToken(request));
	}
	
	/**
	 * 通过用户加入活动小组
	 * @param user
	 * @param groupId
	 * @return
	 */
	@RequestMapping("passApply/{groupApplyId}")
	@ResponseBody
	public boolean passApply(HttpServletRequest request,@PathVariable String groupApplyId){
		groupService.savePassApply(getUserToken(request),groupApplyId);
		return true;
	}
	
	
	/**
	 * 更新用户权限
	 * @param user
	 * @param groupId
	 * @param authority 
	 * @return
	 */
	@RequestMapping("updateAuth/{authority}/{groupId}/{userId}")
	@ResponseBody
	public boolean updateUserAuthority(HttpServletRequest request,
			@PathVariable String groupId,
			@PathVariable String userId,
			@PathVariable int authority){
		groupService.updateUserAuthority(getUserToken(request),groupId,userId,authority);
		return true;
	}
	
	
	/**
	 * 用户退出小组
	 * @param user
	 * @param friendId
	 * @param groupId
	 * @return
	 */
	@RequestMapping(value = "inviteJoinGroup/{friendId}/{groupId}")
	@ResponseBody
	public boolean InviteJoinGroup(HttpServletRequest request,
			@PathVariable String friendId,
			@PathVariable String groupId){
		inviteService.saveInviteJoinGroup(getUserToken(request), friendId, groupId);
		return true;
	}
	
	@RequestMapping(value = "quitGroup/{groupId}")
	@ResponseBody
	public boolean quitGroup(HttpServletRequest request,@PathVariable String groupId){
		groupService.updateQuitGroup(getUserToken(request),groupId);
		return true;
	}
	
	/**
	 * 获得所有的小组邀请信息
	 * @param user
	 * @param pn
	 * @return
	 */
	public Page<Invite> loadAllGroupInvite(HttpServletRequest request,int pn){
		return inviteService.loadAllInvite(getUserToken(request),InviteType.GROUP,pn,CommonConstant.PAGE_SIZE);
	}
	
	public boolean passInviteGroup(HttpServletRequest request,
			@PathVariable String inviteId){
		groupService.savePassInviteGroup(getUserToken(request),inviteId);
		return true;
	}
	
	/**
	 * 获取附近所有活动小组
	 * @param currPage
	 * @return
	 */
	@RequestMapping("loadAllGroup")
	@ResponseBody
	public Page<Group> loadAllGroup(HttpServletRequest request, 
			@RequestParam(required=false) Place place,
			@RequestParam(value="pn",required=false) Integer pn){
		String remoteAddr =request.getRemoteAddr();
		return groupService.loadAllGroup(place,remoteAddr,pn, CommonConstant.PAGE_SIZE);
	}
	
	
	
	@RequestMapping("loadGroupById/{groupId}")
	@ResponseBody
	public Group loadGroupById(@PathVariable String groupId){
		Group group = groupService.loadGroupById(groupId);
		return group;
	}
	
	/**
	 * 进入新增小组页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("toAddGroupPage")
	public String toAddGroupPage() throws Exception{
		return "group/addGroupPage";
		
	}
	
	@RequestMapping("toShowAllPage")
	public String toShowAllPage() throws Exception{
		return "group/showAllGroupPage";
		
	}
	
	@RequestMapping("toShowGroupPage")
	public ModelAndView toAddActPage(@RequestParam String groupId) throws Exception{
		ModelAndView mav = new ModelAndView();
		mav.addObject("groupId", groupId);
		mav.setViewName("group/showGroupPage");
		return mav;
		
	}
}

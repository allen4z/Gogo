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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.gogo.domain.Group;
import com.gogo.domain.User;
import com.gogo.exception.Business4JsonException;
import com.gogo.helper.CommonConstant;
import com.gogo.service.GroupService;

@Controller
@RequestMapping("/group")
@SessionAttributes(CommonConstant.USER_CONTEXT)
public class GroupController {

	@Autowired
	private GroupService groupService;
	
	
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
	public boolean saveGroup(@ModelAttribute(CommonConstant.USER_CONTEXT) User user ,@Valid @RequestBody Group group,BindingResult result) throws Exception{
		//验证用户信息
		if(result.hasErrors()){
			List<ObjectError> errorList = result.getAllErrors();
			StringBuffer errMsg = new StringBuffer();
			for (ObjectError oe : errorList) {
				errMsg.append(oe.getDefaultMessage()+"\n");
			}
			throw new Business4JsonException(errMsg.toString());
		}
				
			
		groupService.saveGroup(group,user);
			
		return true;
	}
	
	/**
	 * 用户加入活动小组
	 * @param user
	 * @param groupId
	 * @return
	 */
	@RequestMapping("join/{groupId}")
	@ResponseBody
	public boolean joinGroup(@ModelAttribute(CommonConstant.USER_CONTEXT) User user,@PathVariable String groupId){
		groupService.saveUserJoinGroup(user,groupId);
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
	public boolean updateUserAuthority(@ModelAttribute(CommonConstant.USER_CONTEXT) User user,
			@PathVariable String groupId,
			@PathVariable String userId,
			@PathVariable int authority){
		groupService.updateUserAuthority(user,groupId,userId,authority);
		return true;
	}
}

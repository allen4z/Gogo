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

import com.gogo.domain.Group;
import com.gogo.domain.User;
import com.gogo.domain.helper.RoleHelper;
import com.gogo.exception.Business4JsonException;
import com.gogo.helper.CommonConstant;
import com.gogo.service.GroupService;

@Controller
public class GroupController {

	@Autowired
	private GroupService groupService;
	
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
				
			if(checkGroupInfo(group)){
				groupService.saveGroup(group,user);
			}
			return true;
	}
	
	@RequestMapping("join/{actId}")
	@ResponseBody
	public boolean joinActivity(@ModelAttribute(CommonConstant.USER_CONTEXT) User user,@PathVariable String groupId){
		int result = groupService.updateAddGroup4UARState(groupId,user,RoleHelper.UAR_JOIN_ACTIVITY);
		if(result ==  RoleHelper.JOIN_QUEUE){
			throw new Business4JsonException("act_join_full","Participate in the activity of the enrollment is full");
		}
		
		return true;
	}
	
	
	private boolean checkGroupInfo(Group group){
		return true;
	}
}

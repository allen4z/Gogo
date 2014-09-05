package com.gogo.ctrl;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.gogo.annotation.GoJsonFilter;
import com.gogo.ctrl.ActivityController;
import com.gogo.domain.Activity;
import com.gogo.domain.User;
import com.gogo.domain.filter.UserFilter;
import com.gogo.service.ActivityService;


@Controller
@RequestMapping("/activity")
public class ActivityController extends BaseController {

	@Autowired
	private ActivityService actService;
	
	/**
	 * 创建活动
	 * @param act
	 * @return
	 */
	@RequestMapping("saveAct")
	@ResponseBody
	public int saveActivity(HttpServletRequest req ,@RequestBody Activity act){
		User user = getSessionUser(req);
		int id = actService.saveActivity(act,user);
		return id;
	}
	
	/**
	 * 根据ID删除活动
	 * @param actId
	 * @return
	 */
	@RequestMapping("deleteAct/{actId}")
	public void delActivity(HttpServletRequest req , int actId) throws Exception{
		User user = getSessionUser(req);
		
		actService.deleteActivity(actId,user.getUserId());
	}
	
	/**
	 * 根据ID跟新活动
	 * @param actId
	 * @return
	 */
	public void updateActivity(HttpServletRequest req,@RequestBody Activity act)throws Exception{
		User user = getSessionUser(req);
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
	public Activity loadActByActId(@PathVariable int actId)throws Exception{
		Activity act = actService.loadActbyActId(actId);
		return act;
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
	
	@RequestMapping("showPage/{actId}")
	public ModelAndView showPage(@PathVariable int actId) throws Exception{
		ModelAndView mav = new ModelAndView();
		mav.addObject(actId);
		mav.setViewName("act/showActPage");
		return mav;
	}


}

package com.gogo.ctrl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gogo.domain.Group;
import com.gogo.domain.MatchList;
import com.gogo.domain.enums.GroupMatchState;
import com.gogo.service.GroupService;
import com.gogo.service.MatchesService;


@Controller
@RequestMapping("/matches")
public class MatchesController extends BaseController {
	
	@Autowired
	private MatchesService matchesService;
	@Autowired
	private GroupService groupService;
	
	/**
	 * 约赛
	 * @param request
	 * @param match
	 * @return
	 */
	@RequestMapping("saveMatches")
	@ResponseBody
	public MatchList saveMatches(HttpServletRequest request,
			@RequestBody MatchList match){
		String tokenId = getUserToken(request);
		return matchesService.saveMatches(tokenId,match);
	}
	
	/**
	 * 通过受邀比赛
	 * @param request
	 * @param matchListId
	 * @return
	 */
	public boolean passInviteMatch(HttpServletRequest request,
			String matchListId){
		String tokenId = getUserToken(request);
		matchesService.savePassInviteMatch(tokenId,matchListId);
		return true;
	}
	
	/**
	 * 更新比赛比分
	 * @param request
	 * @param matchList
	 * @return
	 */
	public MatchList updateMatchScore(HttpServletRequest request,
			MatchList matchList){
		String tokenId = getUserToken(request);
		matchesService.updateMatchScore(tokenId,matchList);
		return matchList;
	}
	
	/**
	 * 根据ID获取已结束比赛
	 * @param tokenId
	 * @param matchListId
	 * @return
	 */
	public MatchList loadMatchList(String tokenId,String matchListId){
		return matchesService.loadMatchById(tokenId,matchListId, GroupMatchState.Done);
	}
	
	/**
	 * 查询受约比赛列表
	 * @param tokenId
	 * @return
	 */
	public List<MatchList> loadWaitMatchByUser(String tokenId){
		return matchesService.loadInviteMatchByUser(tokenId,GroupMatchState.Wait);
	}
	
	@RequestMapping("toAddInviteMatches/{groupId}")
	public ModelAndView toShowInviteMatches(@RequestParam(value="access_token") String tokenId,
			@PathVariable String groupId){
		
		Group group = groupService.loadGroupById(groupId);
		ModelAndView mav = new ModelAndView();
		mav.addObject("otherGroup", group);
		mav.addObject("tokenId", tokenId);
		mav.setViewName("matches/addInviteMatches");
		return mav;
	}
	
}

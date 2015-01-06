package com.gogo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gogo.dao.GroupDao;
import com.gogo.dao.MatchesDao;
import com.gogo.dao.PlaceDao;
import com.gogo.dao.UserAndGroupDao;
import com.gogo.domain.Group;
import com.gogo.domain.MatchList;
import com.gogo.domain.Place;
import com.gogo.domain.User;
import com.gogo.domain.UserAndGroup;
import com.gogo.domain.enums.GroupMatchState;
import com.gogo.domain.enums.MatchType;
import com.gogo.domain.enums.UserAndGroupState;
import com.gogo.domain.helper.RoleHelper;
import com.gogo.exception.Business4JsonException;

@Service
public class MatchesService extends BaseService {

	@Autowired
	private UserAndGroupDao userAndGroupDao;
	@Autowired
	private GroupDao groupDao;
	@Autowired
	private PlaceDao placeDao;
	@Autowired
	private MatchesDao matchesDao;
	
	/**
	 * 保存前台传入的比赛信息 （约赛）
	 * @param tokenId
	 * @param match
	 * @return
	 */
	public MatchList saveMatches(String tokenId, MatchList match) {
		User user = getUserbyToken(tokenId);
	 	UserAndGroup uags = userAndGroupDao.loadByUser(user.getId());
	 	if(uags == null){
	 		throw new Business4JsonException("您没有加入任何球队！");
	 	}
 		Group group = uags.getGroup();
 		int authState = uags.getAuthorityState();
 		boolean hasAuth = RoleHelper.judgeState(authState, RoleHelper.FOUR_AUTHORITY_EXPEL);
 		//TODO 现阶段只有队长和用户，所以只对第一个有权限的队伍进行约赛
 		if(hasAuth){
 			match.setBelongGroup(group);
	 		Group otherGroup = groupDao.get(match.getOtherGroup().getId());
	 		match.setOtherGroup(otherGroup);
	 		
	 		Place dbPlaceInfo = placeDao.findPlaceByNameAndLocal(match.getMatchPlace());
	 		if(dbPlaceInfo!=null){
	 			match.setMatchPlace(dbPlaceInfo);
	 		}
	 		//友谊赛
	 		match.setType(MatchType.Friendly);
	 		match.setState(GroupMatchState.Wait);
	 		matchesDao.save(match);
		}

	 	return match;
	}
	
	/**
	 * 通过受约申请
	 * @param tokenId
	 * @param matchListId
	 */
	public void savePassInviteMatch(String tokenId, String matchListId) {
		User user = getUserbyToken(tokenId);
		MatchList matchList = matchesDao.get(matchListId);
		UserAndGroup uag = userAndGroupDao.loadByUserAndGroup(user.getId(), matchList.getOtherGroup().getId(),UserAndGroupState.FORMAL);
		int auth = uag.getAuthorityState();
		
		if(RoleHelper.judgeState(auth, RoleHelper.FOUR_AUTHORITY_EXPEL)){
			matchList.setState(GroupMatchState.Agree);
		}else{
			throw new Business4JsonException("您没有所在的小组"+uag.getGroup().getName()+"的约赛权限！");
		}
	}
	
	public void updateMatchScore(String tokenId, MatchList matchList) {
		String matchListId = matchList.getId();
		MatchList dbMatchList = matchesDao.get(matchListId);
		dbMatchList.setBelongGroupGoals(matchList.getBelongGroupGoals());
		dbMatchList.setOtherGroupGoals(matchList.getOtherGroupGoals());
		matchesDao.update(dbMatchList);
	}
	
	/**
	 * 根据token和比赛状态查询比赛
	 * @param tokenId
	 * @param state
	 * @return
	 */
	public List<MatchList> loadMatchByUser(String tokenId,GroupMatchState state) {
		User user = getUserbyToken(tokenId);
		UserAndGroup uags = userAndGroupDao.loadByUser(user.getId());
		if(uags!= null){

			Group group = uags.getGroup();
			List<MatchList> matchlists =  matchesDao.loadAllMatchByUser(group.getId(),state);
			 return matchlists;
		}
		return null;
	}
	
	/**
	 * 查询所有受邀比赛
	 * @param tokenId
	 * @param wait
	 * @return
	 */
	public List<MatchList> loadInviteMatchByUser(String tokenId,
			GroupMatchState state) {
		User user = getUserbyToken(tokenId);
		
		UserAndGroup uags = userAndGroupDao.loadByUser(user.getId());
		if(uags!= null){
			
			Group group = uags.getGroup();

			List<MatchList> matchlists =  matchesDao.loadAllInviteMatchByUser(group.getId(),state);
			 return matchlists;
		}
		return null;
	}
	

	/**
	 * 通过ID和状态查询比赛信息
	 * @param tokenId
	 * @param matchListId
	 * @param done
	 * @return
	 */
	public MatchList loadMatchById(String tokenId, String matchListId,
			GroupMatchState done) {
		return matchesDao.loadMatchById(matchListId,done);
	}


}

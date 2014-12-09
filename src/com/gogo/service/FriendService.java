package com.gogo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gogo.dao.FriendListDao;
import com.gogo.dao.UserDao;
import com.gogo.dao.UserTokenDao;
import com.gogo.domain.FriendList;
import com.gogo.domain.Place;
import com.gogo.domain.User;
import com.gogo.domain.UserToken;
import com.gogo.exception.Business4JsonException;
import com.gogo.helper.MapHelper;
import com.gogo.page.Page;
import com.gogo.page.PageUtil;

@Service
public class FriendService extends BaseService{
	
	@Autowired
	private FriendListDao friendListDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserTokenDao userTokenDao;
	
	
	public Page<User> loadPersonByPlace(String token,Place place,String ip,int currPage,int pageSize){
		UserToken userToken = userTokenDao.get(token);
		String userId = userToken.getUser().getId();
		Page<User> queryList = null;
		if(place != null && place.getLongitude() != 0 && place.getLongitude() != 0){
			//TODO 按地图查询
//			queryList = PageUtil.getPage(friendGroupDao.lodActByPlaceCount(user,place),currPage , friendGroupDao.loadActByPlace(user,place, currPage, pageSize), pageSize);
		}else if(ip != null ){
			//TODO 按城市查询
			String city = MapHelper.getCity(ip);
			if(city != null){
				queryList =PageUtil.getPage(userDao.loadPersonAllCount(userId), currPage, userDao.loadPersonAll(userId, currPage, pageSize), pageSize);	
			}else{
				queryList =PageUtil.getPage(userDao.loadPersonAllCount(userId), currPage, userDao.loadPersonAll(userId, currPage, pageSize), pageSize);	
			}
		}
		return queryList;
	}
	
	/**
	 * 好友申请
	 * @param belongUser
	 * @param friendUserId
	 */
	public void saveFriendRequest(String tokenId,String friendUserId){
		
		User belongUser = getUserbyToken(tokenId);
		
		
		if(belongUser.getId().equals(friendUserId)){
			throw new Business4JsonException("friend_request_yourself","don't request yourself!");
		}
		
		User friendUser = userDao.load(friendUserId);
	
		FriendList fl = friendListDao.loadFriendListByUserId(belongUser.getId(), friendUserId);
		
		if(fl== null){
			fl = new FriendList();
			fl.setBelongUser(belongUser);
			fl.setFriendUser(friendUser);
			fl.setfAliasName(friendUser.getAliasName());
			fl.setPassed(false);
		}else{
			
			if(fl.isPassed()){
				throw new Business4JsonException("friend_already_friends","You are already friends!");
			}else{
				fl.setUpdate_time(new Date());
			}
			
			
		}
		friendListDao.save(fl);
	}
	
	/**
	 * 通过申请
	 * @param belongUser
	 * @param friendUserId
	 */
	public void saveAgreeApply(String tokenId,String friendUserId){
		User belongUser = getUserbyToken(tokenId);
		
		if(belongUser.getId().equals(friendUserId)){
			throw new Business4JsonException("friend_request_yourself","don't request yourself!");
		}
		
		FriendList applyList = friendListDao.loadFriendListByUserId(friendUserId,belongUser.getId());
		applyList.setPassed(true);
		
		User friendUser = userDao.load(friendUserId);
		FriendList fl = new FriendList();
		fl.setBelongUser(belongUser);
		fl.setFriendUser(friendUser);
		fl.setfAliasName(friendUser.getAliasName());
		fl.setPassed(true);
		
		friendListDao.save(fl);
	}
	
	public List<User> loadFriends(String tokenId) throws Exception{
		 User user = getUserbyToken(tokenId);
		
		 List<User> friends = userDao.loadAllFriends(user.getId());
		 return friends;
	}
	
	public List<User> loadFriendRequestList(String tokenId) throws Exception{
		User user = getUserbyToken(tokenId);
		 List<User> friends = userDao.loadFriendRequestList(user.getId());
		 return friends;
	}
	
}

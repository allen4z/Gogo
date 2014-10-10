package com.gogo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gogo.dao.FriendListDao;
import com.gogo.dao.UserDao;
import com.gogo.domain.City;
import com.gogo.domain.FriendList;
import com.gogo.domain.Place;
import com.gogo.domain.User;
import com.gogo.map.GoMapHelper;
import com.gogo.page.Page;
import com.gogo.page.PageUtil;

@Service
public class FriendService{
	
	@Autowired
	private FriendListDao friendListDao;
	@Autowired
	private UserDao userDao;
	
	public Page<User> loadPersonByPlace(User user,Place place,String ip,int currPage,int pageSize){
		Page<User> queryList = null;
		if(place != null && place.getLongitude() != 0 && place.getLongitude() != 0){
			//TODO 按地图查询
//			queryList = PageUtil.getPage(friendGroupDao.lodActByPlaceCount(user,place),currPage , friendGroupDao.loadActByPlace(user,place, currPage, pageSize), pageSize);
		}else if(ip != null ){
			//TODO 按城市查询
			City city = GoMapHelper.getCityInfo(ip);
			if(city != null){
				//queryList =PageUtil.getPage(friendGroupDao.loadActbyAddrCount(user,city), currPage, friendGroupDao.loadActbyAddr(user,city, currPage, pageSize), pageSize);	
			}else{
				queryList =PageUtil.getPage(friendListDao.loadPersonAllCount(user), currPage, friendListDao.loadPersonAll(user, currPage, pageSize), pageSize);	
			}
		}
		return queryList;
	}
	
	/**
	 * 好友申请
	 * @param belongUser
	 * @param friendUserId
	 */
	public void saveFriendRequest(User belongUser,String friendUserId){
		User friendUser = userDao.load(friendUserId);

		FriendList fl = new FriendList();
		fl.setBelongUser(belongUser);
		fl.setFriendUser(friendUser);
		fl.setfAlisName(friendUser.getAlisName());
		fl.setPassed(false);
		
		friendListDao.save(fl);
	}
	
	/**
	 * 通过申请
	 * @param belongUser
	 * @param friendUserId
	 */
	public void saveAgreeApply(User belongUser,String friendUserId){
		
		FriendList applyList = friendListDao.loadFriendListByUserId(friendUserId,belongUser.getUserId());
		applyList.setPassed(true);
		
		User friendUser = userDao.load(friendUserId);
		FriendList fl = new FriendList();
		fl.setBelongUser(belongUser);
		fl.setFriendUser(friendUser);
		fl.setfAlisName(friendUser.getAlisName());
		fl.setPassed(true);
		
//		friendListDao.update(applyList);
		
		friendListDao.save(fl);
	}
	
	public List<User> loadFriends(String userId) throws Exception{
		 List<User> friends = friendListDao.loadAllFriends(userId);
		 return friends;
	}
	
	public List<User> loadFriendRequestList(String userId) throws Exception{
		 List<User> friends = friendListDao.loadFriendRequestList(userId);
		 return friends;
	}
	
}
package com.gogo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gogo.domain.FriendList;
import com.gogo.domain.User;

@Repository
public class FriendListDao extends BaseDao<FriendList> {
	
	private static final String HQL_AILS=" user ";
	
	private static final String HQL_LIST="FROM User"; 

	/**
	 * 查询所有好友
	 * @param userId
	 * @return
	 */
	public List<User> loadAllFriends(String userId){
		
		String hql = " select friendList.friendUser from FriendList friendList "
				+ " where friendList.belongUser.userId=:userId and friendList.passed=:passed "
				+ " order by friendList.friendUser.alisName ";
		
		List firends = getSession().createQuery(hql)
				.setString("userId", userId)
				.setBoolean("passed", true)
				.list();
		return firends;
	}
	
	public List<User> loadFriendRequestList(String userId){
		
		String hql = " select friendList.belongUser from FriendList friendList "
				+ " where friendList.friendUser.userId=:userId and friendList.passed=:passed "
				+ " order by friendList.belongUser.alisName  ";
		
		List firends = getSession().createQuery(hql)
				.setString("userId", userId)
				.setBoolean("passed", false)
				.list();
		return firends;
	}
	
	
	public List<User> loadPersonAll(User user,int pn,int pageSize) {
		
		String hql=getHql4City(user,  false);
		List<User> actPage = findByPage(hql, pn, pageSize, null);
		return actPage;
	}
	
	public int loadPersonAllCount(User user){
		String hql=getHql4City(user, true);
		return  this.<Number>getCount(hql, null).intValue();
	}
	
	private String getHql4City(User user,boolean isCount){
		StringBuffer hql = new StringBuffer();
		
		if(isCount){
			hql.append("select count("+HQL_AILS+") ");
		}else{
			hql.append("select "+HQL_AILS+" ");
		}
		hql.append(" "+HQL_LIST+" "+HQL_AILS+" ");
	
		hql.append(" where "+HQL_AILS+".userId !='"+user.getUserId()+"' ");
		
		return hql.toString();
	}


	public FriendList loadFriendListByUserId(String requestUserId, String agreeUserId) {
		String hql = "select friendList from FriendList friendList "
				+ " where friendList.belongUser.userId=:requestUserId "
				+ " and friendList.friendUser.userId=:agreeUserId ";
	
		FriendList fl = (FriendList) getSession()
				.createQuery(hql)
				.setString("requestUserId", requestUserId)
				.setString("agreeUserId", agreeUserId)
				.uniqueResult();
		
		return fl;
	}
}

package com.gogo.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.gogo.domain.User;
import com.gogo.domain.enums.FriendListState;

@Repository
public class UserDao extends BaseDao<User>{
	
	
	private static final String HQL_AILS=" user ";
	
	private static final String HQL_LIST="FROM User"; 
	
	public List<User> loadUserByName(String userName){
		String hql = "from User where name='"+userName+"'";
		List<User> userList  = find(hql);
		return userList;
	}
	
	public User loadUserByNameAndPassword(String userName,String password){
		
		User user = (User) getSession().createCriteria(User.class)
				.add(Restrictions.eq("name",userName))
				.add(Restrictions.eq("password",password))
				.setMaxResults(1)
				.uniqueResult();
		
		return user;
	}
	
	public List<User> loadUserByName(String userName,int curPage,int pagesize){
		String hql = "from User where name='"+userName+"'";		
		List<User> userList  = findByPage(hql,curPage,pagesize);
		return userList;
	}

	/**
	 * 根据活动ID查询活动用户Sql  查询参与用户，观众用户...
	 * @param isCount
	 * @param paramlist 权限状态（最多传入一个值，为空则查询所有用户）
	 * @return
	 */
	private String getActUserByActSql(boolean isCount,Object... paramlist){
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" select ");
		if(isCount){
			sqlBuffer.append(" count(uaa.user) ");
		}else{
			sqlBuffer.append(" uaa.user ");
		}
		sqlBuffer.append(" FROM UserAndAct uaa join uaa.act a where a.id=? ");
		
		if(paramlist!=null && paramlist.length>0){
			sqlBuffer.append(" and uaa.uaaState in (");
			for (int i = 0; i < paramlist.length; i++) {
				if(i==0){
					sqlBuffer.append(paramlist[i]);
				}else{
					sqlBuffer.append(","+paramlist[i]);
				}
			}
			sqlBuffer.append(")");
		}
		return sqlBuffer.toString();
	}
	
	/**
	 * 根据活动ID查询跟该活动有关的所有用户的数量
	 * @param actId
	 * @param params
	 * @return
	 */
	public int loadUserByActCount (String actId,Object...params){
		String hql = getActUserByActSql(true,params);
		return  getCount(hql, actId);
	}

	/**
	 * 根据活动ID查询跟该活动有关的所有用户
	 * @param actId
	 * @param currPage
	 * @param pageSize
	 * @param params
	 * @return
	 */
	public List<User> loadUserByAct(String actId,int currPage,int pageSize,Object...params){
		String hql = getActUserByActSql(false,params);
		List<User> userList = findByPage(hql,currPage,pageSize,actId);
		return userList;
	}

	
	/**
	 * 查询所有好友
	 * @param userId
	 * @return
	 */
	public List<User> loadAllFriends(String userId){
		
		String hql = " select friendList.friendUser from FriendList friendList "
				+ " where friendList.belongUser.id=:userId and friendList.passed=:passed "
				+ " order by friendList.friendUser.aliasName ";
		
		List firends = getSession().createQuery(hql)
				.setString("userId", userId)
				.setParameter("passed", FriendListState.Agree)
				.list();
		return firends;
	}
	
	public List<User> loadFriendRequestList(String userId){
		
		String hql = " select friendList.belongUser from FriendList friendList "
				+ " where friendList.friendUser.id=:userId and friendList.passed=:passed "
				+ " order by friendList.belongUser.aliasName  ";
		
		List firends = getSession().createQuery(hql)
				.setString("userId", userId)
				.setParameter("passed", FriendListState.Wait)
//				.setInteger("passed", FriendListState.Agree)
				.list();
		return firends;
	}
	
	
	public List<User> loadPersonAll(String userId,int pn,int pageSize) {
		
		String hql=getHql4City(userId,  false);
		List<User> actPage = findByPage(hql, pn, pageSize, null);
		return actPage;
	}
	
	public int loadPersonAllCount(String userId){
		String hql=getHql4City(userId, true);
		return getCount(hql, null);
	}
	
	private String getHql4City(String userId,boolean isCount){
		StringBuffer hql = new StringBuffer();
		
		if(isCount){
			hql.append("select count("+HQL_AILS+") ");
		}else{
			hql.append("select "+HQL_AILS+" ");
		}
		hql.append(" "+HQL_LIST+" "+HQL_AILS+" ");
	
		hql.append(" where "+HQL_AILS+".id !='"+userId+"' ");
		
		return hql.toString();
	}
	
}

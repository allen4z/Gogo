package com.gogo.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gogo.page.PageUtil;


@SuppressWarnings("unchecked")
public class BaseDao<T> {
	
	@Autowired
	private SessionFactory sessionFactory;

	//TODO 增加了泛型 ？
	private Class<T> entityClass;
	
	public BaseDao() {
	        Type genType = getClass().getGenericSuperclass();
	        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
	        entityClass = (Class<T>) params[0];
	    }

	    /**
	     * 根据ID加载PO实例
	     *
	     * @param id
	     * @return 返回相应的持久化PO实例
	     */
	    public T load(Serializable id) {
	        return (T) getSession().load(entityClass, id);
	    }

	    /**
	     * 根据ID获取PO实例
	     *
	     * @param id
	     * @return 返回相应的持久化PO实例
	     */
	    public T get(Serializable id) {
	        return (T) getSession().get(entityClass, id);
	    }

	    /**
	     * 获取PO的所有对象
	     *
	     * @return
	     */
	    public List<T> loadAll() {	
	        Criteria criteria = getSession().createCriteria(entityClass);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			return criteria.list();
	    }

	    /**
	     * 保存PO
	     *
	     * @param entity
	     */
	    public Serializable save(T entity) {
	    	return getSession().save(entity);
	    }
	    
	    /**
	     * 保存或更新
	     * @param entity
	     */
	    public void saveOrUpdate(T entity){
	    	getSession().saveOrUpdate(entity);
	    }

	    /**
	     * 删除PO
	     *
	     * @param entity
	     */
	    public void remove(T entity) {
	    	getSession().delete(entity);
	    }

	    /**
	     * 更改PO
	     *
	     * @param entity
	     */
	    public void update(T entity) {
	    	getSession().update(entity);
	    }

	    /**
	     * 执行HQL查询
	     *
	     * @param sql
	     * @return 查询结果
	     */
	    public List<T> find(String hql) {
	        Query queryObject = getSession().createQuery(hql);
			return queryObject.list();
	    }
	    
	    /**
	     * 执行带参的HQL查询
	     *
	     * @param sql
	     * @param params
	     * @return 查询结果
	     */
		public List<T> find(String hql, Object... params) {
	    	 Object[] values = params;
		     Query queryObject = getSession().createQuery(hql);
			 if (values != null) {
			 	for (int i = 0; i < values.length; i++) {
			 		queryObject.setParameter(i, values[i]);
			 	}
			 }
			 return queryObject.list();
	    }
	    
	    
	    /**
	     * 查询第一个记录
	     * @param hql
	     * @param params
	     * @return
	     */
	    public T findUnique(String hql, Object... params) {
	    	 Object[] values = params;
		     Query queryObject = getSession().createQuery(hql);
			 if (values != null) {
			 	for (int i = 0; i < values.length; i++) {
			 		queryObject.setParameter(i, values[i]);
			 	}
			 }
			
			return (T)queryObject.uniqueResult();
	    }


	    
	 
	    
	    /**
	     * 分页查询
	     * @param hql
	     * @param page
	     * @param pagesize
	     * @return
	     */
	    public List<T> findByPage(String hql, int pn,int pageSize, Object... paramlist){
	    	
	    	  Query query = getSession().createQuery(hql);
	          setParameters(query, paramlist);
	          if (pn > -1 && pageSize > -1) {
	              query.setMaxResults(pageSize);
	              int start = PageUtil.getPageStart(pn, pageSize);
	              if (start != 0) {
	                  query.setFirstResult(start);
	              }
	          }
	          if (pn < 0) {
	              query.setFirstResult(0);
	          }
	          List<T> results = query.list();
	          return results;
	    }
	    
	    
	    public int getCount(String hql,Object...paramlist){
	    	  Query query = getSession().createQuery(hql);
	          setParameters(query, paramlist);
	          
	          Long count = (Long)query.uniqueResult();
	          return count.intValue();
	    }
	    
	    protected void setParameters(Query query, Object[] paramlist) {
	        if (paramlist != null) {
	            for (int i = 0; i < paramlist.length; i++) {
	                if(paramlist[i] instanceof Date) {
	                    query.setTimestamp(i, (Date)paramlist[i]);
	                } else {
	                    query.setParameter(i, paramlist[i]);
	                }
	            }
	        }
	    }
	    
	    public Session getSession() {
	        return sessionFactory.getCurrentSession();
	    }
}

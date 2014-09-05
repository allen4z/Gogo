package com.gogo.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gogo.page.IPageContext;
import com.gogo.page.Page;
import com.gogo.page.QueryPageContext;

public class BaseDao<T> {
	
	@Autowired
	private SessionFactory sessionFactory;

	private Class entityClass;
	
	 public BaseDao() {
	        Type genType = getClass().getGenericSuperclass();
	        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
	        entityClass = (Class) params[0];
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
	    public List find(String hql) {
	        Query queryObject = getSession().createQuery(hql);
			return queryObject.list();
	    }
	    
	    public Page find(String hql,int i) {
	        Query queryObject = getSession().createQuery(hql);
	        List list = queryObject.list();
	        IPageContext<T> pageContext = new QueryPageContext<T>(list.size(),1, list);
			return  pageContext.getPage(i);
	    }

	    /**
	     * 执行带参的HQL查询
	     *
	     * @param sql
	     * @param params
	     * @return 查询结果
	     */
	    public List find(String hql, Object... params) {
	    	 Object[] values = params;
		     Query queryObject = getSession().createQuery(hql);
			 if (values != null) {
			 	for (int i = 0; i < values.length; i++) {
			 		queryObject.setParameter(i, values[i]);
			 	}
			 }
			 return queryObject.list();
	    }


	    public Session getSession() {
	        return sessionFactory.getCurrentSession();
	    }
	    
	    //分页查询
	    public List findByPage(String hql, int page,int pagesize){
	    	Query q = getSession().createQuery(hql);
	    	//开始位置
	    	int start = (page-1)*pagesize+1;
	    	q.setFirstResult(start);
	    	q.setMaxResults(pagesize);
	    	return q.list(); 
	    }
}

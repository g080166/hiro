package com.hiro.spider.data.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hiro.spider.data.dao.IBaseDao;




/** 
 * @remark
 * @author  yezm 
 * @createTime 2014-1-6 下午04:31:52 
 */
@SuppressWarnings("serial")
public class BaseDao <T extends Serializable,PK extends Serializable>  implements IBaseDao<T, PK>{
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFacory;
	private Class<T> clazz;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BaseDao(){
		this.clazz = 
			((Class)((ParameterizedType)super.getClass()
				      .getGenericSuperclass()).getActualTypeArguments()[0]);
	}
	
	public Session getSession() {
		return this.sessionFacory.getCurrentSession();
	}
	
	public void clearCache(){
		getSession().flush();
		getSession().clear();
	}
	
	public void save(T t) {
		getSession().save(t);
	}

	public void saveOrUpdate(T t) {
		getSession().saveOrUpdate(t);
	}
	
	@SuppressWarnings("unchecked")
	public T findById(Serializable id) {
		return (T) getSession().get(clazz, id);
	}

	@Override
	public List<T> findAll() {
		String hql = "from " + clazz.getName();
		Query query = getSession().createQuery(hql);
		return query.list();
	}

	@Override
	public List<T> findAllByOrder(String param, boolean desc) {
		String hql = "from " + clazz.getName() + " as model order by " + param;
		if(desc)
			hql += " desc";
		Query query = getSession().createQuery(hql);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public Object unique(String hql,Object...p){
		Query query = getSession().createQuery(hql);
		bindParameter(query, p);
		return query.setMaxResults(1).uniqueResult();
	}
	@SuppressWarnings("unchecked")
	public List<T> query(String hql){
		Query query = getSession().createQuery(hql);
		return query.list();
	}
	 
	@SuppressWarnings("unchecked")
	public List<T> query(String hql,Object...p){
		Query query = getSession().createQuery(hql);
		bindParameter(query, p);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> queryPage(String hql,Integer pageSize,Integer pageIndex,Object...p){
		Query query = getSession().createQuery(hql);
		bindParameter(query, p);
		return query.setFirstResult((pageIndex-1)*pageSize).setMaxResults(pageSize).list();
	}
	
	
	
	
	/**
	 * 绑定参数
	 * @remark
	 * @author yezm
	 * @createTIme 2014-1-6 下午04:36:22
	 * @param query
	 * @param p
	 */
	private void bindParameter(Query query,Object...p){
		if(p!=null&&p.length>0){
			for (int i = 0; i < p.length; i++) {
				query.setParameter(i, p[i]);
			}
		}
	}

	@Override
	public void remove(T t) {
		getSession().delete(t);
	}

	@Override
	public List<T> findByParams(String[] paramsName, Object[] params) {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("from " + clazz.getName());
		strBuffer.append(" as model where ");
		for (int i = 0; i < paramsName.length; i++) {
			if (i != 0)
				strBuffer.append(" and");
			strBuffer.append(" model.");
			strBuffer.append(paramsName[i]);
			strBuffer.append("=");
			strBuffer.append("?");
		}
		String hql = strBuffer.toString();
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}

		return query.list();
	}

	@Override
	public List<T> findByParams(String[] paramsName, Object[] params,
			boolean isRangeQuery) {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("from " + clazz.getName());
		strBuffer.append(" as model where ");
		for (int i = 0; i < paramsName.length; i++) {
			if (i != 0)
				strBuffer.append(" and");
			strBuffer.append(" model.");
			strBuffer.append(paramsName[i]);
			strBuffer.append(" in(");
			strBuffer.append("?)");
		}
		String hql = strBuffer.toString();
		System.out.println(strBuffer.toString());
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}

		return query.list();
	}

	@Override
	public List<T> findByParams(String[] paramsName, Object[] params,
			String order, boolean isSort) {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("from " + clazz.getName());
		strBuffer.append(" as model where ");
		for (int i = 0; i < paramsName.length; i++) {
			if (i != 0)
				strBuffer.append(" and");
			strBuffer.append(" model.");
			strBuffer.append(paramsName[i]);
			strBuffer.append("=");
			strBuffer.append("?");
		}
		strBuffer.append(" order by " + order);
		if (isSort) {
			strBuffer.append(" desc");
		}
		String hql = strBuffer.toString();
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}

		return query.list();
	}
}

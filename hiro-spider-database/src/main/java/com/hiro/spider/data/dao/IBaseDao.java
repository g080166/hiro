package com.hiro.spider.data.dao;


import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;

/** 
 * @remark
 * @author  yezm 
 * @createTime 2014-1-6 下午04:28:41 
 */
public interface IBaseDao<T extends Serializable,PK extends Serializable> extends Serializable{
	/**
	 * 获取hibernateSession
	 * @remark
	 * @author yezm
	 * @createTIme 2013-11-26 下午02:20:50
	 * @return
	 */
	public Session getSession();
	
	public void clearCache();
	/**
	 * 新增
	 * @remark
	 * @author yezm
	 * @createTIme 2013-11-26 下午02:29:16
	 * @param t
	 */
	public void save(T t);
	/**
	 * 新增或修改
	 * @remark
	 * @author yezm
	 * @createTIme 2013-11-26 下午02:29:23
	 * @param t
	 */
	public void saveOrUpdate(T t);
	
	/**
	 * 删除
	 * @remark
	 * @author yezm
	 * @createTIme 2014-4-9 上午09:08:37
	 * @param t
	 */
	public void remove(T t);
	
	/**
	 * 根据主键ID查
	 * @remark
	 * @author yezm
	 * @createTIme 2014-1-6 下午05:28:17
	 * @param id
	 * @return
	 */
	public T findById(Serializable id);
	
	/**
	 * 获取单行单列数据（count，sum...）
	 * @remark
	 * @author yezm
	 * @createTIme 2014-1-6 下午05:29:14
	 * @param hql
	 * @param p
	 * @return
	 */
	public Object unique(String hql,Object...p);
	
	/**
	 * 条件查询所有数据
	 * @remark
	 * @author yezm
	 * @createTIme 2014-1-6 下午05:29:54
	 * @param hql
	 * @param p
	 * @return
	 */
	public List<T> query(String hql,Object...p);
	
	/**
	 * 条件查询分页数据
	 * @remark
	 * @author yezm
	 * @createTIme 2014-1-6 下午05:30:17
	 * @param hql
	 * @param pageSize 每页显示记录条数
	 * @param pageIndex 显示第几页
	 * @param p
	 * @return
	 */
	public List<T> queryPage(String hql,Integer pageSize,Integer pageIndex,Object...p);

	/**
	 * 根据条件查询信息
	 * 
	 * @param paramsName
	 * @param params
	 * @return
	 */
	List<T> findByParams(String[] paramsName, Object[] params);

	List<T> findAllByOrder(String param, boolean desc);

	List<T> findAll();

	List<T> findByParams(String[] paramsName, Object[] params, String order,
			boolean isSort);

	List<T> findByParams(String[] paramsName, Object[] params,
			boolean isRangeQuery);
}

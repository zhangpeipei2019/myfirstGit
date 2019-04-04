package com.it.service;


import java.sql.Connection;
import java.sql.ResultSet;

import com.it.bean.PageBean;
import com.it.bean.Role;
/**
 * 
 * 
 * 
 * <p>
 * Title:角色业务
 * 
 *
 * </p>
 * <p>
 * Description:com.it.service.IRole.java
 * </p>
 * <p>
 * Copyright: Copyright (c) j2se 8.0
 * </p>
 * <p>
 * date: 2018年9月29日  下午2:30:07
 * </p>
 * <p>
 * Company: zxySoft
 * </p>
 * 
 * @author zxy
 */
public interface IRole {
	/**
	 * 根据角色id获取角色名称
	 * @param id
	 * @return
	 */
	public String getRoleNameById(int id);
	/**
	 * 根据角色id查询该角色下所有的权限
	 * @param id
	 * @return
	 */
	public String getAuthIdsById(int id);
	/**
	 * 查询所有的角色
	 * @param con
	 * @param pageBean
	 * @param role
	 * @return
	 */
	public ResultSet roleList(Connection con,PageBean pageBean,Role role);
	/**
	 * 查询角色的总数
	 * @param role
	 * @return
	 */
	public int roleCount(Role role);
	/**
	 * 删除角色
	 * @param delIds
	 * @return
	 */
	public int roleDelete(String delIds);
	/**
	 * 添加角色
	 * @param role
	 * @return
	 */
	public int roleAdd(Role role);
	/**
	 * 修改角色
	 * @param role
	 * @return
	 */
	public int roleUpdate(Role role);
	/**
	 * 修改角色
	 * @param role
	 * @return
	 */
	public int roleAuthIdsUpdate(Role role);
}

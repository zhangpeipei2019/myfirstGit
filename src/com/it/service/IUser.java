package com.it.service;

import java.sql.Connection;
import java.sql.ResultSet;

import com.it.bean.User;
import com.it.bean.PageBean;
/**
 * 用户业务
 * 
 * 
 * <p>
 * Title:
 * 
 *
 * </p>
 * <p>
 * Description:com.it.service.IUser.java
 * </p>
 * <p>
 * Copyright: Copyright (c) j2se 8.0
 * </p>
 * <p>
 * date: 2018年9月29日  下午2:20:37
 * </p>
 * <p>
 * Company: zxySoft
 * </p>
 * 
 * @author zxy
 */
public interface IUser {
	/**
	 * 登录业务
	 * @param user
	 * @return
	 */
	public User login(User user);
	/**
	 * 修改密码业务
	 * @param user
	 * @return
	 */
	public int modifyPassword(User user);
	/**
	 * 查询所有用户和分页一起实现
	 * @param con
	 * @param pageBean
	 * @param user
	 * @return
	 */
	public ResultSet userList(Connection con,PageBean pageBean,User user);
	/**
	 * 查询用户总数
	 * @param user
	 * @return
	 */
	public int userCount(User user);
	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	public int userAdd(User user);
	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	public int userUpdate(User user);
	/**
	 * 判断当前账户名是否可以用
	 * @param userName
	 * @return
	 */
	public boolean existUserWithUserName(String userName);
	/**
	 * 删除用户
	 * @param delIds
	 * @return
	 */
	public int userDelete(String delIds);
	/**
	 * 查询该角色该用户是否有
	 * @param roleId
	 * @return
	 */
	public boolean existUserWithRoleId(String roleId);
	
}

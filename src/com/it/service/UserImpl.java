package com.it.service;

import java.sql.Connection;
import java.sql.ResultSet;

import com.it.bean.PageBean;
import com.it.bean.User;
import com.it.dao.UserDao;
/**
 * 
 * 
 * 
 * <p>
 * Title:用户实现类
 * 
 *
 * </p>
 * <p>
 * Description:com.it.service.UserImpl.java
 * </p>
 * <p>
 * Copyright: Copyright (c) j2se 8.0
 * </p>
 * <p>
 * date: 2018年9月29日  下午2:20:15
 * </p>
 * <p>
 * Company: zxySoft
 * </p>
 * 
 * @author zxy
 */
public class UserImpl implements IUser {
	UserDao userdao = new UserDao();

	@Override
	public User login(User user) {
		// TODO Auto-generated method stub
		return userdao.login(user);
	}

	@Override
	public int modifyPassword(User user) {
		// TODO Auto-generated method stub
		return userdao.modifyPassword(user);
	}

	@Override
	public ResultSet userList(Connection con,PageBean pageBean, User user) {
		// TODO Auto-generated method stub
		try {
			return userdao.userList(con, pageBean, user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int userCount(User user) {
		// TODO Auto-generated method stub
		return userdao.userCount(user);
	}

	@Override
	public int userAdd(User user) {
		// TODO Auto-generated method stub
		return userdao.userAdd(user);
	}

	@Override
	public int userUpdate(User user) {
		// TODO Auto-generated method stub
		return userdao.userUpdate(user);
	}

	@Override
	public boolean existUserWithUserName(String userName) {
		// TODO Auto-generated method stub
		return userdao.existUserWithUserName(userName);
	}

	@Override
	public int userDelete(String delIds) {
		// TODO Auto-generated method stub
		return userdao.userDelete(delIds);
	}

	@Override
	public boolean existUserWithRoleId(String roleId) {
		// TODO Auto-generated method stub
		return userdao.existUserWithRoleId(roleId);
	}

}

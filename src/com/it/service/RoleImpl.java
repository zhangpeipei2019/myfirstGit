package com.it.service;

import java.sql.Connection;
import java.sql.ResultSet;

import com.it.bean.PageBean;
import com.it.bean.Role;
import com.it.dao.RoleDao;
/**
 *  * 角色实现类
 * 
 * 
 * <p>
 * Title:
 * 
 *
 * </p>
 * <p>
 * Description:com.it.service.RoleImpl.java
 * </p>
 * <p>
 * Copyright: Copyright (c) j2se 8.0
 * </p>
 * <p>
 * date: 2018年9月29日  下午2:19:47
 * </p>
 * <p>
 * Company: zxySoft
 * </p>
 * 
 * @author zxy
 */
public class RoleImpl implements IRole {
	RoleDao roleDao = new RoleDao();

	@Override
	public String getRoleNameById(int id) {
		// TODO Auto-generated method stub
		return roleDao.getRoleNameById(id);
	}

	@Override
	public String getAuthIdsById(int id) {
		// TODO Auto-generated method stub
		return roleDao.getAuthIdsById(id);
	}

	@Override
	public ResultSet roleList(Connection con, PageBean pageBean, Role role) {
		// TODO Auto-generated method stub
		try {
			return roleDao.roleList(con, pageBean, role);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int roleCount(Role role) {
		// TODO Auto-generated method stub
		return roleDao.roleCount(role);
	}

	@Override
	public int roleDelete(String delIds) {
		// TODO Auto-generated method stub
		return roleDao.roleDelete(delIds);
	}

	@Override
	public int roleAdd(Role role) {
		// TODO Auto-generated method stub
		return roleDao.roleAdd(role);
	}

	@Override
	public int roleUpdate(Role role) {
		// TODO Auto-generated method stub
		return roleDao.roleUpdate(role);
	}

	@Override
	public int roleAuthIdsUpdate(Role role) {
		// TODO Auto-generated method stub
		return roleDao.roleAuthIdsUpdate(role);
	}

}

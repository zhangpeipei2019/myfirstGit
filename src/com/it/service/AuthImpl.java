package com.it.service;

import net.sf.json.JSONArray;

import com.it.bean.Auth;
import com.it.dao.AuthDao;
/**
 * 权限实现类
 * 
 * 
 * <p>
 * Title:
 * 
 *
 * </p>
 * <p>
 * Description:com.it.service.AuthImpl.java
 * </p>
 * <p>
 * Copyright: Copyright (c) j2se 8.0
 * </p>
 * <p>
 * date: 2018年9月29日  下午2:18:56
 * </p>
 * <p>
 * Company: zxySoft
 * </p>
 * 
 * @author zxy
 */
public class AuthImpl implements IAuth {
	AuthDao authDao = new AuthDao();

	@Override
	public JSONArray getAuthByParentId(String parentId, String authIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren(String parentId, String authIds) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public JSONArray getAuthsByParentId(String parentId, String authIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONArray getCheckedAuthByParentId(String parentId, String authIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONArray getCheckedAuthsByParentId(String parentId, String authIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONArray getTreeGridAuthByParentId(String parentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONArray getListByParentId(String parentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int authAdd(Auth auth) {
		// TODO Auto-generated method stub
		return authDao.authAdd(auth);
	}

	@Override
	public int authUpdate(Auth auth) {
		// TODO Auto-generated method stub
		return authDao.authUpdate(auth);
	}

	@Override
	public boolean isLeaf(String authId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int updateStateByAuthId(String state, String authId) {
		// TODO Auto-generated method stub
		return authDao.updateStateByAuthId(state, authId);
	}

	@Override
	public int authDelete(String authId) {
		// TODO Auto-generated method stub
		return authDao.authDelete(authId);
	}

	@Override
	public int getAuthCountByParentId(String parentId) {
		// TODO Auto-generated method stub
		return 0;
	}

}

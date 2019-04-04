package com.it.controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.it.dao.RoleDao;
import com.it.bean.PageBean;
import com.it.bean.Role;
import com.it.service.IRole;
import com.it.service.IUser;
import com.it.service.RoleImpl;
import com.it.service.UserImpl;
import com.it.utils.DbUtil;
import com.it.utils.JsonUtil;
import com.it.utils.ResponseUtil;
import com.it.utils.StringUtil;
/**
 * 
 * 
 * 
 * <p>
 * Title:角色实现控制器
 * 
 *
 * </p>
 * <p>
 * Description:com.it.controller.RoleServlet.java
 * </p>
 * <p>
 * Copyright: Copyright (c) j2se 8.0
 * </p>
 * <p>
 * date: 2018年9月29日  上午11:47:19
 * </p>
 * <p>
 * Company: zxySoft
 * </p>
 * 
 * @author zxy
 */

@WebServlet({ "/RoleServlet", "/role" })
public class RoleServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	DbUtil dbUtil = new DbUtil();
	IUser iUser = new UserImpl();
	IRole iRole = new RoleImpl();
	RoleDao roleDao = new RoleDao();

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		if ("comBoList".equals(action)) {
			comBoList(request, response);
		} else if ("list".equals(action)) {
			roleList(request, response);
		} else if ("delete".equals(action)) {
			roleDelete(request, response);
		} else if ("save".equals(action)) {
			roleSave(request, response);
		} else if ("auth".equals(action)) {
			auth(request, response);
		}
	}

	private void comBoList(HttpServletRequest request,
			HttpServletResponse response) {
		Connection con = null;
		try {
			con = dbUtil.getCon();
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("roleId", "");
			jsonObject.put("roleName", "请选择...");
			jsonArray.add(jsonObject);
			jsonArray.addAll(JsonUtil.formatRsToJsonArray(roleDao.roleList(con,
					null, new Role())));
			ResponseUtil.write(response, jsonArray);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void roleList(HttpServletRequest request,
			HttpServletResponse response) {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		Role role = new Role();
		String s_roleName = request.getParameter("s_roleName");
		if (StringUtil.isNotEmpty(s_roleName)) {
			role.setRoleName(s_roleName);
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page),
				Integer.parseInt(rows));
		Connection con = null;
		try {
			con = dbUtil.getCon();
			JSONObject result = new JSONObject();
			JSONArray jsonArray = JsonUtil.formatRsToJsonArray(iRole.roleList(con, pageBean, role));
			int total = iRole.roleCount(role);
			result.put("rows", jsonArray);
			result.put("total", total);
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void roleDelete(HttpServletRequest request,
			HttpServletResponse response) {
		String delIds = request.getParameter("delIds");
		JSONObject result = new JSONObject();
		String str[] = delIds.split(",");
		for (int i = 0; i < str.length; i++) {
			boolean f = iUser.existUserWithRoleId(str[i]);
			if (f) {
				result.put("errorIndex", i);
				result.put("errorMsg", "角色下面有用户，不能删除！");
				try {
					ResponseUtil.write(response, result);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
			}
		}
		int delNums = iRole.roleDelete(delIds);
		if (delNums > 0) {
			result.put("success", true);
			result.put("delNums", delNums);
		} else {
			result.put("errorMsg", "删除失败");
		}
		try {
			ResponseUtil.write(response, result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void roleSave(HttpServletRequest request,
			HttpServletResponse response) {
		String roleName = request.getParameter("roleName");
		String roleDescription = request.getParameter("roleDescription");
		String roleId = request.getParameter("roleId");
		Role role = new Role(roleName, roleDescription);
		if (StringUtil.isNotEmpty(roleId)) {
			role.setRoleId(Integer.parseInt(roleId));
		}

		JSONObject result = new JSONObject();
		int saveNums = 0;
		if (StringUtil.isNotEmpty(roleId)) {
			saveNums = iRole.roleUpdate(role);
		} else {
			saveNums = iRole.roleAdd(role);
		}
		if (saveNums > 0) {
			result.put("success", true);
		} else {
			result.put("success", true);
			result.put("errorMsg", "保存失败");
		}
		try {
			ResponseUtil.write(response, result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void auth(HttpServletRequest request, HttpServletResponse response){
		String roleId = request.getParameter("roleId");
		String authIds = request.getParameter("authIds");
		Role role = new Role(Integer.parseInt(roleId), authIds);
		
			JSONObject result = new JSONObject();
			int updateNums = iRole.roleAuthIdsUpdate(role);
			if (updateNums > 0) {
				result.put("success", true);
			} else {
				result.put("errorMsg", "授权失败");
			}
			try {
				ResponseUtil.write(response, result);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
}

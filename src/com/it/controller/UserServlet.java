package com.it.controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.dbutils.DbUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.it.bean.User;
import com.it.bean.PageBean;
import com.it.dao.UserDao;
import com.it.service.IRole;
import com.it.service.IUser;
import com.it.service.RoleImpl;
import com.it.service.UserImpl;
import com.it.utils.DbUtil;
import com.it.utils.JDBCUtils;
import com.it.utils.JsonUtil;
import com.it.utils.ResponseUtil;
import com.it.utils.StringUtil;

/**
 * 用户控制器
 * 
 * 
 * <p>
 * Title:
 * 
 *
 * </p>
 * <p>
 * Description:com.it.controller.UserServlet.java
 * </p>
 * <p>
 * Copyright: Copyright (c) j2se 8.0
 * </p>
 * <p>
 * date: 2018年9月29日  上午11:48:04
 * </p>
 * <p>
 * Company: zxySoft
 * </p>
 * 
 * @author zxy
 */
@WebServlet({ "/UserServlet", "/user" })
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	IUser iUser = new UserImpl();
	IRole iRole = new RoleImpl();
	DbUtil dbUtil = new DbUtil();
	UserDao userDao = new UserDao();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}
    
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		if ("login".equals(action)) {
			login(request, response);
		} else if ("logout".equals(action)) {
			logout(request, response);
		} else if ("modifyPassword".equals(action)) {
			modifyPassword(request, response);
		} else if ("list".equals(action)) {
			list(request, response);
		} else if ("save".equals(action)) {
			save(request, response);
		} else if ("delete".equals(action)) {
			delete(request, response);
		}

	}

	private void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String imageCode = request.getParameter("imageCode");
		request.setAttribute("userName", userName);
		request.setAttribute("password", password);
		request.setAttribute("imageCode", imageCode);
		if (StringUtil.isEmpty(userName) || StringUtil.isEmpty(password)) {
			request.setAttribute("error", "用户名或密码为空！");
			request.getRequestDispatcher("login.jsp")
					.forward(request, response);
			return;
		}
		if (StringUtil.isEmpty(imageCode)) {
			request.setAttribute("error", "验证码为空！");
			request.getRequestDispatcher("login.jsp")
					.forward(request, response);
			return;
		}
		if (!imageCode.equals(session.getAttribute("sRand"))) {
			request.setAttribute("error", "验证码错误！");
			request.getRequestDispatcher("login.jsp")
					.forward(request, response);
			return;
		}
		User user = new User(userName, password);
		User currentUser = iUser.login(user);
		if (currentUser == null) {
			request.setAttribute("error", "用户名或密码错误！");
			request.getRequestDispatcher("login.jsp")
					.forward(request, response);
		} else {
			String roleName = iRole.getRoleNameById(currentUser.getRoleId());
			currentUser.setRoleName(roleName);
			session.setAttribute("currentUser", currentUser);
			response.sendRedirect("main.jsp");
		}
	}
      /**
       * 退出系统
       * @param request
       * @param response
       * @throws ServletException
       * @throws IOException
       */
	private void logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().invalidate();
		response.sendRedirect("login.jsp");
	}
     /**
      * 修改密码
      * @param request
      * @param response
      * @throws ServletException
      * @throws IOException
      */
	private void modifyPassword(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userId");
		String newPassword = request.getParameter("newPassword");
		User user = new User();
		user.setUserId(Integer.parseInt(userId));
		user.setPassword(newPassword);

		JSONObject result = new JSONObject();
		int updateNum = iUser.modifyPassword(user);
		if (updateNum > 0) {
			result.put("success", "true");
			System.out.println("result-->"+result.toString());
			//String str = "{'success':'true'}";
		} else {
			//result.put("success", "true");
			result.put("errorMsg", "修改密码失败！");
		}
		ResponseUtil.write(response, result);

	}
     /**
      * 显示用户列表
      * @param request
      * @param response
      * @throws ServletException
      * @throws IOException
      */
	private void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		User user = new User();
		String s_userName = request.getParameter("s_userName");
		String s_roleId = request.getParameter("s_roleId");
		/**
		 * 判断不为null 和 ""
		 */
		if (StringUtil.isNotEmpty(s_userName)) {
			user.setUserName(s_userName);
		}
		/**
		 * 判断不为null 和 ""
		 */
		if (StringUtil.isNotEmpty(s_roleId)) {
			user.setRoleId(Integer.parseInt(s_roleId));
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page),
				Integer.parseInt(rows));
		Connection con = null;
		try {
			con = dbUtil.getCon();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		JSONObject result = new JSONObject();
		JSONArray jsonArray = null;
		try {
			//把rs-->jsonArray
			jsonArray = JsonUtil.formatRsToJsonArray(userDao.userList(con, pageBean,user));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//获取总数量
		int total = iUser.userCount(user);
		result.put("rows", jsonArray);
		
		result.put("total", total);
		
	
		ResponseUtil.write(response, result);

	}

	private void save(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String roleId = request.getParameter("roleId");
		String userDescription = request.getParameter("userDescription");
		String userId = request.getParameter("userId");
		User user = new User(userName, password, Integer.parseInt(roleId),
				userDescription);
		if (StringUtil.isNotEmpty(userId)) {
			user.setUserId(Integer.parseInt(userId));
		}

		JSONObject result = new JSONObject();

		int saveNums = 0;
		if (StringUtil.isNotEmpty(userId)) {
			saveNums = iUser.userUpdate(user);
		} else {
			if (iUser.existUserWithUserName(userName)) {
				saveNums = -1;
			} else {
				saveNums = iUser.userAdd(user);
			}
		}
		if (saveNums == -1) {
			result.put("success", true);
			result.put("errorMsg", "此用户名已经存在");
		} else if (saveNums == 0) {
			result.put("success", true);
			result.put("errorMsg", "保存失败");
		} else {
			result.put("success", true);
		}
		try {
			ResponseUtil.write(response, result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void delete(HttpServletRequest request, HttpServletResponse response) {
		String delIds = request.getParameter("delIds");

		JSONObject result = new JSONObject();
		System.out.println("delIds-->"+delIds);
		int delNums = iUser.userDelete(delIds);
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

}

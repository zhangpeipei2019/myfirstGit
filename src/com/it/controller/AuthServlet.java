package com.it.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.it.dao.AuthDao;
import com.it.dao.RoleDao;
import com.it.bean.Auth;
import com.it.bean.User;
import com.it.service.AuthImpl;
import com.it.service.IAuth;
import com.it.service.IRole;
import com.it.service.RoleImpl;
import com.it.utils.DbUtil;
import com.it.utils.ResponseUtil;
import com.it.utils.StringUtil;

/**
 * 权限控制器
 * 
 * 
 * <p>
 * Title:
 * 
 *
 * </p>
 * <p>
 * Description:com.it.controller.AuthServlet.java
 * </p>
 * <p>
 * Copyright: Copyright (c) j2se 8.0
 * </p>
 * <p>
 * date: 2018年9月29日  上午11:47:46
 * </p>
 * <p>
 * Company: zxySoft
 * </p>
 * 
 * @author zxy
 */
@WebServlet({ "/AuthServlet", "/auth" })
public class AuthServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	DbUtil dbUtil=new DbUtil();
	AuthDao authDao=new AuthDao();
	RoleDao roleDao=new RoleDao();
	IRole iRole = new RoleImpl();
	IAuth iAuth = new AuthImpl();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action=request.getParameter("action");
		//展示菜单
		if("menu".equals(action)){
			this.menuAction(request, response);
			//权限展示菜单
		}else if("authMenu".equals(action)){
			this.authMenuAction(request, response);
			//权限树展示菜单
		}else if("authTreeGridMenu".equals(action)){
			this.authTreeGridMenuAction(request, response);
		}else if("save".equals(action)){
			this.saveAction(request, response);
		}else if("delete".equals(action)){
			this.deleteAction(request, response);
		}
	}
   /**
    * 根据根目录展示所有菜单
    * @param request
    * @param response
    * @throws ServletException
    * @throws IOException
    */
	private void menuAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取根目录id
		String parentId=request.getParameter("parentId");
		Connection con=null;
		try{
			con=dbUtil.getCon();
			HttpSession session=request.getSession();
			//通过session获取当前用户
			User currentUser=(User)session.getAttribute("currentUser");
			//通过当前用户的查询当前用户的角色id
			//有角色id查询出权限ids集合
			String authIds=iRole.getAuthIdsById(currentUser.getRoleId());
			JSONArray jsonArray=authDao.getAuthsByParentId(con, parentId,authIds);
			System.out.println();
			ResponseUtil.write(response, jsonArray);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	private void authMenuAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String parentId=request.getParameter("parentId");
		String roleId=request.getParameter("roleId");
		Connection con=null;
		try{
			con=dbUtil.getCon();
			String authIds=iRole.getAuthIdsById(Integer.parseInt(roleId));
			JSONArray jsonArray=authDao.getCheckedAuthsByParentId(con, parentId,authIds);
			ResponseUtil.write(response, jsonArray);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void authTreeGridMenuAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String parentId=request.getParameter("parentId");
		Connection con=null;
		try{
			con=dbUtil.getCon();
			JSONArray jsonArray=authDao.getListByParentId(con, parentId);
			ResponseUtil.write(response, jsonArray);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void saveAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String authId=request.getParameter("authId");
		String authName=request.getParameter("authName");
		String authPath=request.getParameter("authPath");
		String parentId =request.getParameter("parentId");
		String authDescription=request.getParameter("authDescription");
		String iconCls=request.getParameter("iconCls");
		Auth auth=new Auth(authName, authPath, authDescription, iconCls);
		if(StringUtil.isNotEmpty(authId)){
			auth.setAuthId(Integer.parseInt(authId));
		}else{
			auth.setParentId(Integer.parseInt(parentId));
		}
		Connection con=null;
		boolean isLeaf=false;
		try{
			JSONObject result=new JSONObject();
			con=dbUtil.getCon();
			int saveNums=0;
			if(StringUtil.isNotEmpty(authId)){
				saveNums=iAuth.authUpdate(auth);
			}else{
				isLeaf=authDao.isLeaf(con, parentId);
				if(isLeaf){
					con.setAutoCommit(false);
					iAuth.updateStateByAuthId("closed", parentId);
					saveNums=iAuth.authAdd(auth);
					con.commit();
				}else{
					saveNums=iAuth.authAdd(auth);
				}
			}
			if(saveNums>0){
				result.put("success", true);
			}else{
				result.put("success", true);
				result.put("errorMsg", "保存失败");
			}
			ResponseUtil.write(response, result);
		}catch(Exception e){
			if(isLeaf){
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void deleteAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String authId=request.getParameter("authId");
		String parentId=request.getParameter("parentId");
		Connection con=null;
		int sonNum=-1;
		try{
			con=dbUtil.getCon();
			JSONObject result=new JSONObject();
			if(!authDao.isLeaf(con, authId)){
				result.put("errorMsg", "该菜单节点有子节点，不能删除！");
			}else{
				int delNums=0;
				sonNum=authDao.getAuthCountByParentId(con, parentId);
				if(sonNum==1){
					con.setAutoCommit(false);
					iAuth.updateStateByAuthId("open", parentId);
					delNums=iAuth.authDelete(authId);
					con.commit();
				}else{
					delNums=iAuth.authDelete(authId);
				}
				if(delNums>0){
					result.put("success", true);
				}else{
					result.put("errorMsg", "删除失败");
				}
			}
			ResponseUtil.write(response, result);
		}catch(Exception e){
			if(sonNum==1){
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

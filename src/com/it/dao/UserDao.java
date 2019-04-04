package com.it.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.it.bean.PageBean;
import com.it.bean.User;
import com.it.utils.JDBCUtils;
import com.it.utils.StringUtil;
/**
 * 
 * 
 * 
 * <p>
 * Title:用户dao
 * 
 *
 * </p>
 * <p>
 * Description:com.it.dao.UserDao.java
 * </p>
 * <p>
 * Copyright: Copyright (c) j2se 8.0
 * </p>
 * <p>
 * date: 2018年9月29日  下午12:07:44
 * </p>
 * <p>
 * Company: zxySoft
 * </p>
 * 
 * @author zxy
 */
public class UserDao {
	QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
/**
 * 用户登录通过账户名和密码
 * @param user
 * @return
 */
	public User login(User user) {
		User resultUser = null;
		String sql = "select * from t_user where userName=? and password=?";
		System.out.println("打印sql语句-------" + sql);
		Object params[] = { user.getUserName(), user.getPassword() };
		try {
			resultUser = queryRunner.query(sql, new BeanHandler<>(User.class),
					params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("查询数据失败!");
		} finally {
			// 释放资源到数据库连接池
			try {
				DbUtils.close(JDBCUtils.getConnections());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException("关闭资源失败!");
			}
		}
		return resultUser;
	}
/**
 * 修改用户密码
 * @param user
 * @return
 */
	public int modifyPassword(User user) {
		String sql = "update t_user set password=? where userId=?";
		System.out.println("打印sql语句-------" + sql);
		Object params[] = { user.getPassword(), user.getUserId() };
		try {
			return queryRunner.update(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("查询数据失败!");
		} finally {
			// 释放资源到数据库连接池
			try {
				DbUtils.close(JDBCUtils.getConnections());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException("关闭资源失败!");
			}
		}
	}
/**
 * 用户列表
 * 
 * @param con
 * @param pageBean
 * @param user
 * @return
 * @throws Exception
 */
	public ResultSet userList(Connection con, PageBean pageBean, User user)
			throws Exception {
		StringBuffer sb = new StringBuffer(
				"select * from t_user u ,t_role r where u.roleId=r.roleId and u.userType!=1 ");
		if (StringUtil.isNotEmpty(user.getUserName())) {
			sb.append(" and u.userName like '%" + user.getUserName() + "%'");
		}
		if (user.getRoleId() != -1) {
			sb.append(" and u.roleId=" + user.getRoleId());
		}
		if (pageBean != null) {
			sb.append(" limit " + pageBean.getStart() + ","
					+ pageBean.getRows());
		}
		System.out.println("用户角色-sql-->"+sb.toString());
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		return pstmt.executeQuery();
	}
   /**
    * 用户数量
    * @param user
    * @return
    */
	public int userCount(User user) {
		StringBuffer sb = new StringBuffer(
				"select count(*) as total from t_user u ,t_role r where u.roleId=r.roleId and u.userType!=1 ");
		if (StringUtil.isNotEmpty(user.getUserName())) {
			sb.append(" and u.userName like '%" + user.getUserName() + "%'");
		}
		if (user.getRoleId() != -1) {
			sb.append(" and u.roleId=" + user.getRoleId());
		}

		System.out.println("sql-->"+sb.toString());
		try {
			  Number num = (Number)queryRunner.query(sb.toString(),new ScalarHandler<>());
			  return num.intValue();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
    /**
     * 添加用户
     * @param user
     * @return
     */
	public int userAdd(User user) {
		String sql = "insert into t_user values(null,?,?,2,?,?)";
		System.out.println("sql-->"+sql);
		Object params[] = { user.getUserName(), user.getPassword(),
				user.getRoleId(), user.getUserDescription() };
		try {
			return queryRunner.update(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
    /**
     * 修改用户
     * @param user
     * @return
     */
	public int userUpdate(User user) {
		String sql = "update t_user set userName=?,password=?,roleId=?,userDescription=? where userId=?";
		Object params[] = { user.getUserName(), user.getPassword(),
				user.getRoleId(), user.getUserDescription(), user.getUserId() };
		System.out.println("sql-->"+sql);
		try {
			return queryRunner.update(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
    /**
     * 判断账户名是否已经存在
     * @param userName
     * @return
     */
	public boolean existUserWithUserName(String userName) {
		String sql = "select * from t_user where userName=?";
		User user = null;
		System.out.println("sql-->"+sql);
		try {
			user = queryRunner.query(sql, new BeanHandler<>(User.class),
					userName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (user != null) {
			return true;
		} else {
			return false;
		}
	}
    /**
     * 删除多个用户
     * @param delIds
     * @return
     */
	public int userDelete(String delIds) {
		String sql = "delete from t_user where userId in (" + delIds + ")";
		try {
			return queryRunner.update(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
     /**
      * 判断该用户是否拥有该角色
      * @param roleId
      * @return
      */
	public boolean existUserWithRoleId(String roleId){
		String sql = "select * from t_user where roleId=?";
		User user=null;
		try {
			user = queryRunner.query(sql, new BeanHandler<>(User.class), roleId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(user!=null){
			return true;
		}else{
			return false;
		}
	}
}

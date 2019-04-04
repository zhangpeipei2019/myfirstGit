package com.it.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.it.bean.PageBean;
import com.it.bean.Role;
import com.it.utils.JDBCUtils;
import com.it.utils.StringUtil;
/**
 * 
* @ClassName: RoleDao
* @Description: 角色dao层
* @author 张佩佩
* @date 2019年3月21日
*
 */
public class RoleDao {
	QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
	/**
     * 通过角色编号获取角色名
     * @param id
     * @return
     */ 
	public String getRoleNameById(int id) {
		String roleName = null;
		String sql = "select roleName from t_role where roleId=?";
		Role role = null;
		try {
			role = queryRunner.query(sql, new BeanHandler<>(Role.class), id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		roleName = role.getRoleName();
		return roleName;
	}
     /**
      *  通过角色id获取权限id字符串
      * @param id
      * @return
      */
	public String getAuthIdsById(int id) {
		String authIds = null;
		String sql = "select authIds from t_role where roleId=?";
		Role role = null;
		try {
			role = queryRunner.query(sql, new BeanHandler<>(Role.class), id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		authIds = role.getAuthIds();

		return authIds;
	}
	 /**
     * 获取所有角色并分页
     * @param con
     * @param pageBean
     * @param role
     * @return
     * @throws Exception
     */
	public ResultSet roleList(Connection con, PageBean pageBean, Role role)
			throws Exception {
		StringBuffer sb = new StringBuffer("select * from t_role");
		if (StringUtil.isNotEmpty(role.getRoleName())) {
			sb.append(" and roleName like '%" + role.getRoleName() + "%'");
		}
		if (pageBean != null) {
			sb.append(" limit " + pageBean.getStart() + ","
					+ pageBean.getRows());
		}
		PreparedStatement pstmt = con.prepareStatement(sb.toString()
				.replaceFirst("and", "where"));
		return pstmt.executeQuery();
	}
	 /**
     * 获取角色总个数
     * @param role
     * @return
     */
	public int roleCount(Role role) {
		StringBuffer sb = new StringBuffer(
				"select count(*) as total from t_role ");
		if (StringUtil.isNotEmpty(role.getRoleName())) {
			sb.append(" and roleName like '%" + role.getRoleName() + "%'");
		}
		try {
			Number num = (Number)queryRunner.query(
					sb.toString().replaceFirst("and", "where"),
					new ScalarHandler<>());
			return num.intValue();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	/**
     * 级联删除角色
     * @param delIds
     * @return
     */
	public int roleDelete(String delIds) {
		String sql = "delete from t_role where roleId in (" + delIds + ")";
		try {
			return queryRunner.update(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	/**
     * 添加角色
     * @param role
     * @return
     */
	public int roleAdd(Role role) {
		String sql = "insert into t_role values(null,?,'',?)";
		Object params[] = { role.getRoleName(), role.getRoleDescription() };
		try {
			return queryRunner.update(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	/**
     * 通过角色id修改角色
     * @param role
     * @return
     */
	public int roleUpdate(Role role) {
		String sql = "update t_role set roleName=?,roleDescription=? where roleId=?";
		Object params[] = { role.getRoleName(), role.getRoleDescription(),
				role.getRoleId() };
		try {
			return queryRunner.update(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	 /**
     * 通过角色id修改权限id
     * 即修改角色功能
     * @param role
     * @return
     */
	public int roleAuthIdsUpdate(Role role) {
		String sql = "update t_role set authIds=? where roleId=?";
		Object params[] = { role.getAuthIds(), role.getRoleId() };
		try {
			return queryRunner.update(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}

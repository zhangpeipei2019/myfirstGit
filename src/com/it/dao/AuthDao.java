package com.it.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.it.bean.Auth;
import com.it.utils.JDBCUtils;
import com.it.utils.StringUtil;
/**
 * 
* @ClassName: AuthDao
* @Description: 权限dao层实现
* @author 张佩佩
* @date 2019年3月21日
*
 */
public class AuthDao {
	QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
	 /**
     * 获取当前权限通过父节点id
     * @param con
     * @param parentId
     * @param authIds
     * @return
     * @throws Exception
     */
	public JSONArray getAuthByParentId(Connection con, String parentId,
			String authIds) throws Exception {
		JSONArray jsonArray = new JSONArray();
		String sql = "select * from t_auth where parentId=? and authId in ("
				+ authIds + ")";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, parentId);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", rs.getInt("authId"));
			jsonObject.put("text", rs.getString("authName"));
			/**
			 * 再次判断是否还有子节点
			 * 如果有sate是文件夹
			 */
			if (!hasChildren(con, rs.getString("authId"), authIds)) {
				jsonObject.put("state", "open");
			} else {
				jsonObject.put("state", rs.getString("state"));
			}
			/**
			 * 设置权限的图片
			 */
			jsonObject.put("iconCls", rs.getString("iconCls"));
			JSONObject attributeObject = new JSONObject();
			attributeObject.put("authPath", rs.getString("authPath"));
			jsonObject.put("attributes", attributeObject);
			jsonArray.add(jsonObject);
			System.out.println("getAuthByParentId--->"+jsonArray.toString());
		}
		return jsonArray;
	}
	/**有权限id和父节点id
     * 判断是否有子节点
     * 
     * @param con
     * @param parentId
     * @param authIds
     * @return
     * @throws Exception
     */
	private boolean hasChildren(Connection con, String parentId, String authIds)
			throws Exception {
		String sql = "select * from t_auth where parentId=? and authId in ("
				+ authIds + ")";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, parentId);
		ResultSet rs = pstmt.executeQuery();
		return rs.next();
	}
	 /**
     * 获取该权限下的所有节点(包含子节点)
     * @param con
     * @param parentId
     * @param authIds
     * @return
     * @throws Exception
     */
	public JSONArray getAuthsByParentId(Connection con, String parentId,
			String authIds) throws Exception {
		JSONArray jsonArray = this.getAuthByParentId(con, parentId, authIds);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			if ("open".equals(jsonObject.getString("state"))) {
				continue;
			} else {
				jsonObject.put(
						"children",
						getAuthsByParentId(con, jsonObject.getString("id"),
								authIds));
			}
		}
		return jsonArray;
	}
	/**
     * 获取拥有的权限，并让其选中状态
     * @param con
     * @param parentId
     * @param authIds
     * @return
     * @throws Exception
     */
	public JSONArray getCheckedAuthByParentId(Connection con, String parentId,
			String authIds) throws Exception {
		JSONArray jsonArray = new JSONArray();
		String sql = "select * from t_auth where parentId=? ";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, parentId);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			JSONObject jsonObject = new JSONObject();
			int authId = rs.getInt("authId");
			jsonObject.put("id", authId);
			jsonObject.put("text", rs.getString("authName"));
			jsonObject.put("state", rs.getString("state"));
			jsonObject.put("iconCls", rs.getString("iconCls"));
			if (StringUtil.existStrArr(authId + "", authIds.split(","))) {
				jsonObject.put("checked", true);
			}
			JSONObject attributeObject = new JSONObject();
			attributeObject.put("authPath", rs.getString("authPath"));
			jsonObject.put("attributes", attributeObject);
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}

	public JSONArray getCheckedAuthsByParentId(Connection con, String parentId,
			String authIds) throws Exception {
		JSONArray jsonArray = this.getCheckedAuthByParentId(con, parentId,
				authIds);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			if ("open".equals(jsonObject.getString("state"))) {
				continue;
			} else {
				jsonObject.put(
						"children",
						getCheckedAuthsByParentId(con,
								jsonObject.getString("id"), authIds));
			}
		}
		return jsonArray;
	}
	 /**
     * 获取树形目录权限通过父节点
     * @param con
     * @param parentId
     * @return
     * @throws Exception
     */
	public JSONArray getTreeGridAuthByParentId(Connection con, String parentId)
			throws Exception {
		JSONArray jsonArray = new JSONArray();
		String sql = "select * from t_auth where parentId=? ";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, parentId);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", rs.getInt("authId"));
			jsonObject.put("text", rs.getString("authName"));
			jsonObject.put("state", rs.getString("state"));
			jsonObject.put("iconCls", rs.getString("iconCls"));
			jsonObject.put("authPath", rs.getString("authPath"));
			jsonObject.put("authDescription", rs.getString("authDescription"));
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}
	/**
     * 获取集合通过父节点
     * @param con
     * @param parentId
     * @return
     * @throws Exception
     */
	public JSONArray getListByParentId(Connection con, String parentId)
			throws Exception {
		JSONArray jsonArray = this.getTreeGridAuthByParentId(con, parentId);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			if ("open".equals(jsonObject.getString("state"))) {
				continue;
			} else {
				jsonObject.put("children",
						getListByParentId(con, jsonObject.getString("id")));
			}
		}
		return jsonArray;
	}
	 /**
     * 添加权限
     * @param auth
     * @return
     */
	public int authAdd(Auth auth) {
		String sql = "insert into t_auth values(null,?,?,?,?,'open',?)";
		Object params[] = { auth.getAuthName(), auth.getAuthPath(),
				auth.getParentId(), auth.getAuthDescription(),
				auth.getIconCls() };
		try {
			return queryRunner.update(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	 /**
     * 修改权限
     * @param auth
     * @return
     */
	public int authUpdate(Auth auth) {
		String sql = "update t_auth set authName=?,authPath=?,authDescription=?,iconCls=? where authId=?";
		Object params[] = { auth.getAuthName(), auth.getAuthPath(),
				auth.getAuthDescription(), auth.getIconCls(), auth.getAuthId() };
		try {
			return queryRunner.update(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	 /**
     * 通过当前权限id判断是否是叶子节点
     * @param con
     * @param authId
     * @return
     * @throws Exception
     */
	public boolean isLeaf(Connection con, String authId) throws Exception {
		String sql = "select * from t_auth where parentId=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, authId);
		ResultSet rs = pstmt.executeQuery();
		return !rs.next();
	}
	 /**
     * 修改权限
     * @param state
     * @param authId
     * @return
     */
	public int updateStateByAuthId(String state, String authId) {
		String sql = "update t_auth set state=? where authId=?";
		Object params[] = { state, authId };
		try {
			return queryRunner.update(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	 /**
     * 删除权限
     * @param authId
     * @return
     */
	public int authDelete(String authId) {
		String sql = "delete from t_auth where authId=?";
		try {
			return queryRunner.update(sql, authId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	  /**
     * 通过权限父节点获取权限的数量
     * @param con
     * @param parentId
     * @return
     * @throws Exception
     */
	public int getAuthCountByParentId(Connection con, String parentId)
			throws Exception {
		String sql = "select count(*) as total from t_auth where parentId=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, parentId);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return rs.getInt("total");
		} else {
			return 0;
		}
	}
}

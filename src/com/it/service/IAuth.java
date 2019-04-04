package com.it.service;



import com.it.bean.Auth;

import net.sf.json.JSONArray;

public interface IAuth {
	/**
	 * 按照父节点查询当前权限
	 * @param parentId
	 * @param authIds
	 * @return
	 */
	public JSONArray getAuthByParentId(String parentId,String authIds);
	/**
	 * 根据父节点和当前权限id查询该用户是否有子节点
	 * @param parentId
	 * @param authIds
	 * @return
	 */
	public boolean hasChildren(String parentId,String authIds);
	/**
	 * 按照父节点查询当前权限以及子权限
	 * @param parentId
	 * @param authIds
	 * @return
	 */
	public JSONArray getAuthsByParentId(String parentId,String authIds);
	/**
	 * 根据父节点和当前权限id查询当前权限处于选中状态的权限
	 * @param parentId
	 * @param authIds
	 * @return
	 */
	public JSONArray getCheckedAuthByParentId(String parentId,String authIds);
	/**根据父节点和当前权限id查询所有权限处于选中状态的权限
	 * 
	 * @param parentId
	 * @param authIds
	 * @return
	 */
	public JSONArray getCheckedAuthsByParentId(String parentId,String authIds);
	/**
	 * 根据父节点查询树目录权限
	 * @param parentId
	 * @return
	 */
	public JSONArray getTreeGridAuthByParentId(String parentId);
	/**
	 * 根据父节点查询所有节点
	 * @param parentId
	 * @return
	 */
	public JSONArray getListByParentId(String parentId);
	/**
	 * 添加权限
	 * @param auth
	 * @return
	 */
	public int authAdd(Auth auth);
	/**
	 * 修改权限
	 * @param auth
	 * @return
	 */
	public int authUpdate(Auth auth);
	/**
	 * 根据权限id判断是否是叶子节点
	 * @param authId
	 * @return
	 */
	public boolean isLeaf(String authId);
	/**
	 * 根据权限id修改状态
	 * @param state
	 * @param authId
	 * @return
	 */
	public int updateStateByAuthId(String state,String authId);
	/**
	 * 删除权限
	 * @param authId
	 * @return
	 */
	public int authDelete(String authId);
	/**
	 * 根据父节点查询在父节点所有子节点的个数
	 * @param parentId
	 * @return
	 */
	public int getAuthCountByParentId(String parentId);
	
	
}

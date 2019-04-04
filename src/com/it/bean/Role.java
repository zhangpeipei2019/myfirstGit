package com.it.bean;
/**
 * 
* @ClassName: Role
* @Description: TODO(这里用一句话描述这个类的作用)
* @author 张佩佩
* @date 2019年3月21日
*
 */
public class Role {

	private int roleId;//角色编号
	private String roleName;//角色名字
	private String authIds;//该角色拥有的权限id列表
	private String authStrs;//权限
	private String roleDescription;//角色描述
	
	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Role(String roleName, String roleDescription) {
		super();
		this.roleName = roleName;
		this.roleDescription = roleDescription;
	}
	
	public Role(int roleId, String authIds) {
		super();
		this.roleId = roleId;
		this.authIds = authIds;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getAuthIds() {
		return authIds;
	}
	public void setAuthIds(String authIds) {
		this.authIds = authIds;
	}
	public String getAuthStrs() {
		return authStrs;
	}
	public void setAuthStrs(String authStrs) {
		this.authStrs = authStrs;
	}
	public String getRoleDescription() {
		return roleDescription;
	}
	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}
	
	
}

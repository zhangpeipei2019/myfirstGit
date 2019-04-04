package com.it.bean;
/**
 * 
* @ClassName: User
* @Description: TODO(这里用一句话描述这个类的作用)
* @author 张佩佩
* @date 2019年3月21日
*
 */
public class User {

	private int userId;//用户编号
	private String userName;//用户名
	private String password;//用户密码
	private int userType;//用户类型
	private int roleId=-1;//角色id，默认该用户没有角色
	private String roleName;//角色名
	private String userDescription;//用户说明
		
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
	
	
	
	public User(String userName, String password, int roleId,
			String userDescription) {
		super();
		this.userName = userName;
		this.password = password;
		this.roleId = roleId;
		this.userDescription = userDescription;
	}

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getUserDescription() {
		return userDescription;
	}
	public void setUserDescription(String userDescription) {
		this.userDescription = userDescription;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	
}

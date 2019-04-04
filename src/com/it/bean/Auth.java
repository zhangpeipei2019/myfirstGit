package com.it.bean;
/**
 * 
* @ClassName: Auth
* @Description: TODO(这里用一句话描述这个类的作用)
* @author 张佩佩
* @date 2019年3月21日
*
 */
public class Auth {

	private int authId;//权限编号
	private String authName;//权限名字
	private String authPath;//权限路径
	private int parentId;//父节点
	private String authDescription;//权限说明
	private String state;//状态
	private String iconCls;//图片
	
	
	
	public Auth() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Auth(String authName, String authPath, String authDescription,
			String iconCls) {
		super();
		this.authName = authName;
		this.authPath = authPath;
		this.authDescription = authDescription;
		this.iconCls = iconCls;
	}


	public int getAuthId() {
		return authId;
	}
	public void setAuthId(int authId) {
		this.authId = authId;
	}
	public String getAuthName() {
		return authName;
	}
	public void setAuthName(String authName) {
		this.authName = authName;
	}
	public String getAuthPath() {
		return authPath;
	}
	public void setAuthPath(String authPath) {
		this.authPath = authPath;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public String getAuthDescription() {
		return authDescription;
	}
	public void setAuthDescription(String authDescription) {
		this.authDescription = authDescription;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	
	
}

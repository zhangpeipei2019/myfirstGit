package com.it.bean;
/**
 * 
* @ClassName: PageBean
* @Description: TODO(这里用一句话描述这个类的作用)
* @author 张佩佩
* @date 2019年3月21日
*
 */
public class PageBean {

	private int page; // 第几页
	private int rows; // 每页记录数
	private int start;  // 起始页
	
	
	public PageBean(int page, int rows) {
		super();
		this.page = page;
		this.rows = rows;
	}
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getStart() {
		return (page-1)*rows;
	}
	
	
}

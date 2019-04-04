package com.it.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;


public class JDBCUtils {
	private static String driverClassName;
	private static String url;
	private static String username;
	private static String password;
	/*
	 * 创建连接池BasicDataSource
	 */
	static BasicDataSource dataSource = new BasicDataSource();
	
	//静态代码块
	static {
		try {
			readConfig();
			//基本4项
			//对连接池对象 进行基本的配置
			dataSource.setDriverClassName(driverClassName);
			//指定要连接的数据库地址
			dataSource.setUrl(url);
			//指定要连接数据的用户名
			dataSource.setUsername(username);
			//指定要连接数据的用户名
			dataSource.setPassword(password);
			//可以选项
			//初始化连接
			dataSource.setInitialSize(10);
			//最大连接数量
			dataSource.setMaxActive(8);
			//最大空闲连接
			dataSource.setMaxIdle(5);
			//最小空闲连接
			dataSource.setMinIdle(3);
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void readConfig()throws Exception{
		InputStream in = JDBCUtils .class.getClassLoader().getResourceAsStream("db.properties");

		Properties pro = new Properties();
		 pro.load(in);
		 driverClassName =pro.getProperty("driverClass");
		 url = pro.getProperty("url");
		 username = pro.getProperty("username");
		 password = pro.getProperty("password");
	}
	
	public static DataSource getDataSource(){
		return dataSource;
	}
	/*
	 * 获取链接
	 */
	public static Connection getConnections(){
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}

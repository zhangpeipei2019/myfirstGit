package com.it.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
/**
 * 
 * 
 * 
 * <p>
 * Title:
 * 
 *
 * </p>
 * <p>
 * Description:com.it.util.ResponseUtil.java
 * </p>
 * <p>
 * Copyright: Copyright (c) j2se 8.0
 * </p>
 * <p>
 * date: 2018年9月14日  下午4:56:49
 * </p>
 * <p>
 * Company: zxySoft
 * </p>
 * 
 * @author zxy
 */
public class ResponseUtil {

	public static void write(HttpServletResponse response,Object o) throws IOException{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out=response.getWriter();
		System.out.println("后台给前台传值-->"+o.toString());
		out.println(o.toString());
		out.flush();
		out.close();
	}
}

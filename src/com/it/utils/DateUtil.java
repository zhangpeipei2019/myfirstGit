package com.it.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 日期处理公共资源类
 * 
 * 
 * <p>
 * Title:
 * 
 *
 * </p>
 * <p>
 * Description:com.it.util.DateUtil.java
 * </p>
 * <p>
 * Copyright: Copyright (c) j2se 8.0
 * </p>
 * <p>
 * date: 2018年9月14日  下午4:56:08
 * </p>
 * <p>
 * Company: zxySoft
 * </p>
 * 
 * @author zxy
 */
public class DateUtil {

	public static String formatDate(Date date,String format){
		String result="";
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		if(date!=null){
			result=sdf.format(date);
		}
		return result;
	}
	
	
	public static Date formatString(String str,String format) throws Exception{
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		return sdf.parse(str);
	}
}

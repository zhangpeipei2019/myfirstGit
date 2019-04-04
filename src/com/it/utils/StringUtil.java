package com.it.utils;
/**
 * 字符串处理类
 * 
 * 
 * <p>
 * Title:
 * 
 *
 * </p>
 * <p>
 * Description:com.it.util.StringUtil.java
 * </p>
 * <p>
 * Copyright: Copyright (c) j2se 8.0
 * </p>
 * <p>
 * date: 2018年9月14日  下午4:56:57
 * </p>
 * <p>
 * Company: zxySoft
 * </p>
 * 
 * @author zxy
 */
public class StringUtil {

	public static boolean isEmpty(String str){
		if("".equals(str)|| str==null){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isNotEmpty(String str){
		if(!"".equals(str)&&str!=null){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean existStrArr(String str,String []strArr){
		for(int i=0;i<strArr.length;i++){
			if(strArr[i].equals(str)){
				return true;
			}
		}
		return false;
	}
}

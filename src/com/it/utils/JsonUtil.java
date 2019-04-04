package com.it.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * json
 * 
 * 
 * <p>
 * Title:
 * 
 *
 * </p>
 * <p>
 * Description:com.it.util.JsonUtil.java
 * </p>
 * <p>
 * Copyright: Copyright (c) j2se 8.0
 * </p>
 * <p>
 * date: 2018年9月14日  下午4:56:25
 * </p>
 * <p>
 * Company: zxySoft
 * </p>
 * 
 * @author zxy
 */
public class JsonUtil {
     /**
      * 结果集合中不是一个单独的对象，是几个对象字段的集合
      * 把一个结果集合封装称为一个JSONObject存入JSONArray
      * @param rs
      * @return
      * @throws Exception
      */
	public static JSONArray formatRsToJsonArray(ResultSet rs)throws Exception{
		// 获取此 ResultSet 对象的列的编号、类型和属性。
		ResultSetMetaData md=rs.getMetaData();
		//  返回此 ResultSet 对象中的列数。
		int num = md.getColumnCount();
		JSONArray array=new JSONArray();
		while(rs.next()){
			JSONObject mapOfColValues=new JSONObject();
			for(int i=1;i<=num;i++){
				Object o=rs.getObject(i);
				if(o instanceof Date){
					//     getColumnName() 获取指定列的名称。
					mapOfColValues.put(md.getColumnName(i), DateUtil.formatDate((Date)o, "yyyy-MM-dd"));
				}else{
					mapOfColValues.put(md.getColumnName(i), rs.getObject(i));					
				}
			}
			System.out.println("********************************8");
			System.out.println("mapOfColValues--->"+mapOfColValues);
			System.out.println("********************************8");
			array.add(mapOfColValues);
		}
		return array;
	}
}

package com.wangxiao.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;

public class JSONUtil {

	
	public static JSONObject handleData(HttpServletRequest request) throws IOException {
		StringBuffer sb = new StringBuffer();
		InputStream is = request.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
		String s = "";
		while((s = br.readLine()) != null) {
			sb.append(s);
		}
		if(sb.toString().length() <= 0) {
			return null;
		}else {
			return JSONObject.parseObject(sb.toString());
		}
	}
}

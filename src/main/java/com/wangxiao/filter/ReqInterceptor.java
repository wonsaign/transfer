package com.wangxiao.filter;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.common.base.Strings;
import com.wangxiao.utils.TencentConfig;

@Component
public class ReqInterceptor extends HandlerInterceptorAdapter{
	
	static Logger logger = LoggerFactory.getLogger(ReqInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("RequestPath:{}",request.getRequestURI());
		String appid = TencentConfig.getResource().getString("SdkAppid");
		String req_appid = request.getParameter("SdkAppid");
		if(Strings.isNullOrEmpty(req_appid) || !appid.equals(req_appid) ) {
			logger.info("SdkAppid is not correct,required {},but {}",appid,req_appid);
			response.setStatus(403);
			return false;
		}
		return true;
	}
	
}

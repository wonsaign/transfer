package com.wangxiao.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebFilterConfig implements WebMvcConfigurer {

	@Autowired
	private ReqInterceptor reqInterceptor;
	
    /*使用addPathPatterns增加拦截规则，使用excludePathPatterns排除拦截规则
    / admin/**：代表http://域名/admin/** 拦截该目录下的所有目录及子目录
    / admin：代表http://域名/admin 仅拦截此形式访问（无法拦截/admin/ 形式）
    / admin/*：代表http://域名/admin/* 拦截该目录的所有下级目录不包含子目录（可以拦截/admin/ 形式）
     */
	@Bean
    public WebMvcConfigurer webMvcConfigurer() {
    	return new WebFilterConfig() {
    		@Override
    		public void addInterceptors(InterceptorRegistry registry) {
    			registry.addInterceptor(reqInterceptor).addPathPatterns("/*");
    			registry.addInterceptor(reqInterceptor).excludePathPatterns("/cache/**");
    		}
    	};
    }

    
}

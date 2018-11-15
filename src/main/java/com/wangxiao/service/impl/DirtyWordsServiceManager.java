package com.wangxiao.service.impl;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

import com.wangxiao.dao.DirtyWordsMapper;
import com.wangxiao.entity.DirtyWords;
import com.wangxiao.service.DirtyWordsService;
import com.wangxiao.utils.SensitivewordFilter;

@Service
public class DirtyWordsServiceManager implements DirtyWordsService,InitializingBean,ServletContextAware{

	@Autowired
	private DirtyWordsMapper dirtyWordsMapper;
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		init();
	}
	
	private void init(){
		List<DirtyWords> words = dirtyWordsMapper.findAll();
		SensitivewordFilter.init(words, "getDirtyWords");
	}

	@Override
	public void reload() {
		SensitivewordFilter.removeAll();
		init();
	}
	
}

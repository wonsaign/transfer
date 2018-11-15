package com.wangxiao.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wangxiao.service.DirtyWordsService;

@RestController
@RequestMapping("/cache")
public class CacheController {

	static Logger log = LoggerFactory.getLogger(CacheController.class);
	
	@Autowired
	private DirtyWordsService dirtyWordsService;
	
	@PostMapping("dirty")
	public String getMsg(HttpServletRequest request){
		try {
			dirtyWordsService.reload();
			return "Success";
		} catch (Exception e) {
			log.error("刷新缓存失败");
			return "Fail";
		}
	}
}

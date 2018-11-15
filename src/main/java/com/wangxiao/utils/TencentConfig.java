package com.wangxiao.utils;

import java.util.ResourceBundle;
/**
 * Tencent 配置类
 * @author wangs
 *
 */
public class TencentConfig {
	
	private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("tencent_config");
	
	public static ResourceBundle getResource() {
		return resourceBundle;
	}

}

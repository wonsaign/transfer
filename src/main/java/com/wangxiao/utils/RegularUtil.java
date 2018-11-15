package com.wangxiao.utils;

public class RegularUtil {

	public static boolean isPhone(String matched) {
		return matched.matches("0?(13|14|15|17|18|19)[0-9]{9}");
	}
	public static String replacePhone(String matched) {
		return matched.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
	}
	public static boolean isEmail(String matched) {
		return matched.matches("^\\w+@(\\w+\\.)+\\w+$");
	}
	public static boolean isCh(String matched) {
		return matched.matches("[\u4e00-\u9fa5]");
	}
	public static boolean isURL(String matched) {
		return matched.matches("^((https|http|ftp|rtsp|mms)?:\\/\\/)[^\\s]+");
	}	
	public static boolean isQQ(String matched) {
		return matched.matches("[1-9]([0-9]{5,11})");
	}
	public static String replaceQQ(String matched) {
		return matched.replaceAll("[1-9]([0-9]{5,11})", "****");
	}
	public static boolean isPostcode(String matched) {
		return matched.matches("\\d{6}");
	}
	public static boolean isVerifyCode(String matched) {
		return matched.matches("\\d{6}");
	}
	public static boolean isIP(String matched) {
		return matched.matches("(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\."
				+ "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\."
				+ "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\."
				+ "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)");
	}
	public static boolean isIDCode(String matched) {
		return matched.matches("\\d{17}[\\d|x]|\\d{15}");
	}	
	/**
	 * 必须是字母开头
	 * @param matched
	 * @return
	 */
	public static boolean isAccount(String matched) {
		return matched.matches("[A-Za-z][A-Za-z0-9_\\-\\u4e00-\\u9fa5]+");
	}
	
}
package com.wangxiao.utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * DFA算法
 * @author wangs
 * {@link} https://www.cnblogs.com/zyguo/p/4705086.html
 */
public class SensitivewordFilter {

	@SuppressWarnings("rawtypes")
	private static Map sensitiveWordMap;
	
	private static final int MINMATCHTYPE = 1; // 最小匹配规则
	@SuppressWarnings("unused")
	private static final int MAXMATCHTYPE = 2; // 最大匹配规则

	/**
	 * 初始化敏感词
	 * @param sensitiveWords
	 * @param methodName
	 */
	public static void init(List<?> sensitiveWords, String methodName) {
		sensitiveWordMap = initKeyWord(sensitiveWords, methodName);
	}
	
	public static void removeAll() {
		if(sensitiveWordMap == null || sensitiveWordMap.size() == 0) {
			return;
		}
		// JVM 自己去清理
		sensitiveWordMap = null;
	}
	
	/**
	 * 判断文字是否包含敏感字符
	 * 
	 * @param txt
	 *            文字
	 * @param matchType
	 *            匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
	 * @return 若包含返回true，否则返回false
	 * @version 1.0
	 */
	public boolean isContaintSensitiveWord(String txt, int matchType) {
		boolean flag = false;
		for (int i = 0; i < txt.length(); i++) {
			int matchFlag = checkSensitiveWord(txt, i, matchType);
			if (matchFlag > 0) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 获取文字中的敏感词
	 * 
	 * @param txt
	 *            文字
	 * @param matchType
	 *            匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
	 * @return
	 * @version 1.0
	 */
	public static Set<String> getSensitiveWord(String txt, int matchType) {
		Set<String> sensitiveWordList = new HashSet<String>();
		for (int i = 0; i < txt.length(); i++) {
			int length = checkSensitiveWord(txt, i, matchType);
			if (length > 0) {
				sensitiveWordList.add(txt.substring(i, i + length));
				i = i + length - 1;
			}
		}
		return sensitiveWordList;
	}

	/**
	 * 替换敏感字字符
	 * 
	 * @date 2014年4月20日 下午5:12:07
	 * @param txt
	 * @param matchType
	 * @param replaceChar
	 *            替换字符，默认*
	 * @version 1.0
	 */
	public static String replaceSensitiveWord(String txt, int matchType, String replaceChar) {
		if(txt == null || "".equals(txt)) {
			return txt;
		}
		String resultTxt = txt;
		Set<String> set = getSensitiveWord(txt, matchType);
		Iterator<String> iterator = set.iterator();
		String word = null;
		String replaceString = null;
		while (iterator.hasNext()) {
			word = iterator.next();
			replaceString = getReplaceChars(replaceChar, word.length());
			resultTxt = resultTxt.replaceAll(word, replaceString);
		}
		// 过滤QQ号
		resultTxt = RegularUtil.replaceQQ(resultTxt);
		
		return resultTxt;
	}



	/**
	 * 检查文字中是否包含敏感字符，检查规则如下：<br>
	 * 
	 * @param txt
	 * @param beginIndex
	 * @param matchType
	 * @return，如果存在，则返回敏感词字符的长度，不存在返回0
	 * @version 1.0
	 */
	@SuppressWarnings({ "rawtypes" })
	public static int checkSensitiveWord(String txt, int beginIndex, int matchType) {
		boolean flag = false;
		int matchFlag = 0;
		char word = 0;
		Map nowMap = sensitiveWordMap;
		for (int i = beginIndex; i < txt.length(); i++) {
			word = txt.charAt(i);
			nowMap = (Map) nowMap.get(word);
			if (nowMap != null) {
				matchFlag++;
				if ("1".equals(nowMap.get("isEnd"))) {
					flag = true;
					if (SensitivewordFilter.MINMATCHTYPE == matchType) {
						break;
					}
				}
			} else {
				break;
			}
		}
		if (matchFlag < 2 || !flag) {
			matchFlag = 0;
		}
		return matchFlag;
	}
	
	/**
	 * 获取替换字符串
	 * 
	 * @date 2014年4月20日 下午5:21:19
	 * @param replaceChar
	 * @param length
	 * @return
	 * @version 1.0
	 */
	private static String getReplaceChars(String replaceChar, int length) {
		String resultReplace = replaceChar;
		for (int i = 1; i < length; i++) {
			resultReplace += replaceChar;
		}
		return resultReplace;
	}
	/**
	 * @version 1.0
	 */
	@SuppressWarnings("rawtypes")
	private static Map initKeyWord(List<?> sensitiveWords, String methodName) {
		try {
			Set<String> keyWordSet = new HashSet<String>();
			for (Object s : sensitiveWords) {
				Method m = s.getClass().getMethod(methodName);
				keyWordSet.add(((String)m.invoke(s)).trim());
			}
			addSensitiveWordToHashMap(keyWordSet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sensitiveWordMap;
	}
	
	/**
	 * @param keyWordSet
	 *            敏感词库
	 * @version 1.0
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void addSensitiveWordToHashMap(Set<String> keyWordSet) {
		sensitiveWordMap = new HashMap(keyWordSet.size());
		String key = null;
		Map nowMap = null;
		Map<String, String> newWorMap = null;

		Iterator<String> iterator = keyWordSet.iterator();
		while (iterator.hasNext()) {
			key = iterator.next();
			nowMap = sensitiveWordMap;
			for (int i = 0; i < key.length(); i++) {
				char keyChar = key.charAt(i);
				Object wordMap = nowMap.get(keyChar);

				if (wordMap != null) {
					nowMap = (Map) wordMap;
				} else {
					newWorMap = new HashMap<String, String>();
					newWorMap.put("isEnd", "0");
					nowMap.put(keyChar, newWorMap);
					nowMap = newWorMap;
				}

				if (i == key.length() - 1) {
					nowMap.put("isEnd", "1");
				}
			}
		}
	}

}
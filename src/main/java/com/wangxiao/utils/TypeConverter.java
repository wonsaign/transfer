package com.wangxiao.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * 类型转换器
 * 
 * 转换从数据库中取得的类型。
 */
public interface TypeConverter {

	public static int toInteger(Object obj, int val) {
		Integer i = toInteger(obj);
		return i == null ? val : i.intValue();
	}

	public static Integer toInteger(Object obj) {
		// 如果为空或整型
		if (obj == null || (obj instanceof Integer)) {
			return (Integer) obj;
		}
		if (obj instanceof String) {
			return Integer.valueOf((String)obj);
		}
		if (obj instanceof Number) {
			return ((Number) obj).intValue();
		}
		if (obj instanceof Date) {
			return (int) (((Date) obj).getTime() / 1000);
		}
		return Integer.valueOf(obj.toString());
	}

	public static Byte toByte(Object obj) {
		// 如果为空或整型
		if (obj == null || (obj instanceof Byte)) {
			return (Byte) obj;
		}
		if (obj instanceof String) {
			return Byte.valueOf((String) obj);
		}
		if (obj instanceof Number) {
			return ((Number) obj).byteValue();
		}
		return null;
	}

	public static Short toShort(Object obj) {
		// 如果为空或整型
		if (obj == null || (obj instanceof Short)) {
			return (Short) obj;
		}
		if (obj instanceof String) {
			return Short.valueOf((String) obj);
		}
		if (obj instanceof Number) {
			return ((Number) obj).shortValue();
		}
		return null;
	}

	public static double toDouble(Object obj, double val) {
		Double d = toDouble(obj);
		return d == null ? val : d.doubleValue();
	}

	public static Double toDouble(Object obj) {
		if (obj == null || obj instanceof Double) {
			return (Double) obj;
		}
		if (obj instanceof Number) {
			return ((Number) obj).doubleValue();
		}
		if (obj instanceof CharSequence) {
			return Double.valueOf((double) obj);
		}
		if (obj instanceof Date) {
			return Double.valueOf(((Date) obj).getTime());
		}
		return Double.valueOf(obj.toString());
	}

	public static long toLong(Object obj, long val) {
		Long v = toLong(obj);
		return v == null ? val : v.longValue();
	}

	public static Long toLong(Object obj) {
		if (obj == null || obj instanceof Long) {
			return (Long) obj;
		}
		if (obj instanceof Number) {
			return ((Number) obj).longValue();
		}
		if (obj instanceof Date) {
			return ((Date) obj).getTime();
		}
		return Long.valueOf((obj.toString()));
	}


	public static boolean toBoolean(Object obj, boolean v) {
		Boolean b = toBoolean(obj);
		return b == null ? v : b.booleanValue();
	}

	public static <T> List<T> toList(Object v, Class<T> type) {
		List<T> retVal = new ArrayList<>();
		if (v == null)
			return null;
		if (!(v instanceof Collection)) {
			throw new IllegalArgumentException("变换为List,必须为集合对象。");
		}
		for (Object obj : ((Collection<?>) v)) {
			retVal.add((T) toType(obj, type));
		}
		return retVal;
	}

	public static <T> Set<T> toSet(Object v, Class<T> type) {
		Set<T> retVal = new HashSet<>();
		if (v == null)
			return null;
		if (!(v instanceof Collection)) {
			throw new IllegalArgumentException("变换为List,必须为集合对象。");
		}
		for (Object obj : ((Collection<?>) v)) {
			retVal.add((T) toType(obj, type));
		}
		return retVal;
	}

	public static BigDecimal toBigDecimal(Object obj) {
		if (obj == null || (obj instanceof BigDecimal)) {
			return (BigDecimal) obj;
		}
		if (obj instanceof Double) {
			return new BigDecimal((Double) obj);
		}
		if (obj instanceof Integer) {
			return new BigDecimal((Integer) obj);
		}
		if (obj instanceof Long) {
			return new BigDecimal((Long) obj);
		}
		if (obj instanceof Float) {
			return new BigDecimal((Float) obj);
		}
		if (obj instanceof BigInteger) {
			return new BigDecimal((BigInteger) obj);
		}
		try {
			if (obj instanceof String) {
				return new BigDecimal((String) obj);
			}
			return new BigDecimal(obj.toString());
		} catch (Exception e) {
			// NOP
		}
		return null;
	}

	public static Boolean toBoolean(Object obj) {
		// 如果是空或布尔类弄，直接返回
		if (obj == null || obj instanceof Boolean) {
			return (Boolean) obj;
		}
		// 如果是数字，仅当为0时，返因TRUE;
		if (obj instanceof Number) {
			return ((Number) obj).intValue() != 0 ? Boolean.TRUE
					: Boolean.FALSE;
		}
		// 如果是串的情况，当为真时，返回TRUE
		if (obj instanceof String) {
			String s = ((String) obj).trim();
			if (s.isEmpty()) {
				return null;
			}
			char c = Character.toUpperCase(s.charAt(0));
			if (c == 'T' || c == 'Y' || c == '1' || c == '是') {
				return Boolean.TRUE;
			}
			if (c == 'f' || c == 'N' || c == '0' || c == '否') {
				return Boolean.FALSE;
			}
		}
		// XXX: 可能从已经状态不能确定是否为TRUE/FALSE。
		return null;
	}

	public static BigInteger toBigInteger(Object bg) {
		if (bg == null || bg instanceof BigInteger) {
			return (BigInteger) bg;
		}

		if (bg instanceof Number) {
			return BigInteger.valueOf(((Number) bg).longValue());
		}

		if (bg instanceof Date) {
			return BigInteger.valueOf(((Date) bg).getTime());
		}
		return null;
	}


	public static float toFloat(Object obj, float val) {
		Float f = toFloat(obj);
		return f == null ? val : f.floatValue();
	}

	public static Float toFloat(Object obj) {
		if (obj == null || obj instanceof Float) {
			return (Float) obj;
		}
		if (obj instanceof Number) {
			return ((Number) obj).floatValue();
		}
		if (obj instanceof String) {
			Float.valueOf((String) obj);
		}
		if (obj instanceof Date) {
			return new Float(((Date) obj).getTime());
		}
		return Float.valueOf(obj.toString());
	}

	@SuppressWarnings({ "unchecked" })
	public static <T> T toType(Object obj, Class<T> type) {
		if (obj == null || type.isAssignableFrom(obj.getClass())) {
			return (T) obj;
		}
		if (type.equals(String.class)) {
			return (T) obj.toString();
		}
		if (type.equals(int.class) || type.equals(Integer.class)) {
			return (T) toInteger(obj);
		}
		if (type.equals(long.class) || type.equals(Long.class)) {
			return (T) toLong(obj);
		}
		if (type.equals(double.class) || type.equals(Double.class)) {
			return (T) toDouble(obj);
		}
		if (type.equals(float.class) || type.equals(Float.class)) {
			return (T) toFloat(obj);
		}
		if (type.equals(boolean.class) || type.equals(Boolean.class)) {
			return (T) toBoolean(obj);
		}
		if (type.equals(BigInteger.class)) {
			return (T) toBigInteger(obj);
		}
		if (type.equals(BigDecimal.class)) {
			return (T) toBigDecimal(obj);
		}
		if (type.equals(Short.class)) {
			return (T) toShort(obj);
		}
		if (type.equals(Byte.class)) {
			return (T) toByte(obj);
		}
		return null;
	}
}

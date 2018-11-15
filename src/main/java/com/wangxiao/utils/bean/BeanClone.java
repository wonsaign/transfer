package com.wangxiao.utils.bean;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wangxiao.utils.TypeConverter;

/**
 * Clone a bean.
 *
 */
public abstract class BeanClone {
	private static Logger logger = LoggerFactory.getLogger(BeanClone.class);

	/**
	 * 克隆所有字段。
	 * 
	 * @param source
	 * @param target
	 * @return 克隆对象。
	 */
	@SuppressWarnings("unchecked")
	static <T> T dupAll(T source, T target) {
		if (target == null //
				&& (source instanceof Cloneable)) {
			return (T)clone(source);
		}
		Map<String, Field> ff = BeanContextFactory.getFileds(source.getClass());
		for (Field f : ff.values()) {
			try {
				Object obj = f.get(source);
				if (obj != null) {
					setQField(target, f, obj);
				}
			} catch (Exception e) {
				logger.warn("{}", e.getMessage());
			}
		}
		return target;
	}

	public static Object clone(Object src) {
		if (src instanceof Cloneable) {
			Class<?> clazz = src.getClass();
			Method m;
			try {
				m = clazz.getMethod("clone", (Class[]) null);
				return m.invoke(src, (Object[])null);
			} catch (Exception ex) {
				// NOP
			}
		}
		// XXX:是否会递归调用？
		return dup(src, null);
	}
	@SuppressWarnings("unchecked")
	public static <T> T dup(T source, T target, String... attrs) {
		// 同类对象复制
		Class<?> cls = source.getClass();
		
		if (target == null) {
			try {
				target = (T) cls.newInstance();
			} catch (InstantiationException e) {
			} catch (IllegalAccessException e) {
			}
		}

		if (attrs.length == 0) {
			return dupAll(source, target);
		}

		Map<String, Field> beanff = BeanContextFactory.getFiledsA(cls);

		// CachedBeans
		for (String attr : attrs) {
			Field f = beanff.get(attr);
			if (f == null) {
				continue;
			}
			Object val;
			try {
				val = f.get(source);
				setField(target, f, val);
			} catch (Exception e) {
				logger.error("", e);
			}
		}
		return target;
	}

	/**
	 * 克隆一个类，并把指定的属性去除。
	 * 
	 * @param source
	 * @param target
	 * @param attrs
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T dupEx(T source, T target, String... attrs) {
		// 同类对象复制
		if (attrs.length == 0) {
			return dupAll(source, target);
		}

		Class<T> cls = (Class<T>) source.getClass();
		if (target == null) {
			try {
				target = cls.newInstance();
			} catch (InstantiationException e1) {
			} catch (IllegalAccessException e1) {
			}
		}

		toUpperCase(attrs);
		Set<String> attrSet = new HashSet<>(Arrays.asList(attrs));

		Map<String, Field> beanFields = BeanContextFactory.getFiledsA(cls);
		for (Entry<String, Field> e : beanFields.entrySet()) {
			// 如果包含的话，SKIP
			if (attrSet.contains(e.getKey())) {
				continue;
			}
			Field f = e.getValue();
			Object val;
			try {
				val = f.get(source);
				if (val == null) {
					continue;
				}
				setField(target, f, val);
			} catch (Exception e1) {
				logger.warn("", e1);
			}
		}
		return target;
	}
	private static void toUpperCase(String s[]) {
		for (int i = 0; i < s.length; i++)
			s[i] = s[i].toUpperCase();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void setQField(Object target, Field field, Object value)
			throws IllegalArgumentException, IllegalAccessException {
		// 空值也处理？
		if (value == null) {
			if (field.getType().isPrimitive() //
					|| Modifier.isFinal(field.getModifiers())) {
				return;
			}
			field.set(target, value);
			return;
		}

		// 如果是final
		if (Modifier.isFinal(field.getModifiers())) {
			Object targetVal = field.get(target);
			if (Collection.class.isAssignableFrom(field.getType())) {
				((Collection) targetVal).addAll((Collection) value);
			} else if (Map.class.isAssignableFrom(field.getType())) {
				((Map) targetVal).putAll((Map) value);
			} else {
				logger.warn("无法复制的类型。{}", field.getType());
			}
		} else {
			field.set(target, value);
		}
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void setField(Object target, //
			Field field, //
			Object value) throws IllegalArgumentException, IllegalAccessException {
		if (value == null) {
			if (field.getType().isPrimitive() //
					|| Modifier.isFinal(field.getModifiers())) {
				return;
			}

			field.set(target, value);
		} else if (Modifier.isFinal(field.getModifiers())) {
			Object targetVal = field.get(target);
			if (Collection.class.isAssignableFrom(field.getType())) {
				((Collection) targetVal).addAll((Collection) value);
			} else if (Map.class.isAssignableFrom(field.getType())) {
				((Map) targetVal).putAll((Map) value);
			}
		} else {
			Class<?> type = field.getType();
			if (type.isAssignableFrom(value.getClass())) {
				field.set(target, value);
			} else {
				field.set(target, TypeConverter.toType(value, type));
			}
		}
	}
	
}

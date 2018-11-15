package com.wangxiao.utils.bean;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wangxiao.utils.TypeConverter;

public class BeanDup {
	static Logger logger = LoggerFactory.getLogger(BeanDup.class);

	static Object dupAll(Object source, Object target) {
		if (source == null || target == null) {
			logger.warn("BeanDup source or target object is null!");
			return null;
		}
		if (source.getClass().equals(target.getClass())) {
			return BeanClone.dupAll(source, target);
		}

		BeanContext srcInfo = BeanContextFactory.register(source.getClass());
		BeanContext dstInfo = BeanContextFactory.register(target.getClass());

		Map<String, PropertyDescriptor> dstDescs = dstInfo.getDescriptorsA();
		Map<String, PropertyDescriptor> srcDescs = srcInfo.getDescriptorsA();

		for (Entry<String, PropertyDescriptor> e : dstDescs.entrySet()) {
			PropertyDescriptor dstDesc = e.getValue();
			Method mw = dstDesc.getWriteMethod();
			PropertyDescriptor srcDesc = null;
			Method mr = ((srcDesc = srcDescs.get(e.getKey())) == null) ? null : srcDesc
					.getReadMethod();

			if (mw == null || mr == null) {
				continue;
			}
			try {
				Object value = mr.invoke(source);
				if (value != null) {
					setProperty(mw, target, value);
				}
			} catch (Exception e1) {
				logger.error("", e1);
			}
		}
		return target;
	}


	public static Object dup(Object source, Object target, String... attrs) {
		if (source == null || target == null) {
			throw new RuntimeException("");
		}
		if (attrs.length == 0) {
			return dupAll(source, target);
		}
		
		if (source.getClass().equals(target.getClass())) {
			return BeanClone.dup(source, target, attrs);
		}
		
		BeanContext srcInfo = BeanContextFactory.register(source.getClass());
		BeanContext dstInfo = BeanContextFactory.register(target.getClass());

		Map<String, PropertyDescriptor> dstDescs = dstInfo.getDescriptorsA();
		Map<String, PropertyDescriptor> srcDescs = srcInfo.getDescriptorsA();

		for (String attr : attrs) {
			PropertyDescriptor srcPd = srcDescs.get(attr);
			PropertyDescriptor dstPd = dstDescs.get(attr);
			if (srcPd == null || dstPd == null) {
				logger.warn("属性{}不存在！", attr);
				continue;
			}
			Method mread = srcPd.getReadMethod();
			Method wread = dstPd.getWriteMethod();
			if (mread == null || wread == null) {
				logger.warn("setter/getter()方法{}不存在！", attr);
				continue;
			}
			try {
				Object val = mread.invoke(source);
				if (val!=null){
					setProperty(wread, target, val);
				}
			} catch (Exception e) {
				logger.error("", e);
			}
		}
		return target;
	}

	/**
	 * 复制非空字段，并排除指字的属性
	 * 
	 * @param source
	 *            源对象
	 * @param target
	 *            目标对象
	 * @param ignoreAttrs
	 *            排除属性
	 */
	public static Object dupEx(Object source, Object target, String... ignoreAttrs) {
		if (source.getClass().equals(target.getClass())) {
			return BeanClone.dupEx(source, target, ignoreAttrs);
		}
		
		if (ignoreAttrs.length == 0) {
			return dupAll(source, target);
		}

		BeanContext srcInfo = BeanContextFactory.register(source.getClass());
		BeanContext dstInfo = BeanContextFactory.register(target.getClass());

		Map<String, PropertyDescriptor> dstDescs = dstInfo.getDescriptorsA();
		Map<String, PropertyDescriptor> srcDescs = srcInfo.getDescriptorsA();

		toUpperCase(ignoreAttrs);
		Set<String> igrSet = new HashSet<>(Arrays.asList(ignoreAttrs));

		for (Entry<String, PropertyDescriptor> e : dstDescs.entrySet()) {
			Method wb = e.getValue().getWriteMethod();
			if (igrSet.contains(e.getKey()) || wb == null) {
				continue;
			}
			PropertyDescriptor srcPd = srcDescs.get(e.getKey());
			if (srcPd == null || srcPd.getReadMethod() == null) {
				continue;
			}

			try {
				Method readMethod = srcPd.getReadMethod();
				Object value = readMethod.invoke(source);
				if (value!=null){
					setProperty(wb, target, value);
				}
			} catch (Exception ex) {
				logger.warn("{}",ex.getMessage());
			}
		}
		return target;
	}
	private static void setProperty(Method m, Object target, Object value)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		assert m != null;
		Class<?> type = m.getParameterTypes()[0];

		if (value == null) {
			if (!type.isPrimitive()) {
				m.invoke(target, new Object[] { null });
			}
			return;
		}
		if (!type.isAssignableFrom(value.getClass())) {
			value = TypeConverter.toType(value, type);
		}
		if (value != null) {
			m.invoke(target, value);
		}
	}	
	private static void toUpperCase(String s[]) {
		for (int i = 0; i < s.length; i++)
			s[i] = s[i].toUpperCase();
	}

}

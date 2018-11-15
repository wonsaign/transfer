package com.wangxiao.utils.bean;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class BeanContextFactory {

	private final static Map<Class<?>, BeanContext> contexts = new ConcurrentHashMap<>();

	private BeanContextFactory(){}
	
	public static BeanContext register(Class<?> type) {
		BeanContext info = contexts.get(type);
		if (info == null) {
			info = new BeanContext(type);
			contexts.put(type, info);
		}
		return info;
	}

	public static Map<String, PropertyDescriptor> getDescriptorsA(Class<?> type) {
		BeanContext beanInfo = contexts.get(type);
		if (beanInfo == null) {
			beanInfo = register(type);
		}
		return beanInfo.getDescriptorsA();
	}

	public static Map<String, PropertyDescriptor> getDescriptors(Class<?> type) {
		BeanContext beanInfo = contexts.get(type);
		if (beanInfo == null) {
			beanInfo = register(type);
		}
		return beanInfo.getDescriptors();
	}

	public static Map<String, Field> getFiledsA(Class<?> type) {
		BeanContext beanInfo = contexts.get(type);
		if (beanInfo == null) {
			beanInfo = register(type);
		}
		return beanInfo.getFieldsA();
	}

	public static Map<String, Field> getFileds(Class<?> type) {
		BeanContext beanInfo = contexts.get(type);
		if (beanInfo == null) {
			beanInfo = register(type);
		}
		return beanInfo.getFields();
	}
}

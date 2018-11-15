package com.wangxiao.utils.bean;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class BeanContext {
	private final static Logger logger = LoggerFactory.getLogger(BeanContext.class);

	private final Map<String, PropertyDescriptor> descriptors = new HashMap<>();
	private final Map<String, PropertyDescriptor> descriptorsA = createMap(PropertyDescriptor.class);
	private final Map<String, Field> fields = new HashMap<>();
	private final Map<String, Field> fieldsA = createMap(Field.class);

	private final Class<?> type;

	BeanContext(Class<?> cls) {
		this.type = cls;
		init();
	}

	public Map<String, PropertyDescriptor> getDescriptors() {
		return this.descriptors;
	}

	public PropertyDescriptor getDescriptor(String key) {
		return this.descriptors.get(key);
	}

	public Map<String, PropertyDescriptor> getDescriptorsA() {
		return this.descriptorsA;
	}

	public PropertyDescriptor getDescriptorA(String key) {
		return this.descriptorsA.get(key);
	}

	public Map<String, Field> getFields() {
		return this.fields;
	}

	public Field getField(String key) {
		return this.fields.get(key);
	}

	public Map<String, Field> getFieldsA() {
		return this.fieldsA;
	}

	public Field getFieldA(String key) {
		return this.fieldsA.get(key);
	}

	public Collection<Field> fields() {
		return fields.values();
	}

	public Collection<PropertyDescriptor> propertyDescriptors() {
		return this.descriptors.values();
	}

	public Class<?> type() {
		return type;
	}

	private static <T> Map<String, T> createMap(Class<T> cls) {
		final Map<String, T> myMap = new HashMap<String, T>() {
			private static final long serialVersionUID = 1L;

			@Override
			public T get(Object key) {
				return super.get(((String) key).toUpperCase());
			}

			@Override
			public T put(String key, T desc) {
				if (key == null || desc == null) {
					return null;
				}
				return super.put(key.toUpperCase(), desc);
			}

			@Override
			public boolean containsKey(Object key) {
				String k = ((String) key).toUpperCase();
				return super.containsKey(k);
			}
		};
		return myMap;
	}

	private void init() {
		BeanInfo beanInfo;
		try {
			Class<?> t = type;
			while (t != Object.class) {
				Field[] ff = t.getDeclaredFields();
				for (Field f : ff) {
					f.setAccessible(true);
					String name = f.getName();
					Class<?> type = f.getType();

					if (Modifier.isStatic(f.getModifiers())//
							|| Modifier.isTransient(f.getModifiers())) {
						continue;
					}
					if (Modifier.isFinal(f.getModifiers())) {
						if (Collection.class.isAssignableFrom(type) //
								|| Map.class.isAssignableFrom(type)) {
							this.fields.put(name, f);
							this.fieldsA.put(name, f);
						}
					} else {
						fields.put(name, f);
						fieldsA.put(name, f);
					}
				}
				t = t.getSuperclass();
			}

			beanInfo = Introspector.getBeanInfo(type);
			PropertyDescriptor[] descs = beanInfo.getPropertyDescriptors();

			for (PropertyDescriptor desc : descs) {
				if (desc.getName().equals("class")//
						&& desc.getWriteMethod() == null) {

					continue;
				}
				descriptors.put(desc.getName(), desc);
				descriptorsA.put(desc.getName(), desc);
			}
		} catch (Exception e) {
			logger.error("注册类：{}错误！", type.getName(), e);
		}
	}

	@Override
	public int hashCode() {
		return type.getName().hashCode();
	}

	@Override
	public boolean equals(Object b) {
		if (this == b) {
			return true;
		}
		if (b == null || !(b instanceof BeanContext)) {
			return false;
		}
		return this.type.equals(((BeanContext) b).type);
	}
}

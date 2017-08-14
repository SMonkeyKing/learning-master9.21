package com.zju.utils.lang;


import com.zju.utils.Lang;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Map工具类
 * 
 *
 * 
 */
public class Maps {

	private Maps() {
	}

	/**
	 * 返回原Map的忽略健大小写的只读Map
	 * 
	 * @param source
	 * @return
	 */
	public static <K, V> Map<K, V> caseInsensitiveMap(final Map<K, V> source) {
		final Map<String, V> lowerCaseMap = new LinkedHashMap<String, V>();
		for (Object key : source.keySet()) {
			if (key != null && key instanceof String) {
				lowerCaseMap.put(toLowercase(key), source.get(key));
			}
		}
		return new AbstractMap<K, V>() {
			public Set<Entry<K, V>> entrySet() {
				return source.entrySet();
			}

			public boolean containsKey(Object key) {
				if (key != null && key instanceof String) {
					return lowerCaseMap.containsKey(toLowercase(key));
				}
				return source.containsKey(key);
			}

			public V get(Object key) {
				if (key != null && key instanceof String) {
					return lowerCaseMap.get(toLowercase(key));
				}
				return source.get(key);
			}

			public V put(K key, V value) {
				if (key != null && key instanceof String) {
					String lowercaseKey = toLowercase(key);
					source.put(key, value);
					return lowerCaseMap.put(lowercaseKey, value);
				}
				return source.put(key, value);
			}

		};
	}

	private static String toLowercase(Object key) {
		return key.toString().toLowerCase();
	}

	/**
	 * 将对象转换为层叠的Map
	 * 
	 * @param obj
	 * @return
	 */
	public static Object toMap(Object obj) {
		return toMap(obj, new ConcurrentHashMap<Integer, Object>());
	}

	private static Object toMap(Object obj, Map<Integer, Object> hashMap) {
		if (obj == null) {
			return obj;
		}
		if (Lang.isBaseType(obj.getClass())) {
			return obj;
		}
		Integer hash = System.identityHashCode(obj);
		Object map = hashMap.get(hash);
		if (map != null) {
			return map;
		}
		if (obj instanceof List<?>) {
			List<Object> newList = Lang.newList();
			for (Object o : (List<?>) obj) {
				newList.add(toMap(o, hashMap));
			}
			hashMap.put(hash, newList);
			return newList;
		}
		if (obj instanceof Set<?>) {
			Set<Object> newSet = Lang.newSet();
			for (Object o : (Set<?>) obj) {
				newSet.add(toMap(o, hashMap));
			}
			hashMap.put(hash, newSet);
			return newSet;
		}
		if (obj instanceof Map<?, ?>) {
			Map<Object, Object> newMap = Lang.newMap();
			for (Object key : ((Map<?, ?>) obj).keySet()) {
				newMap.put(toMap(key, hashMap),
						toMap(((Map<?, ?>) obj).get(key), hashMap));
			}
			hashMap.put(hash, newMap);
			return newMap;
		}
		try {
			Map<Object, Object> newMap = Lang.newMap();
			PropertyDescriptor[] propertyDescriptors = Mirrors
					.getPropertys(obj.getClass());
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				if ("class".equals(propertyDescriptor.getName())) {
					continue;
				}
				Method readMethod = propertyDescriptor.getReadMethod();
				if (readMethod != null) {
					Object value = readMethod.invoke(obj);
					newMap.put(propertyDescriptor.getName(),
							toMap(value, hashMap));
				}
			}
			hashMap.put(hash, newMap);
			return newMap;
		} catch (Exception e) {
			throw Lang.unchecked(e);
		}
	}

}

package com.zju.utils.convert.converters.map;


import com.zju.utils.Lang;
import com.zju.utils.convert.Converter;
import com.zju.utils.lang.Proxys;
import org.springframework.cglib.beans.BeanMap;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;

public class ObjectToMapConverter implements Converter {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object convert(Object from, Class<?> toType, Object... args) {
		final Map original = from instanceof Map ? (Map) from : BeanMap
				.create(from);
		if (toType.isInterface() || Modifier.isAbstract(toType.getModifiers())) {
			return Proxys.newProxyInstance(new InvocationHandler() {
				public Object invoke(Object proxy, Method method, Object[] args)
						throws Throwable {
					return method.invoke(original, args);
				}
			}, toType);
		} else {
			try {
				Map map = (Map) toType.newInstance();
				map.putAll(original);
				return map;
			} catch (InstantiationException e) {
				throw Lang.unchecked(e);
			} catch (IllegalAccessException e) {
				throw Lang.unchecked(e);
			}

		}
	}

}

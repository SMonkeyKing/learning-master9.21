package com.zju.utils.convert.converters.primitive;


import com.zju.utils.Lang;
import com.zju.utils.convert.Converter;
import com.zju.utils.convert.Converters;

public class ObjectToBooleanConverter implements Converter {

	public Object convert(Object from, Class<?> toType, Object... args) {
		String string = Converters.BASE.convert(from, String.class);
		return Boolean.valueOf(string);
	}

}

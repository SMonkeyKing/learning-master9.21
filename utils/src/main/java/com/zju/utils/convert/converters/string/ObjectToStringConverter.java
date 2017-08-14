package com.zju.utils.convert.converters.string;


import com.zju.utils.convert.Converter;

public class ObjectToStringConverter implements Converter {

	public Object convert(Object from, Class<?> toType, Object... args) {
		return String.valueOf(from);
	}

}

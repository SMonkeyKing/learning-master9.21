package com.zju.utils.convert.converters.primitive;

import com.zju.utils.Lang;
import com.zju.utils.convert.Converter;
import com.zju.utils.convert.Converters;
import com.zju.utils.lang.Classes;


public class ObjectToClassConverter implements Converter {

	public Object convert(Object from, Class<?> toType, Object... args) {
		return Classes.forName(from.toString());
	}

}

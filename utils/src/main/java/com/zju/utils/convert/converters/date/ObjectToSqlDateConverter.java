package com.zju.utils.convert.converters.date;


import com.zju.utils.convert.Converter;
import com.zju.utils.convert.Converters;

import java.util.Date;

public class ObjectToSqlDateConverter implements Converter {

	public Object convert(Object from, Class<?> toType, Object... args) {
		return new java.sql.Date(Converters.BASE
				.convert(from, Date.class, args).getTime());
	}

}

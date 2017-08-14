package com.zju.utils.convert.converters.date;


import com.zju.utils.convert.Converter;
import com.zju.utils.convert.Converters;

import java.sql.Timestamp;
import java.util.Date;

public class ObjectToTimestampConverter implements Converter {

	public Object convert(Object from, Class<?> toType, Object... args) {
		return new Timestamp(Converters.BASE.convert(from, Date.class, args)
				.getTime());
	}

}

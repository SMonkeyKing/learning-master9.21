package com.zju.utils.convert.converters.primitive;

import com.zju.utils.Lang;
import com.zju.utils.convert.Converter;
import com.zju.utils.convert.Converters;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

public class ObjectToLongConverter implements Converter {

	public Object convert(Object from, Class<?> toType, Object... args) {
		String string = Converters.BASE.convert(from, String.class);
		if (args != null && args.length != 0 && args[0] != null) {
			NumberFormat format;
			if (args[0] instanceof NumberFormat) {
				format = (NumberFormat) args[0];
			} else {
				format = new DecimalFormat(args[0].toString());
			}
			try {
				return ((Number) format.parseObject(string)).longValue();
			} catch (ParseException e) {
				throw Lang.unchecked(e);
			}
		}
		return Long.valueOf(string);
	}

}

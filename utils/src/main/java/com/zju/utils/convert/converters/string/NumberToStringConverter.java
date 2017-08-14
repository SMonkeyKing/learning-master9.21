package com.zju.utils.convert.converters.string;


import com.zju.utils.Lang;
import com.zju.utils.convert.Converter;
import com.zju.utils.convert.Converters;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

public class NumberToStringConverter implements Converter {

	public Object convert(Object from, Class<?> toType, Object... args) {
		if (args != null && args.length > 0 && args[0] != null) {
			if (args[0] instanceof String) {
				NumberFormat format = new DecimalFormat(args[0].toString());
				return format.format(from);
			}
			if (args[0] instanceof NumberFormat) {
				NumberFormat format = (NumberFormat) args[0];
				return format.format(from);
			}
		}
		return from.toString();
	}

}

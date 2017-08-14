package com.zju.utils.convert.converters.string;




import com.zju.utils.convert.Converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateToStringConverter implements Converter {

	public Object convert(Object from, Class<?> toType, Object... args) {
		if (args != null && args.length > 0 && args[0] != null) {
			if (args[0] instanceof String) {
				SimpleDateFormat format = new SimpleDateFormat(
						args[0].toString());
				return format.format((Date) from);
			}
			if (args[0] instanceof DateFormat) {
				DateFormat format = (DateFormat) args[0];
				return format.format((Date) from);
			}
		}
		return from.toString();
	}

}

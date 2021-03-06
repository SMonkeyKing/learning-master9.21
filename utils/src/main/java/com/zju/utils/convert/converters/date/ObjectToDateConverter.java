package com.zju.utils.convert.converters.date;

import com.zju.utils.Lang;
import com.zju.utils.convert.Converter;
import com.zju.utils.convert.Converters;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ObjectToDateConverter implements Converter {

	public Object convert(Object from, Class<?> toType, Object... args) {
		if (from instanceof Number) {
			long time = ((Number) from).longValue();
			return new Date(time);
		}
		String string = Converters.BASE.convert(from, String.class);
		if (args != null && args.length != 0 && args[0] != null) {
			DateFormat format;
			if (args[0] instanceof DateFormat) {
				format = (DateFormat) args[0];
			} else {
				format = new SimpleDateFormat(args[0].toString());
			}
			try {
				return format.parseObject(string);
			} catch (ParseException e) {
				throw Lang.unchecked(e);
			}
		}
		string = string.replaceAll("[^0-9]+", "");
		if ("".equals(string)) {
			return null;
		}
		String format = "yyyyMMddHHmmssSSS";
		DateFormat dateFormat = new SimpleDateFormat(format);
		string = StringUtils.rightPad(string, format.length(), '0').substring(
				0, format.length());
		try {
			return dateFormat.parseObject(string);
		} catch (ParseException e) {
			throw Lang.unchecked(e);
		}
	}

}

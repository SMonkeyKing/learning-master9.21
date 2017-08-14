package com.zju.utils.convert.converters.string;

import com.zju.utils.Lang;
import com.zju.utils.convert.Converter;
import com.zju.utils.convert.Converters;
import org.apache.commons.io.IOUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;

public class ClobToStringConverter implements Converter {

	public Object convert(Object from, Class<?> toType, Object... args) {
		Clob clob = (Clob) from;
		try {
			return IOUtils.toString(clob.getCharacterStream());
		} catch (IOException e) {
			throw Lang.unchecked(e);
		} catch (SQLException e) {
			throw Lang.unchecked(e);
		}
	}

}

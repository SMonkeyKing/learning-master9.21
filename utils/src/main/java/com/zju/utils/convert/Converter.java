package com.zju.utils.convert;

public interface Converter {
	Object convert(Object from, Class<?> toType, Object... args);
}

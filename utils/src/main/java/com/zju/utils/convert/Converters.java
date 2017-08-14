package com.zju.utils.convert;

import com.zju.utils.Lang;
import com.zju.utils.convert.converters.array.BlobToByteArrayConverter;
import com.zju.utils.convert.converters.array.CollectionToArrayConverter;
import com.zju.utils.convert.converters.date.ObjectToDateConverter;
import com.zju.utils.convert.converters.date.ObjectToSqlDateConverter;
import com.zju.utils.convert.converters.date.ObjectToTimestampConverter;
import com.zju.utils.convert.converters.map.ObjectToMapConverter;
import com.zju.utils.convert.converters.primitive.*;
import com.zju.utils.convert.converters.string.ClobToStringConverter;
import com.zju.utils.convert.converters.string.DateToStringConverter;
import com.zju.utils.convert.converters.string.NumberToStringConverter;
import com.zju.utils.convert.converters.string.ObjectToStringConverter;
import com.zju.utils.lang.Mirrors;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.collections.map.LRUMap;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.*;

/**
 * 转换工具类
 * 
 *
 *
 */
public class Converters {

	/**
	 * 创建带有匹配缓存的转换器
	 * @param cache
	 * @return
	 */
	public static Converters create(int cache) {
		return new Converters(cache);
	}

	/**
	 * 创建带有默认匹配缓存的转换器（默认缓存为1024）
	 * @return
	 */
	public static Converters create() {
		return create(1024);
	}

	/**
	 * 转换元数据
	 * 
	 *
	 *
	 */
	class ConverterMeta {
		Class<?> fromType;
		Class<?> toType;
		Converter converter;
	}

	/**
	 * 匹配到的转换器
	 */
	LRUMap matchConverterMap;

	/**
	 * 转换元列表
	 */
	List<ConverterMeta> converterMetas = Lang.newList();

	private Converters(int cache) {
		matchConverterMap = new LRUMap(cache);
	}

	/**
	 * 添加一个转换器
	 * @param fromType
	 * @param toType
	 * @param converter
	 * @param <S>
	 * @param <T>
	 * @return
	 */
	public <S, T> Converters add(Class<S> fromType, Class<T> toType,
			Converter converter) {
		ConverterMeta converterMeta = new ConverterMeta();
		converterMeta.fromType = fromType;
		converterMeta.toType = toType;
		converterMeta.converter = converter;
		converterMetas.add(converterMeta);
		return this;
	}

	/**
	 * 转换一个对象到另一类型
	 * 
	 * @param from
	 *            源对象
	 * @param toType
	 *            目标类型
	 * @param args
	 *            转换器需要的额外参数
	 * @return 目标类型对象
	 */

	public <S, T> T convert(S from, Class<T> toType, Object... args) {
		if (from == null) {
			return null;
		}
		if (toType.isPrimitive()) {
			toType = (Class<T>) MethodUtils.getPrimitiveWrapper(toType);
		}
		if (toType.isAssignableFrom(from.getClass())) {
			return (T) from;
		}
		Class<S> fromType = (Class<S>) from.getClass();
		int key = Arrays.hashCode(new Object[] { fromType, toType });
		ConverterMeta matchConverterMeta = (ConverterMeta) matchConverterMap
				.get(key);
		if (matchConverterMeta == null) {
			float cost = Float.MAX_VALUE;
			for (ConverterMeta converterMeta : converterMetas) {
				float converterCost = Mirrors.getTotalTransformationCost(
						new Class<?>[] { fromType, converterMeta.toType },
						new Class<?>[] { converterMeta.fromType, toType });
				if (converterCost == -1) {
					continue;
				}
				if (converterCost < cost) {
					cost = converterCost;
					matchConverterMeta = converterMeta;
				}
			}
			if (matchConverterMeta == null) {
				throw new IllegalArgumentException(String.format(
						"Coudn't convert object %s to type %s", from, toType));
			}
			matchConverterMap.put(key, matchConverterMeta);
		}

		Converter converter = matchConverterMeta.converter;
		return (T) converter.convert(from, matchConverterMeta.toType, args);
	}

	/**
	 * 转换一个对象到另一类型
	 * 
	 * @param from
	 *            源对象
	 * @param toType
	 *            目标类型
	 * @return 目标类型对象
	 */
	public <S, T> T convert(S from, Class<T> toType) {
		return convert(from, toType, Lang.EMPTY_ARRAY);
	}

	/**
	 * 锁定转换规则
	 */
	public void lock() {
		this.converterMetas = Collections.unmodifiableList(this.converterMetas);
	}

	/**
	 * 从别的转换器工具继承转换规则
	 * 
	 * @param other
	 * @return
	 */
	public Converters extend(Converters other) {
		this.converterMetas.addAll(other.converterMetas);
		return this;
	}

	/**
	 * 默认转换器，自带有大量的默认转换功能
	 */
	public static final Converters BASE = create();
	static {
		// 输出字符串
		BASE.add(Object.class, String.class, new ObjectToStringConverter());
		BASE.add(Date.class, String.class, new DateToStringConverter());
		BASE.add(Number.class, String.class, new NumberToStringConverter());
		BASE.add(Clob.class, String.class, new ClobToStringConverter());
		// 输出基础类型
		BASE.add(Object.class, Byte.class, new ObjectToByteConverter());
		BASE.add(Object.class, Short.class, new ObjectToShortConverter());
		BASE.add(Object.class, Integer.class, new ObjectToIntegerConverter());
		BASE.add(Object.class, Long.class, new ObjectToLongConverter());
		BASE.add(Object.class, Float.class, new ObjectToFloatConverter());
		BASE.add(Object.class, Double.class, new ObjectToDoubleConverter());
		BASE.add(Object.class, Boolean.class, new ObjectToBooleanConverter());
		BASE.add(Object.class, Character.class,
				new ObjectToCharacterConverter());
		BASE.add(Object.class, BigInteger.class,
				new ObjectToBigIntegerConverter());
		BASE.add(Object.class, BigDecimal.class,
				new ObjectToBigDecimalConverter());
		BASE.add(Object.class, Class.class, new ObjectToClassConverter());
		// 输出时间
		BASE.add(Object.class, Date.class, new ObjectToDateConverter());
		BASE.add(Object.class, java.sql.Date.class,
				new ObjectToSqlDateConverter());
		BASE.add(Object.class, Timestamp.class,
				new ObjectToTimestampConverter());
		// 输出数组
		BASE.add(Blob.class, byte[].class, new BlobToByteArrayConverter());
		// 集合转换为常规类型数组
		BASE.add(Collection.class, byte[].class,
				new CollectionToArrayConverter());
		BASE.add(Collection.class, short[].class,
				new CollectionToArrayConverter());
		BASE.add(Collection.class, int[].class,
				new CollectionToArrayConverter());
		BASE.add(Collection.class, long[].class,
				new CollectionToArrayConverter());
		BASE.add(Collection.class, float[].class,
				new CollectionToArrayConverter());
		BASE.add(Collection.class, double[].class,
				new CollectionToArrayConverter());
		BASE.add(Collection.class, boolean[].class,
				new CollectionToArrayConverter());
		BASE.add(Collection.class, char[].class,
				new CollectionToArrayConverter());
		BASE.add(Collection.class, Byte[].class,
				new CollectionToArrayConverter());
		BASE.add(Collection.class, Short[].class,
				new CollectionToArrayConverter());
		BASE.add(Collection.class, Integer[].class,
				new CollectionToArrayConverter());
		BASE.add(Collection.class, Long[].class,
				new CollectionToArrayConverter());
		BASE.add(Collection.class, Float[].class,
				new CollectionToArrayConverter());
		BASE.add(Collection.class, Double[].class,
				new CollectionToArrayConverter());
		BASE.add(Collection.class, Boolean[].class,
				new CollectionToArrayConverter());
		BASE.add(Collection.class, Character[].class,
				new CollectionToArrayConverter());
		BASE.add(Collection.class, BigDecimal[].class,
				new CollectionToArrayConverter());
		BASE.add(Collection.class, BigInteger[].class,
				new CollectionToArrayConverter());
		BASE.add(Collection.class, String[].class,
				new CollectionToArrayConverter());
		BASE.add(Collection.class, Date[].class,
				new CollectionToArrayConverter());
		BASE.add(Collection.class, Class[].class,
				new CollectionToArrayConverter());
		// 输出Map
		BASE.add(Object.class, Map.class, new ObjectToMapConverter());
		BASE.lock();
	}

}

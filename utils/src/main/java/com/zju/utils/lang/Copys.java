package com.zju.utils.lang;

import com.zju.utils.Lang;
import com.zju.utils.convert.Converter;
import com.zju.utils.convert.Converters;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 拷贝器
 * 
 *
 *
 */
public class Copys {

	private Copys() {

	}

	public static Copys create() {
		return new Copys();
	}

	/**
	 * 包含字段
	 */
	Set<String> includes = new HashSet<String>();

	/**
	 * 排除字段
	 */
	Set<String> excludes = new HashSet<String>();

	/**
	 * 映射字段
	 */
	Map<String, String> matchMap = Lang.newMap();
	/**
	 * 映射字段默认值
	 */
	Map<String, Object> matchDefaultValueMap = Lang.newMap();
	/**
	 * 是否大小写敏感，默认否
	 * 
	 * @return
	 */
	boolean caseSensitive = false;

	/**
	 * 是否包含null值属性
	 * 
	 * @return
	 */
	boolean includeNull = false;

	/**
	 * 是否包含null值属性
	 * 
	 * @return
	 */
	boolean includeEmpty = false;

	/**
	 * 是否包含基类型的0
	 * 
	 * @return
	 */
	boolean includePrimitiveZero = false;

	/**
	 * 是否包含基类型的false
	 * 
	 * @return
	 */
	boolean includePrimitiveFalse = false;

	/**
	 * 数据来源的bean
	 */
	Object from;

	/**
	 * 数据目标bean
	 */
	List<Object> toList;

	/**
	 * 转换器
	 */
	Converters converters = Converters.create().extend(Converters.BASE);

	/**
	 * 新增转换器
	 * 
	 * @param fromType
	 *            源类型
	 * @param toType
	 *            目标类型
	 * @param converter
	 *            转换器
	 * @return
	 */
	public <S, T> Copys converter(Class<S> fromType, Class<T> toType,
			Converter converter) {
		converters.add(fromType, toType, converter);
		return this;
	}

	/**
	 * 指定要拷贝的属性名映射关系，不指定默认映射
	 * 
	 * @param fromName
	 * @param toName
	 * @param defaultValue
	 *            默认值，如果源对象没有值的话，则将目标对象的字段设置为该默认值
	 * @return
	 */
	public Copys match(String fromName, String toName, Object defaultValue) {
		matchMap.put(fromName, toName);
		matchDefaultValueMap.put(toName, defaultValue);
		return this;
	}

	/**
	 * 指定要拷贝的属性名映射关系，不指定默认映射
	 * 
	 * @param fromName
	 * @param toName
	 * @return
	 */
	public Copys match(String fromName, String toName) {
		return match(fromName, toName, null);
	}

	/**
	 * 包含null字段<br>
	 * copier.includeNull();
	 * 
	 * @return 复印机对象本身
	 */
	public Copys includeNull() {
		this.includeNull = true;
		return this;
	}

	/**
	 * 排除null字段<br>
	 * copier.excludeNull();
	 * 
	 * @return 复印机对象本身
	 */
	public Copys excludeNull() {
		this.includeNull = false;
		return this;
	}

	/**
	 * 排除空对象字段<br>
	 * copier.includeEmpty();
	 * 
	 * @return 复印机对象本身
	 */
	public Copys includeEmpty() {
		this.includeEmpty = true;
		return this;
	}

	/**
	 * 排除空对象字段<br>
	 * copier.excludeEmpty();
	 * 
	 * @return 复印机对象本身
	 */
	public Copys excludeEmpty() {
		this.includeEmpty = false;
		return this;
	}

	/**
	 * 包含基类型的false
	 * 
	 * @return
	 */
	public Copys includePrimitiveFalse() {
		this.includePrimitiveFalse = true;
		return this;
	}

	/**
	 * 排除基类型的false
	 * 
	 * @return
	 */
	public Copys excludePrimitiveFalse() {
		this.includePrimitiveFalse = false;
		return this;
	}

	/**
	 * 包含基类型的0
	 * 
	 * @return
	 */
	public Copys includePrimitiveZero() {
		this.includePrimitiveZero = true;
		return this;
	}

	/**
	 * 排除基类型的0
	 * 
	 * @return
	 */
	public Copys excludePrimitiveZero() {
		this.includePrimitiveZero = false;
		return this;
	}

	/**
	 * 字段名大小写敏感
	 * 
	 * @return
	 */
	public Copys caseSensitive() {
		this.caseSensitive = true;
		return this;
	}

	/**
	 * 字段名忽略大小写
	 * 
	 * @return
	 */
	public Copys caseInsensitive() {
		this.caseSensitive = false;
		return this;
	}

	/**
	 * 指定包含的字段名称<br>
	 * 
	 * <pre>
	 * Copier copier = Lang.newCopier();
	 * copier.includes(&quot;name&quot;);
	 * </pre>
	 * 
	 * @param names
	 *            字段名
	 * @return 复印机对象本身
	 */
	public Copys includes(String... names) {
		for (String name : names) {
			if (name == null) {
				continue;
			}
			name = name.trim();
			this.includes.add(name);
			this.excludes.remove(name);
		}
		return this;
	}

	/**
	 * 指定排除的字段名称<br>
	 * 
	 * <pre>
	 * Copier copier = Lang.newCopier();
	 * copier.excludes(&quot;name&quot;);
	 * </pre>
	 * 
	 * @param names
	 *            字段名
	 * @return 复印机对象本身
	 */
	public Copys excludes(String... names) {
		for (String name : names) {
			if (name == null) {
				continue;
			}
			name = name.trim();
			this.excludes.add(name);
			this.includes.remove(name);
		}
		return this;
	}

	public Copys from(Object from) {
		this.from = from;
		done();
		return this;
	}

	public Copys to(Object... tos) {
		toList = Lang.newList();
		for (Object to : tos) {
			if (to != null) {
				toList.add(to);
			}
		}
		done();
		return this;
	}

	/**
	 * 清除内含对象，必须重新调用from和to方法才能再次拷贝数据
	 * 
	 * @return
	 */
	public Copys clear() {
		this.from = null;
		this.toList = null;
		return this;
	}

	/**
	 * 完成拷贝
	 */
	private void done() {
		if (from == null || toList == null || toList.isEmpty()) {
			return;
		}
		Map<String, PropertyDescriptor> fromPropertyMap = caseSensitive ? Mirrors
				.getPropertyMap(from.getClass()) : Maps
				.caseInsensitiveMap(Mirrors.getPropertyMap(from.getClass()));
		for (Object to : toList) {
			if (to == null) {
				continue;
			}
			Map<String, PropertyDescriptor> toPropertyMap = caseSensitive ? Mirrors
					.getPropertyMap(to.getClass()) : Maps
					.caseInsensitiveMap(Mirrors.getPropertyMap(to.getClass()));
			for (String fromName : fromPropertyMap.keySet()) {
				if (!includes.isEmpty() && !includes.contains(fromName)) {
					continue;
				}
				if (excludes.contains(fromName)) {
					continue;
				}
				String toName = matchMap.get(fromName);
				// 目标属性默认是源属性
				toName = toName == null ? fromName : toName;

				PropertyDescriptor toProperty = toPropertyMap.get(toName);
				if (toProperty == null) {
					continue;
				}
				Method toWriteMethod = toProperty.getWriteMethod();
				if (toWriteMethod == null) {
					continue;
				}
				PropertyDescriptor fromProperty = fromPropertyMap.get(fromName);
				Method fromReadMethod = fromProperty.getReadMethod();
				if (fromReadMethod == null) {
					continue;
				}
				try {
					Object fromValue = fromReadMethod.invoke(from);
					// 从映射取目标默认值
					fromValue = fromValue != null ? fromValue
							: matchDefaultValueMap.get(toName);

					if (fromValue == null && !includeNull) {
						continue;
					}
					if (fromValue != null && Lang.isEmpty(fromValue)
							&& !includeEmpty) {
						continue;
					}
					if (fromValue != null
							&& fromProperty.getPropertyType().isPrimitive()) {
						if (Lang.equals(fromValue, false)
								&& !includePrimitiveFalse) {
							continue;
						}
						if (Lang.equals(fromValue, 0) && !includePrimitiveZero) {
							continue;
						}
					}
					Object toValue = fromValue == null ? null : converters
							.convert(fromValue, toProperty.getPropertyType());
					toWriteMethod.invoke(to, toValue);
				} catch (Throwable e) {
					throw Lang
							.unchecked(
									e,
									"Couldn't copy property %s with object %s to property %s with object %s",
									fromName,
									Lang.isBaseType(from.getClass()) ? from
											: ToStringBuilder
													.reflectionToString(from),
									toName,
									Lang.isBaseType(to.getClass()) ? to
											: ToStringBuilder
													.reflectionToString(to));
				}
			}
		}
	}
}

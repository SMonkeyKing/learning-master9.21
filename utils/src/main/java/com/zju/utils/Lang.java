package com.zju.utils;




import com.zju.utils.convert.Converters;
import com.zju.utils.lang.Callback;
import com.zju.utils.lang.Mirrors;
import com.zju.utils.lang.OID;
import com.zju.utils.log.Log;
import com.zju.utils.log.Logs;

import java.beans.PropertyDescriptor;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Java通用工具类
 * 
 *
 * 
 */
public class Lang {

	private static final String DATE_FORMAT_YYYYMMDDHHMMSS_SSS = "yyyyMMddHHmmssSSS";

	/**
	 * 固定日志器，类似System.out
	 */
	public static final Log log = Logs.getLog();

	private Lang() {
	}

	/**
	 * 空对象
	 */
	public final static Object EMPTY = new Object();
	/**
	 * 空数组
	 */
	public final static Object[] EMPTY_ARRAY = new Object[] {};

	public static String formattedDecimalToPercentage(double decimal)
	{
		//获取格式化对象
		NumberFormat nt = NumberFormat.getPercentInstance();
		//设置百分数精确度2即保留两位小数
		nt.setMinimumFractionDigits(2);
		return nt.format(decimal);
	}

	/**
	 * 获取对象系统引用哈希值（不为负数）
	 * 
	 * @param x
	 * @return
	 */
	public static long identityHashCode(Object x) {
		return (long) System.identityHashCode(x) + (long) Integer.MAX_VALUE;
	}

	/**
	 * 将UUID转换为22位字符串，依据Base64编码（URL安全）
	 * 
	 * @return
	 */
	public static String id() {
		OID oid = new OID();
		return oid.toString();
	}

	/**
	 * 将CheckedException转换为RuntimeException.
	 */
	public static RuntimeException unchecked(Throwable e) {
		if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		} else {
			return new RuntimeException(e);
		}
	}

	/**
	 * 将CheckedException转换为RuntimeException.
	 */
	public static RuntimeException unchecked(Throwable e, String message,
			Object... args) {
		return new RuntimeException(String.format(message, args), e);
	}

	/**
	 * 判断一个对象是否是空对象
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		if (obj instanceof CharSequence) {
			return obj.toString().trim().length() == 0;
		}
		if (obj.getClass().equals(Object.class)) {
			return true;
		}
		if (isBaseType(obj.getClass())) {
			return false;
		}
		if (obj instanceof Map) {
			return ((Map) obj).isEmpty();
		}
		if (obj instanceof Collection) {
			return ((Collection) obj).isEmpty();
		}
		if (obj.getClass().isArray()) {
			return Array.getLength(obj) == 0;
		}
		return Object.class.equals(obj.getClass());
	}

	/**
	 * 判断一个类型是否是基本类型
	 * 
	 * @param type
	 * @return
	 */
	public static boolean isBaseType(Class<?> type) {
		if (type.isPrimitive()) {
			return true;
		}
		if (CharSequence.class.isAssignableFrom(type)) {
			return true;
		}
		if (Number.class.isAssignableFrom(type)) {
			return true;
		}
		if (Date.class.isAssignableFrom(type)) {
			return true;
		}
		if (Boolean.class.equals(type)) {
			return true;
		}
		if (Character.class.equals(type)) {
			return true;
		}
		if (Class.class.equals(type)) {
			return true;
		}
		if (StringBuilder.class.equals(type)) {
			return true;
		}
		if (StringBuffer.class.equals(type)) {
			return true;
		}
		if (Object.class.equals(type)) {
			return true;
		}
        return Void.class.equals(type);
    }

	/**
	 * 判断是否是数字类型
	 * 
	 * @param type
	 * @return
	 */
	public static boolean isNumber(Class<?> type) {
		if (Number.class.isAssignableFrom(type)) {
			return true;
		}
		if (type.equals(int.class)) {
			return true;
		}
		if (type.equals(short.class)) {
			return true;
		}
		if (type.equals(long.class)) {
			return true;
		}
		if (type.equals(float.class)) {
			return true;
		}
		if (type.equals(double.class)) {
			return true;
		}
        return type.equals(byte.class);
    }

	/**
	 * 获得本源异常信息
	 * 
	 * @param e
	 * @return
	 */
	public static Throwable getCause(Throwable e) {
		return e.getCause() == null ? e : getCause(e.getCause());
	}

	/**
	 * 输出对象字符串格式
	 * 
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj) {
		return toString(obj, null);
	}

	/**
	 * 输出对象字符串格式
	 * 
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj, String format) {
		if (obj == null) {
			return "null";
		}
		if (obj instanceof Throwable) {
			Throwable throwable = (Throwable) obj;
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			throwable.printStackTrace(pw);
			pw.flush();
			pw.close();
			sw.flush();
			return sw.toString();
		}
		if (obj instanceof Date) {
			return new SimpleDateFormat(
					format == null || format.trim().length() == 0 ? DATE_FORMAT_YYYYMMDDHHMMSS_SSS
							: format).format((Date) obj);
		}
		if (isNumber(obj.getClass())) {
			if (format != null && format.trim().length() != 0) {
				return new DecimalFormat(format).format(obj);
			}
		}
		return String.valueOf(obj);
	}

	/**
	 * 尝试调用，有一个成功则返回
	 * 
	 * @param callbacks
	 * @return
	 */
	public static <T> T tryCall(Callback<T>... callbacks) {
		List<Throwable> throwables = new ArrayList<Throwable>();
		for (Callback<T> callback : callbacks) {
			try {
				return callback.execute();
			} catch (Throwable e) {
				throwables.add(e);
				continue;
			}
		}
		throw new RuntimeException(String.format(
				"Try call failure,the errors is:\r\n%s", throwables));
	}

	/**
	 * 将一个对象转换为指定类型
	 * 
	 * @param value
	 * @param type
	 * @return
	 */
	public static <T> T convert(Object value, Class<T> type) {
		return convert(value, type, null);
	}

	/**
	 * 将一个对象转换为指定类型
	 * 
	 * @param value
	 * @param type
	 * @return
	 */
	public static <T> T convert(Object value, Class<T> type, String format) {
		return Converters.BASE.convert(value, type, format);
	}

	/**
	 * 新建一个Set
	 * 
	 * @param args
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> Set<T> newSet(T... args) {
		int length = args == null ? 1 : args.length;
		Set<T> set = new HashSet<T>(length);
		if (args == null) {
			set.add(null);
		} else {
			for (int i = 0; i < args.length; i++) {
				set.add(args[i]);
			}
		}
		return set;
	}

	/**
	 * 新建一个List
	 * 
	 * @param args
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> newList(T... args) {
		int length = args == null ? 1 : args.length;
		List<T> list = new ArrayList<T>(length);
		if (args == null) {
			list.add(null);
		} else {
			for (int i = 0; i < args.length; i++) {
				list.add(args[i]);
			}
		}
		return list;
	}

	/**
	 * 抛出一个带消息的异常
	 * 
	 * @param type
	 * @param message
	 * @param args
	 * @return
	 */
	public static <T extends Throwable> T newThrowable(Class<T> type,
			String message, Object... args) {
		try {
			return type.getConstructor(String.class).newInstance(
					String.format(message, args));
		} catch (InstantiationException e) {
			throw Lang.unchecked(e, message, args);
		} catch (IllegalAccessException e) {
			throw Lang.unchecked(e, message, args);
		} catch (IllegalArgumentException e) {
			throw Lang.unchecked(e, message, args);
		} catch (InvocationTargetException e) {
			throw Lang.unchecked(e, message, args);
		} catch (NoSuchMethodException e) {
			throw Lang.unchecked(e, message, args);
		} catch (SecurityException e) {
			throw Lang.unchecked(e, message, args);
		}
	}

	/**
	 * 抛出一个带消息的运行时异常
	 * 
	 * @param message
	 * @param args
	 * @return
	 */
	public static IllegalStateException newThrowable(String message,
			Object... args) {
		return newThrowable(IllegalStateException.class, message, args);
	}

	/**
	 * 新建一个Map，必须是偶数个参数
	 * 
	 * @param args
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> newMap(Object... args) {
		Map<K, V> map = new HashMap<K, V>();
		if (args != null) {
			if (args.length % 2 != 0) {
				throw new IllegalArgumentException(
						"The number of arguments must be an even number");
			}
			for (int i = 0; i < args.length; i += 2) {
				map.put((K) args[i], (V) args[i + 1]);
			}
		}
		return map;
	}

	/**
	 * 生成一个固定容量的LRU策略的Map
	 * 
	 * @param capacity
	 *            容量
	 * @param args
	 *            参数列表，通newMap
	 * @return
	 */

	public static <K, V> Map<K, V> newLRUMap(final int capacity, Object... args) {
		Map<K, V> map = newMap(args);
		return new LinkedHashMap<K, V>(map){
			/**
			 * 
			 */
			private static final long serialVersionUID = -5820354698308020916L;

			/**
			 * Returns <tt>true</tt> if this map should remove its eldest entry.
			 * This method is invoked by <tt>put</tt> and <tt>putAll</tt> after
			 * inserting a new entry into the map.  It provides the implementor
			 * with the opportunity to remove the eldest entry each time a new one
			 * is added.  This is useful if the map represents a cache: it allows
			 * the map to reduce memory consumption by deleting stale entries.
			 *
			 * <p>Sample use: this override will allow the map to grow up to 100
			 * entries and then delete the eldest entry each time a new entry is
			 * added, maintaining a steady state of 100 entries.
			 * <pre>
			 *     private static final int MAX_ENTRIES = 100;
			 *
			 *     protected boolean removeEldestEntry(Map.Entry eldest) {
			 *        return size() > MAX_ENTRIES;
			 *     }
			 * </pre>
			 *
			 * <p>This method typically does not modify the map in any way,
			 * instead allowing the map to modify itself as directed by its
			 * return value.  It <i>is</i> permitted for this method to modify
			 * the map directly, but if it does so, it <i>must</i> return
			 * <tt>false</tt> (indicating that the map should not attempt any
			 * further modification).  The effects of returning <tt>true</tt>
			 * after modifying the map from within this method are unspecified.
			 *
			 * <p>This implementation merely returns <tt>false</tt> (so that this
			 * map acts like a normal map - the eldest element is never removed).
			 *
			 * @param    eldest The least recently inserted entry in the map, or if
			 *           this is an access-ordered map, the least recently accessed
			 *           entry.  This is the entry that will be removed it this
			 *           method returns <tt>true</tt>.  If the map was empty prior
			 *           to the <tt>put</tt> or <tt>putAll</tt> invocation resulting
			 *           in this invocation, this will be the entry that was just
			 *           inserted; in other words, if the map contains a single
			 *           entry, the eldest entry is also the newest.
			 * @return   <tt>true</tt> if the eldest entry should be removed
			 *           from the map; <tt>false</tt> if it should be retained.
			 */
			protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
				return size() > capacity;
			}
		};
	}

	/**
	 * 比较两个对象是否相同，对于数字、日期等按照大小进行比较，自动兼容包装器实例
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean equals(Object a, Object b) {
		if (a == b) {
			return true;
		}
		if (a == null || b == null) {
			return false;
		}
		if (a.equals(b)) {
			return true;
		}
		// 比较大数字
		if (isNumber(a.getClass()) && isNumber(b.getClass())) {
			return new BigDecimal(a.toString()).compareTo(new BigDecimal(b
					.toString())) == 0;
		}
		// 比较日期
		if (a instanceof Date && b instanceof Date) {
			return ((Date) a).compareTo((Date) b) == 0;
		}
		return false;
	}

	/**
	 * 计时执行
	 * 
	 * @return 返回runnable的执行时间
	 */
	public static long timing(Runnable runnable) {
		long begin = System.currentTimeMillis();
		try {
			runnable.run();
			return System.currentTimeMillis() - begin;
		} catch (Throwable e) {
			throw unchecked(e);
		}
	}

	/**
	 * 浅层克隆，如果传入对象的类型实现了Cloneable接口，则优先调用对象的clone方法
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T clone(T obj) {
		if (obj == null) {
			return null;
		}
		if (obj instanceof Cloneable) {
			try {
				return (T) obj.getClass().getMethod("clone").invoke(obj);
			} catch (NoSuchMethodException e) {

			} catch (SecurityException e) {
				throw Lang.unchecked(e);
			} catch (IllegalAccessException e) {
				throw Lang.unchecked(e);
			} catch (IllegalArgumentException e) {
				throw Lang.unchecked(e);
			} catch (InvocationTargetException e) {
				throw Lang.unchecked(e);
			}
		}
		try {
			T newObject;
			if (obj.getClass().isArray()) {
				int length = Array.getLength(obj);
				newObject = (T) Array.newInstance(obj.getClass()
						.getComponentType(), length);
				for (int i = 0; i < length; i++) {
					Array.set(newObject, i, Array.get(obj, i));
				}
			} else {
				newObject = (T) obj.getClass().newInstance();
				if (obj instanceof Collection) {
					((Collection<Object>) newObject)
							.addAll((Collection<Object>) obj);
				} else {
					PropertyDescriptor[] propertyDescriptors = Mirrors
							.getPropertys(obj.getClass());
					for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
						Method readMethod = propertyDescriptor.getReadMethod();
						if (readMethod == null) {
							continue;
						}
						Method writeMethod = propertyDescriptor
								.getWriteMethod();
						if (writeMethod == null) {
							continue;
						}
						writeMethod.invoke(newObject, readMethod.invoke(obj));
					}
				}
			}
			return newObject;
		} catch (Exception e) {
			throw Lang.unchecked(e);
		}
	}

	/**
	 * 判断是否为真，不为真则抛出异常
	 * 
	 * @param flag
	 *            真假标志位
	 * @param message
	 *            消息体，可带格式，将使用String.format进行格式化
	 * @param args
	 *            格式化参数，可为空
	 */
	public static void isTrue(boolean flag, String message, Object... args) {
		if (!flag) {
			throw new IllegalArgumentException(String.format(message, args));
		}
	}

	/**
	 * 判断是否非null，为null则抛出异常
	 * 
	 * @param object
	 *            要判断的对象
	 * @param message
	 *            消息体，可带格式，将使用String.format进行格式化
	 * @param args
	 *            格式化参数，可为空
	 */
	public static void notNull(Object object, String message, Object... args) {
		isTrue(object != null, message, args);
	}

	/**
	 * 判断是否非空，为空则抛出异常
	 * 
	 * @param object
	 *            要判断的对象
	 * @param message
	 *            消息体，可带格式，将使用String.format进行格式化
	 * @param args
	 *            格式化参数，可为空
	 */
	public static void notEmpty(Object object, String message, Object... args) {
		isTrue(!Lang.isEmpty(object), message, args);
	}

	public static void main(String[] args) {
		System.out.println(BigDecimal.valueOf(0).equals(0));
	}

	/**
	 * 获取最初的消息异常
	 * 
	 * @param e
	 * @return
	 */
	public static Throwable getMessageCause(Throwable e) {
		while (e != null && e.getMessage() == null && e.getCause() != null) {
			e = e.getCause();
		}
		return e;
	}
	
	
	public static String generateDynamic(int len) {
		boolean isDigit = false;
		boolean isLetter = false;
		final int maxNum = 36;
		int m = 0;
		StringBuffer pwd = null;
		while(!isDigit || !isLetter){
			isDigit = false;
			isLetter = false;
			pwd = new StringBuffer("");
			int i; // 生成的随机数
			int count=0;
			 char[] str = { '4', 'b', 'c', '8', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
			 'l', '6', 'n', 'o', 'p', 'q', 'r', 's', '0', 'u', 'v', 'w',
			 '1', 'y', 'z', 't', 'x', '2', '3', 'a', '5', 'm', '7', 'd', '9' };

			Random r = new Random();
			while (count < len) {
				// 生成随机数，取绝对值，防止生成负数，
				// 生成的数最大为36-1

				i = Math.abs(r.nextInt(maxNum));
				if (i >= 0 && i < str.length) {
					pwd.append(str[i]);
					count++;
				}
			} 
			
			for(int j=0 ; j<pwd.toString().length() ; j++){ //循环遍历字符串
                 if(Character.isDigit(pwd.toString().charAt(j))){     //用char包装类中的判断数字的方法判断每一个字符
                     isDigit = true;
                 }
                 if(Character.isLetter(pwd.toString().charAt(j))){   //用char包装类中的判断字母的方法判断每一个字符
                     isLetter = true;
                 }
             }
			m++;
			log.info("--------------"+"第"+m+"次生成密码："+pwd.toString()+"--------------------");
		}

		return pwd.toString();
	}
	
	public static BigDecimal fourCutFiveIn(double d , int decimalPlace){
		BigDecimal bigDecimal = new BigDecimal(d);
		return bigDecimal.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
	}
	
	public static BigDecimal fourCutFiveIn(BigDecimal bigDecimal , int decimalPlace){
		return bigDecimal.setScale(decimalPlace , BigDecimal.ROUND_HALF_UP);
	}

}

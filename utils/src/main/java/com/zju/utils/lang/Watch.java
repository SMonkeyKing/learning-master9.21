package com.zju.utils.lang;

/**
 * 计时器
 * 
 *
 * 
 */
public class Watch {
	/**
	 * 线程时间持有池
	 */
	private final static ThreadLocal<Long> TIME = new ThreadLocal<Long>();

	/**
	 * 开始计时方法
	 * 
	 * @return
	 */
	public static void start() {
		long time = System.currentTimeMillis();
		TIME.set(time);
	}

	/**
	 * 计时，返回从开始方法调用时算起的毫秒数
	 * 
	 * @return
	 */
	public static long timing() {
		Long time = TIME.get();
		if (time == null) {
			throw new IllegalStateException(
					"Please call the start method first");
		}
		return System.currentTimeMillis() - time;
	}

	/**
	 * 重置计时，返回从开始方法调用时算起的毫秒数
	 * 
	 * @return
	 */
	public static long reset() {
		Long time = TIME.get();
		if (time == null) {
			throw new IllegalStateException(
					"Please call the start method first");
		}
		Long now = System.currentTimeMillis();
		TIME.set(now);
		return now - time;
	}

	/**
	 * 终止计时，并返回从开始方法调用时算起的毫秒数
	 * 
	 * @return
	 */
	public static long end() {
		Long time = TIME.get();
		TIME.remove();
		if (time == null) {
			throw new IllegalStateException(
					"Please call the start method first");
		}
		return System.currentTimeMillis() - time;
	}
}

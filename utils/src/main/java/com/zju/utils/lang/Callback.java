package com.zju.utils.lang;

/**
 * 回调接口
 * 
 *
 * 
 * @param <T>
 */
public interface Callback<T> {
	T execute() throws Throwable;
}

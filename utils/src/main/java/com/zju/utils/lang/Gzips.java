package com.zju.utils.lang;

import com.zju.utils.io.UnsafeByteArrayInputStream;
import com.zju.utils.io.UnsafeByteArrayOutputStream;
import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Gzip压缩工具
 * 
 *
 * 
 */
public class Gzips {

	/**
	 * 将字节流经过gzip压缩后返回
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static byte[] gzip(byte[] in) throws IOException {
		UnsafeByteArrayOutputStream byteArrayOutputStream = new UnsafeByteArrayOutputStream();
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
				byteArrayOutputStream);
		GZIPOutputStream gzipOutputStream = new GZIPOutputStream(
				bufferedOutputStream);
		try {
			gzipOutputStream.write(in);
			gzipOutputStream.finish();
			gzipOutputStream.flush();
			bufferedOutputStream.flush();
		} finally {
			gzipOutputStream.close();
			bufferedOutputStream.close();
		}
		return byteArrayOutputStream.toByteArray();
	}

	/**
	 * 将字节流经过gzip解压缩后返回
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static byte[] ungzip(byte[] in) throws IOException {
		UnsafeByteArrayInputStream byteArrayInputStream = new UnsafeByteArrayInputStream(
				in);
		BufferedInputStream bufferedInputStream = new BufferedInputStream(
				byteArrayInputStream);
		GZIPInputStream gzipInputStream = new GZIPInputStream(
				bufferedInputStream);
		UnsafeByteArrayOutputStream byteArrayOutputStream = new UnsafeByteArrayOutputStream();
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
				byteArrayOutputStream);
		try {
			IOUtils.copy(gzipInputStream, bufferedOutputStream);
			bufferedOutputStream.flush();
			byteArrayOutputStream.flush();
		} finally {
			gzipInputStream.close();
			bufferedInputStream.close();
			bufferedOutputStream.close();
		}
		return byteArrayOutputStream.toByteArray();
	}
}

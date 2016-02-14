package com.domker.androidtoy.webp;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

import android.graphics.Bitmap;
import com.google.webp.libwebp;

/** 
 * WebP格式的一些方法
 * 
 * @ClassName: WebPFactory 
 * @author wanlipeng
 * @date 2016年2月14日 下午5:36:12  
 */
public final class WebPUtil {
	static {
		System.loadLibrary("webp");
	}

	public static Bitmap webpToBitmap(byte[] encoded) {

		int[] width = new int[] { 0 };
		int[] height = new int[] { 0 };
		byte[] decoded = libwebp.WebPDecodeARGB(encoded, encoded.length, width,
				height);

		int[] pixels = new int[decoded.length / 4];
		ByteBuffer.wrap(decoded).asIntBuffer().get(pixels);

		return Bitmap.createBitmap(pixels, width[0], height[0],
				Bitmap.Config.ARGB_8888);

	}

	/** 
	 * 字节流换转为字节
	 * 
	 * @param in
	 * @return byte[] 
	 */
	public static byte[] streamToBytes(InputStream in) {
		ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
		byte[] buffer = new byte[1024];
		int len = -1;
		try {
			while ((len = in.read(buffer)) >= 0) {
				out.write(buffer, 0, len);
				out.flush();
			}
		} catch (java.io.IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return out.toByteArray();
	}

	/** 
	 * 判断是否是webp格式
	 * 
	 * @param data
	 * @return boolean 
	 */
	public static boolean isWebp(byte[] data) {
		return data != null && data.length > 12 && data[0] == 'R'
				&& data[1] == 'I' && data[2] == 'F' && data[3] == 'F'
				&& data[8] == 'W' && data[9] == 'E' && data[10] == 'B'
				&& data[11] == 'P';
	}
}

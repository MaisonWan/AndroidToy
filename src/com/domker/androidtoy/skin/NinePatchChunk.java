package com.domker.androidtoy.skin;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import android.graphics.Rect;

public class NinePatchChunk {

	public static final int NO_COLOR = 0x00000001;
	public static final int TRANSPARENT_COLOR = 0x00000000;

	public final Rect mPaddings = new Rect();

	public int mDivX[];
	public int mDivY[];
	public int mColor[];

	private static void readIntArray(final int[] data, final ByteBuffer buffer) {
		for (int i = 0, n = data.length; i < n; ++i) {
			data[i] = buffer.getInt();
		}
	}

	private static void checkDivCount(final int length) {
		if (length == 0 || (length & 0x01) != 0)
			throw new RuntimeException("invalid nine-patch: " + length);
	}
	
	public static byte[] getNinePatchChunk(int[] xDivs, int[] yDivs) {
		byte numXDivs = (byte) (xDivs.length & 0xFF);
		byte numYDivs = (byte) (yDivs.length & 0xFF);
		byte numColors = numXDivs;
		int paddingLeft = 10;
		int offset = 0;
		numColors = (byte) ((numColors + 1) * (numYDivs + 1));
		int size = 32 + (numXDivs + numYDivs + numColors) * 4;
		byte chunk[] = new byte[size];
		chunk[0] = 1;
		chunk[1] = numXDivs;
		chunk[2] = numYDivs;
		chunk[3] = numColors;
		byte pVal = 0;
		// 以下8位无用
		chunk[4] = pVal;
		chunk[5] = pVal;
		chunk[6] = pVal;
		chunk[7] = pVal;

		chunk[8] = pVal;
		chunk[9] = pVal;
		chunk[10] = pVal;
		chunk[11] = pVal;
		
		// 下面是上下左右间隔，然后还有4个无用字节
		offset = 12;
		putInt(chunk, offset, paddingLeft);
		offset += 4;
		putInt(chunk, offset, 10);
		offset += 4;
		putInt(chunk, offset, 10);
		offset += 4;
		putInt(chunk, offset, 12);
		offset += 4;
		putInt(chunk, offset, 0);
		offset += 4;
		offset = 32;

		// X方向的区域
		for (int i = 0; i < xDivs.length; i++) {
			putInt(chunk, offset, xDivs[i]);
			offset += 4;
		}
		// X方向的区域
		for (int i = 0; i < yDivs.length; i++) {
			putInt(chunk, offset, yDivs[i]);
			offset += 4;
		}
		// 颜色
		int color = 0x00000F;
		int color2 = 0xFFFFF0;
		int colorInc = (color2 - color) / numColors;
		for (int i = 0; i < numColors; i++) {
			putInt(chunk, offset, NO_COLOR);//0x2F000000 | color);
			offset += 4;
//			color += colorInc;
		}
		return chunk;
	}

	private static void putInt(byte array[], int offset, int val) {
		array[offset] = (byte) (val & 0xFF);
		val = val >> 8;
		array[offset + 1] = (byte) (val & 0xFF);
		val = val >> 8;
		array[offset + 2] = (byte) (val & 0xFF);
		val = val >> 8;
		array[offset + 3] = (byte) (val & 0xFF);
	}
	
	public static NinePatchChunk deserialize(final byte[] data) {
		final ByteBuffer byteBuffer = ByteBuffer.wrap(data).order(
				ByteOrder.nativeOrder());

		// 判断是否是NinePatch
		if (byteBuffer.get() == 0)
			return null; // is not serialized

		final NinePatchChunk chunk = new NinePatchChunk();
		chunk.mDivX = new int[byteBuffer.get()];
		chunk.mDivY = new int[byteBuffer.get()];
		chunk.mColor = new int[byteBuffer.get()];

		checkDivCount(chunk.mDivX.length);
		checkDivCount(chunk.mDivY.length);

		// skip 8 bytes
		byteBuffer.getInt();
		byteBuffer.getInt();

		chunk.mPaddings.left = byteBuffer.getInt();
		chunk.mPaddings.right = byteBuffer.getInt();
		chunk.mPaddings.top = byteBuffer.getInt();
		chunk.mPaddings.bottom = byteBuffer.getInt();

		// skip 4 bytes
		byteBuffer.getInt();

		readIntArray(chunk.mDivX, byteBuffer);
		readIntArray(chunk.mDivY, byteBuffer);
		readIntArray(chunk.mColor, byteBuffer);

		return chunk;
	}
	private class Res_png_9patch {
	    byte wasDeserialized;
	    byte numXDivs;
	    byte numYDivs;
	    byte numColors;
	     // These tell where the next section of a patch starts.
	    // For example, the first patch includes the pixels from
	    // 0 to xDivs[0]-1 and the second patch includes the pixels
	    // from xDivs[0] to xDivs[1]-1.
	    // Note: allocation/free of these pointers is left to the caller.
	    int[] xDivs;
	    int[] yDivs;
	    int paddingLeft, paddingRight;
	    int paddingTop, paddingBottom;
	    // Note: allocation/free of this pointer is left to the caller.
	    int[] colors;   
	}
}
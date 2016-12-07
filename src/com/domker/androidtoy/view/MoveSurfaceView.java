/**
 * @file MoveSurfaceView.java
 * @Package com.tencent.test
 * Description: TODO 
 * 
 * @author maisonwan
 * @since 1.0.0.0
 * @version 1.0.0.0
 * @date 2013-11-27
 * 
 *       \if TOSPLATFORM_CONFIDENTIAL_PROPRIETARY
 *       ================================
 *            \n Copyright (c) 2011
 *       maisonwan. All Rights Reserved.\n \n
 *       ==================================
 *       ==========================================\n \n Update History\n \n
 *       Author (Name[WorkID]) | Modification | Tracked Id | Description\n
 *       --------------------- | ------------ | ---------- |
 *       ------------------------\n maisonwan | 2013-11-27 | <xxxxxxxx>
 *       | Initial Created.\n \n \endif
 * 
 *       <tt>
 * \n
 * Release History:\n
 * \n
 * Author (Name[WorkID]) | ModifyDate | Version | Description \n
 * --------------------- | ---------- | ------- | -----------------------------\n
 * maisonwan |2013-11-27 | 1.0.0.0 | Initial created. \n
 * \n
 * </tt>
 */
package com.domker.androidtoy.view;

import com.domker.androidtoy.R;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader.TileMode;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

//=============================================================================
//CLASS DEFINITIONS
//=============================================================================
/**
 * TODO
 * 
 * @author maisonwan
 * @date 2013-11-27 下午6:16:04
 * 
 */
public class MoveSurfaceView extends SurfaceView implements Callback {

	private LoopThread thread;

	// -----------------------------------------------------------------------
	/**
	 * TODO
	 * 
	 * @param context
	 */
	public MoveSurfaceView(Context context) {
		super(context);
		this.init();
	}

	private void init() {
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);
		thread = new LoopThread(holder, getContext());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.view.SurfaceHolder.Callback#surfaceCreated(android.view.SurfaceHolder
	 * )
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		this.thread.isRunning = true;
		this.thread.start();
		BitmapFactory.decodeResource(getResources(), R.drawable.bottom_button_down);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.view.SurfaceHolder.Callback#surfaceChanged(android.view.SurfaceHolder
	 * , int, int, int)
	 */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.SurfaceHolder.Callback#surfaceDestroyed(android.view.
	 * SurfaceHolder)
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		this.thread.isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 执行绘制的绘制线程
	 * 
	 * @author Administrator
	 * 
	 */
	class LoopThread extends Thread {

		SurfaceHolder surfaceHolder;
		Context context;
		boolean isRunning;
		float radius = 10f;
		int step = 3;
		Paint paint;

		public LoopThread(SurfaceHolder surfaceHolder, Context context) {

			this.surfaceHolder = surfaceHolder;
			this.context = context;
			isRunning = false;

			paint = new Paint();
			paint.setColor(Color.YELLOW);
			paint.setStyle(Paint.Style.FILL);
		}

		@Override
		public void run() {

			Canvas c = null;

			while (isRunning) {

				try {
					synchronized (surfaceHolder) {

						c = surfaceHolder.lockCanvas(null);
						if (c != null) {
							doDraw(c);
						}
						// 通过它来控制帧数执行一次绘制后休息50ms
						Thread.sleep(50);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					try {
						surfaceHolder.unlockCanvasAndPost(c);
					} catch(Exception e) {
						
					}
				}
			}
		}

		public void doDraw(Canvas c) {
			
			RadialGradient shader = new RadialGradient(0, 0, radius, new int[] {
					Color.YELLOW, Color.YELLOW & 0xFFFFFF | 0x99000000,
					Color.YELLOW & 0xFFFFFF | 0x22000000 }, new float[] { 0,
					0.6f, 1.0f }, TileMode.CLAMP);
			paint.setShader(shader);
			
			// 这个很重要，清屏操作，清楚掉上次绘制的残留图像
			c.drawColor(Color.BLACK);

			c.translate(getWidth() / 2, getHeight() / 2);
			c.drawCircle(0, 0, radius, paint);
			radius += step;
			if (radius > 100) {
				step = -step;
			} else if(radius < 10) {
				step = -step;
			}
		}
	}
}

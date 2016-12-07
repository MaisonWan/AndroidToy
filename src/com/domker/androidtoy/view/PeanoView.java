package com.domker.androidtoy.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 绘制皮亚诺曲线
 * 
 * @author wanlipeng 2016年12月7日
 */
public class PeanoView extends View {
	private Paint paint = null;
	private boolean hasStart = false;
	private int level = 1;
	private int color = Color.BLACK;
	private int startX = 0;
	private int startY = 0;

	public PeanoView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public PeanoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public PeanoView(Context context) {
		super(context);
		init();
	}

	private void init() {
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(color);
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getLineWidth() {
		return (int) paint.getStrokeWidth();
	}

	public void setLineWidth(int lineWidth) {
		this.paint.setStrokeWidth(lineWidth);
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
		this.paint.setColor(color);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int min = Math.min(widthMeasureSpec, heightMeasureSpec);
		super.onMeasure(min, min);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		hasStart = false;
		drawPeano(canvas, level, true, 0, 0, getWidth(), getHeight());
	}

	private void drawPeano(Canvas c, int level, boolean clockWise, int x,
			int y, int dx, int dy) {
		int width = dx - x;
		int height = dy - y;
		int offset = clockWise ? 1 : -1;
		if (level == 1) {
			drawLine(c, x + width / 4, y + height / 4);
			drawLine(c, x + (2 + offset) * width / 4, y + (2 - offset) * height
					/ 4);
			drawLine(c, x + 3 * width / 4, y + 3 * height / 4);
			drawLine(c, x + (2 - offset) * width / 4, y + (2 + offset) * height
					/ 4);
			return;
		}
		if (clockWise) { // 顺时针
			drawPeano(c, level - 1, !clockWise, x, y, (x + dx) / 2,
					(y + dy) / 2);
			drawPeano(c, level - 1, clockWise, (x + dx) / 2, y, dx,
					(y + dy) / 2);
			drawPeano(c, level - 1, clockWise, (x + dx) / 2, (y + dy) / 2, dx,
					dy);
			drawPeano(c, level - 1, !clockWise, (x + dx) / 2, dy, x,
					(y + dy) / 2);
		} else {
			drawPeano(c, level - 1, !clockWise, x, y, (x + dx) / 2,
					(y + dy) / 2);
			drawPeano(c, level - 1, clockWise, x, (y + dy) / 2, (x + dx) / 2,
					dy);
			drawPeano(c, level - 1, clockWise, (x + dx) / 2, (y + dy) / 2, dx,
					dy);
			drawPeano(c, level - 1, !clockWise, dx, (y + dy) / 2, (x + dx) / 2,
					y);
		}
	}

	private void drawLine(Canvas c, int x, int y) {
		if (!hasStart) {
			hasStart = true;
		} else {
			c.drawLine(startX, startY, x, y, paint);
		}
		startX = x;
		startY = y;
	}
}
